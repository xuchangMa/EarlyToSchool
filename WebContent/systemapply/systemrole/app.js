/**
 * 角色信息
 */

/*定义数据结构*/
/**
 * public class SystemRole { //表ID public static String Id; //角色编码 public static
 * String RoleCode; //角色名称 public static String RoleName; //是否生效 public static
 * String DeleteFlag; }
 */
/*
 * 定义数据 1001 管理员 1002 出题者 1003 校方 1004 级主任 1005 班主任 1006 普通教师 1007 学生
 */
var tableName = "SystemRole";
/*
 * //表id//角色编码//角色名称//是否生效 共4项
 */
var Roledatastructure = new Array("Id", "RoleCode", "RoleName", "DeleteFlag");

// 定义角色信息数据
var SystemRoleData;
// 定义查询数据
var SystemRoleSelectData = [];
// 定义数据当前页
var SystemRoleIndexPage = 1;
// 页面加载方法
$(function() {
	// 获取数据
	getRoleData();
	// 调用查询方法
	GetRoleQuery();
});
// 页面加载方法
function getRoleData() {
	// 定义当前页面访问的接口地址
	var url = "../SystemRole/GetRole";	
	$.ajax({
		type : "post",
		url : url,
		data : null,
		async : false,
		success : function(data) {
			if (data != "") {
				// 把查询数据赋值给js变量
				SystemRoleData = eval('(' + data + ')');
			} else {
				SystemRoleData = null;
			}
		}
	});
}

function GetRoleQuery() {
	if (UpdateRoleQuery()) {
		// 每次查询重新绑定当前页
		SystemRoleIndexPage = 1;
		// 调用绑定数据的方法
		BandingRoleData(SystemRoleIndexPage);
	}
}

// 定义更新查询数据
function UpdateRoleQuery() {
	if (SystemRoleData == null) {
		return false;
	}
	SystemRoleSelectData = SystemRoleData;
	var sele = $("#Roleshowselected").html();
	var seleval = $("#RoleSeleVal").val();
	if (sele == "角色编码") {
		SystemRoleSelectData = [];
		for (var i = 0; i < SystemRoleData.length; i++) {
			if (SystemRoleData[i].RoleCode.indexOf(seleval) > -1) {
				SystemRoleSelectData.push(SystemRoleData[i]);
			}
		}
	} else if (sele == "角色名称") {
		SystemRoleSelectData = [];
		for (var i = 0; i < SystemRoleData.length; i++) {
			if (SystemRoleData[i].RoleName.indexOf(seleval) > -1) {
				SystemRoleSelectData.push(SystemRoleData[i]);
			}
		}
	} else {
		SystemRoleSelectData = [];
		for (var i = 0; i < SystemRoleData.length; i++) {
			if (SystemRoleData[i].RoleCode.indexOf(seleval) > -1
					|| SystemRoleData[i].RoleName.indexOf(seleval) > -1) {
				SystemRoleSelectData.push(SystemRoleData[i]);
			}
		}
	}
	return true;
}

// 角色页面的选择查询条件
function Roleselected() {
	var stu = document.getElementById("Roleselect").style.display;
	if (stu == "none") {
		document.getElementById("Roleselect").style.display = "block";
	} else {
		document.getElementById("Roleselect").style.display = "none";
	}
}
// 角色页面的选择条件值后返回
function Roleoption(name) {
	document.getElementById("Roleshowselected").innerHTML = name;
	document.getElementById("Roleselect").style.display = "none";
}
// 绑定角色数据
function BandingRoleData(IndexPage) {
	// 判断传进来的页码是否大于最后一页
	if (IndexPage > Math.ceil(SystemRoleSelectData.length / PageNumber)) {
		IndexPage = Math.ceil(SystemRoleSelectData.length / PageNumber);
	}
	// 赋值当前页码
	SystemRoleIndexPage = IndexPage;

	var data = "";
	for (var i = ((SystemRoleIndexPage - 1) * PageNumber); i < SystemRoleSelectData.length
			&& i < (SystemRoleIndexPage * PageNumber); i++) {
		// data.push(SystemRoleData[i]);
		data += "<tr class=\"gradeX\"><td>" + SystemRoleSelectData[i].RoleCode
				+ "</td>";
		data += "<td>" + SystemRoleSelectData[i].RoleName + "</td>";
		var img = "../assets/i/ico_no.png";
		if (SystemRoleSelectData[i].DeleteFlag == 0) {
			img = "../assets/i/ico_yes.png";
		}
		var strData = "[{Id:'" + SystemRoleSelectData[i].Id + "',RoleCode:'"
				+ SystemRoleSelectData[i].RoleCode + "',RoleName:'"
				+ SystemRoleSelectData[i].RoleName + "',DeleteFlag:'"
				+ SystemRoleSelectData[i].DeleteFlag + "'}]";
		data += "<td><img src = '" + img
				+ "' style=\"width: 15px; vertical-align: middle;\" /></td>";
		data += "<td><div class=\"tpl-table-black-operation\">"
				+ "<a href=\"javascript:RoleEditFunc(" + strData
				+ ");\"> <i class=\"am-icon-pencil\"></i> 编辑"
				+ "</a> <a href=\"javascript:RoleDeleteFunc(" + strData
				+ ");\" class=\"tpl-table-black-operation-del\">"
				+ "	<i class=\"am-icon-trash\"></i> 删除</a></div></td></tr>";
	}
	// alert(JSON.stringify(data));
	$("#SystemRoleData").html(data);
	// 绑定分页
	RoleBandingPages();
}

// 绑定角色页面的分页选项
function RoleBandingPages() {
	var PagesData = "<li><a href=\"javascript:BandingRoleData(" + 1
			+ ") \">«</a></li>";
	var Pages2Data = "";
	var Pages = Math.ceil(SystemRoleData.length / PageNumber);
	if (Pages <= 5) {
		for (var i = 0; i < Pages; i++) {
			var ind = i + 1;
			if (SystemRoleIndexPage == ind) {
				PagesData += "<li class=\"am-active\"><a href=\"javascript:BandingRoleData("
						+ ind + ") \">" + ind + "</a></li>";
			} else {
				PagesData += "<li><a href=\"javascript:BandingRoleData(" + ind
						+ ") \">" + ind + "</a></li>";
			}
		}
	} else {
		for (var i = 0; i < Pages; i++) {
			var ind = i + 1;
			if (ind > 2 & ind < Pages - 1) {
				if (ind == 3) {
					PagesData += "<li><a href=\"javascript:OpenPages('RolePagesDialog') \"><span>...<span></a></li>";
				}
				if (SystemRoleIndexPage == ind) {
					Pages2Data += "<li class=\"am-active\"><a href=\"javascript:BandingRoleData("
							+ ind + ") \">" + ind + "</a></li>";
				} else {
					Pages2Data += "<li><a href=\"javascript:BandingRoleData("
							+ ind + ") \">" + ind + "</a></li>";
				}
			} else {
				if (SystemRoleIndexPage == ind) {
					PagesData += "<li class=\"am-active\"><a href=\"javascript:BandingRoleData("
							+ ind + ") \">" + ind + "</a></li>";
				} else {
					PagesData += "<li><a href=\"javascript:BandingRoleData("
							+ ind + ") \">" + ind + "</a></li>";
				}
			}
		}
	}
	PagesData += "<li><a href=\"javascript:BandingRoleData(" + Pages
			+ ") \">»</a></li>";
	$("#RolePages").html(PagesData);
	$("#RolePages2").html(Pages2Data);
	$("#RolePagesDialog").css('display', 'none');
	// RolePages
}

// 打开编辑页面
function RoleEditFunc(data) {
	$("#RoleShowPage").hide();
	$("#RoleEditPage").show();
	if (data == null) {
		SystemRoleFromState = "I";
		$("#SystemRoleCode").val("");
		$("#SystemRoleName").val("");
		$("#SystemRoleDeleteFlag").prop("checked", "checked");

	} else {
		SystemRoleFromState = data[0].Id;
		$("#SystemRoleCode").val(data[0].RoleCode);
		$("#SystemRoleName").val(data[0].RoleName);
		if (data[0].DeleteFlag == 0) {
			$("#SystemRoleDeleteFlag").prop("checked", "checked");
		} else {
			$("#SystemRoleDeleteFlag").prop("checked", "");
		}
	}
}
// 关闭编辑页面
function RoleEditClose() {
	$("#RoleShowPage").show();
	$("#RoleEditPage").hide();
}
var SystemRoleFromState = "I";
// 保存数据
function RoleSaveData() {
	// 定义当前页面访问的接口地址
	var url = "../SystemRole/UpdateRole";
	var da;
	var df = "0";
	if (!$("#SystemRoleDeleteFlag").is(':checked')) {
		df = "1";
	}
	if (SystemRoleFromState == "I") {
		url = "../SystemRole/InsertRole";
		da = {
			"RoleCode" : $("#SystemRoleCode").val(),
			"RoleName" : $("#SystemRoleName").val(),
			"DeleteFlag" : df
		};
	} else {
		da = {
			"Id" : SystemRoleFromState,
			"RoleCode" : $("#SystemRoleCode").val(),
			"RoleName" : $("#SystemRoleName").val(),
			"DeleteFlag" : df
		};
	}
	// alert(SystemRoleFromState);
	// 获取接口数据
	$.post(url, da, function(data, state) {
		if (state == "success") {
			if (data != "No") {
				// 把查询数据赋值给js变量
				SystemRoleData = eval('(' + data + ')');
				UpdateRoleQuery();
				// 调用绑定数据的方法
				BandingRoleData(SystemRoleIndexPage);
			}
		} else {
			alert("保存失败！")
		}

	});
	RoleEditClose();
}

function RoleOKMessage(da) {
	// 定义当前页面访问的接口地址
	var url = "../SystemRole/DeleteRole";
	da = {
		"Id" : da[0].Id
	};
	// 获取接口数据
	$.post(url, da, function(data, state) {
		if (state == "success") {
			if (data != "No") {
				// 把查询数据赋值给js变量
				SystemRoleData = eval('(' + data + ')');
				UpdateRoleQuery();
				// 调用绑定数据的方法
				BandingRoleData(SystemRoleIndexPage);
			}
		} else {
			alert("删除失败！")
		}

	});
	RoleNOMessage();
}

function RoleNOMessage() {
	$("#SysetmRoleMessage").hide();
}

function RoleDeleteFunc(da) {
	// var top = ($("#RoleShowPage").height() -
	// $("#SysetmRoleMessage").height())/2;
	var top = 125;
	var left = ($("#RoleShowPage").width() - $("#SysetmRoleMessage").width()) / 2;
	$("#SysetmRoleMessage").css({
		'display' : 'block',
		'top' : top,
		left : left
	}).show();
	$("#RoleOKDelete").bind("click", function() {
		RoleOKMessage(da);
	})
}
