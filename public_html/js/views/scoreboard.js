define([
    'backbone',
    'tmpl/scoreboard'
], function(
    Backbone,
    tmpl
){

var View = Backbone.View.extend({

        template: tmpl,
        el: $('#page'),
        events: {

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
    });


    return new View();
});