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
        events: {
            "click input[name=submit]": "saveProfileClick",
        },

        initialize: function () {
            this.listenTo(this.session, 'successChangePassword', this.showSuccess);
            this.listenTo(this.session, 'errorChangePassword', this.handleSessionError);
            this.listenTo(this.session, 'userNotIdentified', this.userNotIdentified);
            this.listenTo(this.session, 'userIdentified', this.userIdentified);
            this.render();
        },
        render: function () {
            this.$el.html(this.template());
            this.session.postIdentifyUser();
            return this;
        },
        show: function () {
            this.trigger('show', this);
        },
        saveProfileClick: function(event) {
            event.preventDefault();
            var curPassw = this.$el.find("input[name=cur_passw]").val(),
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


            if (this.validate(curPassw, newPassw, confirmPassw)) {
                this.session.postChangePassword({
                    curPassw: curPassw,
                    newPassw: newPassw,
                    confirmPassw: confirmPassw,
                    url: this.$el.find('.form').data('action'),
                });
            }
        },

        validate: function (curPassw, newPassw, confirmPassw) {
            if (curPassw == '') {
                this.showError("Missing current password", ".cur-passw-error");
                return false;
            }
            if (newPassw == '') {
                this.showError("Missing new password", ".passw-error");
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

        handleSessionError: function (message) {
            this.showError(message, ".confirm-passw-error");
        },

        showSuccess: function() {
            var elem = this.$el.find(".alert-success").slideDown().delay(3000).slideUp();
            elem.html('');
            elem.append("<p>Password successfully changed</p>");
        },
        showError: function(message, div_error) {
            var elem = this.$el.find(div_error).slideDown().delay(3000).slideUp();
            elem.html('');
            elem.append("<p>" + message + "</p>");
        },
        userIdentified: function(data) {
            if (window.location.hash.substring(1) == 'profile') { // TODO delete
            this.$el.find('h1').html("Profile " + data.login)
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