(ns jagrid.system
  (:require [compojure.core :as compojure]
            [com.stuartsierra.component :as component]
            [meta-merge.core :refer [meta-merge]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [wrap-defaults]]
            [jagrid.example.core :refer [example-routes]]))

(defrecord EndpointComponent [build-routes]
  component/Lifecycle
  (start [component]
    (if (:routes component)
      component
      (assoc component :routes (build-routes component))))
  (stop [component]
    (dissoc component :routes)))

(defn endpoint-component [build-routes]
    (->EndpointComponent build-routes))

(defn- find-endpoint-keys [component]
  (sort (map key (filter (comp :routes val) component))))

(defn- find-routes [component]
  (map #(:routes (get component %))
       (:endpoints component (find-endpoint-keys component))))

(defn- middleware-fn [component middleware]
  (if (vector? middleware)
    (let [[f & keys] middleware
          arguments  (map #(get component %) keys)]
      #(apply f % arguments))
    middleware))

(defn- compose-middleware [{:keys [middleware] :as component}]
  (->> (reverse middleware)
       (map #(middleware-fn component %))
              (apply comp identity)))

(defrecord Handler [middleware]
  component/Lifecycle
  (start [component]
    (if-not (:handler component)
      (let [routes  (find-routes component)
            wrap-mw (compose-middleware component)
            handler (wrap-mw (apply compojure/routes routes))]
        (assoc component :handler handler))
      component))
  (stop [component]
    (dissoc component :handler)))

(defn handler-component [options]
  (map->Handler options))

(defrecord JettyServer [options app server]
  component/Lifecycle
  (start [component]
    (if (:server component)
      component
      (let [handler (get-in component [:app :handler])
            server  (jetty/run-jetty handler (assoc options :join? false))]
        (assoc component :server server))))
  (stop [component]
    (when-let [server (:server component)]
      (.stop server))
    (assoc component :server nil)))

(defn jetty-server [options]
  (map->JettyServer {:options options}))

(def base-config
  {:app {:middleware [[wrap-defaults :defaults]]}})

(defn example-endpoint [config]
  example-routes)

(defn new-system [config]
  (let [config (meta-merge base-config config)]
    (-> (component/system-map
         :app  (handler-component (:app config))
         :http (jetty-server (:http config))
         :example (endpoint-component example-endpoint))
        (component/system-using
         {:http [:app]
          :app  [:example]}))))
