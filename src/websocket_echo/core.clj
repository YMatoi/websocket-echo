(ns websocket-echo.core
  (:require [org.httpkit.server :as httpkit]
            [clojure.tools.namespace.repl :refer [refresh-all]]
            [com.stuartsierra.component :as component]))

(defn handler [req]
  (httpkit/with-channel req channel
    (httpkit/on-close channel (fn [status]
                                (println "channel closed")))
    (if (httpkit/websocket? channel)
      (println "Websocket channel")
      (println "HTTP Channel"))
    (httpkit/on-receive channel (fn [data]
                                  (println data)
                                  (httpkit/send! channel data)))))

(defrecord Server [server port]
  component/Lifecycle
  (start [this]
    (println ";; Starting Server")
    (if (:server this)
      this
      (assoc this :server (httpkit/run-server #'handler {:port port}))))
  (stop [this]
    (println ";; Stopping Server")
    (if (:server this)
      (do
        ((:server this) :timeout 100)
        (dissoc this :server))
      this)))

(defn example-system [config]
  (let [{:keys [port]} config]
    (component/system-map
     :server (map->Server {:port port}))))

(defonce system (example-system {:port 9999}))

(defn run []
  (alter-var-root #'system component/start))

(defn stop []
  (alter-var-root #'system component/stop))

(defn reset []
  (stop)
  (refresh-all :after 'websocket-echo.core/run))
