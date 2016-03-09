(ns jagrid.example.layout
  (:use [hiccup core page element]))

(defn view-layout [options & content]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:title (:title options)]
    (include-css
     "http://cdnjs.cloudflare.com/ajax/libs/bootswatch/3.1.1-1/css/simplex/bootstrap.min.css"
     "http://cdnjs.cloudflare.com/ajax/libs/highlight.js/8.0/styles/github.min.css"
     "example.css")
    [:link#jagrid-css {:href "../css/jagrid.css" :rel "stylesheet" :type "text/css"}]
    (include-js
     "http://cdnjs.cloudflare.com/ajax/libs/highlight.js/8.0/highlight.min.js"
     "../js/jagrid.js")]
   [:body
    [:div.navbar.navbar-default.navbar-fixed-top
     [:div.container
      [:div.navbar-header
       [:a.navbar-brand {:href "index.html"} "jagrid"]]]]
    [:div.container
     [:div.form-group
      [:label "Theme"]
      [:select#theme.form-control {:name "theme"}
      [:option {:value ""} "Default (Excel 2010)"]
      [:option {:value "excel2000"} "Excel 2000"]
      [:option {:value "lotus123"} "Lotus 1-2-3"]]]
     
     content]
    (javascript-tag "
hljs.initHighlightingOnLoad();
document.getElementById('theme').addEventListener('change', function(e) {
  var filename = (e.target.value != '') ? '-' + e.target.value : '';
  document.getElementById('jagrid-css')
    .setAttribute('href', '../css/jagrid' + filename + '.css');
});
")]))
