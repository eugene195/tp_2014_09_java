define([
    'backbone',
    'views/main',
    'views/game',
    'views/login',
    'views/scoreboard',
    'views/register',
    'views/profile',
    'views/viewman'
], function(
    Backbone,
    main,
    game,
    login,
    scoreboard,
    register,
    profile,
    viewman
    
){
    viewman.subscribe([main, game, login, scoreboard, register, profile])

    var Router = Backbone.Router.extend({
        initialize: function () {
            this.listenTo(login, 'success', this.toIndex);
            this.listenTo(main, 'success', this.toIndex);
        },

        routes: {
            '': 'index',
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'register': 'registerAction',
            'profile': 'profileAction',
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
        toIndex: function () {
            this.navigate('', {trigger: true});
        },
        toLogin: function () {
            this.navigate('login', {trigger: true});
        }
    });

    return new Router();
});