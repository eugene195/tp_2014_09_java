define([
    'backbone',
    'models/score'
], function(
    Backbone,
    ScoreModel
){

    var ScoreCollection = Backbone.Collection.extend({
        model: ScoreModel,
        url: "/scores",

        parse: function (response) {
            if (response.status == 1) {
                this.reset(response.bestScores);
            }
        },

        comparator: function (model) {
            return -model.get('score');
        }
    });

    return new ScoreCollection();
});