(ns trinne-components.views
  (:require [reagent.core :as r]
            [re-frame.core :as re-frame]
            [trinne-components.subs :as subs]
            [trinne-components.atoms.input.textfield :as textfield]))

(defn pakollinen [state]
  (textfield/validate state {:valid? not-empty
                             :info-message "* pakollinen"}))

(defn pakollinen+etunimi-sukunimi [dokumentti state]
  (textfield/validate state {:valid?  #(and (not-empty %)
                                            (= % (str (:etunimi @dokumentti) " " (:sukunimi @dokumentti))))
                             :info-message  "* pakollinen"
                             :error-message (str "Kentän arvon tulee olla '" (:etunimi @dokumentti) " " (:sukunimi @dokumentti) "'")}))

(defn lomake-kentta [dokumentti avain-polku label validointi]
  [textfield/textfield-validated {:label label
                                  :validation validointi
                                  :on-change #(swap! dokumentti assoc-in avain-polku (-> % .-target .-value))}])

(defn trinne-form []
  (let [document (r/atom {})
        etunmimi-sukunimi (reagent.ratom/make-reaction #(select-keys @document [:etunimi :sukunimi]))]
    (fn[]
      [:div
       [:form
        [:h2 "Trinne components"]
        [lomake-kentta document [:etunimi] "Etunimi*" pakollinen]
        [lomake-kentta document [:sukunimi] "Sukunimi*" pakollinen]
        [lomake-kentta document [:etunimi-sukunimi] "Etunimi Sukunimi" (partial pakollinen+etunimi-sukunimi etunmimi-sukunimi)]
        [lomake-kentta document [:jotain] "Jotain" nil]]])))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from " @name]
     [trinne-form]]))