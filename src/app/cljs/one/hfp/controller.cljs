(ns ^{:doc "Respond to user actions by updating local and remote
  application state."}
  one.hfp.controller
  (:use [one.browser.remote :only (request)]
        [one.hfp.model :only (state)])
  (:require [cljs.reader :as reader]
            [clojure.browser.event :as event]
            [one.dispatch :as dispatch]
            [goog.uri.utils :as uri]
            [one.hfp.logging :as log]
            [one.hfp.view :as view]))

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
  (reset! state {:state :init})
)
  
(defmethod action :open_projs [_]
  (when-not (#{:open_projs :init} (:state @state))
  (swap! state assoc :state :open_projs))
)

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
                   
(dispatch/react-to #{:load_projs} (fn [t d] (get-init-data (get d 0)))
)
                  
(defn host
  []
  (uri/getHost (.toString window.location ())))

(defn remote
  [f data on-success]
  (request f (str (host) "/remote")
           :method "POST"
           :on-success #(on-success (reader/read-string (:body %)))
           :on-error #(swap! state assoc :error "Error communicating with server.")
           :content (str "data=" (pr-str {:fn f :args data}))))                  
                   
(defn init-proj-callback
  "This is the success callback function which will be called when a
  initiating the application's state. The main purpose is to get Projects titles and details from the 
  server store."
  [response] 
  ;;DEBUG(log/log (str "------> Projects " (pr-str (get (get (response :plist) 0) 0) )))
  (view/load-projects (response :plist)) 
)

(defn get-init-data
  "Call back to server for initial project data." 
  [cbname] 
  (remote :get-project {:cbname cbname} #(init-proj-callback % ))
)