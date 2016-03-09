(ns jagrid.example.core
  (:require [compojure.core :refer :all]
            [ring.util.response :refer [file-response content-type]]
            [jagrid.css.core :as css]
            [jagrid.example.index :as index]
            [jagrid.example.basic :as basic]
            [jagrid.example.sales-report :as sales-report]))

(defn build-examples []
  (spit "example/index.html" (index/view))
  (spit "example/basic.html" (basic/view))
  (spit "example/sales-report.html" (sales-report/view)))

(defroutes example-routes
  (GET "/" [] (index/view))
  (GET "/basic.html" [] (basic/view))
  (GET "/sales-report.html" [] (sales-report/view))
  (GET "/js/jagrid.js" [] (-> (file-response "js/jagrid.js")
                              (content-type "text/javascript")))
  (GET "/example.css" [] (-> (file-response "example/example.css")
                             (content-type "text/css")))
  (GET "/css/*.css" {{filename :*} :params}
    (-> (file-response (str "css/" filename ".css"))
        (content-type "text/css"))))
