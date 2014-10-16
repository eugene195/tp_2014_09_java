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
                this.listenTo(views[I], 'show', this.add);
            }
        },

        unsubscribe: function (view) {
            this.stopListening(view);
        },

        add: function (view) {
            this.$el.html(view.render());
        }

    });

    return new Manager();
});