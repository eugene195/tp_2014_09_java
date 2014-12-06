
function Snake(snakeObj, size, name) {
    this.width = size;
    this.head = new Rectangle(snakeObj.posX, snakeObj.posY, this.width / 2);
    this.tail = new Tail();
    this.name = name;
    this.color = colorCheck(snakeObj.color);
    this.direction = snakeObj.direction;
    this.snakeId = snakeObj.snakeId;

    this.grow = function () {
        this.tail.append(this.head.x, this.head.y);
    };

    this.setCoordinates = function(x, y) {
        if (this.head.x && this.head.y) {
            this.grow();
        }
        this.head.x = x * this.width;
        this.head.y = y * this.width;
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
        //this.head.clear(context);
//        this.tail.drawTail(context, this.color);
        this.head.draw(context, this.color, 1);
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