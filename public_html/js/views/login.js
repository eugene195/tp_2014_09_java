define([
    'backbone',
    'tmpl/login'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({

        template: tmpl,
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
            // TODO
        },
        render: function () {
            this.$el.html(this.template());
        },
        show: function () {
            
        },
        hide: function () {
            // TODO
        },
        authClick: function(event) {
            event.preventDefault();
            var username = this.$el.$("#username").val(),
                password = this.$el.$("#passw").val(),
                wasError = false;

            $("#login").prop('disabled', true).delay(1700).queue(
                function(next) { $(this).attr('disabled', false);
                next();
                });
            $("#login").addClass("form__footer__button--disabled").delay(1700).queue(
                function(next) { $(this).removeClass("form__footer__button--disabled");
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
            
            $.ajax({
                url: $('.form').data('action'),
                method: "POST",
                data:  {
                    login: username,
                    passw: password,
                },
            dataType: "json"
            }).done(function(data){
                if (data.status == 1) {
                    window.location.replace('#');
                } else {
                    $("#login-error").slideDown().delay(3000).slideUp();
                    $("#login-error-message").html(data.message);
                }
            }).fail(function(data) {
                alert("Error, please try again later");
            })           
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