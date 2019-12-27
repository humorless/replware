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
  [:nav.bg-white.relative.flex.justify-between.items-center
   {:role "navigation"}
   [:div.db.db-ns.h3]
   [:ul.list.mv0.pr2.tr.flex-auto
    [:li.di.mr3
     [:a.link.dark-gray.hover-red
      {:href "/"} "Home"]]
    [:li.di.mr3
     [:a.link.dark-gray.hover-red
      {:href "/blog"} "Blog"]]
    [:li.di.mr3
     [:a.link.dark-gray.hover-red
      {:href "/about"} "About"]]]])

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

(defn- create-main
  "render the <main>"
  [content data]
  [:main.w-100.mw8.center.ph3.pv4
   [:section
    [:h2 "Who"]
    [:img.mw5.relative.fr {:src "/assets/images/photo.jpg"}]
    content]
   [:section
    [:h2 "Services"]
    [:ul
     [:li "Rapid prototyping and MVP"]
     [:li "Enterprise software solution"]
     [:li "Datomic database"]]]
   [:section
    [:h2 "Talks"]
    [:ul
     (for [t (-> data :entry :talk)]
       [:li t])]]])


(defn- create-footer
  "render the <footer>"
  []
  [:footer])

(defn- create-body
  "This will render html <body>"
  [data]
  [:body.helvetica.dark-gray
   (create-nav)
   (create-header)
   (create-main
    (-> data :entry :content)
    data)
   (create-footer)])

(defn page
  "the page renderer"
  [data]
  (hp/html5
   {:lang "en"}
   (create-head "replware")
   (create-body data)))
