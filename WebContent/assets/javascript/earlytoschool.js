/**
 * 主页面相关js方法
 */
$(function (){
	//获取当前登录用户的编码
	var UserCode = $.cookie("UserCode");
	if(UserCode == "" || UserCode == null){
		alert(1);
		return false;
	}
	//获取用户基本信息
	da = {
			"UserCode" : $.cookie("UserCode")
		};
	// 定义当前页面访问的接口地址
	var url = "../SystemUser/getSystemApplicationSet";
	// 获取接口数据
	$.post(url, da, function(data, state) {
		if (state == "success") {
			if (data != "No") {
				// 调用绑定数据的方法
				BandingMenu(eval('(' + data + ')'));
			}
		} else {
			alert("加载菜单失败！")
		}

	});
	//返回菜单列表
	
	
})
 
//绑定菜单方法
function BandingMenu(menuJson) {
    /*var menuJson = [{
        "type": "2", "Icon": "1", "Name": "系统设置2", "value": [
            { "type": "3", "Icon": "1", "Name": "系统设置3", "url": "" },
            { "type": "3", "Icon": "1", "Name": "系统设置3", "url": "" },
            { "type": "3", "Icon": "1", "Name": "系统设置3", "url": "" }]
    },
    { "type": "3", "Icon": "1", "Name": "系统设置3", "url": "" }
    ]*/
    var menuData = "";
    for (var i = 0; i < menuJson.length; i++) {
        menuData += "<li class=\"sidebar-nav-link\">";
       if (menuJson[i].MountFlag == "0") {
            menuData += "<a href=\"javascript:;\" class=\"sidebar-nav-sub-title\">" +
                        "<i class=\""+menuJson[i].ApplicationIcon+" sidebar-nav-link-logo\"></i>" + menuJson[i].ApplicationName +
                        "<span class=\"am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico\"></span></a>";
            menuData += "<ul class=\"sidebar-nav sidebar-nav-sub\">";
            for (var j = 0; j < menuJson[i].ApplicationURL.length; j++) {
                menuData += "<li class=\"sidebar-nav-link\">" +
                            "<a href=\"javascript:addTab('" + menuJson[i].ApplicationURL[j].ApplicationName + "','" + menuJson[i].ApplicationURL[j].ApplicationURL + "');\">" +
                            "    <span class=\"" + menuJson[i].ApplicationURL[j].ApplicationIcon + " sidebar-nav-link-logo\"></span> " + menuJson[i].ApplicationURL[j].ApplicationName +
                            "</a>" +
                            "</li>";
            }
            menuData += "</ul>";
        }
        else if (menuJson[i].MountFlag == "1") {
            menuData += "<a href=\"javascript:addTab('" + menuJson[i].ApplicationName + "','" + menuJson[i].ApplicationURL + "');\">" +
                        "    <span class=\"" + menuJson[i].ApplicationIcon+" sidebar-nav-link-logo\"></span> " + menuJson[i].ApplicationName +
                        "</a>";
        }
        menuData += "</li>";
    }

    $("#SystemMenu").html(menuData);
    $('.sidebar-nav-sub-title').on('click', function () {
        $(this).siblings('.sidebar-nav-sub').slideToggle(80)
            .end()
            .find('.sidebar-nav-sub-ico').toggleClass('sidebar-nav-sub-ico-rotate');
    })
}