define([
    'backbone',
    'tmpl/register'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({

        template: tmpl,

        events: {
            "click #register": "buttonClick",
            "click .username": "loginClick",
            "click #passw": "passwordClick",
            "blur .username": "loginBlur",
            "blur #passw": "passwordBlur",
        },

        initialize: function () {
            this.render();
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
            var username = $(".username").val();
            var password = $("#passw").val();
            var password2 = $("#passw2").val();
            var wasError = false;
            $("#register").prop('disabled', true).delay(1700).queue(
                function(next) { $(this).attr('disabled', false);
                next();
                });
            $("#register").addClass("button-disabled").delay(1700).queue(
                function(next) { $(this).removeClass("button-disabled");
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
            $(".user-icon").css("left","-48px");
        },
        passwordClick: function() {
            $(".pass-icon").css("left","-48px");
        },
        loginBlur: function() {
            $(".user-icon").css("left","0px");
        },
        passwordBlur: function() {
            $(".pass-icon").css("left","0px");
        },
    });

    return new View();
});