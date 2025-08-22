# socket-spring

A minimal Spring Boot WebSocket (STOMP over SockJS) example with a simple REST endpoint.

## Overview
- WebSocket endpoint: `/ws` (SockJS enabled, CORS: all origins)
- STOMP broker: application prefix `/app`, topic prefix `/topic`
- Message route: send to `/app/chat/{groupId}`; subscribers receive on `/topic/{groupId}`
- REST health check: `GET /hello` → "Hello wold"

## Run locally
- Windows:
  - Gradle wrapper: `gradlew.bat bootRun`
- macOS/Linux:
  - Gradle wrapper: `./gradlew bootRun`

App name is configured via `spring.application.name=kafka_test`.

## Test the REST endpoint
- `GET http://localhost:8080/hello`

## Test WebSocket/STOMP
Use any STOMP client. Example (browser) using SockJS + @stomp/stompjs:

```html
<!doctype html>
<html>
<body>
  <input id="group" value="general" />
  <input id="sender" value="alice" />
  <input id="text" value="hello" />
  <button id="connect">Connect</button>
  <button id="send">Send</button>
  <pre id="log"></pre>
  <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7/dist/stomp.umd.min.js"></script>
  <script>
    let client;
    const log = (m) => document.getElementById('log').textContent += m + "\n";
    document.getElementById('connect').onclick = () => {
      const socket = new SockJS('http://localhost:8080/ws');
      client = new StompJs.Client({ webSocketFactory: () => socket });
      client.onConnect = () => {
        const group = document.getElementById('group').value;
        client.subscribe(`/topic/${group}`, (msg) => log(msg.body));
        log('Connected');
      };
      client.activate();
    };
    document.getElementById('send').onclick = () => {
      const group = document.getElementById('group').value;
      const sender = document.getElementById('sender').value;
      const text = document.getElementById('text').value;
      client.publish({ destination: `/app/chat/${group}`, body: JSON.stringify({ sender, content: text }) });
    };
  </script>
</body>
</html>
```

## Endpoints summary
- `GET /hello` → returns a plain string
- WebSocket handshake: `ws://localhost:8080/ws` (or SockJS fallback at `/ws/**`)
- Send messages: STOMP `SEND` to `/app/chat/{groupId}`
- Subscribe: STOMP `SUBSCRIBE` to `/topic/{groupId}`

## Notes
- CORS for WebSocket is open for demo purposes (`*`). Restrict in production.
- Uses the simple in-memory broker; switch to a full broker (e.g., RabbitMQ) for scale.
