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
        el: $('#page'),
        events: {
            "click #login": "authClick",
            "click #username": "loginClick",
            "click #passw": "passwordClick",
            "blur #username": "loginBlur",
            "blur #passw": "passwordBlur",
            "focus #passw": "passwFocus",
            "focus #username": "usernameFocus"
        },

        initialize: function () {
            this.listenTo(this.session, 'successAuth', this.redirectMain);
            this.listenTo(this.session, 'errorAuth', this.showError);
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
        authClick: function(event) {
            event.preventDefault();
            var username = $("#username").val(),
                password = $("#passw").val(),
                wasError = false;

            $("#login").prop('disabled', true).delay(1700).queue(
                function(next) { $(this).attr('disabled', false);
                next();
            });
            $("#login").addClass("form__footer__button_disabled").delay(1700).queue(
                function(next) { $(this).removeClass("form__footer__button_disabled");
                next();
            });

            if (username == '') {
                wasError = true;
                $("#login-error").slideDown().delay(3000).slideUp();
                $("#login-error-message").html('Missing username');
            }
            if (password == '') {
                wasError = true;
                $("#passw-error").slideDown().delay(3000).slideUp();
                $("#passw-error-message").html('Missing password');
            }
            
            if (wasError) {
                return;
            }

            this.session.postAuth({
                username: username,
                password: password
            });
        },
        redirectMain: function() {
            window.location.replace('#');
        },
        showError: function(message) {
            $("#login-error").slideDown().delay(3000).slideUp();
            $("#login-error-message").html(message);
        },
        loginClick: function() {
            $(".form__content__user-icon").css("left","-48px");
        },
        passwordClick: function() {
            $(".form__content__pass-icon").css("left","-48px");
        },
        loginBlur: function() {
            $(".form__content__user-icon").css("left","0px");
        },
        passwordBlur: function() {
            $(".form__content__pass-icon").css("left","0px");
        },
        passwFocus: function() {
            $("#passw").val("");
        },
        usernameFocus: function() {
            $("#username").val("");
        }


    });

    return new View();
});