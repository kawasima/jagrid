(ns jagrid.example.util
  (:import [org.jsoup Jsoup]))

(defn pretty-print [html]
  (let [doc (Jsoup/parseBodyFragment html)]
    (.. doc outputSettings (prettyPrint true))
    (.. doc body children toString)))

