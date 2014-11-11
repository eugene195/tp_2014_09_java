define([
    'backbone'
], function(
    Backbone
){
    var SocketMan = {
        SOCKET_URL : "ws://localhost:8081/gameplay",

        setGameSocket : function() {
            if (this.ws) return;

            this.ws = new WebSocket(this.SOCKET_URL);
            this.ws.onopen = function (event) {
                
            }.bind(this);

            this.ws.onclose = function (event) {

            };

            this.ws.onmessage = function (event) {
                this.trigger('startLoad');
                var data = JSON.parse(event.data);
                var action = JSON.parse(event.action);
                this.trigger(action, data);
                if (action == 'startGame')
                    this.confirm();
            }.bind(this);
        },

        dropSocket : function () {
    //        ws.onclose();
            delete ws;
        },

        loadData : function () {
    //        var kind = 'startGame' || 'tick' || ...;

            this.trigger(kind, data);
            this.confirm();
        },

        confirm : function () {
            // send confirm
        },

        sendMessage : function(message) {
//            this.
            this.ws.send(message);
        }
    };
    return _.extend(SocketMan, Backbone.Events);
});