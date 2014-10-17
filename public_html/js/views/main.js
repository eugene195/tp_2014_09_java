define([
    'backbone',
    'tmpl/main',
    'models/session'
], function(
    Backbone,
    tmpl,
    sessionModel
){

    var View = Backbone.View.extend({
        template: tmpl,
        session: sessionModel,
        events: {
            "click .logout": "logout"
        },
        initialize: function () {
            this.listenTo(this.session, 'main:anonymous', this.userNotIdentified);
            this.listenTo(this.session, 'main:known', this.userIdentified);
            this.listenTo(this.session, 'successLogout', this.logoutSuccess);
            this.listenTo(this.session, 'errorLogout', this.logoutError);

            this.render();
        },
        render: function () {
            this.$el.html(this.template());
            return this.$el;
        },
        show: function () {
            this.session.postIdentifyUser('main');
        },
        logout: function (event) {
            event.preventDefault();
            this.session.postLogout();
        },
        
        userIdentified: function(data) {
            var form = this.$('.form');
            form.find(".profile").show();
            form.find(".exit").show();
            form.find(".auth").hide();
            form.find(".reg").hide();
            this.trigger('show', this);
        },
        userNotIdentified: function() {
            var form = this.$('.form');
            form.find(".profile").hide();
            form.find(".exit").hide();
            form.find(".auth").show();
            form.find(".reg").show();
        },

        logoutSuccess: function (data) {
            this.trigger('success');
            this.show();
        },

        logoutError: function (message) {
            var elem = this.$(".alert-error").slideDown().delay(3000).slideUp();
            elem.html("<p>" + message + "</p>");
        }
    });

    return new View();
});