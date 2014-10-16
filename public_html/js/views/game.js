define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

var View = Backbone.View.extend({

        template: tmpl,
        events: {
            "click #gmscr": "gameClick"
        },

        initialize: function() {
            this.render();
        },
        render: function () {
            this.$el.html(this.template());
            return this.$el;
        },
        show: function () {
            this.trigger('show', this);
        },
        gameClick: function(event) {
            alert("Great shot");
        }

    });


    return new View();
});