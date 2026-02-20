(ns jagrid.css.core
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :refer [px]]
            [environ.core :refer [env]]))

(def cell-size (or (env :cell-size) 20))

(defn base-css [& {:keys [header-bgcolor header-textcolor header-border-color
                          btn-color btn-primary-color btn-bgcolor btn-primary-bgcolor
                          cursor-color header-active-bgcolor header-active-textcolor
                          header-active-style]
                   :or {header-bgcolor "#dae7f5"
                        header-textcolor "#47385a"
                        header-border-color "#b1b5ba"
                        btn-bgcolor "#f0f0f0"
                        btn-color "#444"
                        btn-primary-bgcolor "#2b5797"
                        btn-primary-color "#fff"
                        cursor-color "#1a73e8"
                        header-active-bgcolor "#c5d9f1"
                        header-active-textcolor nil
                        header-active-style :fill}}]
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
    [:&.jg-row
     {:position "absolute"
      :margin-top  (px (- cell-size))
      :margin-left (px (- cell-size))}]
    [:td :th
     {:text-align "center"
      :color header-textcolor
      :font-size "90%"
      :box-shadow (str "inset " header-border-color " -1px -1px 0")}]]
   [:*
    {:margin 0
     :padding 0
     :box-sizing "content-box"}]

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
   [:.jg-cell-cursor
    {:position "absolute"
     :border (str "2px solid " cursor-color)
     :pointer-events "none"
     :z-index 10
     :box-sizing "border-box"}]
   (if (= header-active-style :border)
     ;; Office 365 style: bottom/right border line on active headers
     [[:.jg-header.column
       [:td.jg-active :th.jg-active
        {:background-color header-active-bgcolor
         :box-shadow (str "inset 0 -2px 0 " cursor-color ", inset " header-border-color " -1px -1px 0")}]]
      [:.jg-header.jg-row
       [:td.jg-active :th.jg-active
        {:background-color header-active-bgcolor
         :box-shadow (str "inset -2px 0 0 " cursor-color ", inset " header-border-color " -1px -1px 0")}]]]
     ;; Classic style: fill background
     [:.jg-header
      [:td.jg-active :th.jg-active
       (merge {:background-color header-active-bgcolor
               :font-weight "bold"}
              (when header-active-textcolor
                {:color header-active-textcolor}))]])
   [:table
    {:box-shadow "#000 -1px -1px 0px"
     :border-collapse "collapse"
     :border-color "inherit"
     :border-spacing 0}
    [:tr
     {:line-height (px cell-size)}
     ["&:first-child"
      [:td {:height 0 :border 0}]]]
    [:td :th
     {:width (px cell-size)
      :max-width (px cell-size)
      :min-width (px cell-size)
      :height (px cell-size)
      :line-height (px cell-size)
      :padding 0
      :vertical-align "top"
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

(defstylesheet theme-office365
  {:output-to "css/jagrid-office365.css"}
  (base-css :header-bgcolor "#f6f6f6"
            :header-textcolor "#333"
            :header-border-color "#d4d4d4"
            :btn-bgcolor "#f3f3f3"
            :btn-primary-bgcolor "#217346"
            :cursor-color "#217346"
            :header-active-bgcolor "#e2efdb"))

