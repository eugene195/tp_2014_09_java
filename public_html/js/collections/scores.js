define([
    'backbone',
    'models/score'
], function(
    Backbone,
    ScoreModel
){

    var ScoreCollection = Backbone.Collection.extend({
        model: ScoreModel,
        URL: "/scores",

        fetch: function (success) {
        	var that = this;
            $.ajax({
                url: "/scores",
                type: "POST",
                dataType: "json"
            }).done(function (response) {
                if (response.status == 1) {
                    that.reset(response.bestScores);
                	success();
                }
            
            });
        }
    });

    return new ScoreCollection();
});