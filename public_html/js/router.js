define([
    'backbone',
    'views/main',
    'views/game',
    'views/login',
    'views/scoreboard',
    'views/register',
    'views/profile',
    'views/viewman',
    'views/canvas',
    'views/gamelist'
], function(
    Backbone,
    main,
    game,
    login,
    scoreboard,
    register,
    profile,
    viewman,
    canvas,
    gamelist
    
){
    viewman.subscribe([main, game, login, scoreboard, register, profile, canvas, gamelist])

    var Router = Backbone.Router.extend({
        initialize: function () {
            this.listenTo(login, 'success', this.toIndex);
            this.listenTo(main, 'success', this.toIndex);
            this.listenTo(register, 'success', this.toLogin);

            this.listenTo(profile, 'anonymous', this.toLogin);
            this.listenTo(gamelist, 'anonymous', this.toLogin);

            this.listenTo(gamelist, 'start', this.toGame);
        },

        routes: {
            '': 'index',
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'register': 'registerAction',
            'profile': 'profileAction',
            'canvas': 'canvasAction',
            'gamelist': 'gamelistAction',
            '*default': 'defaultActions'
        },

        index: function () {
            main.show();
        },
        defaultActions: function () {
            alert('404');
        },
        scoreboardAction: function () {
            scoreboard.show();
        },
        gameAction: function () {
            game.show();
        },
        loginAction: function () {
            login.show();
        },
        registerAction: function () {
            register.show();
        },
        profileAction: function () {
            profile.show();
        },
        gamelistAction: function () {
            gamelist.show();
        },
        canvasAction: function () {
            canvas.show();
        },

        toIndex: function () {
            this.navigate('', {trigger: true});
        },
        toGame: function () {
            this.navigate('game', {trigger: true});
        },
        toLogin: function () {
            this.navigate('login', {trigger: true});
        }
    });

    return new Router();
});