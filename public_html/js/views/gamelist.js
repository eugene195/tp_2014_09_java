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

    createGame: function(event) {
        var target = event.target,
            playersCnt = target[0].value,
            launchTime = target[1].value;

        playersCnt = parseInt(playersCnt);

        this.controller.sendMessage({
            action: "startGameSession",
            playersCnt: playersCnt,
            launchTime: launchTime
        });
    },

    addUser: function() {
        var sessionId = 0;
        this.controller.sendMessage({
            action: "addUser",
            sessionId: sessionId
        });
    },

    gameStarted: function(data) {
        alert("Your game starts");
        this.trigger('start');
    }
});
    return new GameList();
});