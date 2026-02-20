(ns jagrid.example.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [hiccup.element :refer [javascript-tag]]))

(defn view-layout [options & content]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:title (:title options)]
    (include-css
     "https://cdn.jsdelivr.net/npm/bootswatch@5.3.3/dist/simplex/bootstrap.min.css"
     "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/github.min.css"
     "example.css")
    [:link#jagrid-css {:href "../css/jagrid.css" :rel "stylesheet" :type "text/css"}]
    (include-js
     "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"
     "../js/jagrid.js")]
   [:body
    [:nav.navbar.navbar-expand-lg.navbar-light.bg-light.fixed-top
     [:div.container
      [:a.navbar-brand {:href "index.html"} "jagrid"]]]
    [:div.container {:style "padding-top: 70px;"}
     [:div.mb-3
      [:label.form-label "Theme"]
      [:select#theme.form-select {:name "theme"}
       [:option {:value ""} "Default (Excel 2010)"]
       [:option {:value "excel2000"} "Excel 2000"]
       [:option {:value "office365"} "Office 365"]
       [:option {:value "lotus123"} "Lotus 1-2-3"]]]

     content]
    (javascript-tag "
hljs.highlightAll();
document.getElementById('theme').addEventListener('change', function(e) {
  var filename = (e.target.value != '') ? '-' + e.target.value : '';
  document.getElementById('jagrid-css')
    .setAttribute('href', '../css/jagrid' + filename + '.css');
});
")]))
