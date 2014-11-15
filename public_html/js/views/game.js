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
    width: 0,
    height: 0,
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
//    Todo
        alert("Please wait for data to load");
    },

    showNoGame: function () {
//    TODO
//        alert("The game hasn't started yet");
    },

    startGame: function(data) {
        this.width = data.width;
        this.height = data.height;
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

    endGame: function (data) {
        var winnerId = data.winner;
        if (this.snakeHolder.isWinner(winnerId))
            alert("You are a winner");
        else
            alert("You are a loser");
    },

    initialize: function() {
        this.render();
        this.$el.hide();

        this.started = false;
        this.listenTo(this.controller, 'startLoad', this.showWait);
        this.listenTo(this.controller, 'adjustGame', this.startGame);
        this.listenTo(this.controller, 'endGame', this.endGame);
        this.listenTo(this.controller, 'tick', this.onTick);
        this.listenTo(this.viewman, 'view-hide', this.onhide);

        $(document).on('keydown', {object : this}, this.keyPressed);

    },

    render: function () {
        var sizes = { width: this.width,
                      height: this.height
                    };
        this.$el.html(this.template(sizes));
    },

    show: function () {
        this.trigger('rerender', this);
        this.started = true;
    },

    update: function () {
        this.trigger('rerender', this);
        if (! this.started) {
            this.showNoGame();
            return;
        }

//      Need to be somewhere else

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
//            $(document).off('keydown');
            this.started = false;
//            this.controller.dropSocket();
        }
    },

//---------------------
//      Events
    gameClick: function(event) {
        alert("Great shot");
    },

    keyPressed: function(e) {
        var that = e.data.object;
        var code = e.keyCode || e.which;
        var dirs = {
            37: "LEFT",
            38: "DOWN",
            39: "RIGHT",
            40: "UP"
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
            Console.log("Unknown key");
    }

    });
    return new GameView();
});