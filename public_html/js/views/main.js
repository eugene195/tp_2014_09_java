define([
    'backbone',
    'tmpl/main'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({

        template: tmpl,


        events: {
            "click #logout": "logout",
        },
        initialize: function () {
            this.render();
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
                    $("#login").hide();
                    $("#register").hide();
                } else {
                    $("#profile").hide();
                    $("#exit").hide();
                    $("#login").show();
                    $("#register").show();
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
            // TODO            
        }

    });

    return new View();
});