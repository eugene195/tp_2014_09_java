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
        el: $('.main'),
        events: {
            "click .logout": "logout",
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
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
        logout: function (event) {
            event.preventDefault();
            this.session.postLogout();
        },
        userIdentified: function(data) {
            var elem = this.$el.find('.form');
            elem.find(".profile").show();
            elem.find(".exit").show();
            elem.find(".auth").hide();
            elem.find(".reg").hide();
        },
        userNotIdentified: function() {
            var elem = this.$el.find('.form');
            elem.find(".profile").hide();
            elem.find(".exit").hide();
            elem.find(".auth").show();
            elem.find(".reg").show();
        },
        showErrorLogout: function(message) {
            var elem = this.$el.find(".alert-error").slideDown().delay(3000).slideUp();
            elem.append("<p>" + message + "</p>");
        },
    });

    return new View();
});