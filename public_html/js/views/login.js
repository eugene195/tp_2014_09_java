define([
    'backbone',
    'tmpl/login',
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
            "click input[name=submit]": "authClick",
            "click input[name=login]": "loginClick",
            "click input[name=passw]": "passwordClick",
            "blur input[name=login]": "loginBlur",
            "blur input[name=passw]": "passwordBlur"
        },

        initialize: function () {
            this.listenTo(this.session, 'successAuth', this.redirectMain);
            this.listenTo(this.session, 'errorAuth', this.handleSessionError);

        },
        render: function () {
            this.$el.html(this.template());
            return this;
        },
        show: function () {
            this.trigger('show', this);
        },
        authClick: function(event) {
            event.preventDefault();
            var username = this.$el.find("input[name=login]").val(),
                password = this.$el.find("input[name=passw]").val();

            var butSubmit = this.$el.find("input[name=submit]").prop('disabled', true).delay(1700).queue(
                function(next) {
                    $(this).attr('disabled', false);
                    next();
                }
            );
            butSubmit.addClass("form__footer__button_disabled").delay(1700).queue(
                function(next) {
                    $(this).removeClass("form__footer__button_disabled");
                    next();
                }
            );

            if (this.validate(username, password)) {
                this.session.postAuth({
                    username: username,
                    password: password,
                    url: this.$el.find('.form').data('action'), 
                });
            }
        },
        redirectMain: function() {
            window.location.replace('#');
        },
        handleSessionError: function(message) {
            this.showError(message, ".login-error");
        },
        showError: function(message, div_error) {
            var elem = this.$el.find(div_error).slideDown().delay(3000).slideUp();
            elem.html('');
            elem.append("<p>" + message + "</p>");
        },
        validate: function(username, password) {
            var status= true;
            if (username == '') {
                status = false;
                this.showError("Missing username", ".login-error");
            }
            if (password == '') {
                status = false;
                this.showError("Missing password", ".passw-error");
            }
            return status;
        },
        loginClick: function() {
            this.$el.find(".form__content__user-icon").css("left","-48px");
        },
        passwordClick: function() {
            this.$el.find(".form__content__pass-icon").css("left","-48px");
        },
        loginBlur: function() {
            this.$el.find(".form__content__user-icon").css("left","0px");
        },
        passwordBlur: function() {
            this.$el.find(".form__content__pass-icon").css("left","0px");
        }
    });

    return new View();
});