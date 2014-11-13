define([
    'backbone',
    'tmpl/gamelist',
    'controllers/socketman',
    'collections/gameSessions'
], function(
    Backbone,
    tmpl,
    SocketMan,
    GamesCollection
){


var GameList = Backbone.View.extend({
    controller: SocketMan,
    collection: GamesCollection,

    el: $('.gamelist'),
    template: tmpl,

    events: {
        "submit .new-game__form": "createGame",
        "click .sessions__entry": "addUser"
    },

    initialize: function() {
        this.render();
        this.$el.hide();

        this.collection.on('reset', this.render, this);
        this.controller.on('notifyStart', this.gameStarted, this);
    },

    render: function () {
        var sessions = this.collection.toJSON();
        this.$el.html(this.template(sessions));
    },

    show: function () {
        this.controller.setGameSocket();
        this.collection.fetch();
        this.trigger('rerender', this);
    },

    playerAdded: function(data) {
        if (data[status] == "OK")
            alert("Please wait for enemies, your snake is #" + data[snakeId]);
    },

    gameStarted: function(data) {
        alert("Your game starts");
        this.trigger('start');
    },

    gameClick: function(event) {
        var message = {
            action: "addUser"
        };
        message = JSON.stringify(message);
        this.controller.sendMessage(message);
    },
});
    return new GameList();
});