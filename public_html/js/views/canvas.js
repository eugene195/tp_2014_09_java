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

            function Button(start, length, text, height, btnName) {
                this.start = start;
                this.length = length;
                this.text = text;
                this.height = height;
                this.btnName = btnName;
            };

            function isInBounds(button, x, y) {
                if ((x >  button.start) && (x < button.finish) && (y > 0) && (y < button.height)) {
                    return true;
                } else {
                    return false;
                }

            };

            function buttonPress(btnName) {
                if (btnName == "freqUp") {
                    freq = freq - 100;
                } else if (btnName == "freqDown") {
                    freq = freq + 100;
                } else if (btnName == "ampUp") {
                    amp = amp + 100;
                } else {
                    amp = amp - 100;
                }
            };

            var btnArray = [
                new Button(0, 150, "Frequency Up", 50, "freqUp"),
                new Button(160, 150, "Frequency Down", 50, "freqDown"),
                new Button(320, 150, "Amplitude Up", 50, "ampUp"),
                new Button(480, 150, "Amplitude Down", 50, "ampDown")
            ]

            var amp = 100;
            var freq = 10;
            var base = 200;

            var Circle = {
                x: 0,
                y: 75,
                radius: 20,
                borderWidth: 2
            };

            function sinePoint(base, time, amp, freq) {
                return base + (amp * Math.sin(freq * time * Math.PI));
            }

            function getMousePos(canvas, evt) {
                var rect = canvas.getBoundingClientRect();
                return {
                    x: evt.clientX - rect.left,
                    y: evt.clientY - rect.top
                };
            }

            function drawButton(Button, context) {
                context.beginPath();
                context.rect(Button.start, 0, Button.length, Button.height);
                context.fillStyle = 'yellow';
                context.fill();
                context.font = '12pt Calibri';
                context.lineWidth = 1;
                context.strokeStyle = 'blue';
                context.strokeText(Button.text, Button.start + 5, Button.height / 2);
                context.lineWidth = 2;
                context.strokeStyle = 'black';
                context.stroke();
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

            function Point (x, y) {
                this.x = x;
                this.y = y;
            }
            var tail = [];
            debugger;

            function drawTail(context, tail) {
                context.beginPath();
                context.lineWidth = 4;
                context.strokeStyle = 'black';

                if (tail.length == 0) return;

                context.moveTo(tail[0].x, tail[0].y);
                for (var I in tail) {
                    context.lineTo(tail[I].x, tail[I].y);
                }
                context.stroke();
            }

            function animate(Circle, canvas, context, startTime) {
                // update
                var time = (new Date()).getTime() - startTime;

                var linearSpeed = 100;
                // pixels / second
                var newX = linearSpeed * time / 1000;

                context.clearRect(Circle.x - Circle.radius * 2, Circle.y - Circle.radius * 2,
                                  Circle.x + Circle.radius * 2, Circle.y + Circle.radius * 2);


                drawTail(context, tail);

                if (newX < canvas.width - Circle.radius * 2) {
                    Circle.x = newX;
                    Circle.y = sinePoint(base, time/10000, amp, freq);

                    tail.push(new Point(Circle.x, Circle.y));
                    if (tail.length == 100) {
                        tail.shift();
                    }
                }

                // clear
//                context.clearRect(0, 0, canvas.width, canvas.height);


                drawCircle(Circle, context);

                // request new frame
                requestAnimFrame(function() {
                    animate(Circle, canvas, context, startTime);
                });
            }

            var canvas = document.getElementById('myCanvas');
            var context = canvas.getContext('2d');

            btnArray.forEach(function(entry) {
//                debugger;
                drawButton(entry, context);
            });

            canvas.addEventListener('click', function(evt) {
                var mousePos = getMousePos(canvas, evt);
                btnArray.forEach(function(entry) {
//                    debugger;
                    if ((mousePos.x >  entry.start) && (mousePos.x < entry.start + entry.length)
                    && (mousePos.y > 0) && (mousePos.y < entry.height)) {
                        buttonPress(entry.btnName);
                    }
                });

            }, false);
//            What is that false?

            // wait one second before starting animation
            setTimeout(function() {
                var startTime = (new Date()).getTime();
                animate(Circle, canvas, context, startTime);
            }, 1000);
        }

    });

    return new View();
});