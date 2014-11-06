define([
    'backbone',
    'tmpl/snakeGame'
], function(
    Backbone,
    tmpl
){

var View = Backbone.View.extend({
        el: $('.snakeGame'),
        template: tmpl,

        events: {

        },

        initialize: function() {
            this.render();
            this.$el.hide();
        },
        render: function () {
            this.$el.html(this.template());
        },
        show: function () {
            this.trigger('reshow', this);
        },

    });


    return new View();
});