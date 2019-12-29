(ns site.core
  (:require [hiccup.page :as hp]))

(defn- create-head
  "This will render html <head>"
  [title]
  [:head
   [:title title]
   [:meta {:charset "utf-8"}]
   [:meta
    {:content "width=device-width, initial-scale=1, shrink-to-fit=no"
     :name "viewport"}]
   [:link
    {:rel "shortcut icon"
     :href "/assets/logo/favicon.ico"
     :type "image/x-icon"}]
   [:link
    {:rel "stylesheet"
     :href "https://unpkg.com/tachyons@4.10.0/css/tachyons.min.css"}]])

(defn- create-nav
  "render the <nav>"
  []
  [:nav.bg-white.flex.justify-between.items-center
   {:role "navigation"}
   [:div.db.db-ns.h3]
   [:ul.list.mv0.pr4.pr5-ns.tr.flex-auto
    [:li.di.mr3
     [:a.link.dark-gray.hover-red
      {:href "/"} "Home"]]
    [:li.di.mr3
     [:a.link.dark-gray.hover-red
      {:href "https://humorless.github.io"} "Blog"]]]])

(defn- create-header
  "render the <header>"
  []
  [:header.bg-dark-gray.pv1.pv2-ns.lh-copy-ns.flex-column.flex-row-ns.flex-ns.justify-between.items-center
   [:a.ml4-ns.db.db-ns.mw4.mw5-ns.link
    {:href "/" :title "home"}
    [:img.mh4.mh2-ns.mt2 {:src "/assets/logo/logo_transparent_background.png"}]]
   [:h1.ph4.pv2.ph5-ns.pv4-ns.white
    [:div "turn " [:span.yellow "your ideas"]]
    [:div "into " [:span.light-blue.center "software"]]]])

(defn ->content-by-tag
  "select by tag"
  [t data]
  (->> data
       (filter t)
       (map :content)
       first))

(defn- create-main
  "render the <main>"
  [entries]
  (let [who (->content-by-tag :who entries)
        talk (->content-by-tag :talk entries)
        contact (->content-by-tag :contact entries)]
    (prn who talk contact)
    [:main.w-100.mw8.center.ph3.pv4
     [:section
      [:h2 "who"]
      [:img.br-100.mw4.mw5-ns.relative.fr {:src "/assets/images/photo.jpg"}]
      who]
     [:section
      [:h2 "services"]
      [:ul
       [:li "Rapid prototyping and MVP"]
       [:li "Enterprise software solution"]
       [:li "Datomic database"]]]
     [:section
      [:h2 "talks"]
      talk]
     [:section
      [:h2 "contact"]
      contact]]))

(defn- create-footer
  "render the <footer>"
  []
  [:footer.db-ns
   [:div.ph3.pv4.flex.justify-between
    [:div.db.db-ns]
    [:ul.mv0.pr4.pr5-ns.list
     [:li.f5.di.mr3
      [:a.link.dark-gray.hover-red
       {:href "https://twitter.com/humorless"} "Twitter"]]
     [:li.f5.di.mr3
      [:a.link.dark-gray.hover-red
       {:href "https://linkedin.com/in/humorless"} "Linkedin"]]
     [:li.f5.di.mr3
      [:a.link.dark-gray.hover-red
       {:href "https://github.com/humorless"} "Github"]]]]])

(defn- create-body
  "This will render html <body>"
  [entries]
  [:body.helvetica.dark-gray
   (create-nav)
   (create-header)
   (create-main entries)
   (create-footer)])

(defn home-page
  "the page renderer"
  [{global-meta :meta entries :entries}]
  (hp/html5
   {:lang "en"}
   (create-head "replware")
   (create-body entries)))
