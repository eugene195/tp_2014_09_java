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
            //this.collection.bind("reset", this.render, this);
        },
        render: function () {
            var that = this;
            this.collection.fetch(function success() {
                var scores = that.collection.toJSON()
                that.$el.html(that.template(scores));
            });
      
        },
        show: function () {

        },
        hide: function () {
            // TODO
        },
    });


    return new View({collection: scoreCollection});
});