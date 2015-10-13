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
			url : Biz.Constant.ctx + 'kpi/getTC',
			method : 'GET'
		},
	};

	function Model(employee) {
		Object.defineProperties(this, {
			"allEmployeeInfo" : {
				set : function(value) {
					this._allEmployeeInfo = value;
					employee.ee.emitEvent('allEmployeeInfo_loaded');
				},
				get : function() {
					return this._allEmployeeInfo;
				},
				enumerable : true,
				configurable : false,
			},
		});
		this.allEmployeeInfo = [];
		return this;
	}

	function Controller(employee) {
		
		Controller.prototype.getAllEmployeeInfoType = function(KPIMonth) {
			
			$.ajax({
				method: urls.getAllEmployeeInfo.method,
				url: urls.getAllEmployeeInfo.url + '?KPIMonth=' + KPIMonth,
				data: {},
				dataType: 'json',
				success: function(data, status) {
					Biz.Utils.handlAjaxResult(data).done(function(json) {
						employee.model.allEmployeeInfo = json;
					}).fail(function() {
						
					});
				}
			});
			
		};
		
	}

	function View(employee) {
		
		View.prototype.allEmployeeInfo_loaded = function() {
			var tbody = $("#tables-rows");
			tbody.html($(tmplTables.render({
				kpi_tcs : employee.model.allEmployeeInfo
			})));

	        $('#dataTables-example').dataTable({
	            "sPaginationType" : "full_numbers",
	            "oLanguage" : {
	                "sLengthMenu": "每页显示 _MENU_ 条记录",
	                "sZeroRecords": "抱歉， 没有找到",
	                "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
	                "sInfoEmpty": "没有数据",
	                "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
	                "sZeroRecords": "没有检索到数据",
	                 "sSearch": "查找:",
	                "oPaginate": {
	                "sFirst": "首页",
	                "sPrevious": "前一页",
	                "sNext": "后一页",
	                "sLast": "尾页"
	                }
	            },
	            "aLengthMenu": [[10, 25, 50, 100, -1], [10, 30, 50, 100, "所有"]],  
	        });
		};
	}

	function employee() {
		var employee = this;

		// 1.初始化变量
		this.ee = new EventEmitter();
		this.model = new Model(employee);
		this.view = new View(employee);
		this.controller = new Controller(employee);
		
		employee.ee.addListeners('allEmployeeInfo_loaded',[ employee.view.allEmployeeInfo_loaded ]);
		
		//查看页面url是否有传值
		var url = location.href;
		var KPIMonth;
		if(url.lastIndexOf("?")!=-1){
			KPIMonth = url.substr(url.indexOf("KPIMonth=") + 9, 7);
		}
		else{
			KPIMonth = url.substr(url.indexOf("KPIMonth=") + 9, 7);
		}
		$('#kpi_tc_btn').click(function(event) {
			//$('#batch_add_form').submit();
			window.location= 'kpi/kpi_tc?KPIMonth=' + KPIMonth
				//'kpi_dx.html?KPIMonth=' + KPIMonth
		});
	}

	employee.prototype.init = function() {
		//查看页面url是否有传值
		var url = location.href;
		var KPIMonth;
		if(url.lastIndexOf("?")!=-1){
			KPIMonth = url.substr(url.indexOf("KPIMonth=") + 9, 7);
		}
		else{
			KPIMonth = url.substr(url.indexOf("KPIMonth=") + 9, 7);
		}
		
		this.controller.getAllEmployeeInfoType(KPIMonth);
		return this;
	};

	module.exports = employee;
});