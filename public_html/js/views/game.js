define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

var View = Backbone.View.extend({

        template: tmpl,
        el: $('#page'),
        events: {
            "click #gmscr": "gameClick"
        },

        initialize: function () {
            // TODO
        },
        render: function () {
            this.$el.html(this.template());
        },
        show: function () {

        },
        hide: function () {
            // TODO
        },
        gameClick: function(event) {
            alert("Great shot");
        }

    });


    return new View();
});