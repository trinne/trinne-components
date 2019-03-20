(ns trinne-components.atoms.input.textfield-styles
  (:require [stylefy.core :as stylefy]))

(defn color-for-status [type color]
  (case type
    :error "red"
    :warning "orange"
    color))

(def color-bg "#FAFAFA")
(def color-bg-hover "#EAEAEA")
(def color-bg-active "lightgray")

(defn textfield-label-styles [type]
  {:position "absolute"
   :top "1rem"
   :left "1rem"
   :pointer-events "none"
   :transition "all 0.5s"
   :color (color-for-status type "black")})

(def textfield-container-styles
  {:position "relative"
   :margin "1rem"
   :cursor "text"})

(defn textfield-input-styles [type]
  {:box-sizing "border-box"
   :border 0
   :width "100%"
   :font-size "1rem"
   :background-color color-bg
   :padding "1rem 1rem 0.5rem 1rem"
   :caret-color (color-for-status type "blue")
   ::stylefy/mode {:focus {:outline "none"
                           :background-color color-bg-active}
                   :focus:hover {:background-color color-bg-active}
                   :hover {:background-color color-bg-hover}
                   :disabled {:cursor "not-allowed"
                              :background-color "lightgray"}
                   :disabled:hover {:cursor "not-allowed"
                                    :background-color "lightgray"}}
   ::stylefy/manual [[:&:focus
                      ["+ label"
                       {:font-size "0.9rem"
                        :top 0
                        :color (color-for-status type "blue")}]]
                     [:&:focus :&:focus:hover
                      ["~ .text-field-border" {:background-color (color-for-status type "blue")
                                               :transition "all 0.5s"}]]
                     [:&:hover
                      ["~ .text-field-border" {:background-color (color-for-status type "black")}]]
                     [:&:disabled
                      ["+ label" {:cursor "not-allowed"
                                  :color "gray"}]]
                     [:&:disabled
                      ["~ .text-field-border" {:cursor "not-allowed"}]]]})

(defn textfield-border-styles [type]
  {:width "100%"
   :height "1px"
   :background-color (color-for-status type "gray")})

(defn textfield-small-style [type]
  {:padding     "1rem"
   :line-height 1
   :color       (color-for-status type "black")})

(defn textfield-styles [type]
  (merge textfield-container-styles
         {::stylefy/sub-styles {:label            (textfield-label-styles type)
                                :label-when-value (merge (textfield-label-styles type) {:top       0
                                                                                        :font-size "0.9rem"})
                                :input            (textfield-input-styles type)
                                :border           (textfield-border-styles type)
                                :padder           {:padding "1rem 1rem 0.5rem 1rem"}
                                :info             (textfield-small-style type)}}))