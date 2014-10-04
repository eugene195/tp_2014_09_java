define([
    'backbone',
    'tmpl/scoreboard',
    'collections/scores'
], function(
    Backbone,
    tmpl,
    scoreCollection
){

var View = Backbone.View.extend({

        template: tmpl,
        el: $('#page'),
        events: {

        },

        initialize: function () {
            this.collection.on("reset", this.render, this);
        },
        render: function () {
            var scores = this.collection.toJSON();
            this.$el.html(this.template(scores));
        },
        show: function () {
            this.collection.fetch();
        },
        hide: function () {
            // TODO
        },
    });


    return new View({collection: scoreCollection});
});