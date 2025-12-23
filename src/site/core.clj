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
      {:href "/client.html"} "How I Work & Who I've Helped"]]
    [:li.di.mr3
     [:a.link.dark-gray.hover-red
      {:href "https://replware.dev/mds"} "Book"]]
    [:li.di.mr3
     [:a.link.dark-gray.hover-red
      {:href "https://replware.substack.com/"} "Blog"]]]])

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
       [:li "Data Platform (Modern Data Stack)"]
       [:li "Workflow Orchestration"]
       [:li "Identity & Access Management"]]]
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
    [:h2 "Engagement Model"]
    [:p "Typically, our partnership begins after you’ve explored my insights on platform architecture. We’ll start with a deep-dive conversation to see if my strategic approach aligns with your organizational goals."]
    [:p "Once I understand your specific challenges, I will draft a tailored proposal that defines the platform boundaries, technical stack, and a clear roadmap for execution."]
    [:p "A typical engagement lasts about 3 months. I provide on-site consultancy one day per week. To ensure successful knowledge transfer, I require at least one dedicated engineer from your team to collaborate 1–2 days per week on the project."]
    [:p "We define success by results. The engagement concludes when your existing business logic is successfully running on the new architecture, proving the platform's reliability and readiness for future scale."]]
   [:section
    [:h2 "My clients"]
    [:div.clients-gallery
     [:img {:src "/assets/images/client/giantplus.svg"}]
     [:img {:src "/assets/images/client/ctbc.svg"}]
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
       {:href "https://g0v.social/@replware"} "Mastodon"]]
     [:li.f5.di.mr3
      [:a.link.dark-gray.hover-red
       {:href "https://linkedin.com/in/replware"} "Linkedin"]]
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
