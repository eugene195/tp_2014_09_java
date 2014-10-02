define([
    'backbone',
    'tmpl/scoreboard',
    'collections/scores'
], function(
    Backbone,
    tmpl,
    ScoreCollection
){

var View = Backbone.View.extend({

        template: tmpl,
        el: $('#page'),
        events: {

        },

        initialize: function () {
            //this.render();
        },
        render: function () {
            this.$el.html(this.template());
            ScoreCollection.fetch();
        },
        show: function () {

        },
        hide: function () {
            // TODO
        },
    });


    return new View();
});