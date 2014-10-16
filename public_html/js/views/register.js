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
        el: $('.register'),
        events: {
            "click input[name=submit]": "registerClick",
            "click input[name=login]": "loginClick",
            "click input[name=passw]": "passwClick",
            "click input[name=confirm_passw]": "confirmPasswClick",
            "blur input[name=login]": "loginBlur",
            "blur input[name=passw]": "passwBlur",
            "blur input[name=confirm_passw]": "confirmPasswBlur",
        },

        initialize: function () {
            this.listenTo(this.session, 'successReg', this.redirectLogin);
            this.listenTo(this.session, 'errorReg', this.handleSessionError);
        },
        render: function () {
            this.$el.html(this.template());
        },
        show: function () {
            this.render();
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
        registerClick: function(event) {
            event.preventDefault();
            var username = this.$el.find("input[name=login]").val(),
                newPassw = this.$el.find("input[name=passw]").val(),
                confirmPassw = this.$el.find("input[name=confirm_passw]").val();

            var butSubmit = this.$el.find("input[name=submit]").prop('disabled', true).delay(1700).queue(
                function(next) { $(this).attr('disabled', false);
                next();
            });
            butSubmit.addClass("form__footer__button_disabled").delay(1700).queue(
                function(next) { $(this).removeClass("form__footer__button_disabled");
                next();
            });
       
            if (this.validate(username, newPassw, confirmPassw)) {
                this.session.postReg({
                    username: username,
                    newPassw: newPassw,
                    confirmPassw: confirmPassw,
                    url: this.$el.find('.form').data('action'),
                });   
            }
         },
        redirectLogin: function() {
            window.location.replace('#login');
        },
        handleSessionError: function(message) {
            this.showError(message, ".register-error")
        },
        showError: function(message, div_error) {
            var elem = this.$el.find(div_error).slideDown().delay(3000).slideUp();
            elem.html('');
            elem.append("<p>" + message + "</p>");
        },
        validate: function(username, newPassw, confirmPassw){
            var status = true;
            if (username == '') {
                status = false;
                this.showError("Missing username", ".register-error");
            }
            if (newPassw == '') {
                status = false;
                this.showError("Missing password", ".passw-error");
            }   
            else if (confirmPassw == '') {
                status = false;
                this.showError("Missing confirm password", ".confirm-passw-error");
            }   
            else if (newPassw != confirmPassw) {
                status = false;
                this.showError("Passwords does not match", ".confirm-passw-error");
            }
            return status;
        },
        loginClick: function() {
            this.$el.find(".form__content__user-icon").css("left","-48px");
        },
        passwClick: function() {
            this.$el.find(".form__content__pass-icon").css("left","-48px");
        },
        confirmPasswClick: function() {
            this.$el.find(".form__content__pass-icon2").css("left","-48px");
        },
        loginBlur: function() {
            this.$el.find(".form__content__user-icon").css("left","0px");
        },
        passwBlur: function() {
            this.$el.find(".form__content__pass-icon").css("left","0px");
        },
        confirmPasswBlur: function() {
            this.$el.find(".form__content__pass-icon2").css("left","0px");
        }
    });
    return new View();
});