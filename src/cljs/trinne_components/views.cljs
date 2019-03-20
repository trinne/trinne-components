(ns trinne-components.views
  (:require [reagent.core :as r]
            [re-frame.core :as re-frame]
            [trinne-components.subs :as subs]
            [trinne-components.atoms.input.textfield :as textfield]
            [trinne-components.atoms.menu.menu :as menu]))

(defn pakollinen [state]
  (textfield/validate state {:valid? not-empty
                             :info-message "* pakollinen"}))

(defn pakollinen+etunimi-sukunimi [dokumentti state]
  (textfield/validate state {:valid?  #(and (not-empty %)
                                            (= % (str (:etunimi @dokumentti) " " (:sukunimi @dokumentti))))
                             :info-message  "* pakollinen"
                             :error-message (str "Kentän arvon tulee olla '" (:etunimi @dokumentti) " " (:sukunimi @dokumentti) "'")}))

(defn lomake-kentta [dokumentti avain-polku label validointi out-fn]
  (let [kentta (reagent.ratom/cursor dokumentti avain-polku)]
    [textfield/textfield-validated {:label      label
                                    :validation validointi
                                    :value      kentta
                                    :on-change  #(do
                                                   (reset! kentta (-> % .-target .-value))
                                                   (when out-fn (out-fn %)))}]))

(defn doc [document]
  [:pre (pr-str @document)])

(defn trinne-form []
  (let [document (r/atom {})
        etunmimi-sukunimi (reagent.ratom/make-reaction #(select-keys @document [:etunimi :sukunimi]))]
    (fn[]
      [:div
       [:form
        [:h2 "Trinne components"]
        [lomake-kentta document [:etunimi] "Etunimi*" pakollinen #(swap! document assoc :jotain-muuta (str (:etunimi @document) " - "  (:sukunimi @document)))]
        [lomake-kentta document [:sukunimi] "Sukunimi*" pakollinen #(swap! document assoc :jotain-muuta (str (:etunimi @document) " - "  (:sukunimi @document)))]
        [lomake-kentta document [:etunimi-sukunimi] "Etunimi Sukunimi" (partial pakollinen+etunimi-sukunimi etunmimi-sukunimi)]
        [lomake-kentta document [:jotain-muuta] "Jotain"]
        [textfield/textfield {:value (r/atom "Et voi muuttaa tätä")
                              :disabled true
                              :label "Disabled"}]
        [menu/menu
         ^{:key "1"}[menu/item "Item 1"]
         ^{:key "2"}[menu/item "Item 2"]
         ^{:key "3"}[menu/item "Item 3"]
         ^{:key "4"}[menu/item "Item 4"]]]
       [doc document]])))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from " @name]
     [trinne-form]]))