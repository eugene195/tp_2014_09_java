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
        el: $('#page'),
        events: {
            "click #register": "buttonClick",
            "click #username": "loginClick",
            "click #passw": "passwordClick",
            "click #passw2": "password2Click",
            "blur #username": "loginBlur",
            "blur #passw": "passwordBlur",
            "blur #passw2": "password2Blur",
            "focus #passw": "passwFocus",
            "focus #passw2": "passw2Focus",
            "focus #username": "usernameFocus"
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
        },
        hide: function () {
            // TODO
        },
        buttonClick: function(event) {
            event.preventDefault();
            var username = $("#username").val(),
                newPassw = $("#passw").val(),
                confirmPassw = $("#passw2").val(),
                wasError = false;

            $("#register").prop('disabled', true).delay(1700).queue(
                function(next) { $(this).attr('disabled', false);
                next();
            });
            $("#register").addClass("form__footer__button_disabled").delay(1700).queue(
                function(next) { $(this).removeClass("form__footer__button_disabled");
                next();
            });

            if (username == '') {
                wasError = true;
                $("#register-error").slideDown().delay(3000).slideUp();
                $("#register-error-message").html('Missing username');
            }
            if (newPassw == '') {
                wasError = true;
                $("#passw-error").slideDown().delay(3000).slideUp();
                $("#passw-error-message").html('Missing password');
            }   
            else if (confirmPassw == '') {
                wasError = true;
                $("#passw2-error").slideDown().delay(3000).slideUp();
                $("#passw2-error-message").html('Missing confirm password');
            }   
            else if (newPassw != confirmPassw) {
                wasError = true;
                $("#passw2-error").slideDown().delay(3000).slideUp();
                $("#passw2-error-message").html('Passwords does not match');
            }
            if (wasError) {
                return;
            }

            this.session.postReg({
                username: username,
                newPassw: newPassw,
                confirmPassw: confirmPassw,
            });
        },
        redirectLogin: function() {
            window.location.replace('#login');
        },
        showError: function(message) {
            $("#register-error").slideDown().delay(3000).slideUp();
            $("#register-error-message").html(message);
        },
        loginClick: function() {
            $(".form__content__user-icon").css("left","-48px");
        },
        passwordClick: function() {
            $(".form__content__pass-icon").css("left","-48px");
        },
        password2Click: function() {
            $(".form__content__pass-icon2").css("left","-48px");
        },
        loginBlur: function() {
            $(".form__content__user-icon").css("left","0px");
        },
        passwordBlur: function() {
            $(".form__content__pass-icon").css("left","0px");
        },
        password2Blur: function() {
            $(".form__content__pass-icon2").css("left","0px");
        },
        passwFocus: function() {
            $("#passw").val("");
        },
        passw2Focus: function() {
            $("#passw2").val("");
        },
        usernameFocus: function() {
            $("#username").val("");
        }

    });

    return new View();
});