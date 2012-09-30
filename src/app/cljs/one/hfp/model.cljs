(ns ^{:doc "Contains client-side state, validators for input fields
 and functions which react to changes made to the input fields."}
 one.hfp.model
  (:require [one.dispatch :as dispatch]
            [one.hfp.logging :as log]))
            
(def project-list "exPList")
(def project-detail "exPArea-1")
(def project-detail "exPArea-2")

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






