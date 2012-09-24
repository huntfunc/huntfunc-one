(ns one.hfp.config
  "Contains configuration for the hfp application."
  (:require [net.cgrand.enlive-html :as html]))

(defn- production-transform [h]
  (html/transform h
                  [:ul#navigation]
                  (html/substitute (html/html-snippet ""))))

(def ^{:doc "Configuration for the hfp application."}
  config {:src-root "src"
          :app-root "src/app/cljs"
          :top-level-package "one"
          :js "public/javascripts"
          :dev-js-file-name "main.js"
          :prod-js-file-name "mainp.js"
          :dev-js ["goog.require('one.hfp.core');"
                   "goog.require('one.hfp.model');"
                   "goog.require('one.hfp.controller');"
                   "goog.require('one.hfp.history');"
                   "goog.require('one.hfp.logging');"
                   "one.hfp.core.start();one.hfp.core.repl();"]
          :prod-js ["one.hfp.core.start();"]
          :reload-clj ["/one/host_page"
                       "/one/reload"
                       "/one/templates"
                       "/one/hfp/api"
                       "/one/hfp/config"
                       "/one/hfp/dev_server"]
          :prod-transform production-transform})
