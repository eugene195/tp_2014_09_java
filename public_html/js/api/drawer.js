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