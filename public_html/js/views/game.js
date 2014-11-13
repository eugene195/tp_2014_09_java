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
        for (var I in data.snakes) {
            var snake = new Snake(I);
            if (I.snakeId == myID)
                snakeHolder.setSnake(snake);

            this.snakes.append(snake);
        }
        this.started = true;
    },

    initialize: function() {
        this.render();
        this.$el.hide();

        this.started = false;

        this.listenTo(this.controller, 'startLoad', this.showWait);
        this.listenTo(this.controller, 'adjustGame', this.startGame);
        this.listenTo(this.viewman, 'view-hide', this.onhide);
    },

    render: function () {
        this.$el.html(this.template());
    },

    show: function () {
        this.trigger('reshow', this);
        // After resources were loaded
//        this.controller.confirm();


        this.update();

    },

    update: function () {
        if (! this.started) {
//            this.showNoGame();
            return;
        }

        $(document).bind('keydown', this.keyPressed);
        var canvas = document.getElementById('snakeGame'),
        context = canvas.getContext('2d');

        for (var snake in this.snakes) {
            drawer.draw(snake);
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
        var code = e.keyCode || e.which;

        var dirs = {
            37: new Direction(-1, 0),
            38: new Direction(0, -1),
            39: new Direction(1, 0),
            40: new Direction(0, 1)
        };
        var mySnake = snakes[this.snakeId];
        if (mySnake.changeDirection(dirs[code])) {
            this.socketManager.changeDirection(dirs[code]);
        }
    }

    });
    return new GameView();
});