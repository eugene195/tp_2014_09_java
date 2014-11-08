define([
    'backbone'
], function(
        Backbone
){
    var SocketMan = Backbone.Controller.extend({
        SOCKET_URL: "ws://localhost:8081/gameplay",

        setSocket: function() {
            if (! this.ws) return;

            this.ws = new WebSocket(this.SOCKET_URL);
            ws.onopen = function (event) {
            //        Чтобы не забыть, что он есть
            };

            ws.onclose = function (event) {

            };

            ws.onmessage = function (event) {
                var data = JSON.parse(event.data);
        //        Сообщение от сервера
            };
        },

        dropSocket: function () {
            ws.onclose();
            delete ws;
        },

        loadData: function () {
            this.trigger('startLoad');

    //        var kind = 'startGame' || 'tick' || ...;

            while (1/* loading */) {
                // form data
                if (error) {
                    this.trigger('errorLoad');
                }
            }
            this.trigger(kind, data);
            this.confirm();
        },

        changeDirection: function (direction) {
    //        form correct data
            var data = [];
            this.sendMessage(data);
        },

        confirm: function () {
            // send confirm
        },

        sendMessage : function(message) {
            this.ws.send(message);
        }
    });

    return new SocketMan();
});