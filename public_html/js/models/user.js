define([
    'backbone',
    'api/sync'
], function(
    Backbone,
    ApiSync
){

    var UserModel = Backbone.Model.extend({
        sync: ApiSync,
        url: '/',
        id: 1,

        defaults: {
            "login":  "",
            "score":     0,
            "passw": ""
        },

        initialize: function() {
           
        },

        login: function(args) {
            args = _.extend({'data': args}, {'method': 'login'},
                {'callbacks': this.callbacks('successAuth', 'errorAuth')});
            this.fetch(args);
        },

        register: function(args) {
            var options = _.extend({'method': 'register'},
                 {'callbacks': this.callbacks('successReg', 'errorReg')});
            this.save(args, options);
        },

        changePassword: function(args) {
            var options = _.extend({'method': 'changePassword'},
                {'callbacks': this.callbacks('successChangePassword', 'errorChangePassword')});
            this.save(args, options);
        },

        identifyUser: function(prefix) {
            args = _.extend({'method': 'identifyUser'},
                {'callbacks': this.callbacks(prefix + ':known', prefix + ':anonymous')});
            this.fetch(args);
        },

        logout: function() {
            args = _.extend({'method': 'logout'},
                {'callbacks': this.callbacks('successLogout', 'errorLogout')});
            this.destroy(args);
        },

        callbacks: function(success, error) {
            return  { success: function(message) { 
                            this.trigger(success, message); 
                        }.bind(this),

                    error: function(message) { 
                            console.log(message);
                            this.trigger(error, message); 
                        }.bind(this) 
                    }
        }
    });

    return new UserModel();
});