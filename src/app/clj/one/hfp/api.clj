(ns one.hfp.api
  "The server side of the hfp application. Provides a simple API for
  updating an in-memory database."
  (:use [compojure.core :only (defroutes POST)]))

(defonce ^:private next-id (atom 0))

(defonce ^:dynamic *database* (atom #{}))

;; Place holder for a database - see Road Map ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; ;;  The detail list item schema - [isLink? : link or text : lable text]  

(def p-1  ["HuntFunc Project Dashboard"
                  [true "https://github.com/huntfunc/huntfunc-one/wiki/huntfunc-one-project-wiki" "Description"]
                  [true "https:/github.com/huntfunc/huntfunc-one" "GitHub Link"]
                  [true "https://github.com/huntfunc/huntfunc-one/wiki/Road-Map" "Road Map"]
                  [true "https://github.com/huntfunc/huntfunc-one/issues" "Issues"]
                  [false "In Development" "Status"]])
(def p-2  ["Virtual Lending Library"
                  [true "https://github.com/huntfunc/huntfunc-one/wiki/The-Virtual-Lending-Library" "Description"]
                  [true "/images/vllUML.png" "A UML (sorta) of the VLL project"]
                  [true "/images/vllUseCase.png" "A use case for the VLL project"]
                  [false "Proposed" "Status"]])
(def p-3  ["--------- Test 3 ---------"
                  [true "https://github.com/huntfunc/huntfunc-one/wiki/huntfunc-one-project-wiki" "Description"]
                  [false "In Development" "Status"]])
(def p-4  ["--------- Test 4 ----------"
                  [true "https://github.com/huntfunc/huntfunc-one/wiki/The-Virtual-Lending-Library" "Description"]
                  [true "/images/vllUML.png" "A UML (sorta) of the VLL project"]
                  [false "Proposed" "Status"]])                  
                  
;;(def projList [p-1 p-2 p-3 p-4])
;; TODO - There are some layout issues with size of title and links to be addressed after database is 
;;        developmed: See Road Map in the GitHub wiki
;;
(def projList [p-1 p-2])
                 
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; 

(defmulti remote
  "Multimethod to handle incoming API calls. Implementations are
  selected based on the :fn key in the data sent by the client.
  Implementation are called with whatever data struture the client
  sends (which will already have been read into a Clojure value) and
  can return any Clojure value. The value the implementation returns
  will be serialized to a string before being sent back to the client."
  :fn)

(defmethod remote :default [data]
  {:status :error :message "Unknown endpoint."})

(defmethod remote :get-project [data]
  {:plist projList})

(defroutes remote-routes
  (POST "/remote" {{data "data"} :params}
        (pr-str
         (remote
          (binding [*read-eval* false]
            (read-string data))))))

  