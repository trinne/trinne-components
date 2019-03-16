(ns trinne-components.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [trinne-components.events :as events]
   [trinne-components.views :as views]
   [trinne-components.config :as config]
   [stylefy.core :as stylefy]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (stylefy/init)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
