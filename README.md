# websocket-echo

A Clojure library designed to ... well, that part is up to you.

## Usage

$ lein repl
```
websocket-echo.core=> (run)
;; Starting Server
#<SystemMap>
websocket-echo.core=> (reset)
;; Stopping Server
:reloading (websocket-echo.core websocket-echo.core-test)
;; Starting Server
#<SystemMap>
```

websocket client (javascript)
```
ws = new WebSocket("ws://localhost:9999")
ws.onmessage = function(e) { console.log("received : " + e.data); }
ws.send("test") # > received : test
```
## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
