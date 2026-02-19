(ns jagrid.example.basic
  (:require [hiccup.core :refer [html h]]
            [jagrid.example.layout :refer [view-layout]]
            [jagrid.example.util :refer [pretty-print]]))

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

(defn- view-grid []
  [:div.jagrid {:style "width: 400px; height: 300px;"}
   [:div {:data-x "1", :data-y "1"}
    [:p "一行目"]
    [:p "二行目"]]

   [:div {:data-x "2", :data-y "6", :data-width "8"}
    [:p "長い文字列は自動的に改行されるのです。"]
    [:p "続きはそのまま下に入ります"]]])

(defn- view-button []
  [:div.jagrid {:style "width: 400px; height: 300px;"}
   [:div {:data-x "1", :data-y "1"}
    [:button {:data-width "4"} "Button"]]
   [:div {:data-x "6", :data-y "1"}
    [:button.primary {:data-width "4"} "Button"]]
   [:div {:data-x "1", :data-y "3", :data-width "10"}
    [:input {:type "text" :name "hoge" :placeholder "Name"}]]])

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
       [:code (h (pretty-print (html (view-list))))]]]

    [:article
     [:h2 "グリッド"]
     (view-grid)
     
     [:h3 "Source code"]
     [:pre
      [:code (h (pretty-print (html (view-grid))))]]]
    
    [:article
     [:h2 "ボタン"]
     (view-button)

     [:h3 "Source code"]
     [:pre
      [:code (h (pretty-print (html (view-button))))]]]))
