#Huntsville Functional Programming Group 
##Project Dashboard - a ClojureScript One project

###NOTE: 4/21/2014 -> This project was an experiment - would like to do more on a ClojureScript Project - maybe one day soon. This project gave me a chance to lean the basics

This project is an experimental take off on the ClojureScript One "sample" project. The original 
ClojureScript One project can be found [here](https://github.com/brentonashworth/one/wiki). The [goals] (https://github.com/huntfunc/huntfunc-one/wiki/huntfunc-one-project-wiki)
and the [road map] (https://github.com/huntfunc/huntfunc-one/wiki/Road-Map) of this project are in the wiki. Feel free to add comments 
and/or ask to be an owner or a collaborator for this project.

This project replaces the application called "sample" in the original ClojureScript One download.
Download this project into a directory call "hfp-one" instead of "one" the default install for ClojureScript One.
The original project can be loadeded from [here] (https://github.com/brentonashworth/one).

If lein is already installed on your machine, run the command "lein bootstrap" then "lein repl" from the directory 
"hfp-one" just as you would to run the sample 
application in the directory "one".

This project is all still very much experimental and has a lot of needed work (see the [road map](https://github.com/huntfunc/huntfunc-one/wiki/Road-Map)). Below
is some of the documentation for the ClojureScript One getting started.

## Is this a library or a framework?

ClojureScript One is hard to classify. It is not a library or a
framework. It is more like a classroom, a laboratory or a starter
kit. Frameworks limit you to a specific way of thinking. Libraries
attempt to do something for you. We hope that this project will help
you to think of things that no one has ever thought of and empower you
to do things that you may not have thought possible. But most of all,
we hope that it will show you how much fun web development can be in
ClojureScript.

The intended use of the project is:
1. Get all the tools running
2. Read through the wiki, running all the examples
3. Fire up a browser-connected REPL and explore the sample application
3. Use this project as a starting point for your own applications
4. Contribute what you have learned back to this project

# Getting started

You will need to have Java, [lein][] and git installed. Execute the
following commands to install and run One:

    git clone https://github.com/brentonashworth/one.git
    cd one
    lein bootstrap
    lein repl

At the REPL prompt which appears, type `(go)`. Your browser will
launch and navigate to the running application.

Once you have this running, see the [wiki][] and the [website][] for
more information.

### Getting an exception when you run `lein repl`?

If you get an exception when you run `lein repl`, try 
[this workaround](https://github.com/brentonashworth/one/wiki/lein-repl-Problem-Workaround).

# Getting Help

The best place to get help is on the
[Clojure Mailing List](https://groups.google.com/group/clojure). You
can also log issues in the [project issue tracker][issues].

# Contributing

ClojureScript One welcomes help from the community in the form of pull
requests, [bug reports][issues], [wiki][wiki] updates, and hugs. If
you wish to contribute code, please read [How We Work][how-we-work].
In particular, note that pull requests should target the
`pull-requests` branch, not `master`.

# Known Issues

* ClojureScript One supports developing under Windows if you are using
  Chrome, Firefox, or IE9. Versions of Internet Explorer previous to 9
  are not supported at this time.
* Everything on the
  [project issues list](https://github.com/brentonashworth/one/issues).

# One last rant

ClojureScript is designed to make client-service
applications. Traditional web applications run mostly on a server with
a small amount of UI code running on the client. The problem with this
kind of application is that there is a big giant network right in the
middle of your application. We have learned how to deal with this so
well that we actually think this is a good way to write software. There
are many applications for which this is a good approach. But there are
also many applications which would be better as client-service applications
where the entire application runs in the client and uses backend services
which can easily be thought of as other applications.

The reason we haven't used the right tool for the job in the past is
because it was much harder than it should have been. For Clojure
developers, ClojureScript has changed this. ClojureScript allows us to
write very large applications that run on any JavaScript platform. Not
only can we do it, but the experience is better than any other
environment can offer, even JavaScript.

ClojureScript allows us to connect to and modify running
applications, communicate with the server using only Clojure data, run
ClojureScript code in the browser from the server, use protocols to
make existing JavaScript play nice and much, much more.

# License

Copyright © 2012 Brenton Ashworth and Relevance, Inc

Distributed under the Eclipse Public License, the same as Clojure uses. See the file COPYING.

[ClojureScript]: https://github.com/clojure/clojurescript
[lein]: https://github.com/technomancy/leiningen
[wiki]: https://github.com/brentonashworth/one/wiki
[website]: http://clojurescriptone.com
[how-we-work]: https://github.com/brentonashworth/one/wiki/HowWeWork
[issues]: https://github.com/brentonashworth/one/issues

