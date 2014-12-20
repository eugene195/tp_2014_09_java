define(function () { return function (__fest_context){"use strict";var __fest_self=this,__fest_buf="",__fest_chunks=[],__fest_chunk,__fest_attrs=[],__fest_select,__fest_if,__fest_iterator,__fest_to,__fest_fn,__fest_html="",__fest_blocks={},__fest_params,__fest_element,__fest_debug_file="",__fest_debug_line="",__fest_debug_block="",__fest_htmlchars=/[&<>"]/g,__fest_htmlchars_test=/[&<>"]/,__fest_short_tags = {"area":true,"base":true,"br":true,"col":true,"command":true,"embed":true,"hr":true,"img":true,"input":true,"keygen":true,"link":true,"meta":true,"param":true,"source":true,"wbr":true},__fest_element_stack = [],__fest_htmlhash={"&":"&amp;","<":"&lt;",">":"&gt;","\"":"&quot;"},__fest_jschars=/[\\'"\/\n\r\t\b\f<>]/g,__fest_jschars_test=/[\\'"\/\n\r\t\b\f<>]/,__fest_jshash={"\"":"\\\"","\\":"\\\\","/":"\\/","\n":"\\n","\r":"\\r","\t":"\\t","\b":"\\b","\f":"\\f","'":"\\'","<":"\\u003C",">":"\\u003E"},___fest_log_error;if(typeof __fest_error === "undefined"){___fest_log_error = (typeof console !== "undefined" && console.error) ? function(){return Function.prototype.apply.call(console.error, console, arguments)} : function(){};}else{___fest_log_error=__fest_error};function __fest_log_error(msg){___fest_log_error(msg+"\nin block \""+__fest_debug_block+"\" at line: "+__fest_debug_line+"\nfile: "+__fest_debug_file)}function __fest_replaceHTML(chr){return __fest_htmlhash[chr]}function __fest_replaceJS(chr){return __fest_jshash[chr]}function __fest_extend(dest, src){for(var i in src)if(src.hasOwnProperty(i))dest[i]=src[i];}function __fest_param(fn){fn.param=true;return fn}function __fest_call(fn, params,cp){if(cp)for(var i in params)if(typeof params[i]=="function"&&params[i].param)params[i]=params[i]();return fn.call(__fest_self,params)}function __fest_escapeJS(s){if (typeof s==="string") {if (__fest_jschars_test.test(s))return s.replace(__fest_jschars,__fest_replaceJS);} else if (typeof s==="undefined")return "";return s;}function __fest_escapeHTML(s){if (typeof s==="string") {if (__fest_htmlchars_test.test(s))return s.replace(__fest_htmlchars,__fest_replaceHTML);} else if (typeof s==="undefined")return "";return s;}var sessions=__fest_context;__fest_buf+=("<div class=\"wrapper\"><form class=\"form\" name=\"gamelist-form\" data-action=\"\/startGame\" method=\"POST\"><div class=\"form__header\"><h1 class=\"form__header__h1\">Game List</h1><span class=\"form__header__span\">Let\'s get started.</span></div><div class=\"form__content\"><div class=\"form__content__row\"><label for=\"players-cnt\">Players:</label><span class=\"right\"><input type=\"button\" class=\"button-spinbox\" id=\"subPlayer\" value=\"-\"/><div id=\"players-cnt\" class=\"background\" style=\"display:inline-block\">2</div><input type=\"button\" class=\"button-spinbox\" id=\"addPlayer\" value=\"+\"/></span></div><div class=\"form__content__row\"><label>Screen size:</label><span class=\"css3-metro-dropdown right\"><select name=\"size\"><option value=\"1\">Small</option><option value=\"2\">Medium</option><option value=\"3\">Large</option></select></span></div><div class=\"form__content__row\"><label for=\"speed\">Game Speed:</label><span class=\"css3-metro-dropdown right\"><select name=\"speed\"><option value=\"1\">Fast</option><option value=\"2\">Normal</option><option value=\"3\">Slow</option></select></span></div><div class=\"form__content__row\"><label for=\"launch-time\">Waiting time:</label><span class=\"css3-metro-dropdown right\"><select name=\"launch-time\"><option>1 min</option><option>5 min</option><option>10 min</option></select></span></div></div>");var i,game,__fest_iterator0;try{__fest_iterator0=sessions || {};}catch(e){__fest_iterator={};__fest_log_error(e.message);}for(i in __fest_iterator0){game=__fest_iterator0[i];__fest_buf+=("<div class=\"session\"><div class=\"sessions__players\">");var j,player,__fest_iterator1;try{__fest_iterator1=game.players || {};}catch(e){__fest_iterator={};__fest_log_error(e.message);}for(j in __fest_iterator1){player=__fest_iterator1[j];try{__fest_buf+=(__fest_escapeHTML(player))}catch(e){__fest_log_error(e.message + "52");}}__fest_buf+=("</div><button class=\"sessions__entry button_small\">Join</button><input class=\"sessions__id\" type=\"hidden\" value=\"");try{__fest_buf+=(__fest_escapeHTML(game.sessionId))}catch(e){__fest_log_error(e.message + "61");}__fest_buf+=("\"/><div class=\"sessions__info\">");var i,__fest_to2,__fest_from2,__fest_iterator2;try{__fest_from2=1;__fest_to2=game.playersCnt;}catch(e){__fest_from2=0;__fest_to2=0;__fest_log_error(e.message);}for(i = __fest_from2;i<=__fest_to2;i++){__fest_buf+=("<img class=\"icon-size right\" src=\"..\/images\/user.png\"/>");}try{__fest_buf+=(__fest_escapeHTML(game.time))}catch(e){__fest_log_error(e.message + "70");}__fest_buf+=("</div></div>");}__fest_buf+=("<div class=\"form__footer\"><div class=\"buttons_wrapper\"><a href=\"#\" id=\"form__footer__back\" class=\"button_orange left\">Back</a><input type=\"submit\" name=\"submit\" value=\"Create\" class=\"button right\"/></div></div></form></div><div class=\"gradient\"></div>");__fest_to=__fest_chunks.length;if (__fest_to) {__fest_iterator = 0;for (;__fest_iterator<__fest_to;__fest_iterator++) {__fest_chunk=__fest_chunks[__fest_iterator];if (typeof __fest_chunk==="string") {__fest_html+=__fest_chunk;} else {__fest_fn=__fest_blocks[__fest_chunk.name];if (__fest_fn) __fest_html+=__fest_call(__fest_fn,__fest_chunk.params,__fest_chunk.cp);}}return __fest_html+__fest_buf;} else {return __fest_buf;}} ; });