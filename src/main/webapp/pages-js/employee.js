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
			url : Biz.Constant.ctx + 'rest/employee/getAllEmployeeInfo',
			method : 'GET'
		},
		employeeDel : {
			url : Biz.Constant.ctx + 'rest/employee/employeeDel',
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
			"employeeDelId" : {
				set : function(value) {
					this._employeeDelId = value;
				},
				get : function() {
					return this._employeeDelId;
				},
				enumerable : true,
				configurable : false,
			},
			
		});
		this.allEmployeeInfo = [];
		this.employeeDelId = '';
		return this;
	}

	function Controller(employee) {
		
		Controller.prototype.getAllEmployeeInfo = function() {
			
			$.ajax({
				method: urls.getAllEmployeeInfo.method,
				url: urls.getAllEmployeeInfo.url,
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
		
		Controller.prototype.employeeDel = function(employeeDelId) {
			
			/*$.ajax({
				method: urls.employeeDel.method,
				url: urls.employeeDel.url,
				data: {employeeDelId : employeeDelId},
                async : false,
				dataType: 'json',
				success: function(data, status) {
					Biz.Utils.handlAjaxResult(data).done(function(json) {
						employee.controller.getAllEmployeeInfo();
					}).fail(function() {
					});
				}
			});
			
			window.location.replace("http://www.baidu.com/");*/

			$.ajax({
				method: urls.employeeDel.method,
				url: urls.employeeDel.url,
				data: {employeeDelId : employeeDelId},
                async : false,
				dataType: 'json',
				success: function(data, status) {
					location.href="employee.html"
				}
			});
			
		};
		
	}

	function View(employee) {
		//查看所有职员信息
		/*View.prototype.allEmployeeInfo_loading = function() {
			var html = '<i class="fa fa-spinner fa-spin" style="font-size: 2em; line-height:2em;"></i>';
			$('#tables-rows').html('<td colspan="9" align="center">' + html + '</td>');
		};*/
		
		View.prototype.allEmployeeInfo_loaded = function() {
			var tbody = $("#tables-rows");
			tbody.html($(tmplTables.render({
				employees : employee.model.allEmployeeInfo
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
	            "aLengthMenu": [[-1, 10, 25, 50, 100], ["所有", 10, 30, 50, 100]],  
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
				
		$('#batch_add_commit').click(function(event) {
			$('#batch_add_form').submit();
		});
		
		$('#single_add_commit').click(function(event) {
			$('#single_add_form').submit();
		});
		
		$('#tables-rows').delegate('.employeeDel','click',function(){
			employee.model.employeeDelId=$($($(this).parent('td')).parent('tr')).find('#employee_id').text();
		})

		$('#employeeDelModal').delegate('#employeeDelBtn','click',function(){
			employee.controller.employeeDel(employee.model.employeeDelId);
		});
	}

	employee.prototype.init = function() {
		this.controller.getAllEmployeeInfo();
		return this;
	};

	module.exports = employee;
});