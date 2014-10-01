define([
    'backbone',
    'tmpl/register'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({

        template: tmpl,
        el: $('#page'),
        events: {
            "click #register": "buttonClick",
            "click #username": "loginClick",
            "click #passw": "passwordClick",
            "click #passw2": "password2Click",
            "blur #username": "loginBlur",
            "blur #passw": "passwordBlur",
            "blur #passw2": "password2Blur",
        },

        initialize: function () {
            //this.render();
        },
        render: function () {
            this.$el.html(this.template());
        },
        show: function () {
            //TODo
        },
        hide: function () {
            // TODO
        },
        buttonClick: function(event) {
            event.preventDefault();
            var username = $("#username").val();
            var password = $("#passw").val();
            var password2 = $("#passw2").val();
            var wasError = false;
            $("#register").prop('disabled', true).delay(1700).queue(
                function(next) { $(this).attr('disabled', false);
                next();
                });
            $("#register").addClass("form__footer__button--disabled").delay(1700).queue(
                function(next) { $(this).removeClass("form__footer__button--disabled");
                next();
                });

            if (username == '') {
                wasError = true;
                $("#register-error").slideDown().delay(3000).slideUp();
                $("#register-error-message").html('Missing username');
            }
            if (password == '') {
                wasError = true;
                $("#passw-error").slideDown().delay(3000).slideUp();
                $("#passw-error-message").html('Missing password');
            }   
            else if (password2 == '') {
                wasError = true;
                $("#passw2-error").slideDown().delay(3000).slideUp();
                $("#passw2-error-message").html('Missing confirm password');
            }   
            else if (password != password2) {
                wasError = true;
                $("#passw2-error").slideDown().delay(3000).slideUp();
                $("#passw2-error-message").html('Passwords does not match');
            }
            if (wasError) {
                return;
            }
            
            $.ajax({
                url: "/register",
                method: "POST",
                url: $('.form').data('action'),
                data:  {
                    login: username,
                    passw: password,
                    passw2: password,
                },
            dataType: "json"
            }).done(function(data){
                if (data.status == 1) {
                    window.location.replace('#login');
                } else {
                    $("#register-error").slideDown().delay(3000).slideUp();
                    $("#register-error-message").html(data.message);
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

    });

    return new View();
});