package EarlyToSchool.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import EarlyToSchool.Manage.SystemRoleConfigurationManage;

@Controller
@RequestMapping(value = "/SystemRoleConfiguration", produces = "text/html; charset=UTF-8")
public class SystemRoleConfigurationController {
	// @RequestBody ��json����ת��java����
		// @ResponseBody ��ʾ���ص���json����
		@RequestMapping("/GetRoleConfiguration")
		public @ResponseBody String GetRoleConfiguration(HttpServletRequest request) {
			return SystemRoleConfigurationManage.SystemRoleConfigurationList();
		}
}
