
function Snake(snakeObj) {
    this.width = snakeObj.size;
    this.head = new Rectangle(snakeObj.posX, snakeObj.posY, this.width / 2);
    this.tail = new Tail();
    this.name = snakeObj.name;
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
        this.head.draw(context, this.color, 1);
    };
}

function CurrentSnakeHolder (snakeObj) {
    this.left_right = ["", "RIGHT", "LEFT"];
    this.up_down = ["", "UP", "DOWN"];
    this.snake = snakeObj;

    this.setCoordinates = function(x, y) {
        this.snake.setCoordinates(x, y);
    };

    this.isWinner = function(winnerID) {
        return this.snake.isWinner(winnerID);
    };

    this.setDirection = function(direction) {
        if ((this.left_right.indexOf(this.snake.direction) > 0) && (this.left_right.indexOf(direction) > 0))
            return false;
        if ( (this.up_down.indexOf(this.snake.direction) > 0) && (this.up_down.indexOf(direction) > 0))
            return false;
        console.log("second");
        this.snake.changeDirection(direction);
        return true;
    };
}

CurrentSnakeHolder.prototype = Object.create(Snake.prototype);