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
//            Data needs to be loaded afterwards. Will fix it after we use confirm
                debugger;
                var inData = JSON.parse(event.data);
                var action = inData.action;
                var data = inData.data;
                this.trigger(action, data);
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
            var message = {
                action: "loaded"
            };
            this.sendMessage(message);
        },

        sendMessage : function(message) {
            message = JSON.stringify(message);
            this.ws.send(message);
        }
    };
    return _.extend(SocketMan, Backbone.Events);
});