package EarlyToSchool.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import EarlyToSchool.Manage.SystemApplicationSetManage;

@Controller
@RequestMapping(value = "/SystemApplicationSet", produces = "text/html; charset=UTF-8")
public class SystemApplicationSetController {
	// @RequestBody 将json对象转成java对象
		// @ResponseBody 表示返回的是json对象
		@RequestMapping("/GetSystemApplicationSet")
		public @ResponseBody String GetSystemApplicationSet(HttpServletRequest request) {
			return SystemApplicationSetManage.GetSystemApplicationSetList();
		}
		
		// 新增一条数据
		@RequestMapping("/InsertSystemApplicationSet")
		public @ResponseBody String InsertSystemApplicationSet(HttpServletRequest request) {
			return SystemApplicationSetManage.InsertSystemApplicationSetData(request);
		}

		// 修改一条数据
		@RequestMapping("/UpdateSystemApplicationSet")
		public @ResponseBody String SystemApplicationSet(HttpServletRequest request) {
			return SystemApplicationSetManage.UpdateSystemApplicationSetData(request);
		}

		// 删除一条数据
		@RequestMapping("/DeleteSystemApplicationSet")
		public @ResponseBody String DeleteSystemApplicationSet(HttpServletRequest request) {
			return SystemApplicationSetManage.DeleteSystemApplicationSetData(request);
		}
}
