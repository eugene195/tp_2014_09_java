define([
    'backbone',
    'tmpl/gamelist',
    'controllers/socketman'
], function(
    Backbone,
    tmpl,
    SocketMan
){


var GameList = Backbone.View.extend({
    controller: SocketMan,
    el: $('.gamelist'),
    template: tmpl,

    events: {
        "click #gmjoin": "gameClick"
    },

    initialize: function() {
        this.render();
        this.$el.hide();
    },

    render: function () {
        this.$el.html(this.template());
    },

    playerAdded: function(data) {
        if (data[status] == "OK")
            alert("Please wait for enemies, your snake is #" + data[snakeId]);
    },

    gameStarted: function(data) {
        alert("Your game starts");
        this.trigger('start');
    },

    show: function () {
        this.trigger('rerender', this);
        this.listenTo(this.controller, 'notifyStart', this.gameStarted);
        this.controller.setGameSocket();
    },

    gameClick: function(event) {
        var message = {
            action: "addUser"
        }
        message = JSON.stringify(message);
        this.controller.sendMessage(message);
    },
});
    return new GameList();
});