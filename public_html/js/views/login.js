define([
    'backbone',
    'tmpl/login'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({

        template: tmpl,

        events: {
            "click #login": "buttonClick",
            "click .username": "loginClick",
            "click .password": "passwordClick",
            "blur .username": "loginBlur",
            "blur .password": "passwordBlur",
        },

        initialize: function () {
            this.render();
        },
        render: function () {
            this.$el.html(this.template());
        },
        show: function () {
            
        },
        hide: function () {
            // TODO
        },
        buttonClick: function(event) {
            event.preventDefault();
            var username = $(".username").val();
            var password = $(".password").val();
            var wasError = false;

            $("#login").prop('disabled', true).delay(1700).queue(
                function(next) { $(this).attr('disabled', false);
                next();
                });
            $("#login").addClass("button-disabled").delay(1700).queue(
                function(next) { $(this).removeClass("button-disabled");
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
                url: "/auth",
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