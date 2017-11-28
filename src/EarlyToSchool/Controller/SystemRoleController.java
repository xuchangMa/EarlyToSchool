package EarlyToSchool.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import EarlyToSchool.Manage.SystemRoleManage;

@Controller
@RequestMapping(value = "/SystemRole", produces = "text/html; charset=UTF-8")
public class SystemRoleController {

	// @RequestBody 将json对象转成java对象
	// @ResponseBody 表示返回的是json对象
	@RequestMapping("/GetRole")
	public @ResponseBody String GetRole(HttpServletRequest request) {
		return SystemRoleManage.GetRoleList();
	}
	
	// 新增一条数据
	@RequestMapping("/InsertRole")
	public @ResponseBody String InsertRole(HttpServletRequest request) {
		return SystemRoleManage.InsertRoleData(request);
	}

	// 修改一条数据
	@RequestMapping("/UpdateRole")
	public @ResponseBody String UpdateRole(HttpServletRequest request) {
		return SystemRoleManage.UpdateRoleData(request);
	}

	// 删除一条数据
	@RequestMapping("/DeleteRole")
	public @ResponseBody String DeleteRole(HttpServletRequest request) {
		return SystemRoleManage.DeleteRoleData(request);
	}
}
