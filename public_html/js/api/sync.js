define(function ApiSync() {
    var self = this;

    var methodMap = {
        'create': 'POST',
        'update': 'POST',
        'patch':  'PATCH',
        'delete': 'POST',
        'read':   'GET'
    };

    var urlMap  = {
        'login': 'auth/',
        'register': 'register/',
        'changePassword': 'profile/',
        'identifyUser': 'identifyuser/',
        'logout': 'logout/'
    }

	return function(method, model, options) {
        var urlSuffix = '', callSuccess = options.callbacks.success,
            callError = options.callbacks.error, data = {};
        
        if (method == 'read') {
            data = options.data;
        }
        else {
            data = (model instanceof Backbone.Model)?model.toJSON():{};    
        }
        var xhr = $.ajax({
            type: methodMap[method],
            url: model.url + urlMap[options.method],
            data: data,
            dataType: 'json',
        }).done(function(data) {
            if (data.status == 1) {
                options.success();
                callSuccess(data); 
            } else {
                options.error();
                callError(data.message);
            }
        }).fail(function(data) {
            callError("Connection error, please try again later");
        });
        return xhr;
    
    };

})