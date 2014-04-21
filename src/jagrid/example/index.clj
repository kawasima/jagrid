(ns jagrid.example.index
  (:use [hiccup.core]
        [jagrid.example.layout]))

(defn view []
  (view-layout {:title "index"}
    [:h1 "Excel方眼紙のようなレイアウトを実現します"]
    [:ul
      [:li [:a {:href "basic.html"} "Excel方眼紙レイアウトの基本"]]
      [:li [:a {:href "sales-report.html"} "組み合わせた例 (営業日報)"]]]))
