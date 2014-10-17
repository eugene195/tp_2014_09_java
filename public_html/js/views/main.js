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
            this.$(".auth, .reg, .profile, .exit").hide();
            this.session.postIdentifyUser('main');
        },
        logout: function (event) {
            //event.preventDefault();
            this.session.postLogout();
        },
        
        userIdentified: function(data) {
            this.$(".profile, .exit").show();
            this.trigger('show', this);
        },
        userNotIdentified: function() {
            this.$(".auth, .reg").show();
            this.trigger('show', this);
        },

        logoutSuccess: function (data) {
            this.trigger('success');
        },

        logoutError: function (message) {
            var elem = this.$(".alert-error").slideDown().delay(3000).slideUp();
            elem.html("<p>" + message + "</p>");
        }
    });

    return new View();
});