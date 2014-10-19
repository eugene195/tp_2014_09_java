/**
 * Created by max on 16.10.14.
 */

define([
    'backbone'
], function (Backbone) {

    var Manager = Backbone.View.extend({
        el: $('#page'),

        subscribe: function (views) {
            for (var I in views) {
                this.listenTo(views[I], 'rerender', this.rerender);
                this.listenTo(views[I], 'reshow', this.reshow);
            }
        },

        unsubscribe: function (view) {
            this.stopListening(view);
        },

        rerender: function (view) {
            this.$el.html(view.render());
        },

        reshow: function (view) {
            this.$el.html(view.$el);
        }

    });

    return new Manager();
});