(ns ^{:doc "Contains client-side state, validators for input fields
 and functions which react to changes made to the input fields."}
 one.hfp.model
  (:require [one.dispatch :as dispatch]
            [one.hfp.logging :as log]))
            
(def project-list "exPList")
(def project-detail "exPArea-1")
(def project-detail "exPArea-2")

(def projList (list "Virtual Lending Library" "HuntFunc Project Dashboard"))
(def details-2 (list (list "https://github.com/huntfunc/huntfunc-one/wiki/huntfunc-one-project-wiki" "Description")
                   (list "https:/github.com/huntfunc/huntfunc-one" "GitHub Link")
                   (list "https://github.com/huntfunc/huntfunc-one/wiki/Road-Map" "Road Map")))
                   
(def ^{:doc "An atom containing a map which is the application's current state."}
  state (atom {}))

(add-watch state :state-change-key
           (fn [k r o n]
             (dispatch/fire :state-change n))
)

(dispatch/react-to #{:show_projs} (fn [t d] (  if (= (get d 1) project-list)
                                               (if  (get d 0)
                                                 (swap! state assoc :state :open_projs)
                                                 (swap! state assoc :state :close_projs))
                                               (if  (get d 0)
                                                 (swap! state assoc :state :open_detail)
                                                 (swap! state assoc :state :close_detail))                                                 
                                            ))
)






