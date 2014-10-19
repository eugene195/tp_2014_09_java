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
            debugger;
            event.preventDefault();
            this.session.postLogout();
        },
        
        userIdentified: function(data) {
            this.trigger('reshow', this);
            this.$(".auth, .reg").hide();
            this.$(".profile, .exit").show();
        },
        userNotIdentified: function() {
            this.trigger('reshow', this);
            this.$(".profile, .exit").hide();
            this.$(".auth, .reg").show();
        },

        logoutSuccess: function (data) {
            debugger;
            this.trigger('success');
            this.show();
        },

        logoutError: function (message) {
            debugger;
            var elem = this.$(".alert-error").slideDown().delay(3000).slideUp();
            elem.html("<p>" + message + "</p>");
        }
    });

    return new View();
});