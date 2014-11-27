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
        "submit form[name=gamelist-form]": "createGame",
        "click .sessions__entry": "addUser"
    },

    initialize: function() {
        this.render();
        this.$el.hide();

        this.collection.on('reset', this.render, this);
        this.controller.on('notifyStart', this.gameStarted, this);

        this.session.on('gamelist:anonymous', this.userNotIdentified, this);
        this.session.on('gamelist:known', this.userIdentified, this);
    },

    render: function () {
        var sessions = this.collection.toJSON();
        this.$el.html(this.template(sessions));
    },

    show: function () {
        this.session.postIdentifyUser('gamelist');
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
        var target = event.target;
        var playersCnt = $('#players-cnt').html();
        var width, height;
        var size = target[0].value;
        if (size == 1)
            width = height = 100;
        else if (size == 2)
            width = height = 125;
        else
            width = height = 150;
        var speed = target[1].value;
        var launchTime = target[2].value;

        playersCnt = parseInt(playersCnt);

        this.controller.sendMessage({
            action: "startGameSession",
            playersCnt: playersCnt,
            width: width,
            height: height,
            speed: speed,
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
        this.trigger('start');
    }
});
    return new GameList();
});