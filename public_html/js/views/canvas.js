define([
    'backbone',
    'tmpl/canvas',
    'controllers/viewman',
    'api/primitives',
    'api/button'
], function(
    Backbone,
    tmpl,
    viewManager
){

    function storageSupport() {
        try {
            return 'localStorage' in window && window['localStorage'] !== null;
        } catch (e) {
            return false;
        }
    }

    var Params = {
        amp: 100,
        freq: 40,
        base: 200,

        load: function () {
            if (storageSupport()) {
                var localStorage = window.localStorage;
                this.amp = localStorage['amp'] || 100;
                this.freq = localStorage['freq'] || 40;
                this.base = localStorage['base'] || 200;
            }
        },
        save: function () {
            if (storageSupport()) {
                var localStorage = window.localStorage;
                localStorage['amp'] = this.amp;
                localStorage['freq'] = this.freq;
                localStorage['base'] = this.base;
            }
        }
    };

    function sinePoint(base, X, amp, freq) {
        return base + (amp * Math.sin(freq * X * Math.PI));
    }

    var View = Backbone.View.extend({
        el: $('.canvas'),
        template: tmpl,

        pause: false,
        timerID: 0,
        viewman: viewManager,

        initialize: function () {
            this.render();
            this.$el.hide();
            this.listenTo(this.viewman, 'view-hide', this.unlaunch);
        },

        render: function () {
            this.$el.html(this.template());
        },

        show: function () {
            this.trigger('reshow', this);
            this.launch();
        },

        launch: function () {
            var canvas = document.getElementById('myCanvas'),
                context = canvas.getContext('2d'),
                btnCanvas = document.getElementById('btnCanvas'),
                btnContext = btnCanvas.getContext('2d');

            var circle = new Circle(0, 75, 20);
            var tail = new Tail();

            var btnArray = [
                new Button(0, "Frequency Up", function () { Params.freq += 10; }),
                new Button(160, "Frequency Down", function () { Params.freq -= 10; }),
                new Button(320, "Amplitude Up", function () { Params.amp += 10; }),
                new Button(480, "Amplitude Down", function () { Params.amp -= 10; }),
                new Button(640, "Pause", function () { this.pause = !this.pause;}.bind(this))
            ];

            btnArray.forEach(function (entry) {
                entry.drawButton(btnContext);
            });

            btnCanvas.addEventListener('click', function (evt) {
                var mousePos = getMousePos(btnCanvas, evt);
                btnArray.forEach(function (entry) {
                    entry.handleClick(mousePos.x, mousePos.y);
                });
            }, false);

            var linearSpeed = 0.5,
                time = 0,
                interval = 1;

            var animate = function() {
                if (! this.pause) {
                    var newX = 4*linearSpeed * time;
                    time += interval;
                    console.log(time);

                    circle.clear(context);
                    tail.drawTail(context);

                    if (newX < canvas.width - circle.diameter()) {
                        circle.x = newX;
                        circle.y = sinePoint(Params.base, newX/1000, Params.amp, Params.freq);
                        tail.append(circle.x, circle.y);
                    }
                    else {
                        time = 0;
                        tail.clearAll();
                        context.clearRect(0, 0, canvas.width, canvas.height);
                    }

                    circle.drawCircle(context, '#00FFFF', 2);
                }
            }.bind(this);

            this.timerID = setInterval(function () {
                animate();
            }, interval);
        },

        unlaunch: function (view) {
            if (this === view) {
                clearInterval(this.timerID);
            }
        }
    });

    return new View();
});
