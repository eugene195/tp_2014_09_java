define(function () { return function (__fest_context){"use strict";var __fest_self=this,__fest_buf="",__fest_chunks=[],__fest_chunk,__fest_attrs=[],__fest_select,__fest_if,__fest_iterator,__fest_to,__fest_fn,__fest_html="",__fest_blocks={},__fest_params,__fest_element,__fest_debug_file="",__fest_debug_line="",__fest_debug_block="",__fest_htmlchars=/[&<>"]/g,__fest_htmlchars_test=/[&<>"]/,__fest_short_tags = {"area":true,"base":true,"br":true,"col":true,"command":true,"embed":true,"hr":true,"img":true,"input":true,"keygen":true,"link":true,"meta":true,"param":true,"source":true,"wbr":true},__fest_element_stack = [],__fest_htmlhash={"&":"&amp;","<":"&lt;",">":"&gt;","\"":"&quot;"},__fest_jschars=/[\\'"\/\n\r\t\b\f<>]/g,__fest_jschars_test=/[\\'"\/\n\r\t\b\f<>]/,__fest_jshash={"\"":"\\\"","\\":"\\\\","/":"\\/","\n":"\\n","\r":"\\r","\t":"\\t","\b":"\\b","\f":"\\f","'":"\\'","<":"\\u003C",">":"\\u003E"},___fest_log_error;if(typeof __fest_error === "undefined"){___fest_log_error = (typeof console !== "undefined" && console.error) ? function(){return Function.prototype.apply.call(console.error, console, arguments)} : function(){};}else{___fest_log_error=__fest_error};function __fest_log_error(msg){___fest_log_error(msg+"\nin block \""+__fest_debug_block+"\" at line: "+__fest_debug_line+"\nfile: "+__fest_debug_file)}function __fest_replaceHTML(chr){return __fest_htmlhash[chr]}function __fest_replaceJS(chr){return __fest_jshash[chr]}function __fest_extend(dest, src){for(var i in src)if(src.hasOwnProperty(i))dest[i]=src[i];}function __fest_param(fn){fn.param=true;return fn}function __fest_call(fn, params,cp){if(cp)for(var i in params)if(typeof params[i]=="function"&&params[i].param)params[i]=params[i]();return fn.call(__fest_self,params)}function __fest_escapeJS(s){if (typeof s==="string") {if (__fest_jschars_test.test(s))return s.replace(__fest_jschars,__fest_replaceJS);} else if (typeof s==="undefined")return "";return s;}function __fest_escapeHTML(s){if (typeof s==="string") {if (__fest_htmlchars_test.test(s))return s.replace(__fest_htmlchars,__fest_replaceHTML);} else if (typeof s==="undefined")return "";return s;}var sessions=__fest_context;__fest_buf+=("<div><div class=\"header\"><div class=\"header__infobar\"><h2>Games list</h2><h3>Choose Yours</h3></div></div><br/><div class=\"new-game\"><form class=\"new-game__form\" data-action=\"\/startGame\" method=\"POST\"><label for=\"players-cnt\">Количество игроков:</label><input name=\"players-cnt\" type=\"number\" value=\"2\"/><br/><p>Размеры игрового поля:</p><label name=\"width\">W:</label><input name=\"width\" type=\"number\" value=\"400\"/><label name=\"height\">H:</label><input name=\"height\" type=\"number\" value=\"400\"/><label for=\"speed\">Скорость игры:</label><select name=\"speed\"><option value=\"1\">Быстро</option><option value=\"2\">Нормально</option><option value=\"3\">Медленно</option></select><label for=\"launch-time\">Время до начала игры:</label><select name=\"launch-time\"><option>1 мин</option><option>5 мин</option><option>10 мин</option></select><br/><button type=\"submit\">Создать заявку</button></form></div><br/><div class=\"sessions\">");var i,game,__fest_iterator0;try{__fest_iterator0=sessions || {};}catch(e){__fest_iterator={};__fest_log_error(e.message);}for(i in __fest_iterator0){game=__fest_iterator0[i];__fest_buf+=("<hr/><div class=\"sessions__players\">");var j,player,__fest_iterator1;try{__fest_iterator1=game.players || {};}catch(e){__fest_iterator={};__fest_log_error(e.message);}for(j in __fest_iterator1){player=__fest_iterator1[j];__fest_buf+=("<strong>");try{__fest_buf+=(__fest_escapeHTML(player))}catch(e){__fest_log_error(e.message + "45");}__fest_buf+=("</strong>");}__fest_buf+=("</div><br/><button class=\"sessions__entry\">Вступить</button><br/><input class=\"sessions__id\" type=\"hidden\" value=\"&lt;fest:value&gt;game.sessionId&lt;\/fest:value&gt;\"/>");try{__fest_buf+=(__fest_escapeHTML(game.playersCnt))}catch(e){__fest_log_error(e.message + "53");}try{__fest_buf+=(__fest_escapeHTML(game.time))}catch(e){__fest_log_error(e.message + "54");}}__fest_buf+=("</div></div>");__fest_to=__fest_chunks.length;if (__fest_to) {__fest_iterator = 0;for (;__fest_iterator<__fest_to;__fest_iterator++) {__fest_chunk=__fest_chunks[__fest_iterator];if (typeof __fest_chunk==="string") {__fest_html+=__fest_chunk;} else {__fest_fn=__fest_blocks[__fest_chunk.name];if (__fest_fn) __fest_html+=__fest_call(__fest_fn,__fest_chunk.params,__fest_chunk.cp);}}return __fest_html+__fest_buf;} else {return __fest_buf;}} ; });