define([
    'backbone',
    'tmpl/profile'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({

        template: tmpl,
        el: $('#page'),
        events: {
            "click #save_profile": "saveProfileClick",
        },

        initialize: function () {
            // TODo
        },
        render: function () {
            this.$el.html(this.template());

            $.ajax({
                url: "/identifyuser",
                method: "POST",
                data:  {
                    data: 'data'
                },
            dataType: "json"
            }).done(function(data){
                // if user is identified
                if (data.status == 1) {
                    $('h1').html("Profile " + data.login)
                } else {
                    window.location.replace('#login');
                }
            }).fail(function(data) {
                alert("Error, please try again later");
            }) 
        },
        show: function () {
            //TODo
        },
        hide: function () {
            // TODO
        },
        saveProfileClick: function(event) {
            event.preventDefault();
            var curPassword = $("#cur-passw").val();
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

            if (curPassword == '') {
                wasError = true;
                $("#cur-passw-error").slideDown().delay(3000).slideUp();
                $("#cur-passw-error-message").html('Missing current password');
            } 
            else if (password == '') {
                wasError = true;
                $("#passw-error").slideDown().delay(3000).slideUp();
                $("#passw-error-message").html('Missing new password');
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
                method: "POST",
                url: $('.form').data('action'),
                data:  {
                    curPassw: curPassword,
                    newPassw: password,
                    confirmPassw: password2,
                },
            dataType: "json"
            }).done(function(data){
                if (data.status == 1) {
                    $("#success").slideDown().delay(3000).slideUp();
                    $("#success-message").html("Password successfully changed");
                } else {
                    $("#cur-passw-error").slideDown().delay(3000).slideUp();
                    $("#cur-passw-error-message").html(data.message);
                }
            }).fail(function(data) {
                alert("Error, please try again later");
            })           
        }
        

    });
    
    return new View();
});