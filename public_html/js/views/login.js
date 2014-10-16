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
        el: $('.login'),
        events: {
            "click input[name=submit]": "authClick",
            "click input[name=login]": "loginClick",
            "click input[name=passw]": "passwordClick",
            "blur input[name=login]": "loginBlur",
            "blur input[name=passw]": "passwordBlur"
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
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
        authClick: function(event) {
            event.preventDefault();
            var username = this.$el.find("input[name=login]").val(),
                password = this.$el.find("input[name=passw]").val(),
                wasError = false;

            var butSubmit = this.$el.find("input[name=submit]").prop('disabled', true).delay(1700).queue(
                function(next) { $(this).attr('disabled', false);
                next();
            });
            butSubmit.addClass("form__footer__button_disabled").delay(1700).queue(
                function(next) { $(this).removeClass("form__footer__button_disabled");
                next();
            });

            if (this.validate(username, password)) {
                this.session.postAuth({
                    username: username,
                    password: password,
                    url: this.$el.find('.form').data('action'), 
                });
            }
        },
        redirectMain: function() {
            window.location.replace('#');
        },
        showError: function(message, div_error) {
            var div_error = div_error || ".login-error";
            var elem = this.$el.find(div_error).slideDown().delay(3000).slideUp();
            elem.html('');
            elem.append("<p>" + message + "</p>");
        },
        validate: function(username, password) {
            var status= true;
            if (username == '') {
                status = false;
                this.showError("Missing username");
            }
            if (password == '') {
                status = false;
                this.showError("Missing password", ".passw-error");
            }
            return status;
        },
        loginClick: function() {
            this.$el.find(".form__content__user-icon").css("left","-48px");
        },
        passwordClick: function() {
            this.$el.find(".form__content__pass-icon").css("left","-48px");
        },
        loginBlur: function() {
            this.$el.find(".form__content__user-icon").css("left","0px");
        },
        passwordBlur: function() {
            this.$el.find(".form__content__pass-icon").css("left","0px");
        }
    });

    return new View();
});