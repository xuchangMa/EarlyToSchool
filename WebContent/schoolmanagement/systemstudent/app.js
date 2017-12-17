/**
 * 学生信息
 */

/*定义数据结构
 * public class SystemStudent{
	//表id
	private String Id;
    //班级名称
	private String ClassName;
	//班级编码
	private String ClassCode;
	//学生姓名
	private String StudentName;
	//学生编码
	private String StudentCode;
	//学生身份证
	private String StudentId;
	//是否生效
	private boolean DeleteFlag;	
}
 * 若有调换班级的情况：1.先有所在班级把该学生移除（标记为未生效），目标班级才可以把该学生添加进所在班级
 */