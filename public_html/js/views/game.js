define([
    'backbone',
    'tmpl/game',
    'controllers/socketman',
    'controllers/viewman',
    'api/primitives',
    'api/drawer'
], function(
    Backbone,
    tmpl,
    SocketMan,
    viewMan
){

var GameView = Backbone.View.extend({
    controller: SocketMan,
    viewman: viewMan,

    drawer : new SnakeDrawer(),
    snakeHolder : new CurrentSnakeHolder(),
    snakes : [],
    started : false,

    el: $('.game'),
    template: tmpl,

    events: {
        "click #gmscr": "gameClick"
    },

    showWait: function () {
//    To Test
        alert("Please wait for data to load");
    },

    startGame: function(data) {
        var myID = data.snakeId;
        var length = data.snakes.length;
        for (var i = 0; i < length; i++) {
            var current = data.snakes[i];
            var snake = new Snake(current);
            if (current.snakeId == myID)
                this.snakeHolder.setSnake(snake);
            this.snakes.push(snake);
        }
        this.started = true;
        this.update();
    },

    onTick: function(data) {
        var length = data.snakes.length;
        for (var i = 0; i < length; i++) {
            var current = data.snakes[i];
            this.snakes[current.snakeId].setCoordinates(current.newX, current.newY);
        }
        var canvas = document.getElementById('snakeGame'),
        context = canvas.getContext('2d');
        this.redraw(context);
    },


    initialize: function() {
        this.render();
        this.$el.hide();

        this.started = false;
        this.listenTo(this.controller, 'startLoad', this.showWait);
        this.listenTo(this.controller, 'adjustGame', this.startGame);
        this.listenTo(this.controller, 'tick', this.onTick);
        this.listenTo(this.viewman, 'view-hide', this.onhide);

    },

    render: function () {
        this.$el.html(this.template());
    },

    show: function () {
        this.trigger('reshow', this);
        // After resources were loaded
//        this.controller.confirm();
        this.started = true;

//        this.update();
    },

    update: function () {
        if (! this.started) {
//            this.showNoGame();
            return;
        }

//      Need to be somewhere else
        $(document).on('keydown', {object : this}, this.keyPressed);
        var canvas = document.getElementById('snakeGame'),
        context = canvas.getContext('2d');

        this.redraw(context);
    },

    redraw: function (context) {
        var length = this.snakes.length;
        for(var i = 0; i < length; i++) {
            var current = this.snakes[i];
            this.drawer.draw(current, context);
        }
    },

    onhide: function (view) {
        if (this === view) {
            $(document).unbind('keydown');
            this.started = false;
            this.controller.dropSocket();
        }
    },

//---------------------
//      Events
    gameClick: function(event) {
        alert("Great shot");
    },

    keyPressed: function(e) {
        debugger;
        var that = e.data.object;
        var code = e.keyCode || e.which;
        var dirs = {
            37: "LEFT",
            38: "UP",
            39: "RIGHT",
            40: "DOWN"
        };
        if (code in dirs) {
            var message = {
                action: "handleKey",
                data : {
                    direct: dirs[code]
                }
            }
            if (that.snakeHolder.setDirection(dirs[code])) {
                that.controller.sendMessage(message);
            }
        }
        else
            alert("your movement is unknown");
    }

    });
    return new GameView();
});