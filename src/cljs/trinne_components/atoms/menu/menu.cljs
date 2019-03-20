(ns trinne-components.atoms.menu.menu
  (:require [stylefy.core :refer [use-style use-sub-style]]
            [trinne-components.atoms.menu.menu-styles :refer [menu-item-style menu-wrapper-style menu-style]]))

(defn item [args & content]
  (let [item-args {:tab-index -1
                   :role "menuitem"}]
    (if (map? args)
      (conj [:li (use-style menu-item-style (merge item-args args))] content)
      (conj [:li (use-style menu-item-style item-args)] args content))))

(defn menu [args & content]
  (let [menu-args {:role "menu"}]
    [:div (use-style menu-wrapper-style)
     (if (map? args)
      (conj [:ul (use-style menu-style (merge menu-args args))] content)
      (conj [:ul (use-style menu-style menu-args)] args content))]))