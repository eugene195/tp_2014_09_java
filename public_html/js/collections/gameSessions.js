    /**
 * Created by max on 13.11.14.
 */

define([
    'backbone',
    'models/gameSession'
], function(
    Backbone,
    GameSession
){

    var GamesCollection = Backbone.Collection.extend({
        model: GameSession,
        url: "/gameList",

        parse: function (response) {
            if (response.status == 1) {
                this.reset(response.sessions);
            }
        }
    });

    return new GamesCollection();
});