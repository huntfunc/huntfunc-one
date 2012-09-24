(ns ^{:doc "Respond to user actions by updating local and remote
  application state."}
  one.hfp.controller
  (:use [one.browser.remote :only (request)]
        [one.hfp.model :only (state)])
  (:require [cljs.reader :as reader]
            [clojure.browser.event :as event]
            [one.dispatch :as dispatch]
            [goog.uri.utils :as uri]))

(defmulti action
  "Accepts a map containing information about an action to perform.

  Actions may cause state changes on the client or the server. This
  function dispatches on the value of the `:type` key and currently
  supports `:init`, `:open_projs`, and `:close_projs` actions.

  The `:init` action will initialize the appliation's state.

  The `:open_projs` action will update the status atom and open project view.

  The `:open_projs` action will update the status atom and hide project view."
  :type)

(defmethod action :init [_]
  (reset! state {:state :init}))

(defmethod action :open_projs [_]
  (when-not (#{:open_projs :init} (:state @state))
    (swap! state assoc :state :open_projs)))

(defmethod action :close_projs [_]
  (when-not (#{:close_projs :init} (:state @state))
    (swap! state assoc :state :close_projs)))

(defmethod action :open_detail [_]
  (when-not (#{:open_detail :close_projs} (:state @state))
    (swap! state assoc :state :open_detail)))

(defmethod action :close_detail [_]
  (when-not (#{:close_detail :close_projs} (:state @state))
    (swap! state assoc :state :close_detail)))

(dispatch/react-to #{:init :open_projs :close_projs :open_detail :close_detail}
                   (fn [t d] (action (assoc d :type t))))
