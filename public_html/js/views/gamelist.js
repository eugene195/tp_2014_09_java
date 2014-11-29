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
        "click .sessions__entry": "addUser",
        "click #addPlayer": "addPlr",
        "click #subPlayer": "subPlr"
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
        this.update();
    },

    userIdentified: function () {
        this.controller.setGameSocket();
    },

    userNotIdentified: function () {
        this.trigger('anonymous');
    },

    update: function() {
        debugger;
        this.collection.fetch();
        this.trigger('rerender', this);
    },

    createGame: function(event) {
        var sizes = {
            1: {h: 100, w: 100},
            2: {h: 110, w: 110},
            3: {h: 110, w: 120}
        };

        event.preventDefault();
        var target = event.target;

        var playersCnt = $('#players-cnt').html();
        playersCnt = parseInt(playersCnt);

        var sizeCode = target[2].value;
        var width = sizes[sizeCode].w, height = sizes[sizeCode].h;

        var speed = target[3].value;
        var launchTime = target[4].value;


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

    addPlr: function () {
        var playersCnt = $('#players-cnt').html();
        playersCnt = parseInt(playersCnt);
        $('#players-cnt').innerHTML = playersCnt++;
    },
//TODO
    subPlr: function () {
        var playersCnt = $('#players-cnt').html();
        playersCnt = parseInt(playersCnt);
        if (playersCnt > 2)
            $('#players-cnt').innerHTML = playersCnt--;
    },

    gameStarted: function(data) {
        this.trigger('start');
    }
});
    return new GameList();
});