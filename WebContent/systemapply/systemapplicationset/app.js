/**
 * 系统应用集
 */

/**
 * public class SystemApplicationSet {
	//表ID
	public static String Id;	
	//应用编码
	public static String ApplicationCode;
	//应用名称
	public static String ApplicationName;		
	//应用图标
	public static String ApplicationIcon;
	//应用级别
	public static String ApplicationGrade;
	//是否挂载
	public static String MountFlag;
	//应用URL
	public static String ApplicationURL;
	//显示位置(1：在网站内打开标签页，2：覆盖当前网站，3：跳转出新页面)
	public static String ApplicationPosition;
	//是否生效
	public static String DeleteFlag;
}
 */

/*应用内容
 * 默认：首页
 * 1.我的课程
 * 2.课程扩展
 * 3.资料中心
 * 4.考试安排
 * 5.学校管理
 * 	5.1	学校信息
 * 	5.2	班级信息
 * 	5.3	教师信息
 * 	5.4	学生信息
 * 6.放松时刻
 * 7.系统设置
 *	7.1	应用中心
 *	7.2	角色信息
 *	7.3	应用权限
 *	7.4	用户信息	
 * 8.论坛
 * 
 *个人中心默认点击图像
 * */

var tableName = "SystemApplicationSet";


//定义角色信息数据
var SystemApplicationSetData;
//定义查询数据
var SystemApplicationSetSelectData = [];
//定义数据当前页
var SystemApplicationSetIndexPage = 1;
//页面加载方法
$(function() {
	// 获取数据
    getSystemApplicationSetData();
	// 调用查询方法
	GetSystemApplicationSetQuery();
});
//页面加载方法
function getSystemApplicationSetData() {
	// 定义当前页面访问的接口地址
    var url = "../SystemApplicationSet/GetSystemApplicationSet";
	$.ajax({
		type : "post",
		url : url,
		data : null,
		async : false,
		success : function(data) {
			if (data != "") {
				// 把查询数据赋值给js变量
				SystemApplicationSetData = eval('(' + data + ')');
			} else {
				SystemApplicationSetData = null;
			}
		}
	});
}

function GetSystemApplicationSetQuery() {
    if (UpdateSystemApplicationSetQuery()) {
		// 每次查询重新绑定当前页
		SystemApplicationSetIndexPage = 1;
		// 调用绑定数据的方法
		BandingSystemApplicationSetData(SystemApplicationSetIndexPage);
	}
}

//定义更新查询数据
function UpdateSystemApplicationSetQuery() {
	if (SystemApplicationSetData == null) {
		return false;
	}
	SystemApplicationSetSelectData = SystemApplicationSetData;
	var sele = $("#SystemApplicationSetshowselected").html();
	var seleval = $("#SystemApplicationSetSeleVal").val();
	if (sele == "应用编码") {
		SystemApplicationSetSelectData = [];
		for (var i = 0; i < SystemApplicationSetData.length; i++) {
			if (SystemApplicationSetData[i].ApplicationCode.indexOf(seleval) > -1) {
				SystemApplicationSetSelectData.push(SystemApplicationSetData[i]);
			}
		}
	} else if (sele == "应用名称") {
		SystemApplicationSetSelectData = [];
		for (var i = 0; i < SystemApplicationSetData.length; i++) {
			if (SystemApplicationSetData[i].ApplicationName.indexOf(seleval) > -1) {
				SystemApplicationSetSelectData.push(SystemApplicationSetData[i]);
			}
		}
	} else if (sele == "应用级别") {
		SystemApplicationSetSelectData = [];
		for (var i = 0; i < SystemApplicationSetData.length; i++) {
			if (SystemApplicationSetData[i].ApplicationGrade.indexOf(seleval) > -1) {
				SystemApplicationSetSelectData.push(SystemApplicationSetData[i]);
			}
		}
	}else {
		SystemApplicationSetSelectData = [];
		for (var i = 0; i < SystemApplicationSetData.length; i++) {
			if (SystemApplicationSetData[i].ApplicationCode.indexOf(seleval) > -1
				|| SystemApplicationSetData[i].ApplicationName.indexOf(seleval) > -1
				|| SystemApplicationSetData[i].ApplicationGrade.indexOf(seleval) > -1) {
				SystemApplicationSetSelectData.push(SystemApplicationSetData[i]);
			}
		}
	}
	return true;
}

//角色页面的选择查询条件
function SystemApplicationSetselected() {
	var stu = document.getElementById("SystemApplicationSetselect").style.display;
	if (stu == "none") {
		document.getElementById("SystemApplicationSetselect").style.display = "block";
	} else {
		document.getElementById("SystemApplicationSetselect").style.display = "none";
	}
}
//角色页面的选择条件值后返回
function SystemApplicationSetoption(name) {
    document.getElementById("SystemApplicationSetshowselected").innerHTML = name;
    document.getElementById("SystemApplicationSetselect").style.display = "none";
}
//绑定角色数据
function BandingSystemApplicationSetData(IndexPage) {
	// 判断传进来的页码是否大于最后一页
	if (IndexPage > Math.ceil(SystemApplicationSetSelectData.length / PageNumber)) {
		IndexPage = Math.ceil(SystemApplicationSetSelectData.length / PageNumber);
	}
	// 赋值当前页码
	SystemApplicationSetIndexPage = IndexPage;

	var data = "";
	for (var i = ((SystemApplicationSetIndexPage - 1) * PageNumber); i < SystemApplicationSetSelectData.length
			&& i < (SystemApplicationSetIndexPage * PageNumber); i++) {
		// data.push(SystemApplicationSetData[i]);
		data += "<tr class=\"gradeX\"><td>" + SystemApplicationSetSelectData[i].ApplicationCode
				+ "</td>";
		data += "<td>" + SystemApplicationSetSelectData[i].ApplicationName + "</td>";
		data += "<td><i class=\"" + SystemApplicationSetSelectData[i].ApplicationIcon + " sidebar-nav-link-logo\"></i></td>";
		data += "<td>" + SystemApplicationSetSelectData[i].ApplicationGrade + "</td>";
		var Mimg = "../assets/i/ico_no.png";
		if (SystemApplicationSetSelectData[i].MountFlag == 0) {
			Mimg = "../assets/i/ico_yes.png";
		}
		data += "<td><img src = '" + Mimg
		+ "' style=\"width: 15px; vertical-align: middle;\" /></td>";
		data += "<td>" + SystemApplicationSetSelectData[i].ApplicationURL + "</td>";
		data += "<td>" + SystemApplicationSetSelectData[i].ApplicationPosition + "</td>";
		var img = "../assets/i/ico_no.png";
		if (SystemApplicationSetSelectData[i].DeleteFlag == 0) {
			img = "../assets/i/ico_yes.png";
		}
		
		var strData = "[{Id:'" + SystemApplicationSetSelectData[i].Id + "',ApplicationCode:'"
				+ SystemApplicationSetSelectData[i].ApplicationCode + "',ApplicationName:'"
				+ SystemApplicationSetSelectData[i].ApplicationName + "',ApplicationIcon:'"
				+ SystemApplicationSetSelectData[i].ApplicationIcon + "',ApplicationGrade:'"
				+ SystemApplicationSetSelectData[i].ApplicationGrade + "',MountFlag:'"
				+ SystemApplicationSetSelectData[i].MountFlag + "',ApplicationURL:'"
				+ SystemApplicationSetSelectData[i].ApplicationURL + "',ApplicationPosition:'"
				+ SystemApplicationSetSelectData[i].ApplicationPosition + "',DeleteFlag:'"
				+ SystemApplicationSetSelectData[i].DeleteFlag + "'}]";
		data += "<td><img src = '" + img
				+ "' style=\"width: 15px; vertical-align: middle;\" /></td>";
		data += "<td><div class=\"tpl-table-black-operation\">"
				+ "<a href=\"javascript:SystemApplicationSetEditFunc(" + strData
				+ ");\"> <i class=\"am-icon-pencil\"></i> 编辑"
				+ "</a> <a href=\"javascript:SystemApplicationSetDeleteFunc(" + strData
				+ ");\" class=\"tpl-table-black-operation-del\">"
				+ "	<i class=\"am-icon-trash\"></i> 删除</a></div></td></tr>";
	}
	// alert(JSON.stringify(data));
	$("#SystemApplicationSetData").html(data);
	// 绑定分页
	SystemApplicationSetBandingPages();
}

//绑定角色页面的分页选项
function SystemApplicationSetBandingPages() {
    var PagesData = "<li><a href=\"javascript:BandingSystemApplicationSetData(" + 1
			+ ") \">«</a></li>";
	var Pages2Data = "";
	var Pages = Math.ceil(SystemApplicationSetData.length / PageNumber);
	if (Pages <= 5) {
		for (var i = 0; i < Pages; i++) {
			var ind = i + 1;
			if (SystemApplicationSetIndexPage == ind) {
			    PagesData += "<li class=\"am-active\"><a href=\"javascript:BandingSystemApplicationSetData("
						+ ind + ") \">" + ind + "</a></li>";
			} else {
			    PagesData += "<li><a href=\"javascript:BandingSystemApplicationSetData(" + ind
						+ ") \">" + ind + "</a></li>";
			}
		}
	} else {
		for (var i = 0; i < Pages; i++) {
			var ind = i + 1;
			if (ind > 2 & ind < Pages - 1) {
				if (ind == 3) {
				    PagesData += "<li><a href=\"javascript:OpenPages('SystemApplicationSetPagesDialog') \"><span>...<span></a></li>";
				}
				if (SystemApplicationSetIndexPage == ind) {
				    Pages2Data += "<li class=\"am-active\"><a href=\"javascript:BandingSystemApplicationSetData("
							+ ind + ") \">" + ind + "</a></li>";
				} else {
				    Pages2Data += "<li><a href=\"javascript:BandingSystemApplicationSetData("
							+ ind + ") \">" + ind + "</a></li>";
				}
			} else {
				if (SystemApplicationSetIndexPage == ind) {
				    PagesData += "<li class=\"am-active\"><a href=\"javascript:BandingSystemApplicationSetData("
							+ ind + ") \">" + ind + "</a></li>";
				} else {
				    PagesData += "<li><a href=\"javascript:BandingSystemApplicationSetData("
							+ ind + ") \">" + ind + "</a></li>";
				}
			}
		}
	}
	PagesData += "<li><a href=\"javascript:BandingSystemApplicationSetData(" + Pages
			+ ") \">»</a></li>";
	$("#SystemApplicationSetPages").html(PagesData);
	$("#SystemApplicationSetPages2").html(Pages2Data);
	$("#SystemApplicationSetPagesDialog").css('display', 'none');
	// RolePages
}

//打开编辑页面
function SystemApplicationSetEditFunc(data) {
    $("#SystemApplicationSetShowPage").hide();
    $("#SystemApplicationSetEditPage").show();
	if (data == null) {
		SystemApplicationSetFromState = "I";
		$("#SystemApplicationCode").val("");
		$("#SystemApplicationName").val("");
		$("#SystemApplicationIcon").val("");
		$("#SystemApplicationGrade").val("");
		$("#SystemMountFlag").prop("checked", "checked");	
		$("#SystemApplicationURL").val("");
		$("#SystemApplicationPosition").val("");
		$("#SystemRoleDeleteFlag").prop("checked", "checked");

	} else {
		SystemApplicationSetFromState = data[0].Id;
		$("#SystemApplicationCode").val(data[0].ApplicationCode);
		$("#SystemApplicationName").val(data[0].ApplicationName);
		$("#SystemApplicationIcon").val(data[0].ApplicationIcon);
		$("#SystemApplicationGrade").val(data[0].ApplicationGrade);
		if (data[0].MountFlag == 0) {
			$("#SystemMountFlag").prop("checked", "checked");
		} else {
			$("#SystemMountFlag").prop("checked", "");
		}
		$("#SystemApplicationURL").val(data[0].ApplicationURL);
		$("#SystemApplicationPosition").val(data[0].ApplicationPosition);
		if (data[0].DeleteFlag == 0) {
			$("#SystemRoleDeleteFlag").prop("checked", "checked");
		} else {
			$("#SystemRoleDeleteFlag").prop("checked", "");
		}
	}
}
//关闭编辑页面
function SystemApplicationSetEditClose() {
    $("#SystemApplicationSetShowPage").show();
    $("#SystemApplicationSetEditPage").hide();
}
var SystemApplicationSetFromState = "I";
//保存数据
function SystemApplicationSetSaveData() {
	// 定义当前页面访问的接口地址
    var url = "../SystemApplicationSet/UpdateSystemApplicationSet";
	var da;
	var df = "0";
	if (!$("#SystemRoleDeleteFlag").is(':checked')) {
		df = "1";
	}
	var Mdf = "0";
	if (!$("#SystemMountFlag").is(':checked')) {
		Mdf = "1";
	}
	if (SystemApplicationSetFromState == "I") {
	    url = "../SystemApplicationSet/InsertSystemApplicationSet";
		da = {
			"ApplicationCode" : $("#SystemApplicationCode").val(),
			"ApplicationName" : $("#SystemApplicationName").val(),
			"ApplicationIcon" : $("#SystemApplicationIcon").val(),
			"ApplicationGrade" : $("#SystemApplicationGrade").val(),
			"MountFlag" : Mdf,
			"ApplicationURL" : $("#SystemApplicationURL").val(),
			"ApplicationPosition" : $("#SystemApplicationPosition").val(),
			"DeleteFlag" : df
		};
	} else {
		da = {
			"Id" : SystemApplicationSetFromState,
			"ApplicationCode" : $("#SystemApplicationCode").val(),
			"ApplicationName" : $("#SystemApplicationName").val(),
			"ApplicationIcon" : $("#SystemApplicationIcon").val(),
			"ApplicationGrade" : $("#SystemApplicationGrade").val(),
			"MountFlag" : Mdf,
			"ApplicationURL" : $("#SystemApplicationURL").val(),
			"ApplicationPosition" : $("#SystemApplicationPosition").val(),
			"DeleteFlag" : df
		};
	}
	// alert(SystemApplicationSetFromState);
	// 获取接口数据
	$.post(url, da, function(data, state) {
		if (state == "success") {
			if (data != "No") {
				// 把查询数据赋值给js变量
				SystemApplicationSetData = eval('(' + data + ')');
				UpdateSystemApplicationSetQuery();
				// 调用绑定数据的方法
				BandingSystemApplicationSetData(SystemApplicationSetIndexPage);
			}
		} else {
			alert("保存失败！")
		}

	});
	SystemApplicationSetEditClose();
}

function SystemApplicationSetOKMessage(da) {
	// 定义当前页面访问的接口地址
    var url = "../SystemApplicationSet/DeleteSystemApplicationSet";
	da = {
		"Id" : da[0].Id
	};
	// 获取接口数据
	$.post(url, da, function(data, state) {
		if (state == "success") {
			if (data != "No") {
				// 把查询数据赋值给js变量
				SystemApplicationSetData = eval('(' + data + ')');
				UpdateSystemApplicationSetQuery();
				// 调用绑定数据的方法
				BandingSystemApplicationSetData(SystemApplicationSetIndexPage);
			}
		} else {
			alert("删除失败！")
		}

	});
	SystemApplicationSetNOMessage();
}

function SystemApplicationSetNOMessage() {
    $("#SystemApplicationSetMessage").hide();
}

function SystemApplicationSetDeleteFunc(da) {
	// var top = ($("#RoleShowPage").height() -
	// $("#SysetmRoleMessage").height())/2;
	var top = 125;
	var left = ($("#SystemApplicationSetShowPage").width() - $("#SystemApplicationSetMessage").width()) / 2;
    $("#SystemApplicationSetMessage").css({
		'display' : 'block',
		'top' : top,
		left : left
	}).show();
    $("#SystemApplicationSetOKDelete").bind("click", function () {
        SystemApplicationSetOKMessage(da);
	})
}
