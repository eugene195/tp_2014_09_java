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

        fetch: function () {
            $.ajax({
                url: "/scores",
                type: "POST",
                dataType: "json"
            }).done(function (response) {
                if (response.status == 1)
                    this.reset(response.bestScores);
            });
        }
    });

    return new ScoreCollection();
});