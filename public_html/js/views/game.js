define([
    'backbone',
    'tmpl/game',
    'controllers/socketman',
    'controllers/viewman',
    'api/primitives',
    'api/snake'
], function(
    Backbone,
    tmpl,
    SocketMan,
    viewMan
){

var GameView = Backbone.View.extend({
    controller: SocketMan,
    viewman: viewMan,
    width: 0,
    height: 0,
    sizeModifier: 1,
    snakeHolder : new CurrentSnakeHolder(),
//    Ids
    snakes : {},
//    Names
    names : [],

    el: $('.game'),
    template: tmpl,

    events: {
        "click .game_overlay": "modalClose",
        "click #game_show": "modalClose"
    },

    showWait: function () {
//    Todo
        alert("Please wait for data to load");
    },

    showNoGame: function () {
        $('.spinner').css('display', 'block');
        $('#result_message').html("No Game in Action");
        this.fade();
    },

    startGame: function(data) {
        this.width = data.width;
        this.height = data.height;

        // Вопрос 1
        this.sizeModifier = this.width * this.height * 0.001 / 2;
        var myID = data.snakeId;
        var names = data.names;
        var length = data.snakes.length;

        for (var i = 0; i < length; i++) {
            var current = data.snakes[i];
            current.name = names[i];
            current.size = this.sizeModifier;
            var snake = new Snake(current);
            if (current.snakeId == myID)
                this.snakeHolder = new CurrentSnakeHolder(snake);

            this.snakes[current.snakeId] = snake;
        }
        this.started = true;
        this.update();
    },

    onTick: function(data) {
        var length = data.snakes.length;
        for (var i = 0; i < length; i++) {
            var current = data.snakes[i];
            this.snakes[current.snakeId].setCoordinates(current.newX, current.newY);
        }
        var canvas = document.getElementById('snakeGame'),
        context = canvas.getContext('2d');
        this.redraw(context);
    },

    endGame: function (data) {
        this.started = false;
        var winnerId = data.winner;
        $('.spinner').css('display', 'none');
        if (this.snakeHolder.isWinner(winnerId))
            $('#result_message').html("You Win");
        else
            $('#result_message').html("You Lose");
        this.fade();
        this.snakes = {};
        this.controller.dropSocket();
    },

    initialize: function() {
        this.render();
        this.$el.hide();

        this.started = false;
        this.listenTo(this.controller, 'startLoad', this.showWait);
        this.listenTo(this.controller, 'adjustGame', this.startGame);
        this.listenTo(this.controller, 'endGame', this.endGame);
        this.listenTo(this.controller, 'tick', this.onTick);
        this.listenTo(this.viewman, 'view-hide', this.onhide);

        $(document).on('keydown', {object : this}, this.keyPressed);

    },

    render: function () {
        var snakes = [];
        for (var I in this.snakes) {
            if (this.snakes.hasOwnProperty(I)) {
                snakes.push(this.snakes[I]);
            }
        }

        var gameContent = {
            snakes: snakes,
            sizes: {
                    width: this.width * this.sizeModifier,
                    height: this.height * this.sizeModifier }
        };
        this.$el.html(this.template(gameContent));
    },

    show: function () {
        this.trigger('rerender', this);
    },

    update: function () {
        this.trigger('rerender', this);
        if (! this.started) {
            return;
        }

        var canvas = document.getElementById('snakeGame'),
        context = canvas.getContext('2d');

        this.redraw(context);
    },

    redraw: function (context) {
        var length = this.snakes.length;
        for(var i = 0; i < length; i++) {
            var current = this.snakes[i];
            current.drawSnake(context);
        }
    },

    onhide: function (view) {
        if (this === view) {
            this.started = false;
            this.controller.dropSocket();
        }
    },

    fade: function () {
       $('.overlay').fadeIn(400,
            function(){
                $('.modal')
                    .css('display', 'block')
                    .animate({opacity: 1, top: '50%'}, 200);
        });
    },

//---------------------
//      Events

    modalClose: function() {
        $('.modal')
            .animate({opacity: 0, top: '45%'}, 200,
                function(){
                    $('.overlay').fadeOut(400);
                }
            );
         $('.modal').css('display', 'none');
    },

    keyPressed: function(e) {
        //e.preventDefault();
        var that = e.data.object;
        var code = e.keyCode || e.which;
        var dirs = {
            37: "LEFT",
            38: "DOWN",
            39: "RIGHT",
            40: "UP"
        };
        if (code in dirs) {
            var message = {
                action: "handleKey",
                data : {
                    direct: dirs[code]
                }
            }
            if (that.snakeHolder.setDirection(dirs[code])) {
                that.controller.sendMessage(message);
            }
        }
        else
            console.log("Unknown key");
    }

    });
    return new GameView();
});