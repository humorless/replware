(set-env! :resource-paths #{"resources"}
          :dependencies '[[org.clojure/clojure "1.9.0"] ;
                          [pandeiro/boot-http "0.8.3"] ;
                          ])

(require '[pandeiro.boot-http :refer [serve]])

;; The site is plain static files under resources/public — no generation step.
(deftask build "Base task, you probably want to use `build-prod` or `dev`." [] identity)

(deftask build-prod "Emit HTML files" [] (comp (build) (target)))

(deftask dev
  "Serve the site and watch the filesystem for changes."
  []
  (comp (watch) (build) (serve :resource-root "public")))
