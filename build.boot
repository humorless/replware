(set-env! :source-paths #{"src" "content"}
          :resource-paths #{"resources"}
          :dependencies '[[org.clojure/clojure "1.9.0"] ;
                          [perun "0.4.3-SNAPSHOT" :scope "test"] ;
                          [hiccup "2.0.0-alpha1"] ;
                          [enlive "1.1.6"] ;
                          [boot-fmt/boot-fmt "0.1.8"] ;
                          [boot-cljfmt "0.1.3" :scope "test"]
                          [pandeiro/boot-http "0.8.3"] ;
                          [boot-deps "0.1.9"] ;
                          [deraen/boot-sass "0.3.1"] ;
                          ])

(require '[io.perun :as perun]
         '[site.core]
         '[clojure.string :as str]
         '[boot-fmt.core :refer [fmt]]
         '[pandeiro.boot-http :refer [serve]]
         '[deraen.boot-sass :refer [sass]])

(def home? (comp #(= % true) :home))
(def auxi? (comp #(= % true) :auxi))

(deftask
  build
  "Base task, you probably want to use `build-prod` or `dev`."
  []
  (comp (perun/global-metadata)
        (perun/markdown)
        (perun/static :renderer 'site.core/client-page
                      :page "client.html")
        (perun/collection :renderer 'site.core/newsletter-page
                          :filterer auxi?
                          :sortby :path
                          :page "newsletter.html")
        (perun/collection :renderer 'site.core/home-page
                          :filterer home?
                          :sortby :path
                          :page "index.html")
        (sass)))

(deftask build-prod "Emit HTML files" [] (comp (build) (target)))

(deftask dev
  "Serve the site and watch the filesystem for changes."
  []
  (comp (watch) (build) (serve :resource-root "public")))

