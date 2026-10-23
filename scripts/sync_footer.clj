;; Copy the homepage footer into every guides/ page.
;; index.html is the single source; relative links are rewritten for pages
;; one directory down. Safe to rerun: output is the same every time.
(ns sync-footer
  (:require [babashka.fs :as fs]
            [clojure.string :as str]))

(def public-dir "resources/public")

(def footer-re #"(?s)<footer class=\"site-footer\">.*?</footer>")

(defn for-guides
  "Rewrite the homepage footer's relative URLs for a page in guides/."
  [footer]
  (-> footer
      (str/replace "src=\"assets/" "src=\"../assets/")
      (str/replace "href=\"#" "href=\"../index.html#")
      (str/replace "href=\"guides/" "href=\"")
      (str/replace "href=\"en/\"" "href=\"../en/\"")))

(defn -main []
  (let [footer (->> (fs/file public-dir "index.html")
                    slurp
                    (re-find footer-re)
                    for-guides)]
    (doseq [f (sort (map fs/file (fs/glob (fs/file public-dir "guides") "*.html")))]
      (let [html    (slurp f)
            updated (str/replace html footer-re (fn [_] footer))]
        (spit f updated)
        (println (str (fs/relativize public-dir f))
                 (if (= html updated) "| unchanged" "| updated"))))))

(-main)
