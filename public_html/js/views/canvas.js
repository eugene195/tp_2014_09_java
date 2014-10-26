define([
    'backbone',
    'tmpl/canvas'
], function(
    Backbone,
    tmpl
){
var View = Backbone.View.extend({
    el: $('.canvas'),
    template: tmpl,
    initialize: function () {
        this.render();
        this.$el.hide();
    },
    render: function () {
        this.$el.html(this.template());
    },
    show: function () {
        this.trigger('reshow', this);

        window.requestAnimFrame = (function(callback) {
            return window.requestAnimationFrame || window.webkitRequestAnimationFrame || 
                window.mozRequestAnimationFrame || window.oRequestAnimationFrame || 
                window.msRequestAnimationFrame ||
            function(callback) {
              window.setTimeout(callback, 1000 / 60);
            };
        })();
      
        var amp = 100;
        var freq = 1;
        var tick = 0;

        var Circle = {
            x: 0,
            y: 75,
            radius: 20,
            borderWidth: 2
        };
      
        function sinePoint(base, tick, amp, freq) {
            return base + (amp * Math.sin(freq*tick*Math.PI));
        }

        function drawCircle(Circle, context) {
            context.beginPath();
            context.arc(Circle.x, Circle.y, Circle.radius, 0, 2 * Math.PI, false);
            context.fillStyle = '#00FFFF';
            context.fill();
            context.lineWidth = Circle.borderWidth;
            context.strokeStyle = '#003300';
            context.stroke();
        }

        function animate(Circle, canvas, context, startTime) {
            // update
            var time = (new Date()).getTime() - startTime;

            var linearSpeed = 100;
            // pixels / second
            var newX = linearSpeed * time / 1000;

            tick += 0.01;

            if(newX < canvas.width - Circle.radius*2) {
                Circle.x = newX;
                Circle.y = sinePoint(200,tick,amp,freq);
            }

            // clear
            context.clearRect(0, 0, canvas.width, canvas.height);

            drawCircle(Circle, context);

            // request new frame
            requestAnimFrame(function() {
                animate(Circle, canvas, context, startTime);
            });
        }

        var canvas = document.getElementById('myCanvas');
        var context = canvas.getContext('2d');

        // wait one second before starting animation
        setTimeout(function() {
            var startTime = (new Date()).getTime();
            animate(Circle, canvas, context, startTime);
            }, 1000);

        }
    });

    return new View();
});