(ns jagrid.example.basic
  (:use [hiccup.core]
        [jagrid.example layout util]))

(defn- view-hello []
  [:div.jagrid {:style "width: 400px; height: 150px;"}
    [:div {:data-x "1" :data-y "1"}
      [:p "こんにちは、Excel方眼紙"]]])

(defn- view-table []
  [:div.jagrid {:style "width: 400px; height: 150px;"}
    [:div {:data-x "1" :data-y "1"}
      [:table
        [:tr
          [:th.title {:data-width "3"} "ID"]
          [:th.title {:data-width "5"} "NAME"]]
        [:tr
          [:td "51"]
          [:td "イチロー"]]]]])

(defn- view-list []
  [:div.jagrid {:style "width: 400px; height: 150px;"}
    [:div {:data-x "1" :data-y "1"}
      [:ul
        [:li "りんご"]
        [:li "ばなな"]
        [:li "いちご"]]]

    [:div {:data-x "8" :data-y "1"}
      [:ol
        [:li "りんご"]
        [:li "ばなな"]
        [:li "いちご"]]]])

(defn view []
  (view-layout {:title "basic"}
    [:article
      [:h2 "方眼紙を出力してみる"]
      (view-hello)

      [:h3 "Source code"]
      [:pre
        [:code (h (pretty-print (html (view-hello))))]]
      ]

    [:article
      [:h2 "表"]
      (view-table)
      
      [:h3 "Source code"]
      [:pre
        [:code (h (pretty-print (html (view-table))))]]]

    [:article
      [:h2 "リスト"]
      (view-list)

      [:h3 "Source code"]
      [:pre
        [:code (h (pretty-print (html (view-list)))) ]]]))
