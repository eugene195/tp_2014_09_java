define([
    'backbone',
    'tmpl/login',
    'models/user'
], function(
    Backbone,
    tmpl,
    userModel
){

    var View = Backbone.View.extend({
        el: $('.login'),
        template: tmpl,
        user: userModel,
        events: {
            "submit form[name=login-form]": "authClick",
            "click input[name=login]": "loginClick",
            "click input[name=passw]": "passwordClick",
            "blur input[name=login]": "loginBlur",
            "blur input[name=passw]": "passwordBlur"
        },

        initialize: function () {
            this.listenTo(this.user, 'successAuth', this.loginSuccess);
            this.listenTo(this.user, 'errorAuth', this.loginError);
            this.render();
            this.$el.hide();
        },

        render: function () {
            this.$el.html(this.template());
        },

        show: function () {
            this.trigger('reshow', this);
        },

        authClick: function(event) {
            event.preventDefault();
            var username = this.$("input[name=login]").val(),
                password = this.$("input[name=passw]").val();

            var btnSubmit = this.$("input[name=submit]");
            btnSubmit.prop('disabled', true).addClass("form__footer__button_disabled").delay(1700).queue(
                function(next) {
                    $(this).attr('disabled', false);
                    $(this).removeClass("form__footer__button_disabled");
                    next();
                }
            );

            if (this.validate(username, password)) {
                var url = this.$('.form').data('action');
                this.user.login({
                    login: username,
                    passw: password
                });
            }
        },

        loginSuccess: function(data) {
            this.trigger('success');
        },

        loginError: function(message) {
            this.showError(message, ".login-error");
        },

        showError: function(message, div_error) {
            var elem = this.$(div_error).slideDown().delay(3000).slideUp();
            elem.html("<p>" + message + "</p>");
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
            this.$(".user-icon").css("left","-48px");
        },
        passwordClick: function() {
            this.$(".pass-icon").css("left","-48px");
        },
        loginBlur: function() {
            this.$(".user-icon").css("left","0px");
        },
        passwordBlur: function() {
            this.$(".pass-icon").css("left","0px");
        }
    });

    return new View();
});