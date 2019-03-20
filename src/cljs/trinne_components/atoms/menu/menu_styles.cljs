(ns trinne-components.atoms.menu.menu-styles
  (:require [stylefy.core :as stylefy]))

(def color-text "black")
(def color-bg "white")
(def color-bg-highlight "lightgray")

(def menu-item-style
  {:align-items "center"
   :background-color color-bg
   :box-sizing "border-box"
   :color color-text
   :cursor "pointer"
   :display "flex"
   :height "48px"
   :justify-content "flex-start"
   :list-style "none"
   :margin 0
   :padding "0 16px"
   :user-select "none"
   ::stylefy/mode {:hover {:background-color color-bg-highlight}
                   :focus {:outline "none"}}})

(def menu-style
  {:margin 0
   :padding "8px 0"})

(def menu-wrapper-style
  {:box-shadow "0 5px 5px -3px rgba(0,0,0,.2),
                0 8px 10px 1px rgba(0,0,0,.14),
                0 3px 14px 2px rgba(0,0,0,.12)"
   :box-sizing "border-box"})