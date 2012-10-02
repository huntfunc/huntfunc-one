(ns ^{:doc "Render the views for the application."}
  one.hfp.view
  (:use [domina :only (append! prepend! detach! set-html! set-styles! styles by-id set-style! 
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
            [one.hfp.model :as model]
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
  ;; the following is hard coded for the sake of a prototype these values come from doseq 
  ;; on a list that will be selected from a presisted source on the server 
  ;;(add-pnode "Virtual Lending Library" (str "-" 2))
  ;;(add-pnode "HuntFunc Project Dashboard" (str "-" 1))
  (load-proj-details model/details-2)
  (load-projects model/projList)
  (log/log "after thought")


)
  
(defmethod render :open_projs [_]
  (log/log "open projects")
  (fx/p-list-show "projectArea")
  ;; (doseq will be used to add and remove listeners
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

(defn add-pnode [pTitle pId]
    (prepend! (xpath "//div[@id='projectArea']") 
      (str "<div id=" (str "pHead" pId) " >"
             "<p class=\"projHead\">" pTitle "</p>"
             "<div id=" (str "exPArea" pId) " class=\"expand\"></div>"
             "<div id=" (str "pDetail" pId) "></div>"
             "</div>"))        
)

(defn load-projects [projList]
   (def cntl (- (count projList) 1))
   (loop  [cnt 0]
     (log/log (nth projList cnt))
     (add-pnode (nth projList cnt) (str "-" (+ 1 cnt)))
    (if (>= cnt cntl)
      nil    
      (recur (inc cnt))
      ))
) 

(defn load-proj-details [details]
    (log/log "in test")  
    (def cntl (- (count details) 1))
    (log/log cntl) 
     (loop  [cnt 0]
     (log/log (str (nth (nth details cnt) 0) " : " (nth (nth details cnt) 1)))
    (if (>= cnt cntl)
      nil
      (recur (inc cnt))))
)
     
  
(defn set-nav-for-pnode [pId]
)

;;(classes (single-node (by-id ele-id)))
   

