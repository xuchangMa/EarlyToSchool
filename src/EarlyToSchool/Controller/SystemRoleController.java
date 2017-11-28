package EarlyToSchool.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import EarlyToSchool.Manage.SystemRoleManage;

@Controller
@RequestMapping(value = "/SystemRole", produces = "text/html; charset=UTF-8")
public class SystemRoleController {

	// @RequestBody ��json����ת��java����
	// @ResponseBody ��ʾ���ص���json����
	@RequestMapping("/GetRole")
	public @ResponseBody String GetRole(HttpServletRequest request) {
		return SystemRoleManage.GetRoleList();
	}
	
	// ����һ������
	@RequestMapping("/InsertRole")
	public @ResponseBody String InsertRole(HttpServletRequest request) {
		return SystemRoleManage.InsertRoleData(request);
	}

	// �޸�һ������
	@RequestMapping("/UpdateRole")
	public @ResponseBody String UpdateRole(HttpServletRequest request) {
		return SystemRoleManage.UpdateRoleData(request);
	}

	// ɾ��һ������
	@RequestMapping("/DeleteRole")
	public @ResponseBody String DeleteRole(HttpServletRequest request) {
		return SystemRoleManage.DeleteRoleData(request);
	}
}
