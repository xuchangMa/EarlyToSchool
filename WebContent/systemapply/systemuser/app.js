/**
 * 用户信息
 */

/*定义数据结构*/
/* 
 * public class SystemUser{
	//表id
	private String Id;	
    //学校名称
	private String SchoolName;
	//学校编码
	private String SchoolCode;	
    //班级名称
	private String ClassName;
	//班级编码
	private String ClassCode;	
	//用户角色编码
	private String RoleCode;	
	//用户角色名称
	private String RoleName;	
	//用户编码
	private String UserCode;		
	//用户名称
	private String UserName;
	//用户图标
	private String UserIcon;
	//用户身份证号
	private String UserIDNumber;
	//用户性别
	private String UserSex;
	//用户出生日期
	private String UserDateOfBirth;
	//用户年龄
	private String UserAge;
	//用户生日
	private String UserBirthday;
	//用户归属地
	private String UserBelonging;
	//用户密码
	private String UserPassword;
	//是否生效
	private boolean DeleteFlag;	
}

 * */
var tableName = "SystemUser";
/*
 * //表id//学校名称//学校编码//班级名称//班级编码//用户角色编码//用户编码//用户密码//用户名称//用户身份证号//用户性别
 * //用户年龄//用户生日//用户归属地//是否生效
 * 共13项
 * */

//定义角色信息数据
var SystemUserData;
//定义查询数据
var SystemUserSelectData = [];
//定义数据当前页
var SystemUserIndexPage = 1;
//页面加载方法
$(function() {
	// 获取数据
	getSystemUserData();
	// 调用查询方法
	GetSystemUserQuery();
});
//页面加载方法
function getSystemUserData() {
	// 定义当前页面访问的接口地址
	var url = "../SystemUser/GetSystemUser";	
	$.ajax({
		type : "post",
		url : url,
		data : null,
		async : false,
		success : function(data) {
			if (data != "") {
				// 把查询数据赋值给js变量
				var datalist = eval('(' + data + ')');
				// 把查询数据赋值给js变量
				SystemRoleData = datalist[1].SystemRoleConfiguration;
				BandingRoleSelectList(datalist[0].SystemRole);
			} else {
				SystemRoleData = null;
			}
		}
	});
}
//绑定选项角色
function BandingSystemUserRoleSelectList(list) {
	var sele = "";
	for (var i = 0; i < list.length; i++) {
		sele += "<li data-index=\"" + i + "\" data-group=\"0\""
				+ "onclick=\"SystemUserRoleoption('"
				+ list[i].RoleName + "')\"><span"
				+ "class=\"am-selected-text\">" + list[i].RoleName
				+ "</span> <i class=\"am-icon-check\"></i>" + "</li>"
	}
	$("#SystemUserRoleshowselected").html(list[0].RoleName);
	$("#SystemUserRoleselectUl").html(sele);
}

function GetSystemUserQuery() {
	if (UpdateSystemUserQuery()) {
		// 每次查询重新绑定当前页
		SystemUserIndexPage = 1;
		// 调用绑定数据的方法
		BandingRoleData(SystemUserIndexPage);
	}
}

//定义更新查询数据
function UpdateSystemUserQuery() {
	if (SystemUserData == null) {
		return false;
	}
	SystemUserSelectData = SystemUserData;
	var sele = $("#SystemUsershowselected").html();
	var seleval = $("#SystemUserSeleVal").val();
	if (sele == "角色编码") {
		SystemUserSelectData = [];
		for (var i = 0; i < SystemUserData.length; i++) {
			if (SystemUserData[i].RoleCode.indexOf(seleval) > -1) {
				SystemUserSelectData.push(SystemRoleData[i]);
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

//角色页面的选择查询条件
function SystemUserRoleselected() {
	var stu = document.getElementById("SystemUserselect").style.display;
	if (stu == "none") {
		document.getElementById("SystemUserselect").style.display = "block";
	} else {
		document.getElementById("SystemUserselect").style.display = "none";
	}
}
//角色页面的选择条件值后返回
function SystemUserRoleoption(name) {
	document.getElementById("SystemUsershowselected").innerHTML = name;
	document.getElementById("SystemUserselect").style.display = "none";
}
//绑定角色数据
function BandingSystemUserData(IndexPage) {
	// 判断传进来的页码是否大于最后一页
	if (IndexPage > Math.ceil(SystemUserSelectData.length / PageNumber)) {
		IndexPage = Math.ceil(SystemUserSelectData.length / PageNumber);
	}
	if(IndexPage==0){return;}
	// 赋值当前页码
	SystemUserIndexPage = IndexPage;

	var data = "";
	for (var i = ((SystemUserIndexPage - 1) * PageNumber); i < SystemUserSelectData.length
			&& i < (SystemUserIndexPage * PageNumber); i++) {
		// data.push(SystemRoleData[i]);
		data += "<tr class=\"gradeX\"><td>" + SystemUserSelectData[i].SchoolName
				+ "</td>";
		data += "<td>" + SystemUserSelectData[i].ClassName + "</td>";
		data += "<td>" + SystemUserSelectData[i].RoleName + "</td>";
		data += "<td>" + SystemUserSelectData[i].UserCode + "</td>";
		data += "<td>" + SystemUserSelectData[i].UserName + "</td>";
		data += "<td>" + SystemUserSelectData[i].UserIDNumber + "</td>";
		data += "<td>" + SystemUserSelectData[i].UserSex + "</td>";
		data += "<td>" + SystemUserSelectData[i].UserAge + "</td>";
		data += "<td>" + SystemUserSelectData[i].UserBelonging + "</td>";
		
		var img = "../assets/i/ico_no.png";
		if (SystemUserSelectData[i].DeleteFlag == 0) {
			img = "../assets/i/ico_yes.png";
		}
		//Id;SchoolName;SchoolCode;ClassName;ClassCode;RoleCode;RoleName;UserCode;UserName;UserIcon;UserIDNumber;
		//UserSex;UserDateOfBirth;UserAge; UserBirthday; UserBelonging;UserPassword; DeleteFlag;	
		var strData = "[{Id:'" + SystemUserSelectData[i].Id + "',SchoolName:'"
				+ SystemUserSelectData[i].SchoolName + "',SchoolCode:'"
				+ SystemUserSelectData[i].SchoolCode + "',ClassName:'"
				+ SystemUserSelectData[i].ClassName + "',ClassCode:'"
				+ SystemUserSelectData[i].ClassCode + "',RoleCode:'"
				+ SystemUserSelectData[i].RoleCode + "',RoleName:'"
				+ SystemUserSelectData[i].RoleName + "',UserCode:'"
				+ SystemUserSelectData[i].UserCode + "',UserName:'"
				+ SystemUserSelectData[i].UserName + "',UserIcon:'"
				+ SystemUserSelectData[i].UserIcon + "',UserIDNumber:'"
				+ SystemUserSelectData[i].UserIDNumber + "',UserSex:'"
				+ SystemUserSelectData[i].UserSex + "',UserDateOfBirth:'"
				+ SystemUserSelectData[i].UserDateOfBirth + "',UserAge:'"
				+ SystemUserSelectData[i].UserAge + "',UserBirthday:'"
				+ SystemUserSelectData[i].UserBirthday + "',UserBelonging:'"
				+ SystemUserSelectData[i].UserBelonging + "',UserPassword:'"
				+ SystemUserSelectData[i].UserPassword + "',DeleteFlag:'"
				+ SystemUserSelectData[i].DeleteFlag + "'}]";
		data += "<td><img src = '" + img
				+ "' style=\"width: 15px; vertical-align: middle;\" /></td>";
		data += "<td><div class=\"tpl-table-black-operation\">"
				+ "<a href=\"javascript:SystemUserEditFunc(" + strData
				+ ");\"> <i class=\"am-icon-pencil\"></i> 编辑"
				+ "</a> <a href=\"javascript:SystemUserDeleteFunc(" + strData
				+ ");\" class=\"tpl-table-black-operation-del\">"
				+ "	<i class=\"am-icon-trash\"></i> 删除</a></div></td></tr>";
	}
	// alert(JSON.stringify(data));
	$("#SystemUserData").html(data);
	// 绑定分页
	SystemUserBandingPages();
}

//绑定角色页面的分页选项
function SystemUserBandingPages() {
	var PagesData = "<li><a href=\"javascript:BandingSystemUserData(" + 1
			+ ") \">«</a></li>";
	var Pages2Data = "";
	var Pages = Math.ceil(SystemUserData.length / PageNumber);
	if (Pages <= 5) {
		for (var i = 0; i < Pages; i++) {
			var ind = i + 1;
			if (SystemUserIndexPage == ind) {
				PagesData += "<li class=\"am-active\"><a href=\"javascript:BandingSystemUserData("
						+ ind + ") \">" + ind + "</a></li>";
			} else {
				PagesData += "<li><a href=\"javascript:BandingSystemUserData(" + ind
						+ ") \">" + ind + "</a></li>";
			}
		}
	} else {
		for (var i = 0; i < Pages; i++) {
			var ind = i + 1;
			if (ind > 2 & ind < Pages - 1) {
				if (ind == 3) {
					PagesData += "<li><a href=\"javascript:OpenPages('SystemUserPagesDialog') \"><span>...<span></a></li>";
				}
				if (SystemRoleIndexPage == ind) {
					Pages2Data += "<li class=\"am-active\"><a href=\"javascript:BandingSystemUserData("
							+ ind + ") \">" + ind + "</a></li>";
				} else {
					Pages2Data += "<li><a href=\"javascript:BandingSystemUserData("
							+ ind + ") \">" + ind + "</a></li>";
				}
			} else {
				if (SystemUserIndexPage == ind) {
					PagesData += "<li class=\"am-active\"><a href=\"javascript:BandingSystemUserData("
							+ ind + ") \">" + ind + "</a></li>";
				} else {
					PagesData += "<li><a href=\"javascript:BandingSystemUserData("
							+ ind + ") \">" + ind + "</a></li>";
				}
			}
		}
	}
	PagesData += "<li><a href=\"javascript:BandingSystemUserData(" + Pages
			+ ") \">»</a></li>";
	$("#SystemUserPages").html(PagesData);
	$("#SystemUserPages2").html(Pages2Data);
	$("#SystemUserPagesDialog").css('display', 'none');
	// RolePages
}

//打开编辑页面
function SystemUserEditFunc(data) {
	$("#SystemUserShowPage").hide();
	$("#SystemUserEditPage").show();
	if (data == null) {
		SystemUserFromState = "I";
		$("#SystemRoleCode").val("");
		$("#SystemRoleName").val("");
		$("#SystemRoleDeleteFlag").prop("checked", "checked");

	} else {
		SystemUserFromState = data[0].Id;
		$("#SystemRoleCode").val(data[0].RoleCode);
		$("#SystemRoleName").val(data[0].RoleName);
		if (data[0].DeleteFlag == 0) {
			$("#SystemRoleDeleteFlag").prop("checked", "checked");
		} else {
			$("#SystemRoleDeleteFlag").prop("checked", "");
		}
	}
}
//关闭编辑页面
function SystemUserEditClose() {
	$("#SystemUserShowPage").show();
	$("#SystemUserEditPage").hide();
}
var SystemUserFromState = "I";
//保存数据
function SystemUserSaveData() {
	// 定义当前页面访问的接口地址
	var url = "../SystemUser/UpdateSystemUser";
	var da;
	var df = "0";
	if (!$("#SystemUserDeleteFlag").is(':checked')) {
		df = "1";
	}
	if (SystemUserFromState == "I") {
		url = "../SystemUser/InsertSystemUser";
		da = {
			"RoleCode" : $("#SystemRoleCode").val(),
			"RoleName" : $("#SystemRoleName").val(),
			"DeleteFlag" : df
		};
	} else {
		da = {
			"Id" : SystemUserFromState,
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
				SystemUserData = eval('(' + data + ')');
				UpdateSystemUserQuery();
				// 调用绑定数据的方法
				BandingSystemUserData(SystemUserIndexPage);
			}
		} else {
			alert("保存失败！")
		}

	});
	SystemUserEditClose();
}

function SystemUserOKMessage(da) {
	// 定义当前页面访问的接口地址
	var url = "../SystemUser/DeleteSystemUser";
	da = {
		"Id" : da[0].Id
	};
	// 获取接口数据
	$.post(url, da, function(data, state) {
		if (state == "success") {
			if (data != "No") {
				// 把查询数据赋值给js变量
				SystemUserData = eval('(' + data + ')');
				UpdateSystemUserQuery();
				// 调用绑定数据的方法
				BandingSystemUserData(SystemUserIndexPage);
			}
		} else {
			alert("删除失败！")
		}

	});
	SystemUserNOMessage();
}

function SystemUserNOMessage() {
	$("#SystemUserMessage").hide();
}

function SystemUserDeleteFunc(da) {
	// var top = ($("#RoleShowPage").height() -
	// $("#SysetmRoleMessage").height())/2;
	var top = 125;
	var left = ($("#SystemUserShowPage").width() - $("#SystemUserMessage").width()) / 2;
	$("#SystemUserMessage").css({
		'display' : 'block',
		'top' : top,
		left : left
	}).show();
	$("#SystemUserOKDelete").bind("click", function() {
		SystemUserOKMessage(da);
	})
}
