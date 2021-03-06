package EarlyToSchool.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import EarlyToSchool.Manage.IndexManage;
import EarlyToSchool.Manage.SystemUserManage;

@Controller
@RequestMapping(value = "/SystemUser", produces = "text/html; charset=UTF-8")
public class SystemUserController {
	// @RequestBody 将json对象转成java对象
	// @ResponseBody 表示返回的是json对象
	@RequestMapping("/GetSystemUser")
	public @ResponseBody String GetSystemUser(HttpServletRequest request) {
		return SystemUserManage.GetSystemUserList();
	}

	// 新增一条数据
	@RequestMapping("/InsertSystemUser")
	public @ResponseBody String InsertSystemUser(HttpServletRequest request) {
		return SystemUserManage.InsertSystemUserData(request);
	}

	// 修改一条数据
	@RequestMapping("/UpdateSystemUser")
	public @ResponseBody String UpdateSystemUser(HttpServletRequest request) {
		return SystemUserManage.UpdateSystemUserData(request);
	}

	// 删除一条数据
	@RequestMapping("/DeleteSystemUser")
	public @ResponseBody String DeleteSystemUser(HttpServletRequest request) {
		return SystemUserManage.DeleteSystemUserData(request);
	}

	// 用户登录
	@RequestMapping("/LoginSystemUser")
	public @ResponseBody String LoginSystemUser(HttpServletRequest request) {
		return SystemUserManage.LoginSystemUserIs(request);
	}
	// 获取登陆信息
	@RequestMapping("/getSystemApplicationSet")
	public @ResponseBody String getSystemApplicationSet(HttpServletRequest request){
	    return IndexManage.GetSystemApplicationSetList(request);
	
	}
}
