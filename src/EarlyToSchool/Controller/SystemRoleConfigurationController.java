package EarlyToSchool.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import EarlyToSchool.Manage.SystemRoleConfigurationManage;

@Controller
@RequestMapping(value = "/SystemRoleConfiguration", produces = "text/html; charset=UTF-8")
public class SystemRoleConfigurationController {
	// @RequestBody 将json对象转成java对象
		// @ResponseBody 表示返回的是json对象
		@RequestMapping("/GetRoleConfiguration")
		public @ResponseBody String GetRoleConfiguration(HttpServletRequest request) {
			return SystemRoleConfigurationManage.SystemRoleConfigurationList();
		}
}
