define([
    'backbone',
    'views/main',
    'views/game',
    'views/login',
    'views/scoreboard',
    'views/register',
    'views/profile'
], function(
    Backbone,
    main,
    game,
    login,
    scoreboard,
    register,
    profile
    
){

    var Router = Backbone.Router.extend({
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'register': 'registerAction',
            'profile': 'profileAction',
            '*default': 'defaultActions'
        },
        defaultActions: function () {
            main.render();
        },
        scoreboardAction: function () {
            scoreboard.render();
        },
        gameAction: function () {
            game.render();
        },
        loginAction: function () {
            login.render();
        },
        registerAction: function () {
            register.render();
        },
        profileAction: function () {
            profile.render();
        }
    });

    return new Router();
});