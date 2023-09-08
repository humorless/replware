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
     :href "/assets/style.css"}]
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
      {:href "/newsletter.html"} "Newsletter"]]
    [:li.di.mr3
     [:a.link.dark-gray.hover-red
      {:href "https://leanpub.com/errors_to_innovation/"} "Book"]]
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
    [:div "bring " [:span.yellow "fast iteration"]]
    [:div "into " [:span.light-blue.center "your business"]]]])

(defn ->content-by-tag
  "select by tag"
  [t data]
  (->> data
       (filter t)
       (map :content)
       first))

(defn- create-newsletter-main
  "render the <main>"
  [entries]
  (let [newsletter (->content-by-tag :newsletter entries)]
    (prn newsletter)
    [:main.w-100.mw8.center.ph3.pv4
     newsletter]))

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
      [:img.br-100.mw4.mw5-ns.relative.fr {:src "/assets/images/photo2.jpg"}]
      who]
     [:section
      [:h2 "services"]
      [:ul
       [:li "Root cause analysis"]
       [:li "Data warehouse/Data pipeline/Operational pipeline"]
       [:li "Recommendation system"]
       [:li "Rapid prototyping"]
       [:li "Software architecture design"]]]
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
    [:p "I help my clients to improve workflow in their business intelligence team by using ELT to replace ETL. With the new workflow, the business intelligence team reduces data latency by 80%."]]
   [:section
    [:h2 "Software architecture design"]
    [:p "I help my clients to design the software architecture. With the new architecture, their program utilizes database more effectively and thus runs 5 times faster."]]
   [:section
    [:h2 "Root cause analysis"]
    [:p "I help my clients to find out the obstacles in their customer support flow and design a telemetry tool to facilitate it. With the telemetry tool, the customer support team can diagnose the problems 10 times faster."]]
   [:section
    [:h2 "My clients"]
    [:div.clients-gallery
     [:img {:src "/assets/images/client/xrexinc.jpeg"}]
     [:img {:src "/assets/images/client/kumon.jpeg"}]
     [:img {:src "/assets/images/client/gmcsr.png"}]
     [:img {:src "/assets/images/client/invistron.png"}]
     [:div [:p "OrangeSkyLab"]]]]])

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
     :newsletter (create-newsletter-main entries)
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

(defn newsletter-page
  "the page renderer"
  [{:keys [meta entries]}]
  (hp/html5
   {:lang "en"}
   (create-head "REPLWARE (睿博資訊)")
   (create-body {:path :newsletter
                 :entries entries})))

(defn client-page
  "the page renderer"
  [{:keys [meta entry]}]
  (hp/html5
   {:lang "en"}
   (create-head "REPLWARE (睿博資訊)")
   (create-body {:path :client
                 :entries nil})))
