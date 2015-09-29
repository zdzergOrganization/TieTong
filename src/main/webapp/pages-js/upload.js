define(function(require, exports, module) {
	// 申明需要用到的第三方类库
	var $ = require('jquery');
	var Biz = require('biz-utils');
	var EventEmitter = require('EventEmitter');
	var juicer = require('juicer');
	// 申明需要使用的模板
	var tmplTables = juicer("#tmpl-tables");
	
	// 申明需要访问到的URL
	var urls = {
		getAllEmployeeInfo : {
			url : Biz.Constant.ctx + 'rest/upload/getAllEmployeeInfo',
			method : 'GET'
		},
		uploadDel : {
			url : Biz.Constant.ctx + 'rest/upload/uploadDel',
			method : 'GET'
		},
	};

	function Model(upload) {
		Object.defineProperties(this, {
			"allEmployeeInfo" : {
				set : function(value) {
					this._allEmployeeInfo = value;
					upload.ee.emitEvent('allEmployeeInfo_loaded');
				},
				get : function() {
					return this._allEmployeeInfo;
				},
				enumerable : true,
				configurable : false,
			},
			"uploadDelId" : {
				set : function(value) {
					this._uploadDelId = value;
				},
				get : function() {
					return this._uploadDelId;
				},
				enumerable : true,
				configurable : false,
			},
			
		});
		this.allEmployeeInfo = [];
		this.uploadDelId = '';
		return this;
	}

	function Controller(upload) {
		
		Controller.prototype.getAllEmployeeInfo = function() {
			
			$.ajax({
				method: urls.getAllEmployeeInfo.method,
				url: urls.getAllEmployeeInfo.url,
				data: {},
				dataType: 'json',
				success: function(data, status) {
					Biz.Utils.handlAjaxResult(data).done(function(json) {
						upload.model.allEmployeeInfo = json;
					}).fail(function() {
						
					});
				}
			});
			
		};
		
		Controller.prototype.uploadDel = function(uploadDelId) {
			
			$.ajax({
				method: urls.uploadDel.method,
				url: urls.uploadDel.url,
				data: {uploadDelId : uploadDelId},
				dataType: 'json',
				success: function(data, status) {
					Biz.Utils.handlAjaxResult(data).done(function(json) {
						upload.controller.getAllEmployeeInfo();
					}).fail(function() {
					});
				}
			});
			
		};
		
	}

	function View(upload) {
		//查看所有职员信息
		View.prototype.allEmployeeInfo_loading = function() {
			var html = '<i class="fa fa-spinner fa-spin" style="font-size: 2em; line-height:2em;"></i>';
			$('#tables-rows').html('<td colspan="9" align="center">' + html + '</td>');
		};
		
		View.prototype.allEmployeeInfo_loaded = function() {
			var tbody = $("#tables-rows");
			tbody.html($(tmplTables.render({
				uploads : upload.model.allEmployeeInfo
			})));

	        $('#dataTables-example').dataTable();
		};
	}

	function upload() {
		var upload = this;

		// 1.初始化变量
		this.ee = new EventEmitter();
		this.model = new Model(upload);
		this.view = new View(upload);
		this.controller = new Controller(upload);
	}

	upload.prototype.init = function() {
		// 初始化时间控件
		$('.datepicker').datepicker({
	        autoclose: true,
	        minViewMode: 1,
	        orientation: "top left"
	    })
		return this;
	};

	module.exports = upload;
});