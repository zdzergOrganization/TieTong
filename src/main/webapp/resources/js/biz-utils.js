define(function(require, exports, module) {
	Biz.Utils.debug("loading... biz-utils");
	var $ = require('jquery');

	// 公共类，这部分纯js原生代码写的，无需使用第三方组件
	/** 格式化带占位符的字符串 */
	Biz.Utils.format = function() {
		if (arguments.length == 0) {
			return '';
		} else if (arguments.length == 1) {
			return arguments[0];
		} else {
			var s = arguments[0];
			if (typeof s == 'object' && s.length) {
				s = Biz.Utils.fnToStr(s);
			}
			for (var i = 1; i < arguments.length; i++) {
				s = s.replace(new RegExp("\\{" + (i - 1) + "\\}", "g"), arguments[i]);
			}
			return s;
		}
	};
	Biz.Utils.fnToStr = function(fn) {
		var str = '';
		if (fn) {
			for (var i = 0; i < fn.length; i++) {
				if (fn[i].outerHTML) {
					str += fn[i].outerHTML;
				} else if (fn[i].wholeText) {
					str += fn[i].wholeText;
				}
			}
		}
		return str;
	}
	/** 解析url中的query参数 */
	Biz.Utils.urlQueries = function(url) {
		var queryStr = url || location.search && location.search.substr(1);
		var result = {};
		if (queryStr) {
			var list = queryStr.split('&'), kv;
			for (var i = 0; i < list.length; i++) {
				kv = list[i].split('=');
				result[kv[0]] = decodeURIComponent(kv[1]);
			}
		}
		return result;
	};
	/** 对数组中的数据除重，相当于把一个List变Set */
	Biz.Utils.arrayUnique = function(a) {
		var newArr = [], obj = {};
		for (var i = 0, len = a.length; i < len; i++) {
			if (!obj[a[i]]) {
				newArr.push(a[i]);
				obj[a[i]] = 'new';
			}
		}
		return newArr;
	};
	/** 得到DOM元素的页面坐标位置 */
	Biz.Utils.getAxises = function(element) {
		var left = element.offsetLeft, top = element.offsetTop;
		var parent = element;
		Biz.Utils.debug(parent + '\t' + left + ', ' + top);
		while (parent = parent.offsetParent) {
			left += parent.offsetLeft;
			top += parent.offsetTop;
			Biz.Utils.debug(parent + '\t' + left + ', ' + top);
		}
		return {
			x : left,
			y : top
		};
	};
	/** 从TableGuid中解析出TableName */
	Biz.Utils.guidToTableName = function(guid) {
		return guid.substr(guid.lastIndexOf('.') + 1);
	};
	/** 将query中的信息赋值给ori，如果是null或undefined则沿用旧值 */
	Biz.Utils.combineParams = function(ori, query) {
		for ( var q in query) {
			if (ori[q] == null) {
				ori[q] = query[q];
			} else {
				ori[q] = query[q] || ori[q];
			}
		}
	};
	/** js原生的Boolean中，把非空字符串都认为是true，而这个函数，则把"true"、"t"(大小写不敏感)、非0数字(或字符串类型的非0数字)认为是true，其他都认为是false */
	Biz.Utils.toBoolean = function(val) {
		if (val === null || val === undefined) {
			return false;
		}
		if (typeof val === 'boolean') {
			return val;
		}
		var i = parseInt(val);
		if (!isNaN(i)) {
			val = i;
		}
		if (typeof val === 'string') {
			val = val.toLowerCase();
			return (val === 'true' || val === 't' || val === '1') ? true : false;
		} else if (typeof val === 'number') {
			return (val === 0 || isNaN(val)) ? false : true;
		}
	};
	/** 解析通用的服务器返回json格式。 */
	Biz.Utils.handlAjaxResult = function(data) {
		var json = data;
		var deferred = $.Deferred();
		try {
			if (typeof data === 'string') {
				json = JSON.parse(data);
			}
			if (json.errCode != 0) {
				deferred.reject(Biz.Constant.errCode);
			} else {
				deferred.resolve(json.data);
			}
		} catch (e) {
			deferred.reject(null);
		}
		return deferred;
	}
	/** 假设当前是offset条数据（从0开始），一页显示max条，总共totalCount条（可选）数据，现在是第几页 */
	Biz.Utils.pageNumber = function(offset, max, totalCount) {
		var num = parseInt(offset);
		if (num < 0) {
			return 0;
		} else if (totalCount && num >= totalCount) {
			num = totalCount - 1;
		}
		var page = Math.floor(num / (max));
		if (page * max == num) {
			return page + 1;
		}
		return page;
	};

	/**
	 * 计算一个字符串的长度，这个长度是字节的长度，即ASCII字符算1个长度，非ASCII字符算2个长度。
	 */
	Biz.Utils.strByteLen = function(str) {
		if (str === null || str === undefined) {
			return 0;
		}
		var len = 0;
		for (var i = 0; i < str.length; i++) {
			var c = str.charCodeAt(i);
			if (c > 255) {
				len += 2;
			} else {
				len++;
			}
		}
		return len;
	};

	/**
	 * @description 根据字节长度截取字符串，双字节字符长度为2，单字节字符长度为1。
	 * 
	 * @param str
	 *            原始字符串
	 * @param byteLength
	 *            期望截取后的最大长度（必须比3大，否则没意义）
	 * @return 截取后的字符串，如果有更多字符，则后面跟“...”，如果是null则返回''
	 */
	Biz.Utils.truncateByteLen = function(str, byteLength) {
		if (str === null || str === undefined) {
			return '';
		}
		if (str.length * 2 <= byteLength || byteLength <= 3) {
			return str;
		}
		var tempStr = "", len = 0;
		for (var i = 0; i < str.length; i++) {
			var c = str.charCodeAt(i);
			if (c > 255) {
				len += 2;
			} else {
				len++;
			}
			tempStr += str.charAt(i);
			if (len == byteLength && i == str.length - 1) {
				return str;
			}
			if (len >= byteLength) {
				do {
					if (c > 255) {
						len -= 2;
					} else {
						len--;
					}
					c = tempStr.charCodeAt(tempStr.length - 1);
					tempStr = tempStr.substring(0, tempStr.length - 1);
				} while (len > byteLength - 3);
				return tempStr + "...";
			}
		}
		return str;
	};

	// 公共类，这部分需要用到第三方组件
	/**
	 * @description 使用ajax的分页方法。
	 * 
	 * @param page
	 *            当前是第几页
	 * @param totalCount
	 *            记录总数
	 * @param limit
	 *            每页显示多少条记录
	 * @param data
	 *            json形式的参数，如果分页组件的class不是默认的.J_tAjaxPage，则需要指定{clazz: 分页组件的特征class}
	 */
	Biz.Utils.createAjaxPager = function(page, totalCount, limit, data) {
		var clazz = '.J_tAjaxPage';
		if (data != null && data.clazz != null) {
			clazz = data.clazz;
		}
		require([ 'jquery' ], function($) {
			var getTmpl = function() {
				var deferred = $.Deferred();
				if (Biz.Utils._createAjaxPager == null) {
					require([ 'juicer' ], function(juicer) {
						$.get(Biz.Constant.ctx + 'resources/tpl/juicer-pager.tpl', function(text, status) {
							Biz.Utils._createAjaxPager = juicer(text);
							deferred.resolve(Biz.Utils._createAjaxPager);
						});
					});
				} else {
					deferred.resolve(Biz.Utils._createAjaxPager);
				}
				return deferred;
			}
			getTmpl().done(function(tmpl) {
				var param = {
					page : (page === null ? '1' : page),
					totalCount : totalCount,
					totalPage : Math.ceil(totalCount / limit),
					pageSize : limit,
					data : data == null ? '' : JSON.stringify(data)
				};
				var html = tmpl.render(param);
				$(clazz).html(html);
			});
		});
	};
	/*
	Biz.Utils.createBoxUseTmpl = function(tmplPath, title, data) {
		var deffered = $.Deferred();
		if (!tmplPath) {
			deffered.reject();
			return deffered;
		}
		$.get(tmplPath).done(function(modalTmpl, status, obj) {
			if (typeof modalTmpl != 'string') {
				modalTmpl = obj.responseText;
			}
			require(['boxy'], function(Boxy) {
				var html = juicer(modalTmpl, data);
				var boxy = new Boxy(html, {
					title : title == null ? ' ' : title,
					closeText : 'x',
					modal : true,
					fixed : false,
					unloadOnHide : true
				});
				$('.boxy-wrapper .title-bar a').blur();
				deffered.resolve(boxy);
			});
		});
		return deffered;
	};*/
	/** 创建一个boxy弹出框，第一个参数是title（可选），第二个参数是body里的html内容（必选） */
	Biz.Utils.createBoxy = function() {
		var args = arguments;
		var deferred = $.Deferred();
		require([ 'boxy' ], function(Boxy) {
			var find = function(fn, selector) {
				var t = fn.find(selector).text();
				if (!t || t.length == 0) {
					t = fn.closest(selector).text();
				}
				return t;
			}
			var title, fn;
			if (args.length == 1) {
				fn = $(args[0]);
				title = find(fn, '#biz-modals-title');
			} else {
				fn = $(args[1]);
				title = args[0];
			}
			var boxy = new Boxy(Biz.Utils.fnToStr(fn), {
				title : title == null ? ' ' : title,
				closeText : 'x',
				modal : true,
				fixed : false,
				unloadOnHide : true
			});
			$('.boxy-wrapper .title-bar a').blur();
			var subTitleText = find(fn, '#biz-modals-title-tip');
			if (subTitleText && subTitleText.length > 0) {
				$('.boxy-wrapper .title-bar h2').append(
						'<span class="modals-auth-title-info" style="font-size:12px;">' + subTitleText + '</span>');
			}
			deferred.resolve(boxy);
		});
		return deferred;
	};

	Biz.Utils.renderBiz = function(model, scope, attName) {
		// 解析第一个参数名，防止js压缩后改名
		var argName = arguments.callee.toString();
		argName = $.trim(argName.substring(argName.indexOf('(') + 1, argName.indexOf(',')));
		var name = 'data-biz-t';
		if (attName) {
			name = attName;
		}
		scope.find('[' + name + ']').each(function(idx, element) {
			var ele = $(element);
			var exp = ele.attr(name);
			if (exp) {
				var attr = null;
				if (exp.indexOf('[') === 0) {
					var parts = exp.split(']');
					exp = parts[1];
					attr = parts[0].substr(1, parts[0].length - 1);
				}
				// 下面一行的model与方法参数的m不可名字一样
				try {
					var value = eval('var m = ' + argName + ';' + exp);
					if (attr == null) {
						ele.text(value);
					} else {
						ele.attr(attr, value);
					}
				} catch (e) {
					console.error(e + ', detail: argName=' + argName + ', exp=' + exp + '.');
				}
			}
		});
	}

	function init() {
		$(document).ready(function() {
			$('body')
			/* fold unfold of sidebar begin */
			.on('click.biz', '.biz-j-hasSub', function() {
				var me = $(this), el = me.next();
				if (el.is(':hidden')) {
					el.addClass('show').removeClass('hide');
					me.removeClass('fold').addClass('unfold');
				} else {
					el.addClass('hide').removeClass('show');
					me.addClass('fold').removeClass('unfold');
				}
			});
		});
	}

	init();
	Biz.Utils.debug("loaded... biz-utils");
	return Biz;
});