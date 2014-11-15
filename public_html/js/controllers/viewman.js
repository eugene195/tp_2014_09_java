/**
 * Created by max on 16.10.14.
 */

define([
    'backbone'
], function (Backbone) {

    var Manager = {

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
            view.render();
            this.reshow(view);
        },

        triggerView: function (eventName) {
            this.trigger(eventName, this.currentView);
        },

        reshow: function (view) {
            if (this.currentView) {
                this.triggerView('view-hide');
                this.currentView.$el.hide();
            }
            this.currentView = view;
            this.triggerView('view-show');
            view.$el.show();
        }

    };

    return _.extend(Manager, Backbone.Events);
});