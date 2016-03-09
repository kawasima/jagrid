(ns jagrid.css.core
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :refer [px]])
  (:use [environ.core]))

(def cell-size (or (env :cell-size) 20))

(defn base-css [& {:keys [header-bgcolor header-textcolor
                          btn-color btn-primary-color btn-bgcolor btn-primary-bgcolor]
                   :or {header-bgcolor "#dae7f5"
                        header-textcolor "#47385a"
                        btn-bgcolor "#f0f0f0"
                        btn-color "#444"
                        btn-primary-bgcolor "#2b5797"
                        btn-primary-color "#fff"}}]
  [:.jagrid
   {:border-width (px 0)
    :line-height (px cell-size)
    :position "relative"
    :border-style "solid"
    :padding-left (px cell-size)
    :overflow "hidden"}
   [:.jg-header
    {:background-color header-bgcolor
     :box-shadow "none"}
    [:&.row
     {:position "absolute"
      :margin-top  (px (- cell-size))
      :margin-left (px (- cell-size))}]
    [:td :th
     {:text-align "center"
      :color header-textcolor
      :font-size "90%"
      :box-shadow "inset #b1b5ba -1px -1px 0"}]]
   [:*
    {:margin 0
     :padding 0}]
   
   [:ul :ol
    {:padding-left (px cell-size)}]

   [:button
    {:color btn-color
     :background-color btn-bgcolor}
    [:&.primary {:color btn-primary-color
                 :background-color btn-primary-bgcolor}]]
   [:input
    {:border {:size 0}
     :width "100%"
     :height (px (- cell-size 1))}]
   [:table
    {:box-shadow "#000 -1px -1px 0px"
     :border-collapse "collapse"}
    ["&:not(.jg-header)"
     {:height "100%"}]
    [:tr
     ["&:first-child"
      [:td {:height 0 :border 0}]]]
    [:td :th
     {:width (px cell-size)
      :max-width (px cell-size)
      :min-width (px cell-size)
      :line-heignt (px cell-size)
      :box-shadow  "inset #000 -1px -1px 0"}
     ["> div"
      {:height "100%"
       :width  "100%"
       :overflow "hidden"
       :white-space "nowrap"
       :box-shadow "inset #000 -1px -1px 0"}]]]])

(defstylesheet screen
  {:output-to "css/jagrid.css"}
  (base-css))

(defstylesheet theme-lotus123
  {:output-to "css/jagrid-lotus123.css"}
  (base-css :header-bgcolor "#ebe9ed"
            :header-textcolor "#000"))

(defstylesheet theme-excel2000
  {:output-to "css/jagrid-excel2000.css"}
  (base-css :header-bgcolor "#d4d0c7"
            :header-textcolor "#000"))

