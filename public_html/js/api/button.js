/**
 * Created by max on 15.11.14.
 */

function getMousePos(canvas, evt) {
    var rect = canvas.getBoundingClientRect();
    return new Point(evt.clientX - rect.left, evt.clientY - rect.top);
}

function Button(start, text, onclick) {
    this.start = start;
    this.text = text;
    this.width = 150;
    this.height = 50;
    this.onclick = onclick;

    this.drawButton = function(context) {
        context.beginPath();
        context.rect(this.start, 0, this.width, this.height);
        context.fillStyle = 'yellow';
        context.fill();
        context.font = '12pt Calibri';
        context.lineWidth = 1;
        context.strokeStyle = 'blue';
        context.strokeText(this.text, this.start + 5, this.height / 2);
        context.lineWidth = 2;
        context.strokeStyle = 'black';
        context.stroke();
    };

    this.handleClick = function(x, y) {
        if ((x >  this.start) && (x < this.start + this.width) && (y > 0) && (y < this.height)) {
            this.onclick();
        }
    };
}