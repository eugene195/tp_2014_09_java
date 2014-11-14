/**
 * Created by max on 13.11.14.
 */

define([
    'backbone'
], function(
    Backbone
){

    var GameSession = Backbone.Model.extend({
        defaults: {
            "playersCnt":  0,
            "players": [],
            "sessioId": 0
        },

        initialize: function() {
        }
    });

    return GameSession;
});