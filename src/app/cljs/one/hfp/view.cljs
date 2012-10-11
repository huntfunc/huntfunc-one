(ns ^{:doc "Render the views for the application."}
  one.hfp.view
  (:use [domina :only  (nodes single-node by-id by-class children
                       common-ancestor ancestor? clone append!
                       prepend! detach!  destroy!  destroy-children!
                       insert!  insert-before!  insert-after!
                       swap-content!  style attr set-style! set-attr!
                       styles attrs remove-attr!  set-styles!
                       set-attrs! has-class?  add-class!
                       remove-class!  classes set-classes!  text
                       set-text! value set-value! html set-html!
                       set-data! get-data log-debug log)]
        [domina.xpath :only (xpath)]
        [one.browser.animation :only (play)])
		
  (:require-macros [one.hfp.snippets :as snippets])
  (:require [goog.events.KeyCodes :as key-codes]
            [goog.events.KeyHandler :as key-handler]
            [goog.events :as g-events]
            [clojure.browser.event :as event]
            [clojure.string :as string]
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
  (log/log "start app")
  (#(dispatch/fire :load_projs ["init"]))
  (add-expand-fold-listener "exPList")
)
  
(defmethod render :open_projs [_]
  (log/log "open projects")
  (fx/p-list-show "projectArea")
  (loop [i 0]
    (add-expand-fold-listener (str "exPArea-" (+ i 1)))
    (if (< i (- (count (children (xpath "//div[@id='projectArea']"))) 1))
      (recur (inc i))))    
)
  
(defmethod render :close_projs [_]
  (log/log "close projects")
  (fx/p-list-hide "projectArea")
  (loop [i 0]
    (remove-expand-fold-listener (str "exPArea-" (+ i 1)))
    (if (< i (- (count (children (xpath "//div[@id='projectArea']"))) 1))
      (recur (inc i))))  
)
  
(defmethod render :open_detail [_]
  (log/log (str "open a project detail"))
  ;;(fx/p-list-show "projectArea") TODO - Html layout and special effects 
)
  
(defmethod render :close_detail [_]
  (log/log "close details")
  ;;(fx/p-list-hide "projectArea") TODO - Html layout and special effects
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
				#(dispatch/fire (re-class true ele-id "foldup" "expand")))
)

(defn remove-expand-fold-listener
  "Accepts a ele-id and creates listeners for click events on div
   which will then fire rendering changes"
  [ele-id]
  ;;DEBUG(log/log (str "removing opening listener on: " ele-id))
  (g-events/removeAll (by-id ele-id) "click")
  ;;DEBUG(log/log "done removing listener")
)		

(defn re-class [do-fire? ele-id show-class hide-class]
   (if do-fire?
   (#(dispatch/fire :show_projs [(has-class? (by-id ele-id) hide-class) ele-id])))    
   (if (has-class? (by-id ele-id) hide-class)
     (set-classes! (by-id ele-id) show-class)
	   (set-classes! (by-id ele-id) hide-class))     
)

(defn add-pnode [pTitle pId]
    (append! (xpath "//div[@id='projectArea']") 
      (str "<div id=" (str "pHead" pId) " >"
             "<p class=\"projHead\">" pTitle "</p>"
             "<div id=" (str "exPArea" pId) " class=\"expand\"></div>"
             "<div id=" (str "pDetail" pId) " class=\"closed\"></div>"
             "</div>"))        
)

(defn load-projects [projList]
  (loop [cnt 0]    
     (add-pnode (first (get projList cnt)) (str "-" (+ 1 cnt)))
     (load-proj-details (str "pDetail-" (+ 1 cnt)) (rest (get projList cnt)))
     (if (< cnt (- (count projList) 1))    
       (recur (inc cnt))
      ))
)

(defn load-proj-details [dnode details]
    (append! (xpath (str "//div[@id='" dnode "']"))
      (str "<ul id=\"dl-" dnode "\" class=\"nav\"></ul>"))     
      (loop  [cnt 0]     
        (if (nth (nth details cnt) 0)     
          (append! (xpath (str "//ul[@id='dl-" dnode "']"))
          (str "<li class=\"detailItem\"><a href=" (nth (nth details cnt) 1) " target=\"_blank\">" (nth (nth details cnt) 2) "</li>"))
          (append! (xpath (str "//ul[@id='dl-" dnode "']"))
          (str "<li class=\"detailItem\">" (nth (nth details cnt) 2) " : " (nth (nth details cnt) 1) "</li>")))   
        (if (< cnt (- (count details) 1))
         (recur (inc cnt))))
)
       
(defn current-proj-detail [data] 
  (set-classes! (xpath "//div[@id='projectArea']//div[@class='foldup']") ["expand"])
  (set-classes! (xpath "//div[@id='projectArea']//div[@class='opened']") ["closed"])  
  (if (get data 0)
    (re-class false (str "pDetail-" (get (string/split (get data 1) #"-") 1)) "opened" "closed") 
    (re-class false (str "exPArea-" (get (string/split (get data 1) #"-") 1)) "expand" "foldup"))  
)

(defn set-nav-for-pnode [pId] - TODO Html layout phase
)
   

