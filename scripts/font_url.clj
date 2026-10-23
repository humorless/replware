;; Regenerate the subset Google Fonts URLs for Noto Serif TC.
;; Each subset covers printable ASCII plus every character used in h1/h2/h3.
;; The two homepages share one URL; each guides/ page gets its own, so adding
;; a guide page never grows the homepage font download.
(ns font-url
  (:require [babashka.fs :as fs]
            [clojure.string :as str]))

(def public-dir "resources/public")

(defn page-groups []
  (cons [(fs/file public-dir "index.html")
         (fs/file public-dir "en" "index.html")]
        (->> (fs/glob (fs/file public-dir "guides") "*.html")
             (map fs/file)
             sort
             (map vector))))

(defn heading-text [html]
  (->> (re-seq #"(?s)<h[123][^>]*>(.*?)</h[123]>" html)
       (map second)
       (map #(-> %
                 (str/replace #"<[^>]+>" "")
                 (str/replace "&amp;" "&")))
       (apply str)))

(defn subset-chars [pages]
  (let [ascii (map char (range 32 127))
        cjk   (->> pages
                   (map (comp heading-text slurp))
                   (apply str)
                   (remove #(Character/isWhitespace ^char %)))]
    (->> (concat ascii cjk) distinct (sort-by int) (apply str))))

(defn font-url [chars]
  (str "https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@700&display=swap&text="
       ;; URLEncoder is form encoding; adjust to RFC 3986 to match the existing URLs
       (-> (java.net.URLEncoder/encode ^String chars "UTF-8")
           (str/replace "+" "%20")
           (str/replace "*" "%2A")
           (str/replace "%7E" "~"))))

(def link-re
  #"(<link rel=\"stylesheet\" href=\")(?:https://fonts\.googleapis\.com/css2\?family=Noto\+Serif\+TC[^\"]*|FONT_URL_PLACEHOLDER)(\">)")

(defn -main []
  (doseq [pages (page-groups)
          :let  [url (font-url (subset-chars pages))]
          f     pages]
    (let [html    (slurp f)
          updated (str/replace html link-re
                               (fn [[_ pre post]] (str pre url post)))]
      (spit f updated)
      (println (str (fs/relativize public-dir f)) "| url length:" (count url)))))

(-main)
