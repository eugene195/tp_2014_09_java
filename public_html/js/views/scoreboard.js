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
        events: {

        },

        initialize: function () {
            this.collection.on("reset", this.render, this);
            this.render();
        },
        render: function () {
            var scores = this.collection.toJSON();
            this.$el.html(this.template(scores));
            return this.$el;
        },
        show: function () {
            this.collection.fetch();
            this.trigger('rerender', this);
        }
    });


    return new View({collection: scoreCollection});
});