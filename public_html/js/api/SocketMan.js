function Message() {
    this.data = data;
    this.status = status;
    this.action = action;
    this.receiver = receiver;
//    Example: data(playername) status(ASK) action(move) receiver(engine)
//  Statuses: ask, answer_OK, answer_FAIL
}

function SocketListener(name) {
//    Это логин пользователя
    this.name = name;
}

function SicketMan() {
     this.ws = null;
     this.listener = null;

     this.ws.onopen = function (event) {

     }

     this.init = function(listener) {
        this.ws = new WebSocket("ws://localhost:8081/gameplay");
        this.listener = listener;
     }

     this.ws.onmessage = function (event) {
         var data = JSON.parse(event.data);
         if(data.status == "players"){

         }

         if(data.status == "finish"){

         }
     }

     this.ws.onclose = function (event) {

     }

     function sendMessage(message) {
         this.ws.send(message);
     }
}