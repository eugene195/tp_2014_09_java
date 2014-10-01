define([
    'backbone',
    'tmpl/main'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({

        template: tmpl,
        el: $('#page'),

        events: {
            "click #logout": "logout",
        },
        initialize: function () {
            //this.render();
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
                    $("#profile").show();
                    $("#exit").show();
                    $("#auth").hide();
                    $("#reg").hide();
                } else {
                    $("#profile").hide();
                    $("#exit").hide();
                    $("#auth").show();
                    $("#reg").show();
                }
            }).fail(function(data) {
                alert("Error, please try again later");
            }) 
        },
        show: function () {
            
        },
        hide: function () {
            // TODO
        },
        logout: function (event) {
            event.preventDefault();

            $.ajax({
                url: "/logout",
                method: "POST",
                data: {
                    data: 'data'
                },
                dataType: "json"
            }).done(function(data){
                if (data.status == 1) {
                    window.location.replace('#login');
                }
                else {
                    $("#logout-error").slideDown().delay(3000).slideUp();
                    $("#logout-error-message").html(data.message);
                }
            }).fail (function(data) {
                alert("Error, please try again later");
            });
        }

    });

    return new View();
});