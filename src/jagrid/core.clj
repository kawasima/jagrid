(ns jagrid.core
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :refer [px]])
  (:use [environ.core]
        [jagrid.example.core]))

(def cell-size (or (env :cell-size) 20))

(defstylesheet screen
  {:output-to "css/jagrid.css"}
  [:.jagrid
   {:border-width (px 0)
    :line-height (px cell-size)
    :position "relative"
    :border-style "solid"
    :padding-left (px cell-size)
    :overflow "hidden"}
    [:.jg-header
      {:background-color "#dfe3e8"
       :box-shadow "none"}
      [:&.row
        {:position "absolute"
         :margin-left (px (- cell-size))}]
      [:td :th
        {:text-align "center"
         :color "#3e3e36"
         :font-size "90%"
         :box-shadow "inset #b1b5ba -1px -1px 0"}]]
    [:*
      {:margin 0
       :padding 0}]
    [:ul :ol
      {:padding-left (px cell-size)}]
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

(defn -main []
  (build-examples))

