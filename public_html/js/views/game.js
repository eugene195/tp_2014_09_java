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
    snakes : [],
//    Names
    names : [],
    started : false,

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
//    TODO
//        alert("The game hasn't started yet");
    },

    startGame: function(data) {
        this.width = data.width;
        this.height = data.height;
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
                this.snakeHolder = new CurrentSnakeHolder(snake)
            this.snakes.push(snake);
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
        var winnerId = data.winner;
        if (this.snakeHolder.isWinner(winnerId))
            $('#result_message').html("You are a winner");
        else
            $('#result_message').html("You are a loser");

        $('.game_overlay').fadeIn(400, // сначала плавно показываем темную подложку
            function(){ // после выполнения предъидущей анимации
                $('.game_modal_form')
                    .css('display', 'block') // убираем у модального окна display: none;
                    .animate({opacity: 1, top: '50%'}, 200); // плавно прибавляем прозрачность одновременно со съезжанием вниз
        });
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
        var gameContent = {
            snakes: this.snakes,
            sizes: {
                    width: this.width * this.sizeModifier,
                    height: this.height * this.sizeModifier }
        };
        this.$el.html(this.template(gameContent));
    },

    show: function () {
        this.trigger('rerender', this);
        this.started = true;
    },

    update: function () {
        this.trigger('rerender', this);
        if (! this.started) {
            this.showNoGame();
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
//            $(document).off('keydown');
            this.started = false;
//            this.controller.dropSocket();
        }
    },

//---------------------
//      Events

    modalClose: function(e) {
        console.log("Close");
        $('.game_modal_form')
            .animate({opacity: 0, top: '45%'}, 200,  // плавно меняем прозрачность на 0 и одновременно двигаем окно вверх
            function(){ // после анимации
                $('.game_modal_form').css('display', 'none'); // делаем ему display: none;
                $('.game_overlay').fadeOut(400); // скрываем подложку
            }
        );
    },

    keyPressed: function(e) {
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