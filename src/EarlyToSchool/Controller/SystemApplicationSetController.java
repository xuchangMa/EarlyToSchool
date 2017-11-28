package EarlyToSchool.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import EarlyToSchool.Manage.SystemApplicationSetManage;

@Controller
@RequestMapping(value = "/SystemApplicationSet", produces = "text/html; charset=UTF-8")
public class SystemApplicationSetController {
	// @RequestBody ��json����ת��java����
		// @ResponseBody ��ʾ���ص���json����
		@RequestMapping("/GetSystemApplicationSet")
		public @ResponseBody String GetSystemApplicationSet(HttpServletRequest request) {
			return SystemApplicationSetManage.GetSystemApplicationSetList();
		}
		
		// ����һ������
		@RequestMapping("/InsertSystemApplicationSet")
		public @ResponseBody String InsertSystemApplicationSet(HttpServletRequest request) {
			return SystemApplicationSetManage.InsertSystemApplicationSetData(request);
		}

		// �޸�һ������
		@RequestMapping("/UpdateSystemApplicationSet")
		public @ResponseBody String SystemApplicationSet(HttpServletRequest request) {
			return SystemApplicationSetManage.UpdateSystemApplicationSetData(request);
		}

		// ɾ��һ������
		@RequestMapping("/DeleteSystemApplicationSet")
		public @ResponseBody String DeleteSystemApplicationSet(HttpServletRequest request) {
			return SystemApplicationSetManage.DeleteSystemApplicationSetData(request);
		}
}
