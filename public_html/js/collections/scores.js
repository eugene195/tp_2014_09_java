define([
    'backbone',
    'models/score'
], function(
    Backbone,
    ScoreModel
){

    var ScoreCollection = Backbone.Collection.extend({
        model: ScoreModel,
        url: "/scores"
    });

    return new ScoreCollection();
});