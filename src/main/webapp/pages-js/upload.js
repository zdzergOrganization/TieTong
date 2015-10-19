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
		getUploadTablesStatus : {
			url : Biz.Constant.ctx + 'rest/upload/getUploadTablesStatus',
			method : 'GET'
		},
	};

	function Model(upload) {
		Object.defineProperties(this, {
			"uploadTablesStatus" : {
				set : function(value) {
					this._uploadTablesStatus = value;
					upload.ee.emitEvent('uploadTablesStatus_loaded');
				},
				get : function() {
					return this._uploadTablesStatus;
				},
				enumerable : true,
				configurable : false,
			},
			
		});
		this.uploadTablesStatus = [];
		return this;
	}

	function Controller(upload) {
		
		Controller.prototype.getUploadTablesStatus = function(uploadMonth) {
			
			$.ajax({
				method: urls.getUploadTablesStatus.method,
				url: urls.getUploadTablesStatus.url,
				data: {uploadMonth : uploadMonth},
				dataType: 'json',
				success: function(data, status) {
					Biz.Utils.handlAjaxResult(data).done(function(json) {
						upload.model.uploadTablesStatus = json;
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
						upload.controller.getUploadTablesStatus();
					}).fail(function() {
					});
				}
			});
			
		};
		
	}

	function View(upload) {
		//查看所有职员信息
		View.prototype.uploadTablesStatus_loading = function() {
			var html = '<i class="fa fa-spinner fa-spin" style="font-size: 2em; line-height:2em;"></i>';
			$('#tables-rows').html('<td colspan="5" align="center">' + html + '</td>');
		};
		
		View.prototype.uploadTablesStatus_loaded = function() {
			var tbody = $("#tables-rows");
			tbody.html($(tmplTables.render({
				uploadTablesStatus : upload.model.uploadTablesStatus
			})));
		};
	}

	function upload() {
		var upload = this;

		// 1.初始化变量
		this.ee = new EventEmitter();
		this.model = new Model(upload);
		this.view = new View(upload);
		this.controller = new Controller(upload);
		
		upload.ee.addListeners('uploadTablesStatus_loaded',[ upload.view.uploadTablesStatus_loaded ]);
		
		// 初始化时间控件
		$('.datepicker').datepicker({
	        autoclose: true,
	        minViewMode: 1,
	        orientation: "top left"
	    });
		
		$('.datepicker')
		.datepicker()
		.on('changeDate', function(){
			var datepicker_date = $('.datepicker').datepicker('getDate');
			
		    var y = datepicker_date.getFullYear();
		    var m = datepicker_date.getMonth()+1;//获取当前月份的日期
		    if(m<10){m='0'+m}
		    var uploadMonth = y + '-' + m
		    upload.controller.getUploadTablesStatus(uploadMonth);
		});
		
		//动态模态框 取得需要上传的表名和时间
		$('#uploadBaseTablesModal').on('show.bs.modal', function (event) {
			  var a = $(event.relatedTarget) 
			  var uploadMonth =$($(a.parent('td')).parent('tr')).find('#idUploadMonth').text();
			  var tablesName =$($(a.parent('td')).parent('tr')).find('#idTablesName').text();
			  var tablesDesc =$($(a.parent('td')).parent('tr')).find('#idTablesDesc').text();
			  var modal = $(this);
			  modal.find('#myModalLabel').text('时间:' + uploadMonth + ', ' + tablesDesc + '表-上传');
			  modal.find('#modules_download').attr('href',Biz.Constant.ctx + 'download/'+tablesDesc+'.xlsx');

			  //modal.find('#modalIdTablesName').text(tablesName);
			  modal.find('#modalIdTablesName').attr('value',tablesName);
			  modal.find('#modalIduploadMonth').attr('value',uploadMonth);
			})
		
		//上传基础表
		$('#batch_add_commit').click(function(event) {
			$('#upload_form').submit();
			upload.view.uploadTablesStatus_loading();
		});
	
	}

	upload.prototype.init = function() {
		//查看页面url是否有传值
		var url = location.href;
		if(url.lastIndexOf("?")!=-1){
			var uploadMonth = url.substr(url.indexOf("uploadMonth=") + 12, 7);
			uploadMonth = uploadMonth.replace(/-/g,"/");
			uploadMonth = uploadMonth + '/01 00:00:00'
			var date1 = new Date(uploadMonth);
			//设置时间
			$('.datepicker').datepicker('setDate',date1);
		}
		else{
			//设置当前时间
			$('.datepicker').datepicker('setDate',new Date());
		}
		
		
		
		var datepicker_date = $('.datepicker').datepicker('getDate');
	    var y = datepicker_date.getFullYear();
	    var m = datepicker_date.getMonth()+1;//获取当前月份的日期
	    if(m<10){m='0'+m}
	    var uploadMonth = y + '-' + m
		
		//查询上传表状态
		this.controller.getUploadTablesStatus(uploadMonth);
		
		return this;
	};

	module.exports = upload;
});