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
        el: $('#page'),
        events: {
            "click #logout": "logout",
        },
        initialize: function () {
            this.listenTo(this.session, 'userNotIdentified', this.userNotIdentified);
            this.listenTo(this.session, 'userIdentified', this.userIdentified);
            this.listenTo(this.session, 'successLogout', this.show);
            this.listenTo(this.session, 'errorLogout', this.showErrorLogout);
        },
        render: function () {
            this.$el.html(this.template());
            this.session.postIdentifyUser();
        },
        show: function () {
            this.render();
        },
        hide: function () {
            // TODO
        },
        logout: function (event) {
            event.preventDefault();
            this.session.postLogout();
        },
        userIdentified: function(data) {
            $("#profile").show();
            $("#exit").show();
            $("#auth").hide();
            $("#reg").hide();
        },
        userNotIdentified: function() {
            $("#profile").hide();
            $("#exit").hide();
            $("#auth").show();
            $("#reg").show();
        },
        showErrorLogout: function(message) {
            $("#logout-error").slideDown().delay(3000).slideUp();
            $("#logout-error-message").html(message);
        },
        
    });

    return new View();
});