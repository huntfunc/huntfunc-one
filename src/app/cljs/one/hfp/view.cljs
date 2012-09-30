(ns ^{:doc "Render the views for the application."}
  one.hfp.view
  (:use [domina :only (append! detach! set-html! set-styles! styles by-id set-style! 
                       add-class! remove-class! set-classes! classes
                       by-class value set-value! set-text! nodes single-node)]
        [domina.xpath :only (xpath)]
        [one.browser.animation :only (play)])
		
  (:require-macros [one.hfp.snippets :as snippets])
  (:require [goog.events.KeyCodes :as key-codes]
            [goog.events.KeyHandler :as key-handler]
            [goog.events :as g-events]
            [clojure.browser.event :as event]
            [one.dispatch :as dispatch]
            [one.hfp.animation :as fx]
			      [one.hfp.logging :as log]
			))

(def ^{:doc "A map which contains chunks of HTML which may be used
  when rendering views."}
  snippets (snippets/snippets))
  
(defmulti render
  "Accepts a map which represents the current state of the application
  and renders a view based on the value of the `:state` key."
  :state)

(defmethod render :init [_]
  (log/start-log "hfp.log")
  (log/log "app started")
  (add-expand-fold-listener "exPList")
  (add_pnode "HuntFunc Project Dashboard" (str "p-head_" 1) (str "exPArea-" 1))
  (add_pnode "Virtual Lending Library" (str "p-head_" 2) (str "exPArea-" 2))

)
  
(defmethod render :open_projs [_]
  (log/log "open projects")
  (fx/p-list-show "projectArea")
  (add-expand-fold-listener (str "exPArea-" 1))
  (add-expand-fold-listener (str "exPArea-" 2))
)
  
(defmethod render :close_projs [_]
  (log/log "close projects")
  (fx/p-list-hide "projectArea")
  (remove-expand-fold-listener (str "exPArea-" 1))
  (remove-expand-fold-listener (str "exPArea-" 2))
)
  
(defmethod render :open_detail [_]
  (log/log "open a project detail")
  ;;(fx/p-list-show "projectArea")
  ;;(add-expand-fold-listener (str "exPArea_" 1))
  ;;(add-expand-fold-listener (str "exPArea_" 2))
)
  
(defmethod render :close_detail [_]
  (log/log "close details")
  ;;(fx/p-list-hide "projectArea")
)
  
(dispatch/react-to #{:state-change} (fn [_ m] (render m))
)

(defn ef-handler [& args] (dispatch/fire (re-class ele-id "foldup" "expand")))
  
(defn add-expand-fold-listener
  "Accepts a ele-id and creates listeners for click events on div
   which will then fire rendering changes"
  [ele-id]
  (log/log (str "adding opening listener on: " ele-id))
  (event/listen (by-id ele-id)
        "click"
				#(dispatch/fire (re-class ele-id "foldup" "expand")))
)

(defn remove-expand-fold-listener
  "Accepts a ele-id and creates listeners for click events on div
   which will then fire rendering changes"
  [ele-id]
  (log/log (str "removing opening listener on: " ele-id))
  (g-events/removeAll (by-id ele-id) "click")
  (log/log "done removing listener")
)		

(defn re-class [ele-id show-class hide-class]
   (#(dispatch/fire :show_projs [(= [hide-class] (classes (by-id ele-id))) ele-id] ))   
   (if (= [hide-class] (classes (by-id ele-id)))
     ((add-class! (single-node (by-id ele-id)) show-class)
       (remove-class! (single-node (by-id ele-id)) hide-class))
	   ((add-class! (single-node (by-id ele-id)) hide-class)
       (remove-class! (single-node (by-id ele-id)) show-class)))       
)

(defn add_pnode [pTitle phead-id pId]
    (append! (xpath "//div[@id='projectArea']") 
      (str "<div id=" phead-id " ><p class=\"projHead\">" pTitle "</p>"
       "<div id=" pId " class=\"expand\"></div></div>"))        
)

;;(classes (single-node (by-id ele-id)))
   

