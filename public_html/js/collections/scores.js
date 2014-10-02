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

        fetch: function () {
            $.when (
                $.ajax(url, {
                    dataType: "json"
                })
            ).then (
                $.proxy( function( response ) {
                    if (response.status == 1)
                        ctx.view.collection.reset(response.bestScores);
                },ctx )
            );
        }
    });

    return new ScoreCollection();
});