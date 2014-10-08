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
        el: $('.scoreboard'),
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
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
    });


    return new View({collection: scoreCollection});
});