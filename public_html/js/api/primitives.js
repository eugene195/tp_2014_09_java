// These classes better be replaced somewhere
// <--
function Point (x, y) {
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

    this.getTail = function () {
        return this.tail;
    }
}
// -->



// Main snake class. Maybe we will need a snake model, but I dunno how to make it.
function Snake(snakeObj) {
    this.x = snakeObj.posX;
    this.y = snakeObj.posY;
    this.color = snakeObj.color;
    this.direction = snakeObj.direction;
    this.tail = new Tail();

    this.getTail = function() {
        return this.tail.getTail();
    }

    this.grow = function () {
        this.tail.append(this.x, this.y);
    }

    this.setCoordinates = function(x, y) {
        if (this.x && this.y) {
            this.   grow();
        }
        this.x = x;
        this.y = y;
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
//        Some BS code
        alert("Changed, direction = " + direction);
        this.snake.changeDirection(direction);
        return true;
    }
}