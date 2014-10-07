define([
    'backbone',
    'tmpl/profile',
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
            "click #save_profile": "saveProfileClick",
        },

        initialize: function () {
            this.listenTo(this.session, 'successChangePassword', this.showSuccess);
            this.listenTo(this.session, 'errorChangePassword', this.showError);
            this.listenTo(this.session, 'userNotIdentified', this.userNotIdentified);
            this.listenTo(this.session, 'userIdentified', this.userIdentified);
        },
        render: function () {
            this.$el.html(this.template());
            this.session.postIdentifyUser();
        },
        show: function () {
            this.render();
        },
        hide: function () {
            // TODO
        },
        saveProfileClick: function(event) {
            event.preventDefault();
            var curPassw = $("#cur-passw").val(),
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

            if (curPassw == '') {
                wasError = true;
                $("#cur-passw-error").slideDown().delay(3000).slideUp();
                $("#cur-passw-error-message").html('Missing current password');
            } 
            else if (newPassw == '') {
                wasError = true;
                $("#passw-error").slideDown().delay(3000).slideUp();
                $("#passw-error-message").html('Missing new password');
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

            this.session.postChangePassword({
                curPassw: curPassw,
                newPassw: newPassw,
                confirmPassw: confirmPassw,
            });
                       
        },
        showSuccess: function() {
            $("#success").slideDown().delay(3000).slideUp();
            $("#success-message").html("Password successfully changed");
        },
        showError: function(message) {
            $("#cur-passw-error").slideDown().delay(3000).slideUp();
            $("#cur-passw-error-message").html(message);
        },
        userIdentified: function(data) {
            if (window.location.hash.substring(1) == 'profile') { // TODO delete
            $('h1').html("Profile " + data.login)
            }
        },
        userNotIdentified: function() {
            if (window.location.hash.substring(1) == 'profile') { // TODO delete
                window.location.replace('#login');
            }
         }
        

    });
    
    return new View();
});