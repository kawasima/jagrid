(ns jagrid.example.core
  (:require [jagrid.example.index :as index]
            [jagrid.example.basic :as basic]
            [jagrid.example.sales-report :as sales-report]))

(defn build-examples []
  (spit "example/index.html" (index/view))
  (spit "example/basic.html" (basic/view))
  (spit "example/sales-report.html" (sales-report/view)))
