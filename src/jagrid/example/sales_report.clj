(ns jagrid.example.sales-report
  (:require [hiccup.core :refer [html h]]
            [jagrid.example.layout :refer [view-layout]]
            [jagrid.example.util :refer [pretty-print]]))

(defn- view-report []
  [:div.jagrid {:style "width: 640px; height: 480px;"}
    [:div {:data-x 1 :data-y 2}
      [:table {:data-margin-left 1 :data-margin-top -1}
        [:tr
          [:td.title-all {:data-width 28} "営業日報"]]
        [:tr
          [:td.title {:data-width 3} "店舗"]
          [:td {:data-width 8} "東京支店"]
          [:td.title {:data-width 3} "担当"]
          [:td {:data-width 7} "川島義隆"]
          [:td.title {:data-width 2} "日付"]
          [:td {:data-width 5} "14/4/15"]]
        [:tr
          [:td.title {:data-width 4} ""]
          [:td.title {:data-width 5} "計画"]
          [:td.title {:data-width 5} "実績"]
          [:td.title {:data-width 5} "達成率"]
          [:td.title {:data-width 9} "備考"]]
        [:tr
          [:td.title "来客数"]
          [:td 150]
          [:td 2]
          [:td "1.75%"]
          [:td {:rowspan 4} "さっぱりでした"]]
        [:tr
          [:td.title "売上高"]
          [:td 150]
          [:td 2]
          [:td "1.75%"]]
        [:tr
          [:td.title "客単価"]
          [:td 150]
          [:td 2]
          [:td "1.75%"]]
        [:tr
          [:td.title "販売単価"]
          [:td 150]
          [:td 2]
          [:td "1.75%"]]
        [:tr
          [:td.title.vertical {:data-width 2 :rowspan 3} "取引記録"]
          [:td.title {:data-width 7} "取引先名"]
          [:td.title {:data-width 6} "連絡先"]
          [:td.title {:data-width 13} "内容"]]
        [:tr
          [:td "A社"]
          [:td "03-xxxx-xxxx"]
          [:td "イケメンの担当者で、緊張しました。"]]
        [:tr
          [:td "B社"]
          [:td "03-xxxx-xxxx"]
          [:td "激しく怒られました。" [:br] "しかし値引きには応じませんでした。"]]
        [:tr
          [:td.title.vertical {:data-height 8} "特記事項"]
          [:td {:data-width 26 :data-height 8} "他にになにかあれば・・・" [:br] "特になし"]]]]])

(defn view []
  (view-layout {:title "basic"}
    (view-report)
    [:h3 "Source code"]
      [:pre
        [:code (h (pretty-print (html (view-report))))]]))
