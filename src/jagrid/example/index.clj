(ns jagrid.example.index
  (:require [jagrid.example.layout :refer [view-layout]]))

(defn view []
  (view-layout {:title "index"}
    [:h1 "Excel方眼紙のようなレイアウトを実現します"]
    [:ul
      [:li [:a {:href "basic.html"} "Excel方眼紙レイアウトの基本"]]
      [:li [:a {:href "sales-report.html"} "組み合わせた例 (営業日報)"]]]))
