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
function Snake(x, y, direction, color, linearSpeed, tail, name) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.direction = direction;
    this.linearSpeed = linearSpeed;
    this.tail = tail;
    this.name = name;           // Name of current user to connect with server TODO

    this.getTail = function() {
        return tail.tail;
    }

    this.setCoordinates = function(x, y) {
        this.x = x;
        this.y = y;
    }

    this.move = function() {
        this.x += this.direction.x * this.linearSpeed;
        this.y += this.direction.y * this.linearSpeed;
    }

    this.changeDirection = function(newDirection) {
        this.direction = newDirection;
    }
}

function CurrentSnakeHolder () {
    this.snake = null;

    this.setSnake = function(snake) {
        this.snake = snake;
    }

    this.setCoordinates = function(x, y) {
        this.snake.setCoordinates(x, y);
    }

    this.setDirection = function(direction) {
        if ( !((this.snake.direction.x == direction.x) || (this.snake.direction.y == direction.y)) ) {
            this.snake.changeDirection(direction);
            return true;
        }
        return false;
    }
}