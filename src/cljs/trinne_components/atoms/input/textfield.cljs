(ns trinne-components.atoms.input.textfield
  (:require [stylefy.core :as stylefy :refer [use-style use-sub-style]]
            [reagent.core :as r]
            [trinne-components.atoms.input.textfield-styles :refer [textfield-styles]]))

(defn textfield [{:keys [label on-change placeholder info value] :as args}]
  (fn [{:keys [label on-change placeholder info value] :as args}]
    (.debug js/console (str "RENDER [textfield - " label "]"))
    (let [tf-styles (textfield-styles (:type info))]
      [:div.text-field-container (use-style tf-styles)
       [:input (use-sub-style tf-styles :input (merge args
                                                      {:on-change #(do
                                                                     (reset! value (-> % .-target .-value))
                                                                     (when on-change (on-change %)))
                                                       :value @value}))]
       [:label.label (if (and (empty? @value) (empty? placeholder))
                       (use-sub-style tf-styles :label)
                       (use-sub-style tf-styles :label-when-value)) label]
       [:div.text-field-border (use-sub-style tf-styles :border)]
       [:small (use-sub-style tf-styles :info) (:text info)]])))

(defn validate [state {:keys [valid? warning-message error-message info-message]}]
  (merge {:type :info
          :text info-message}
         (when (and (:touched? @state)
                    (not (valid? (:value @state))))
           {:type :error
            :text (or error-message info-message)})
         (when (and (:dirty? @state)
                    (:editing? @state)
                    (not (valid? (:value @state))))
           {:type :warning
            :text (or warning-message info-message)})))

(defn textfield-validated [{:keys [validation info on-blur on-change] :as args}]
  (let [state (r/atom {:touched? false
                       :dirty?   false
                       :editing? false
                       :value nil})]
    (fn [{:keys [validation info on-blur on-change] :as args}]
      [textfield (merge (dissoc args :validation)
                        {:info      (if validation
                                      (validation state)
                                      info)
                         :on-blur   #(do
                                       (when (:dirty? @state)
                                         (swap! state assoc :touched? true))
                                       (swap! state assoc :editing? false)
                                       (when on-blur (on-blur %)))
                         :on-change #(do
                                       (swap! state assoc :dirty? true)
                                       (swap! state assoc :editing? true)
                                       (swap! state assoc :value (-> % .-target .-value))
                                       (when on-change (on-change %)))})])))
