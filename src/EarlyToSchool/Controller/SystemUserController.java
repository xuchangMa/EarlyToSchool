package EarlyToSchool.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import EarlyToSchool.Manage.SystemUserManage;

@Controller
@RequestMapping(value = "/SystemUser", produces = "text/html; charset=UTF-8")
public class SystemUserController {
	// @RequestBody ��json����ת��java����
		// @ResponseBody ��ʾ���ص���json����
		@RequestMapping("/GetSystemUser")
		public @ResponseBody String GetSystemUser(HttpServletRequest request) {
			return SystemUserManage.GetSystemUserList();
		}
		
		// ����һ������
		@RequestMapping("/InsertSystemUser")
		public @ResponseBody String InsertSystemUser(HttpServletRequest request) {
			return SystemUserManage.InsertSystemUserData(request);
		}

		// �޸�һ������
		@RequestMapping("/UpdateSystemUser")
		public @ResponseBody String UpdateSystemUser(HttpServletRequest request) {
			return SystemUserManage.UpdateSystemUserData(request);
		}

		// ɾ��һ������
		@RequestMapping("/DeleteSystemUser")
		public @ResponseBody String DeleteSystemUser(HttpServletRequest request) {
			return SystemUserManage.DeleteSystemUserData(request);
		}
}
