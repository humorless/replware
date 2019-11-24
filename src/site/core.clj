(ns site.core
  (:require [hiccup.page :as hp]))


(defn- create-head
 "This will render the <head>"
 [title]
 [:head
   [:title title]
   [:meta {:charset "utf-8"}]
   [:meta
    {:content "width=device-width, initial-scale=1, shrink-to-fit=no",
     :name "viewport"}]
   [:link
    {:rel "shortcut icon",
     :href "/assets/images/favicon.ico",
     :type "image/x-icon"}]
   [:link
    {:rel "stylesheet",
     :href "https://unpkg.com/tachyons@4.10.0/css/tachyons.min.css"}]])

(defn- create-body
  "This will render the html body"
  [data]
  [:body
    [:main
      (-> data :entry :content)]])

(defn page
  "the page renderer"
  [data]
  (hp/html5 {:lang "en"}
    (create-head "replware")
    (create-body data)))
