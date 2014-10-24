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
        el: $('.main'),
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
            this.$el.hide();
        },

        render: function () {
            this.$el.html(this.template());
        },

        show: function () {
            debugger;
            this.session.postIdentifyUser('main');
        },
        logout: function (event) {
            debugger;
            event.preventDefault();
            this.session.postLogout();
        },
        
        userIdentified: function(data) {
            this.$(".auth, .reg").hide();
            this.$(".to_profile, .exit").show();
            this.trigger('reshow', this);
        },
        userNotIdentified: function() {
            this.$(".to_profile, .exit").hide();
            this.$(".auth, .reg").show();
            debugger;
            this.trigger('reshow', this);
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