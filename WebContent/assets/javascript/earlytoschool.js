/**
 * 主页面相关js方法
 */
window.onload=function(){
	//绑定菜单
	BindingMenu();
	$('#doc-my-tabs').tabs();
	addTab();
}

//绑定主菜单
function BindingMenu(){
	//绑定学生和老师的菜单项
	if(1==1){
		StudentsAndTeachers();		
	}
	
}
//学生&老师菜单项
function StudentsAndTeachers(){
	var str="<ul class=\"am-nav am-nav-pills am-nav-justify\">" +
			"<li class=\"\"><a href=\"./index.html\">首页</a></li>" +
              "<li>" +
               "<a href=\"#\">我的课程</a>" +
                "<!-- sub-menu start-->" +
                "<ul class=\"sub-menu\">" +
                  "<li class=\"menu-item\"><a href=\"html/product1.html\">产品展示1</a></li>" +
                  "<li class=\"menu-item\"><a href=\"html/product2.html\">产品展示2</a></li>" +
                  "<li class=\"menu-item\"><a href=\"html/product3.html\">产品展示3</a></li>" +
                "</ul>" +
                "<!-- sub-menu end-->" +
              "</li>" +
              "<li><a href=\"html/solution.html\">课程扩展</a></li>  " +                   
              "<li>" +
                "<a href=\"html/news.html\">资料中心</a>" +
                "<!-- sub-menu start-->" +
                "<ul class=\"sub-menu\">" +
                  "<li class=\"menu-item\"><a href=\"html/news-content.html\">公司动态</a></li>" +
                  "<li class=\"menu-item\"><a href=\"html/404-dark.html\">行业动态</a></li>" +
                  "<li class=\"menu-item\"><a href=\"html/404-light.html\">精彩专题</a></li>" +
                "</ul>" +
                "<!-- sub-menu end-->" +
              "</li>  " +
              "<li><a href=\"html/example.html\">考试安排</a></li>  " +                
              "<li><a href=\"html/join.html\">放松时刻</a></li>" +
              "<li><a href=\"html/contact.html\">论坛</a></li>" +
              "<li><a href=\"javascript:asd();\">个人中心</a></li>" +
            "</ul>";
		document.getElementById("mymenu").innerHTML = str;
		str = "<ul class=\"am-menu-nav am-avg-sm-1\">" +
                  "<li><a href=\"./index.html\" class=\"\" >首页</a></li>" +
                  "<li class=\"am-parent\">" +
                    "<a href=\"#\" class=\"\" >课程中心</a>" +
                      "<ul class=\"am-menu-sub am-collapse \">" +
                          "<li class=\"\">" +
                            "<a href=\"html/product1.html\" class=\"\" >产品展示1</a>" +
                          "</li>" +
                          "<li class=\"\">" +
                            "<a href=\"html/product2.html\" class=\"\" >产品展示2</a>" +
                          "</li>" +
                          "<li class=\"\">" +
                            "<a href=\"html/product3.html\" class=\"\" >产品展示3</a>" +
                          "</li>" +
                      "</ul>" +
                  "</li>" +
                  "<li class=\"\"><a href=\"html/example.html\" class=\"\" >课程扩展</a></li>" +                 
                  "<li class=\"am-parent\">" +
                    "<a href=\"html/news.html\" class=\"\" >资料中心</a>" +
                      "<ul class=\"am-menu-sub am-collapse  \">" +
                          "<li class=\"\">" +
                            "<a href=\"html/news-content.html\" class=\"\" >公司动态</a>" +
                          "</li>" +
                          "<li class=\"\">" +
                            "<a href=\"html/404-dark.html\" class=\"\" >行业动态</a>" +
                          "</li>" +
                          "<li class=\"\">" +
                            "<a href=\"html/404-light.html\" class=\"\" >精彩专题</a>" +
                          "</li>" +
                      "</ul>" +
                  "</li>" +
                  "<li class=\"\"><a href=\"html/solution.html\" class=\"\" >考试安排</a></li>" +
                  "<li class=\"\"><a href=\"html/about.html\" class=\"\" >放松时刻</a></li>" +
                  "<li class=\"\"><a href=\"html/join.html\" class=\"\" >论坛</a></li>" +
                  "<li class=\"\"><a href=\"javascript:asd();\" class=\"\" >个人中心</a></li>" +
                  "<li class=\"am-parent\">" +
                    "<a href=\"\" class=\"nav-icon nav-icon-globe\" >Language</a>" +
                      "<ul class=\"am-menu-sub am-collapse  \">" +
                          "<li>" +
                            "<a href=\"#\" >English</a>" +
                          "</li>" +
                          "<li class=\"\">" +
                            "<a href=\"#\" >Chinese</a>" +
                          "</li>" +
                      "</ul>" +
                  "</li>" +
                  "<li class=\"nav-share-contain\">" +
                    "<div class=\"nav-share-links\">" +
                      "<i class=\"am-icon-facebook\"></i>" +
                      "<i class=\"am-icon-twitter\"></i>" +
                      "<i class=\"am-icon-google-plus\"></i>" +
                      "<i class=\"am-icon-pinterest\"></i>" +
                      "<i class=\"am-icon-instagram\"></i>" +
                      "<i class=\"am-icon-linkedin\"></i>" +
                      "<i class=\"am-icon-youtube-play\"></i>" +
                      "<i class=\"am-icon-rss\"></i>" +
                    "</div>" +
                  "</li>" +
                  "<li class=\"\"><a href=\"html/login.html\" class=\"\" >登录</a></li>" +
                  "<li class=\"\"><a href=\"html/register.html\" class=\"\" >注册</a></li>" +
              "</ul>";
		document.getElementById("myminmenu").innerHTML = str;
}

//测试
function asd(){
	var ss= document.getElementById("MyReceptacle").innerHTML;
	alert(ss);
	document.getElementById("MyReceptacle").innerHTML="";
}


    var tabCounter = 0;
    var $tab = $('#doc-tab-demo-1');
    var $nav = $tab.find('.am-tabs-nav');
    var $bd = $tab.find('.am-tabs-bd');

    function addTab() {
      var nav = '<li><span class="am-icon-close"></span>' +
        '<a href="javascript: void(0)">标签 ' + tabCounter + '</a></li>';
      var content = '<div class="am-tab-panel">动态插入的标签内容' + tabCounter + '</div>';

      $nav.append(nav);
      $bd.append(content);
      tabCounter++;
      $tab.tabs('refresh');
    }

    // 动态添加标签页
    $('.js-append-tab').on('click', function() {
      addTab();
    });

    // 移除标签页
    $nav.on('click', '.am-icon-close', function() {
      var $item = $(this).closest('li');
      var index = $nav.children('li').index($item);

      $item.remove();
      $bd.find('.am-tab-panel').eq(index).remove();

      $tab.tabs('open', index > 0 ? index - 1 : index + 1);
      $tab.tabs('refresh');
    });
 
