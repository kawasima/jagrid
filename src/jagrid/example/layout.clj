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
        "example.css"
        "../css/jagrid.css")
      (include-js
        "http://cdnjs.cloudflare.com/ajax/libs/highlight.js/8.0/highlight.min.js"
        "../js/jagrid.js")]
    [:body
      [:div.navbar.navbar-default.navbar-fixed-top
        [:div.container
          [:div.navbar-header
            [:a.navbar-brand {:href "index.html"} "jagrid"]]]]
      [:div.container content]
      (javascript-tag "hljs.initHighlightingOnLoad();")]))
