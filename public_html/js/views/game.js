define([
    'backbone',
    'tmpl/game',
    'api/primitives',
    'api/drawer',
    'controllers/socketman'
], function(
    Backbone,
    tmpl
){

// TODO we need a hide function to unbind keypress event from $document
var GameView = Backbone.View.extend({
        controller: SocketMan,
        drawer : new SnakeDrawer(),

        snakeHolder : new CurrentSnakeHolder(),
        snakes : [],
        started : false,
        snakeId : 0,

        el: $('.game'),
        template: tmpl,

        events: {
            "click #gmscr": "gameClick"
        },

        initialize: function() {
            this.render();
            this.$el.hide();
            //            TODO NEED TO UNBIND EVENT ON HIDE()

            this.started = false;
            this.listenTo(controller, 'startLoad', showWait);
            this.listenTo(controller, 'startGame', startGame);
        },

        showWait: function () {
            // set wait window
        },

        render: function () {
            this.$el.html(this.template());
        },

        startGame: function(data) {
            this.snakeId = data.snakeId;
            for (var I in data.snakes) {
                this.snakes.append(new Snake(I));
            }
            this.started = true;
        },

        show: function () {
            this.trigger('reshow');
            this.update();
        },

        update: function () {
            if (! this.started) {
                this.showNoGame();
                return;
            }

            $(document).bind('keydown', this.keyPressed);
            var canvas = document.getElementById('snakeGame'),
            context = canvas.getContext('2d');

            for (var snake in this.snakes) {
                drawer.draw(snake);
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