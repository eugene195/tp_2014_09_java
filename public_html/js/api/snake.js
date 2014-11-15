
function Snake(snakeObj) {
    this.head = new Circle(snakeObj.posX, snakeObj.posY, 5);
    this.tail = new Tail();

    this.color = snakeObj.color.toLowerCase();
    this.direction = snakeObj.direction;
    this.snakeId = snakeObj.snakeId;

    this.grow = function () {
        this.tail.append(this.head.x, this.head.y);
    };

    this.setCoordinates = function(x, y) {
        if (this.head.x && this.head.y) {
            this.grow();
        }
        this.head.x = x;
        this.head.y = y;
    };

    this.changeDirection = function(newDirection) {
        this.direction = newDirection;
    };

    this.isWinner = function(winnerId) {
        if (winnerId == this.snakeId)
            return true;
        return false;
    };

    this.drawSnake = function (context) {
        this.head.clear(context);
        this.tail.drawTail(context, this.color);
        this.head.drawCircle(context, '#00FF00', 1);
    };
}
//
//function SnakeControl(snakeObj) {
//    this.constructor(snakeObj);
//    this.setDirection = function (direction) {
//        this.changeDirection(direction);
//        return true;
//    };
//}
//
//SnakeControl.prototype = Snake;

function CurrentSnakeHolder () {
    this.snake = null;

    this.setSnake = function(snake) {
        this.snake = snake;
    };

    this.setCoordinates = function(x, y) {
        this.snake.setCoordinates(x, y);
    };

    this.isWinner = function(winnerID) {
        return this.snake.isWinner(winnerID);
    };

    this.setDirection = function(direction) {
//        Some BS code
        this.snake.changeDirection(direction);
        return true;
    };
}