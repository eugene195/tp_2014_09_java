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
            this.hidePrev();
            main.show();
        },
        scoreboardAction: function () {
            this.hidePrev();
            scoreboard.show();
        },
        gameAction: function () {
            this.hidePrev();
            game.show();
        },
        loginAction: function () {
            this.hidePrev();
            login.show();
        },
        registerAction: function () {
            this.hidePrev();
            register.show();
        },
        profileAction: function () {
            this.hidePrev();
            profile.show();
        },
        hidePrev: function () {
            main.hide();
            scoreboard.hide();
            game.hide();
            login.hide();
            register.hide();
            profile.hide();
        }
    });

    return new Router();
});