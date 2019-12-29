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
   [:ul.list.mv0.pr5.tr.flex-auto
    [:li.di.mr3
     [:a.link.dark-gray.hover-red
      {:href "/"} "Home"]]
    [:li.di.mr3
     [:a.link.dark-gray.hover-red
      {:href "https://humorless.github.io"} "Blog"]]]])

(defn- create-header
  "render the <header>"
  []
  [:header.bg-dark-gray.pv1.pv2-ns.lh-copy.flex.justify-between.items-center
   [:a.ml4.db.db-ns.mw5.link
    {:href "/" :title "home"}
    [:img.mh2.mv2 {:src "/assets/logo/logo_transparent_background.png"}]]
   [:h1.pr5.pv4.white
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
      [:img.mw5.relative.fr {:src "/assets/images/photo.jpg"}]
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
  [:footer])

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
