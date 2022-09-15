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
     :href "https://unpkg.com/tachyons@4.10.0/css/tachyons.min.css"}]
   [:script
    {:src "https://www.googletagmanager.com/gtag/js?id=UA-135441027-2"}]
   [:script
    {:type "text/javascript"}
    "
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-135441027-2');
    "]])

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
      {:href "/client.html"} "Case Studies"]]
    [:li.di.mr3
     [:a.link.dark-gray.hover-red
      {:href "https://replware.medium.com/"} "Blog"]]]])

(defn- create-header
  "render the <header>"
  []
  [:header.bg-dark-gray.pv1.pv2-ns.lh-copy-ns.flex-column.flex-row-ns.flex-ns.justify-between.items-center
   [:a.ml4-ns.db.db-ns.mw4.mw5-ns.link
    {:href "/" :title "home"}
    [:img.mh4.mh2-ns.mt2 {:src "/assets/logo/logo_transparent_background.png"}]]
   [:h1.ph4.pv2.ph5-ns.pv4-ns.white
    [:div "turn " [:span.yellow "technology"]]
    [:div "into " [:span.light-blue.center "the edge"]]]])

(defn ->content-by-tag
  "select by tag"
  [t data]
  (->> data
       (filter t)
       (map :content)
       first))

(defn- create-home-main
  "render the <main>"
  [entries]
  (let [who (->content-by-tag :who entries)
        talk (->content-by-tag :talk entries)
        testimonial (->content-by-tag :testimonial entries)
        contact (->content-by-tag :contact entries)]
    (prn who talk contact testimonial)
    [:main.w-100.mw8.center.ph3.pv4
     [:section
      [:h2 "who"]
      [:img.br-100.mw4.mw5-ns.relative.fr {:src "/assets/images/photo.jpg"}]
      who]
     [:section
      [:h2 "services"]
      [:ul
       [:li "Data analytic stack"]
       [:li "Developer productivity"]
       [:li "Effective debugging"]
       [:li "Business strategy formulation"]]]
     [:section
      [:h2 "talks"]
      talk]
     [:section
      [:h2 "customer testimonials"]
      testimonial]
     [:section
      [:h2 "contact"]
      contact]]))

(defn- create-client-main
  []
  [:main.w-100.mw8.center.ph3.pv4
   [:section
    [:h2 "Data analytic stack"]
    [:p "I helped my client to change the workflow in their business intelligence team, used ELT to replace ETL."
     " With the new workflow, the business intelligence team reduces the data latency by 80%."]]
   [:section
    [:h2 "Developer productivity"]
    [:p "I helped my client to introduce Clojure programming language as their new technical stack and gave their software team necessary training."
     " With the technical stack, the software team can deliver 3 times faster."]]
   [:section
    [:h2 "Debug"]
    [:p "I helped my client to find out the obstacles in their customer support workflow, and designed a telemetry tool to faciliate it."
     " With the telemetry tool, the customer support team can diagnose the problems 10 times faster."]]
   [:section
    [:h2 "Our clients"]
    [:div.w-100.w-80-ns.pv4-ns.pr7-ns.pr4.flex.justify-between
     [:img.w-20-ns.w-10 {:src "/assets/images/client/invistron.png"}]
     [:img.w-20-ns.w-10 {:src "/assets/images/client/gmcsr.png"}]
     [:img.w-20-ns.w-10 {:src "/assets/images/client/kumon.jpeg"}]
     [:div.w-20-ns.pv4-ns [:p "OrangeSkyLab"]]]]])

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
       {:href "https://github.com/humorless"} "Github"]]
     [:li.f5.di.mr3
      [:a.link.dark-gray.hover-red
       {:href "https://www.slideshare.net/humorless/presentations"} "Slideshare"]]]]])

(defn- create-body
  "This will render html <body>"
  [{:keys [path entries]}]
  [:body.helvetica.dark-gray
   (create-nav)
   (create-header)
   (case path
     :home (create-home-main entries)
     :client (create-client-main))
   (create-footer)])

(defn home-page
  "the page renderer"
  [{:keys [meta entries]}]
  (hp/html5
   {:lang "en"}
   (create-head "REPLWARE (睿博資訊)")
   (create-body {:path :home
                 :entries entries})))

(defn client-page
  "the page renderer"
  [{:keys [meta entry]}]
  (hp/html5
   {:lang "en"}
   (create-head "REPLWARE (睿博資訊)")
   (create-body {:path :client
                 :entries nil})))
