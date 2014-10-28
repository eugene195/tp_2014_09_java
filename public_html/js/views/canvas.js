define([
    'backbone',
    'tmpl/canvas',
    'views/viewman'
], function(
    Backbone,
    tmpl,
    Manager
){

    function Point (x, y) {
        this.x = x;
        this.y = y;
    }

    function Button(start, length, text, height, btnName) {
        this.start = start;
        this.length = length;
        this.text = text;
        this.height = height;
        this.btnName = btnName;

        this.drawButton = function(context) {
            context.beginPath();
            context.rect(this.start, 0, this.length, this.height);
            context.fillStyle = 'yellow';
            context.fill();
            context.font = '12pt Calibri';
            context.lineWidth = 1;
            context.strokeStyle = 'blue';
            context.strokeText(this.text, this.start + 5, this.height / 2);
            context.lineWidth = 2;
            context.strokeStyle = 'black';
            context.stroke();
        };

        this.isInBounds = function(x, y) {
            if ((x >  this.start) && (x < this.start + this.length) && (y > 0) && (y < this.height)) {
                return true;
            }
            return false;
        };
    }

    function sinePoint(base, time, amp, freq) {
        return base + (amp * Math.sin(freq * time * Math.PI));
    }

    function getMousePos(canvas, evt) {
        var rect = canvas.getBoundingClientRect();
        return new Point(evt.clientX - rect.left, evt.clientY - rect.top);
    }

    var View = Backbone.View.extend({
        el: $('.canvas'),
        template: tmpl,
        animation: true,
        viewman: Manager,

        initialize: function () {
            this.render();
            this.$el.hide();
        },

        render: function () {
            this.$el.html(this.template());
        },

        hideHandler: function () {
            this.animation = false;
            var canvas = document.getElementById('myCanvas');
            var context = canvas.getContext('2d');
            context.clearRect(0, 0, canvas.width, canvas.height);
        },

        show: function () {
            this.listenTo(this.viewman, 'hide', this.hideHandler);
            this.trigger('reshow', this);
            this.animation = true;

            window.requestAnimFrame = (function(callback) {
                return window.requestAnimationFrame || window.webkitRequestAnimationFrame ||
                    window.mozRequestAnimationFrame || window.oRequestAnimationFrame ||
                    window.msRequestAnimationFrame ||
                function (callback) {
                  window.setTimeout(callback, param);
                };
            })();

            var amp = 100;
            var freq = 40;
            var base = 200;

            var self = this;

            var canvas = document.getElementById('myCanvas');
            var context = canvas.getContext('2d');

            var btnCanvas = document.getElementById('btnCanvas');
            var btnContext = btnCanvas.getContext('2d');

            var btnArray = [
                new Button(0, 150, "Frequency Up", 50, "freqUp"),
                new Button(160, 150, "Frequency Down", 50, "freqDown"),
                new Button(320, 150, "Amplitude Up", 50, "ampUp"),
                new Button(480, 150, "Amplitude Down", 50, "ampDown")
            ];

            btnArray.forEach(function(entry) {
                entry.drawButton(btnContext);
            });

            btnCanvas.addEventListener('click', function(evt) {
                var mousePos = getMousePos(btnCanvas, evt);
                btnArray.forEach(function(entry) {
                    if (entry.isInBounds(mousePos.x, mousePos.y)) {
                        buttonPress(entry.btnName);
                    }
                });
            }, false);

            function buttonPress(btnName) {
                if (btnName == "freqUp") {
                    freq = freq + 10;
                } else if (btnName == "freqDown") {
                    freq = freq - 10;
                } else if (btnName == "ampUp") {
                    amp = amp + 10;
                } else {
                    amp = amp - 10;
                }
            };

            function Circle(x, y, radius, borderWidth) {
                this.x = x;
                this.y = y;
                this.radius = radius;
                this.borderWidth = borderWidth;

                this.drawCircle = function(context) {
                    context.beginPath();
                    context.arc(this.x, this.y, this.radius, 0, 2 * Math.PI, false);
                    context.fillStyle = '#00FFFF';
                    context.fill();
                    context.lineWidth = this.borderWidth;
                    context.strokeStyle = '#003300';
                    context.stroke();
                }

                this.animate = function(canvas, context, startTime) {
                    // update
                    var time = (new Date()).getTime() - startTime;
                    var linearSpeed = 100;
                    // pixels / second
                    var newX = linearSpeed * time / 1000;

                    context.clearRect(this.x - this.radius * 2, this.y - this.radius * 2,
                                      this.x + this.radius * 2, this.y + this.radius * 2);


                    drawTail(context, tail);

                    if (newX < canvas.width - this.radius * 2) {
                        this.x = newX;
                        this.y = sinePoint(base, time/10000, amp, freq);

                        tail.push(new Point(this.x, this.y));
                        if (tail.length == 100) {
                            tail.shift();
                        }
                    }

                    this.drawCircle(context);

                    // request new frame
                    var circle = this;

                    if (self.animation) {
                        requestAnimFrame(function() {
                            circle.animate(canvas, context, startTime);
                        });
                    }
                }
            }

            setTimeout(function() {
                var startTime = (new Date()).getTime();
                var circle = new Circle(0, 75, 20, 2);
                circle.animate(canvas, context, startTime);
            }, 1000);

            var tail = [];

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
        }

    });

    return new View();
});
