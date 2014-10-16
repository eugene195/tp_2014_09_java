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
        el: $('.register'),
        events: {
            "click input[name=submit]": "registerClick",
            "click input[name=login]": "loginClick",
            "click input[name=passw]": "passwClick",
            "click input[name=confirm_passw]": "confirmPasswClick",
            "blur input[name=login]": "loginBlur",
            "blur input[name=passw]": "passwBlur",
            "blur input[name=confirm_passw]": "confirmPasswBlur",
        },

        initialize: function () {
            this.listenTo(this.session, 'successReg', this.redirectLogin);
            this.listenTo(this.session, 'errorReg', this.showError);
        },
        render: function () {
            this.$el.html(this.template());
        },
        show: function () {
            this.render();
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
        registerClick: function(event) {
            event.preventDefault();
            var username = this.$el.find("input[name=login]").val(),
                newPassw = this.$el.find("input[name=passw]").val(),
                confirmPassw = this.$el.find("input[name=confirm_passw]").val(),
                wasError = false;

            var butSubmit = this.$el.find("input[name=submit]").prop('disabled', true).delay(1700).queue(
                function(next) { $(this).attr('disabled', false);
                next();
            });
            butSubmit.addClass("form__footer__button_disabled").delay(1700).queue(
                function(next) { $(this).removeClass("form__footer__button_disabled");
                next();
            });

            if (username == '') {
                wasError = true;
                var elem = this.$el.find(".register-error").slideDown().delay(3000).slideUp();
                elem.html('');
                elem.append("<p>Missing username</p>");
            }
            if (newPassw == '') {
                wasError = true;
                var elem = this.$el.find(".passw-error").slideDown().delay(3000).slideUp();
                elem.html('');
                elem.append("<p>Missing password</p>");
            }   
            else if (confirmPassw == '') {
                wasError = true;
                var elem = this.$el.find(".confirm-passw-error").slideDown().delay(3000).slideUp();
                elem.html('');
                elem.append("<p>Missing confirm password</p>");
            }   
            else if (newPassw != confirmPassw) {
                wasError = true;
                var elem = this.$el.find(".confirm-passw-error").slideDown().delay(3000).slideUp();
                elem.html('');
                elem.append("<p>Passwords does not match</p>");
            }
            if (wasError) {
                return;
            }

            this.session.postReg({
                username: username,
                newPassw: newPassw,
                confirmPassw: confirmPassw,
                url: this.$el.find('.form').data('action'),
            });
        },
        redirectLogin: function() {
            window.location.replace('#login');
        },
        showError: function(message) {
            var elem = this.$el.find(".register-error").slideDown().delay(3000).slideUp();
            elem.html('');
            elem.append("<p>" + message + "</p>");
        },
        loginClick: function() {
            this.$el.find(".form__content__user-icon").css("left","-48px");
        },
        passwClick: function() {
            this.$el.find(".form__content__pass-icon").css("left","-48px");
        },
        confirmPasswClick: function() {
            this.$el.find(".form__content__pass-icon2").css("left","-48px");
        },
        loginBlur: function() {
            this.$el.find(".form__content__user-icon").css("left","0px");
        },
        passwBlur: function() {
            this.$el.find(".form__content__pass-icon").css("left","0px");
        },
        confirmPasswBlur: function() {
            this.$el.find(".form__content__pass-icon2").css("left","0px");
        }
    });
    return new View();
});