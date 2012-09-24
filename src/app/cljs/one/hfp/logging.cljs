(ns ^{:doc "When this library is loaded, create a logger named
'events' and send all application-specific events to it.

To view log messages in the browser console, add a call
to `(log/console-output)` to this namespace or evaluate this from the
REPL.

For more information see library.logging."}
  one.hfp.logging
  (:require [one.dispatch :as dispatch]
            [one.logging :as log]))

(def ^{:doc "The logger that receives all application-specific events."}
  logger (log/get-logger "events"))

(dispatch/react-to (constantly true)
                   (fn [t d] (log/info logger (str (pr-str t) " - " (pr-str d)))))
                   
;;str 

(defn start-log [log-name]
  (def hfp-log (log/get-logger log-name))
  (def console-out (log/console-output))
  (log/start-display console-out)
  (log/info hfp-log "Logging started"))
  
(defn log [message]
  (log/info hfp-log message))
  
(comment
  ;; log to the console
  (log/start-display (log/console-output))
  ;; log to to the "fancy" window
  (log/start-display (log/fancy-output "main"))
  ;; change the logging level
  (log/set-level logger :fine)
  )
