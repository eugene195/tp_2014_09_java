function colorCheck (strColor) {
    this.colors = {
        "WHITE": "#ffffff",
        "BLACK": "#000000",
        "ORANGE": "#FFA200",
        "BLUE": "#3f9db8",
        "RED": "#FF0000",
        "GREEN": "#00FF00",
        "CYAN": "#dfe9ec",
        "BROWN": "#A52A2A"
    }

    return this.colors[strColor];
}

function Point (x, y) {
    this.x = x;
    this.y = y;
}

function Tail() {
    this.tail = [];

    this.drawTail = function (context, color) {
        context.beginPath();
        context.lineWidth = 10;
        context.strokeStyle = color;

        if (this.tail.length == 0) return;

        context.moveTo(this.tail[0].x, this.tail[0].y);
        for (var I in this.tail) {
            context.lineTo(this.tail[I].x, this.tail[I].y);
        }
        context.stroke();
    };

    this.append = function (x, y) {
        this.tail.push(new Point(x, y));
        if (this.tail.length == 100) {
            this.tail.shift();
        }
    };

    this.getPoints = function () {
        return this.tail;
    };

    this.clearAll = function () {
        this.tail = [];
    };
}

function Rectangle(x, y, size) {
    this.x = x;
    this.y = y;
    this.size = size;

    this.draw = function(context, color, borderWidth) {
        context.beginPath();
        context.rect(this.x - this.size, this.y - this.size, this.size * 2, this.size * 2);
        context.fillStyle = color;
        context.fill();
        context.lineWidth = borderWidth;
        context.strokeStyle = "#ffffff";
        context.stroke();
    };

    this.clear = function (context) {
        context.clearRect(this.x - this.size, this.y - this.size, this.size, this.size);
    };
}

function Circle(x, y, radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;

    this.draw = function(context, color, borderWidth) {
        context.beginPath();
        context.arc(this.x, this.y, this.radius, 0, 2 * Math.PI, false);
//        context.rect(this.x - this.radius, this.y - this.radius, this.radius * 2, this.radius * 2);
        context.fillStyle = color;
        context.fill();
        context.lineWidth = borderWidth;
        //context.strokeStyle = '#003300';
        context.strokeStyle = color;
        context.stroke();
    };

    this.clear = function (context) {
        var R = this.radius, D = this.diameter();
        context.clearRect(this.x - R - 3, this.y - R - 3, D + 6, D + 6);
    };

    this.diameter = function () {
        return 2*this.radius;
    };
}