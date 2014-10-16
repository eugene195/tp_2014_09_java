define([
    'backbone',
    'tmpl/register',
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
            "click input[name=submit]": "registerClick",
            "click input[name=login]": "loginClick",
            "click input[name=passw]": "passwClick",
            "click input[name=confirm_passw]": "confirmPasswClick",
            "blur input[name=login]": "loginBlur",
            "blur input[name=passw]": "passwBlur",
            "blur input[name=confirm_passw]": "confirmPasswBlur"
        },

        initialize: function () {
            this.listenTo(this.session, 'successReg', this.registerSuccess);
            this.listenTo(this.session, 'errorReg', this.registerError);
            this.render();
        },

        render: function () {
            this.$el.html(this.template());
            return this.$el;
        },

        show: function () {
            this.trigger('show', this);
        },

        registerClick: function(event) {
            event.preventDefault();
            var username = this.$("input[name=login]").val(),
                newPassw = this.$("input[name=passw]").val(),
                confirmPassw = this.$("input[name=confirm_passw]").val();

            var btnSubmit = this.$("input[name=submit]");
            btnSubmit.addClass("form__footer__button_disabled").prop('disabled', true).delay(1700).queue(
                function(next) {
                    $(this).attr('disabled', false);
                    $(this).removeClass("form__footer__button_disabled");
                    next();
                }
            );
       
            if (this.validate(username, newPassw, confirmPassw)) {
                this.session.postReg({
                    username: username,
                    newPassw: newPassw,
                    confirmPassw: confirmPassw,
                    url: this.$('.form').data('action')
                });   
            }
        },

        registerSuccess: function() {
            this.trigger('success');
        },

        registerError: function(message) {
            this.showError(message, ".register-error")
        },

        showError: function(message, div_error) {
            var elem = this.$(div_error).slideDown().delay(3000).slideUp();
            elem.append("<p>" + message + "</p>");
        },

        validate: function(username, newPassw, confirmPassw) {
            if (username == '') {
                this.showError("Missing username", ".register-error");
                return false;
            }
            if (newPassw == '') {
                this.showError("Missing password", ".passw-error");
                return false;
            }
            if (confirmPassw == '') {
                this.showError("Missing confirm password", ".confirm-passw-error");
                return false;
            }
            if (newPassw != confirmPassw) {
                this.showError("Passwords does not match", ".confirm-passw-error");
                return false;
            }
            return true;
        },

        loginClick: function() {
            this.$(".form__content__user-icon").css("left","-48px");
        },
        passwClick: function() {
            this.$(".form__content__pass-icon").css("left","-48px");
        },
        confirmPasswClick: function() {
            this.$(".form__content__pass-icon2").css("left","-48px");
        },
        loginBlur: function() {
            this.$(".form__content__user-icon").css("left","0px");
        },
        passwBlur: function() {
            this.$(".form__content__pass-icon").css("left","0px");
        },
        confirmPasswBlur: function() {
            this.$(".form__content__pass-icon2").css("left","0px");
        }
    });
    return new View();
});