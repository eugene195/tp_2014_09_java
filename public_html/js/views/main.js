define([
    'backbone',
    'tmpl/main',
    'models/user'
], function(
    Backbone,
    tmpl,
    userModel
){

    var View = Backbone.View.extend({
        el: $('.main'),
        template: tmpl,
        user: userModel,
        events: {
            "click .logout": "logout"
        },
        initialize: function () {
            this.listenTo(this.user, 'main:anonymous', this.userNotIdentified);
            this.listenTo(this.user, 'main:known', this.userIdentified);
            this.listenTo(this.user, 'successLogout', this.logoutSuccess);
            this.listenTo(this.user, 'errorLogout', this.logoutError);
            this.render();
            this.$el.hide();
        },

        render: function () {
            this.$el.html(this.template());
        },

        show: function () {
            this.user.identifyUser('main');
        },
        logout: function (event) {
            event.preventDefault();
            this.user.logout();
        },
        
        userIdentified: function(data) {
            this.$(".auth, .reg").hide();
            this.$(".to_profile, .exit").show();
            this.trigger('reshow', this);
        },
        userNotIdentified: function() {
            this.$(".to_profile, .exit").hide();
            this.$(".auth, .reg").show();
            this.trigger('reshow', this);
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