define([
    'backbone',
    'views/main',
    'views/game',
    'views/login',
    'views/scoreboard',
    'views/register'
], function(
    Backbone,
    main,
    game,
    login,
    scoreboard,
    register
){

    var Router = Backbone.Router.extend({
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'register': 'registerAction',
            '*default': 'defaultActions'
        },
        defaultActions: function () {
            $('#page').html(main.$el);
        },
        scoreboardAction: function () {
            $('#page').html(scoreboard.$el);
        },
        gameAction: function () {
            $('#page').html(game.$el);
        },
        loginAction: function () {
            $('#page').html(login.$el);
        },
        registerAction: function () {
            $('#page').html(register.$el);
        }
    });

    return new Router();
});