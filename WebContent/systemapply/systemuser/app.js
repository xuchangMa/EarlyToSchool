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
	private String UserRoleCode;	
	//用户角色名称
	private String UserRoleName;	
	//用户编码
	private String UserCode;	
	//用户密码
	private String UserPassword;
	//用户名称
	private String UserName;
	//用户图标
	private String UserIcon;
	//用户身份证号
	private String UserIDNumber;
	//用户性别
	private String UserSex;
	//用户年龄
	private String UserAge;
	//用户生日
	private String UserBirthday;
	//用户归属地
	private String Belonging;
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
var datastructure = new Array("Id", "SchoolName", "SchoolCode", "ClassName", "ClassCode", "UserRoleCode",
		"UserCode","UserPassword","UserName","UserIDNumber","UserSex","UserAge",
		"UserBirthday","Belonging","DeleteFlag");

function add() { alert(1); }

function selected() {
    var stu = document.getElementById("select1").style.display;
    if (stu == "none") {
        document.getElementById("select1").style.display = "block";
    }
    else {
        document.getElementById("select1").style.display = "none";
    }
}
function option(name) {
    document.getElementById("showselected").innerHTML = name;
    document.getElementById("select1").style.display = "none";
}
function Edit() {
    //userform							
    var datas = GetTabs('../systemapply/systemuser/edit.html');
    document.getElementById("userform").innerHTML = datas;    
    // 判断用户是否已有自己选择的模板风格
    if (storageLoad('SelcetColor')) {
        $('#divedit').attr('class', storageLoad('SelcetColor').Color)
    } else {
        storageSave(saveSelectColor);
        $('#divedit').attr('class', 'theme-black')
    }
}