define([
    'backbone',
    'tmpl/gamelist',
    'controllers/socketman',
    'collections/gameSessions',
    'models/session'
], function(
    Backbone,
    tmpl,
    SocketMan,
    GamesCollection,
    sessionModel
){


var GameList = Backbone.View.extend({
    controller: SocketMan,
    collection: GamesCollection,
    session: sessionModel,

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

        this.session.on('gamelist:anonymous', this.userNotIdentified);
        this.session.on('gamelist:known', this.userIdentified);
    },

    render: function () {
        var sessions = this.collection.toJSON();
        this.$el.html(this.template(sessions));
    },

    show: function () {
        this.session.postIdentifyUser('profile');
    },

    userIdentified: function () {
        this.controller.setGameSocket();
        this.update();
    },

    userNotIdentified: function () {
        this.trigger('anonymous');
    },

    update: function() {
        this.collection.fetch();
        this.trigger('rerender', this);
    },

    createGame: function(event) {
        event.preventDefault();

        var target = event.target,
            playersCnt = target[0].value,
            launchTime = target[1].value;

        playersCnt = parseInt(playersCnt);

        this.controller.sendMessage({
            action: "startGameSession",
            playersCnt: playersCnt,
            launchTime: launchTime
        });
        this.update();
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