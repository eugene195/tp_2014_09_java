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
            "click input[name=submit]": "saveProfileClick"
        },

        initialize: function () {
            this.listenTo(this.session, 'successChangePassword', this.passwSuccess);
            this.listenTo(this.session, 'errorChangePassword', this.passwError);
            this.listenTo(this.session, 'profile:anonymous', this.userNotIdentified);
            this.listenTo(this.session, 'profile:known', this.userIdentified);
        },

        render: function () {
            this.$el.html(this.template());
            this.session.postIdentifyUser('profile');
            return this.$el;
        },

        show: function () {
            this.trigger('show', this);
        },

        saveProfileClick: function(event) {
            event.preventDefault();
            var curPassw = this.$("input[name=cur_passw]").val(),
                newPassw = this.$("input[name=passw]").val(),
                confirmPassw = this.$("input[name=confirm_passw]").val();

            var btnSubmit = this.$el.find("input[name=submit]");
            btnSubmit.addClass("form__footer__button_disabled").prop('disabled', true).delay(1700).queue(
                function(next) {
                    $(this).attr('disabled', false);
                    $(this).removeClass("form__footer__button_disabled");
                    next();
                }
            );

            if (this.validate(curPassw, newPassw, confirmPassw)) {
                this.session.postChangePassword({
                    curPassw: curPassw,
                    newPassw: newPassw,
                    confirmPassw: confirmPassw,
                    url: this.$('.form').data('action')
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

        passwError: function (message) {
            this.showError(message, ".confirm-passw-error");
        },

        passwSuccess: function() {
            this.showError("Password successfully changed", ".alert-success");
        },

        showError: function(message, div_error) {
            var elem = this.$(div_error).slideDown().delay(3000).slideUp();
            elem.append("<p>" + message + "</p>");
        },

        userIdentified: function(data) {
            if (window.location.hash.substring(1) == 'profile') { // TODO delete
                this.$('h1').html("Profile " + data.login)
            }
        },

        userNotIdentified: function() {
            this.trigger('anonymous');
        }
    });
    return new View();
});