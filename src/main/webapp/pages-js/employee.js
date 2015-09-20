define(function(require, exports, module) {
	// 申明需要用到的第三方类库
	var $ = require('jquery');
	var Biz = require('biz-utils');
	var EventEmitter = require('EventEmitter');
	var juicer = require('juicer');
	// 申明需要使用的模板
	var auditsTables = juicer("#audit-tables");
	
	// 申明需要访问到的URL
	var urls = {
		batchAddCommit : {
			url : Biz.Constant.ctx + 'rest/employee/batchAddCommit',
			method : 'GET'
		},
	};

	function Model(employee) {
		Object.defineProperties(this, {
			"audits" : {
				set : function(value) {
					this._audits = value;
					employee.ee.emitEvent('audits_loaded');
				},
				get : function() {
					return this._audits;
				},
				enumerable : true,
				configurable : false,
			},
			
		});
		this.audits = [];
		return this;
	}

	function Controller(employee) {
		
		Controller.prototype.batchAddCommit = function(filePath,monthEnd) {
			
			$.ajax({
				method: urls.batchAddCommit.method,
				url: urls.batchAddCommit.url,
				data: {
					filePath: filePath,
					monthEnd: monthEnd
				},
				dataType: 'json',
				success: function(data, status) {
					Biz.Utils.handlAjaxResult(data).done(function(json) {
						
					}).fail(function() {
						
					});
				}
			});
			
		}
		
	}

	function View(employee) {
		//点击bucket后查看内部文件
		View.prototype.objectsLoading = function() {
			var html = '<i class="fa fa-spinner fa-spin" style="font-size: 2em; line-height:2em;"></i>';
			$('#otherBucket-tables-rows').html('<td colspan="5" align="center">' + html + '</td>');
		}
	}

	function employee() {
		var employee = this;

		// 1.初始化变量
		this.ee = new EventEmitter();
		this.model = new Model(employee);
		this.view = new View(employee);
		this.controller = new Controller(employee);
		
		var filePath = $('#file-path').val();
		var monthEnd = $('#month-end').val();
		$('#batch_add_commit').on('click', function(event) {
			employee.controller.batchAddCommit(filePath,monthEnd);
		});
	}

	employee.prototype.init = function() {
		return this;
	};

	module.exports = employee;
});