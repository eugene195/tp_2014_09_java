define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

// KeyPressed is a global event. We need to access current player globally. One way of doing it is setting a holder
function CurrentSnakeHolder () {
    this.snake = null;

    this.setSnake = function(snake) {
        this.snake = snake;
    }

    this.setCoordinates = function(x, y) {
        this.snake.x = x;
        this.snake.y = y;
    }

    this.setDirection = function(direction) {
        if ( !((this.snake.direction.x == direction.x) || (this.snake.direction.y == direction.y)) )
            this.snake.direction = direction;
    }
}

// These classes better be replaced somewhere
// <--
function Point (x, y) {
    this.x = x;
    this.y = y;
}

function Direction (x, y) {
    this.x = x;
    this.y = y;
}

function Tail() {
    this.tail = [];

    this.append = function (x, y) {
        this.tail.push(new Point(x, y));
        if (this.tail.length == 100) {
            this.tail.shift();
        }
    };

    this.clearAll = function () {
        this.tail = [];
    }
}
// -->

// Main snake class. Maybe we will need a snake model, but I dunno how to make it.
function Snake(x, y, direction, color, linearSpeed, tail) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.direction = direction;
    this.linearSpeed = linearSpeed;
    this.tail = tail;

    this.getTail = function() {
        return tail.tail;
    }
    this.move = function() {
        this.x += this.direction.x * this.linearSpeed;
        this.y += this.direction.y * this.linearSpeed;
    }

    this.changeDirection = function(newDirection) {
        this.direction = newDirection;
    }
}

function SnakeDrawer() {
//  Current Drawer Parameters
    this.D = 10;
    this.R = this.D / 2;
    this.headColor = '#00FF00';
//  Current Context
    this.context = null;
//    Main draw function called in animate()
    this.draw = function(snake, context) {
        var tail = snake.getTail();
        this.context = context;
        this.context.clearRect(snake.x - this.R - 3, snake.y - this.R - 3, this.D + 6, this.D + 6);
        if (tail.length > 0)
            this.drawTail(tail);
        this.drawHead(snake);
    }

    this.drawHead = function(snake) {
        this.context.beginPath();
        this.context.arc(snake.x, snake.y, this.R, 0, 2 * Math.PI, false);
        this.context.fillStyle = this.headColor;
        this.context.fill();
        this.context.lineWidth = 1;
        this.context.strokeStyle = '#003300';
        this.context.stroke();
    }

    this.drawTail = function(tail) {
        this.context.beginPath();
        this.context.lineWidth = 4;
        this.context.strokeStyle = 'white';

        if (tail.length == 0) return;

        this.context.moveTo(tail[0].x, tail[0].y);
        for (var I in tail) {
            this.context.lineTo(tail[I].x, tail[I].y);
        }
        this.context.stroke();
    }
}

// Current player class
var snakeHolder = new CurrentSnakeHolder();
var drawer = new SnakeDrawer();
// TODO we need a hide function to unbind keypress event from $document
var View = Backbone.View.extend({
        el: $('.game'),
        template: tmpl,

        events: {
            "click #gmscr": "gameClick"
        },

        initialize: function() {
            this.render();
            this.$el.hide();

//            TODO NEED TO UNBIND EVENT ON HIDE()
        },
        render: function () {
            this.$el.html(this.template());
        },
        show: function () {
            this.trigger('reshow', this);
//            TODO Get Enemies list here
            var enemies = [];
            this.play(enemies);
        },

        play: function (enemies) {
//        Here we bind a keyPress Event to $document. If we do a $game, we will have to setFocus
            $(document).bind('keydown', this.keyPressed);
            var canvas = document.getElementById('snakeGame'),
            context = canvas.getContext('2d');

//            Snake constructor, will be called somewhere else
            var defaultSpeed = 0.35;
            var first = new Snake(200, 100, new Direction(1, 0), "#FF0000", defaultSpeed, new Tail());
            snakeHolder.setSnake(first);

            var interval = 1;

            var animate = function(snake, canvas, context) {
                drawer.draw(snake, context);
                snake.move();

//              SocketMan.moveSnake(snake.currentDirection);
                if (snake.x < canvas.width - drawer.D) {
                    snake.tail.append(snake.x, snake.y);
                } else {
                    snake.tail.clearAll();
                    context.clearRect(0, 0, canvas.width, canvas.height);
                }
            };
            this.timerID = setInterval(function () {
                animate(first, canvas, context);
            }, interval);
        },

//---------------------
//      Events
        gameClick: function(event) {
            alert("Great shot");
        },

        keyPressed: function(e) {
            var code = e.keyCode || e.which;

            switch(code) {
                case 37:
                    snakeHolder.setDirection(new Direction(-1, 0));
                    break;
                case 38:
                    snakeHolder.setDirection(new Direction(0, -1));
                    break;
                case 39:
                    snakeHolder.setDirection(new Direction(1, 0));
                    break;
                case 40:
                    snakeHolder.setDirection(new Direction(0, 1));
                    break;
            }
//            TODO
//            SocketMan.ChangeDirection(direction)
        }

    });
    return new View();
});