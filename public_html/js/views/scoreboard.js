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
            this.collection.bind("reset", this.render, this);
        },
        render: function () {
            this.collection.fetch();
            var renderedContext = this.template(this.collection.toJSON());
            this.$el.html(renderedContext);
        },
        show: function () {

        },
        hide: function () {
            // TODO
        },
    });


    return new View({collection: scoreCollection});
});