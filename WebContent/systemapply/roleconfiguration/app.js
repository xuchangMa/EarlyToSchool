/**
 * 角色权限配置
 */

/**
 * public class RoleConfiguration { //表ID public static String Id; //角色编码 public
 * static String RoleCode; //角色名称 public static String RoleName; //应用编码 public
 * static String ApplicationCode; //应用名称 public static String ApplicationName;
 * //查看 public static String View; //新增&修改 public static String AddUpdate; //删除
 * public static String Delete; //导入&导出 public static String ImportExport; //上传
 * public static String Upload; //下载 public static String Download; }
 */
var tableName = "SystemRoleConfiguration";

// 定义角色配置信息数据
var SystemRoleConfigurationData;
// 定义查询数据
var SystemRoleConfigurationSelectData = [];
// 定义数据当前页
var SystemRoleConfigurationIndexPage = 1;
// 页面加载方法
$(function() {
	// 获取数据
	getRoleConfigurationData();
	// 调用查询方法
	GetRoleConfigurationQuery();
});
// 页面加载方法
function getRoleConfigurationData() {
	// 定义当前页面访问的接口地址
	var url = "../SystemRoleConfiguration/GetRoleConfiguration";
	$.ajax({
				type : "post",
				url : url,
				data : null,
				async : false,
				success : function(data) {
					if (data != "") {
						var datalist = eval('(' + data + ')');
						// 把查询数据赋值给js变量
						SystemRoleConfigurationData = datalist[1].SystemRoleConfiguration;
						BandingRoleSelectList(datalist[0].SystemRole);
					} else {
						SystemRoleConfigurationData = null;
					}
				}
			});
}

function BandingRoleSelectList(list) {
	var sele = "";
	for (var i = 0; i < list.length; i++) {
		sele += "<li data-index=\"" + i + "\" data-group=\"0\""
				+ "onclick=\"SystemRoleConfigurationoption('"
				+ list[i].RoleName + "')\"><span"
				+ "class=\"am-selected-text\">" + list[i].RoleName
				+ "</span> <i class=\"am-icon-check\"></i>" + "</li>"
	}
	$("#RoleConfigurationshowselected").html(list[0].RoleName);
	$("#RoleConfigurationselectUi").html(sele);
}

function GetRoleConfigurationQuery() {
	if (UpdateRoleConfigurationQuery()) {
		// 每次查询重新绑定当前页
		SystemRoleConfigurationIndexPage = 1;
		// 调用绑定数据的方法
		BandingRoleConfigurationData(SystemRoleConfigurationIndexPage);
	}
}

// 定义更新查询数据
function UpdateRoleConfigurationQuery() {
	if (SystemRoleConfigurationData == null) {
		return false;
	}
	var sele = $("#RoleConfigurationshowselected").html();
	SystemRoleConfigurationSelectData = [];
	for (var i = 0; i < SystemRoleConfigurationData.length; i++) {
		if (SystemRoleConfigurationData[i].RoleName.indexOf(sele) > -1) {
			SystemRoleConfigurationSelectData
					.push(SystemRoleConfigurationData[i]);
		}
	}
	return true;
}

// 角色页面的选择查询条件
function RoleConfigurationselected() {
	var stu = document.getElementById("RoleConfigurationselect").style.display;
	if (stu == "none") {
		document.getElementById("RoleConfigurationselect").style.display = "block";
	} else {
		document.getElementById("RoleConfigurationselect").style.display = "none";
	}
}
// 角色页面的选择条件值后返回
function SystemRoleConfigurationoption(name) {
	document.getElementById("RoleConfigurationshowselected").innerHTML = name;
	document.getElementById("RoleConfigurationselect").style.display = "none";
	// 掉查询方法
	GetRoleConfigurationQuery();
}
// 绑定角色数据
function BandingRoleConfigurationData(IndexPage) {
	// 判断传进来的页码是否大于最后一页
	if (IndexPage > Math.ceil(SystemRoleConfigurationSelectData.length
			/ PageNumber)) {
		IndexPage = Math.ceil(SystemRoleConfigurationSelectData.length
				/ PageNumber);
	}
	if (IndexPage == 0) {
		return;
	}
	// 赋值当前页码
	SystemRoleConfigurationIndexPage = IndexPage;
	var data = "";
	for (var i = ((SystemRoleConfigurationIndexPage - 1) * PageNumber); i < SystemRoleConfigurationSelectData.length
			&& i < (SystemRoleConfigurationIndexPage * PageNumber); i++) {
	    // data.push(SystemRoleData[i]);

	    var strData = "[{Id:'" + SystemRoleConfigurationSelectData[i].Id + "',RoleCode:'"
				+ SystemRoleConfigurationSelectData[i].RoleCode + "',RoleName:'"
				+ SystemRoleConfigurationSelectData[i].RoleName + "',ApplicationCode:'"
                + SystemRoleConfigurationSelectData[i].ApplicationCode + "',ApplicationName:'"
				+ SystemRoleConfigurationSelectData[i].ApplicationName + "',View:'"
                + SystemRoleConfigurationSelectData[i].View + "',AddUpdate:'"
				+ SystemRoleConfigurationSelectData[i].AddUpdate + "',Delete:'"
                + SystemRoleConfigurationSelectData[i].Delete + "',ImportExport:'"
				+ SystemRoleConfigurationSelectData[i].ImportExport + "',Upload:'"
                + SystemRoleConfigurationSelectData[i].Upload + "',Download:'"
				+ SystemRoleConfigurationSelectData[i].Download + "'}]";

		data += "<tr class=\"gradeX\"><td>"
				+ SystemRoleConfigurationSelectData[i].ApplicationCode
				+ "</td>";
		data += "<td>" + SystemRoleConfigurationSelectData[i].ApplicationName
				+ "</td>";
		if (SystemRoleConfigurationSelectData[i].View == "0") {
		    data += "<td><div class=\"tpl-switch\"><input type=\"checkbox\" onchange=\"RoleConfigurationUpdateState(this," + strData + ",'View')\"" +
                    "class=\"ios-switch bigswitch tpl-switch-btn\" checked=\"checked\">" +
                    "<div class=\"tpl-switch-btn-view\"><div></div></div></div></td>";
		}
		else {
		    data += "<td><div class=\"tpl-switch\"><input type=\"checkbox\" onchange=\"RoleConfigurationUpdateState(this," + strData + ",'View')\"" +
                    "class=\"ios-switch bigswitch tpl-switch-btn\">" +
                    "<div class=\"tpl-switch-btn-view\"><div></div></div></div></td>";
		}
		if (SystemRoleConfigurationSelectData[i].AddUpdate == "0") {
		    data += "<td><div class=\"tpl-switch\"><input type=\"checkbox\" onchange=\"RoleConfigurationUpdateState(this," + strData + ",'AddUpdate')\"" +
                    "class=\"ios-switch bigswitch tpl-switch-btn\" checked=\"checked\">" +
                    "<div class=\"tpl-switch-btn-view\"><div></div></div></div></td>";
		}
		else {
		    data += "<td><div class=\"tpl-switch\"><input type=\"checkbox\" onchange=\"RoleConfigurationUpdateState(this," + strData + ",'AddUpdate')\"" +
                    "class=\"ios-switch bigswitch tpl-switch-btn\">" +
                    "<div class=\"tpl-switch-btn-view\"><div></div></div></div></td>";
		}
		if (SystemRoleConfigurationSelectData[i].Delete == "0") {
		    data += "<td><div class=\"tpl-switch\"><input type=\"checkbox\" onchange=\"RoleConfigurationUpdateState(this," + strData + ",'Delete')\"" +
                    "class=\"ios-switch bigswitch tpl-switch-btn\" checked=\"checked\">" +
                    "<div class=\"tpl-switch-btn-view\"><div></div></div></div></td>";
		}
		else {
		    data += "<td><div class=\"tpl-switch\"><input type=\"checkbox\" onchange=\"RoleConfigurationUpdateState(this," + strData + ",'Delete')\"" +
                    "class=\"ios-switch bigswitch tpl-switch-btn\">" +
                    "<div class=\"tpl-switch-btn-view\"><div></div></div></div></td>";
		}
		if (SystemRoleConfigurationSelectData[i].ImportExport == "0") {
		    data += "<td><div class=\"tpl-switch\"><input type=\"checkbox\" onchange=\"RoleConfigurationUpdateState(this," + strData + ",'ImportExport')\"" +
                    "class=\"ios-switch bigswitch tpl-switch-btn\" checked=\"checked\">" +
                    "<div class=\"tpl-switch-btn-view\"><div></div></div></div></td>";
		}
		else {
		    data += "<td><div class=\"tpl-switch\"><input type=\"checkbox\" onchange=\"RoleConfigurationUpdateState(this," + strData + ",'ImportExport')\"" +
                    "class=\"ios-switch bigswitch tpl-switch-btn\">" +
                    "<div class=\"tpl-switch-btn-view\"><div></div></div></div></td>";
		}
		if (SystemRoleConfigurationSelectData[i].Upload == "0") {
		    data += "<td><div class=\"tpl-switch\"><input type=\"checkbox\" onchange=\"RoleConfigurationUpdateState(this," + strData + ",'Upload')\"" +
                    "class=\"ios-switch bigswitch tpl-switch-btn\" checked=\"checked\">" +
                    "<div class=\"tpl-switch-btn-view\"><div></div></div></div></td>";
		}
		else {
		    data += "<td><div class=\"tpl-switch\"><input type=\"checkbox\" onchange=\"RoleConfigurationUpdateState(this," + strData + ",'Upload')\"" +
                    "class=\"ios-switch bigswitch tpl-switch-btn\">" +
                    "<div class=\"tpl-switch-btn-view\"><div></div></div></div></td>";
		}
		if (SystemRoleConfigurationSelectData[i].Download == "0") {
		    data += "<td><div class=\"tpl-switch\"><input type=\"checkbox\" onchange=\"RoleConfigurationUpdateState(this," + strData + ",'Download')\"" +
                    "class=\"ios-switch bigswitch tpl-switch-btn\" checked=\"checked\">" +
                    "<div class=\"tpl-switch-btn-view\"><div></div></div></div></td>";
		}
		else {
		    data += "<td><div class=\"tpl-switch\"><input type=\"checkbox\" onchange=\"RoleConfigurationUpdateState(this," + strData + ",'Download')\"" +
                    "class=\"ios-switch bigswitch tpl-switch-btn\">" +
                    "<div class=\"tpl-switch-btn-view\"><div></div></div></div></td>";
		}
		data += "</tr>";

	}
	//alert(JSON.stringify(data));
	$("#SystemRoleConfigurationData").html(data);
	// 绑定分页
	RoleConfigurationBandingPages();
}

// 绑定角色页面的分页选项
function RoleConfigurationBandingPages() {
	var PagesData = "<li><a href=\"javascript:BandingRoleData(" + 1
			+ ") \">«</a></li>";
	var Pages2Data = "";
	var Pages = Math
			.ceil(SystemRoleConfigurationSelectData.length / PageNumber);
	if (Pages <= 5) {
		for (var i = 0; i < Pages; i++) {
			var ind = i + 1;
			if (SystemRoleConfigurationIndexPage == ind) {
				PagesData += "<li class=\"am-active\"><a href=\"javascript:BandingRoleConfigurationData("
						+ ind + ") \">" + ind + "</a></li>";
			} else {
				PagesData += "<li><a href=\"javascript:BandingRoleConfigurationData("
						+ ind + ") \">" + ind + "</a></li>";
			}
		}
	} else {
		for (var i = 0; i < Pages; i++) {
			var ind = i + 1;
			if (ind > 2 & ind < Pages - 1) {
				if (ind == 3) {
					PagesData += "<li><a href=\"javascript:OpenPages('RoleConfigurationPagesDialog') \"><span>...<span></a></li>";
				}
				if (SystemRoleConfigurationIndexPage == ind) {
					Pages2Data += "<li class=\"am-active\"><a href=\"javascript:BandingRoleConfigurationData("
							+ ind + ") \">" + ind + "</a></li>";
				} else {
					Pages2Data += "<li><a href=\"javascript:BandingRoleConfigurationData("
							+ ind + ") \">" + ind + "</a></li>";
				}
			} else {
				if (SystemRoleConfigurationIndexPage == ind) {
					PagesData += "<li class=\"am-active\"><a href=\"javascript:BandingRoleConfigurationData("
							+ ind + ") \">" + ind + "</a></li>";
				} else {
					PagesData += "<li><a href=\"javascript:BandingRoleConfigurationData("
							+ ind + ") \">" + ind + "</a></li>";
				}
			}
		}
	}
	PagesData += "<li><a href=\"javascript:BandingRoleConfigurationData("
			+ Pages + ") \">»</a></li>";
	$("#RoleConfigurationPages").html(PagesData);
	$("#RoleConfigurationPages2").html(Pages2Data);
	$("#RoleConfigurationPagesDialog").css('display', 'none');
	// RolePages
}

//修改角色配置数据
function RoleConfigurationUpdateState(obj, objlist, col) {
    if (obj.checked) {
        objlist[0][col] = "0";
    }
    else {
        objlist[0][col] = "1";
    }    
    // 定义当前页面访问的接口地址
    var url = "../SystemRoleConfiguration/UpdateRoleConfiguration";
    // 获取接口数据
    $.post(url, objlist[0], function (data, state) {
        if (state == "success") {
            if (data != "No") {
            	// 把查询数据赋值给js变量
            	var datalist = eval('(' + data + ')');
				// 把查询数据赋值给js变量
				SystemRoleConfigurationData = datalist[1].SystemRoleConfiguration;
				//BandingRoleSelectList(datalist[0].SystemRole);
				UpdateRoleConfigurationQuery();
				// 调用绑定数据的方法
				BandingRoleConfigurationData(SystemRoleConfigurationIndexPage);
            }
        } else {
            alert("保存失败！")
        }

    });
}

