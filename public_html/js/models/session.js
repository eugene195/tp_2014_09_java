define([
    'backbone'
], function(
    Backbone
){
    var SessionModel = Backbone.Model.extend({
        initialize: function() {
        },
        postAuth: function(args) {
            var self = this;
            $.ajax({
                url: args.url,
                method: "POST",
                data:  {
                    login: args.username,
                    passw: args.password,
                },
            dataType: "json"
            }).done(function(data){
                if (data.status == 1) {
                    self.trigger("successAuth");
                } else {
                    self.trigger("errorAuth", data.message);
                }
            }).fail(function(data) {
                self.trigger("errorAuth", "Error, please try again later");
            })  
        },
        postReg: function(args) {
            var self = this;
            $.ajax({
                method: "POST",
                url: args.url,
                data:  {
                    login: args.username,
                    passw: args.newPassw,
                    passw2: args.confirmPassw,
                },
            dataType: "json"
            }).done(function(data){
                if (data.status == 1) {
                    self.trigger("successReg");
                } else {
                    self.trigger("errorReg", data.message);
                }
            }).fail(function(data) {
                self.trigger("errorReg", "Error, please try again later");
            })
        },
        postChangePassword: function(args) {
            var self = this;
            $.ajax({
                method: "POST",
                url: args.url,
                data:  {
                    curPassw: args.curPassw,
                    newPassw: args.newPassw,
                    confirmPassw: args.confirmPassw,
                },
            dataType: "json"
            }).done(function(data){
                if (data.status == 1) {
                    self.trigger("successChangePassword");
                } else {
                    self.trigger("errorChangePassword", data.message);
                }
            }).fail(function(data) {
                self.trigger("errorChangePassword", "Error, please try again later");
            })
        },
        postIdentifyUser: function() {
            var self = this;
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
                    self.trigger("userIdentified", {login: data.login});
                } else {
                    self.trigger("userNotIdentified");
                }
            }).fail(function(data) {
                self.trigger("userNotIdentified");
            }) 
        },
        postLogout: function() {
            var self = this;
            $.ajax({
                url: "/logout",
                method: "POST",
                data: {
                    data: 'data'
                },
                dataType: "json"
            }).done(function(data){
                if (data.status == 1) {
                    self.trigger("successLogout");
                }
                else {
                    self.trigger("errorLogout", data.message);
                }
            }).fail (function(data) {
                self.trigger("errorLogout", "Error, please try again later");
            });
        }
    });

    return new SessionModel();
});