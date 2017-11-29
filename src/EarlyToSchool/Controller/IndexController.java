package EarlyToSchool.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import EarlyToSchool.Manage.IndexManage;

@Controller
@RequestMapping(value="/Home",produces="text/html; charset=UTF-8")
public class IndexController {
	@RequestMapping("/Index")
    public String hello(){ 
	 	System.out.println("hello world");
        return "index";
    }
 
	//@RequestBody ��json����ת��java����
	//@ResponseBody ��ʾ���ص���json����
	@RequestMapping("/getData")
	public @ResponseBody String jsonSource(@RequestBody String str){
		System.out.println("jsonsource");
	    return "sdt:"+str;
	
	}
	@RequestMapping("/getSystemApplicationSet")
	public @ResponseBody String getSystemApplicationSet(HttpServletRequest request){
		System.out.println("getSystemApplicationSet");
	    return IndexManage.GetSystemApplicationSetList(request);
	
	}
}
