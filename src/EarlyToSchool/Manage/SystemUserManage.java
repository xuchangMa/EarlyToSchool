package EarlyToSchool.Manage;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import Mongodb.MongoDataBase;

public class SystemUserManage {
	static String TableName = "SystemUser";

	// 查询所有用户数据
	public static String GetSystemUserList() {
		// 查询角色信息数据
		BasicDBObject rolequery = new BasicDBObject();
		rolequery.append(QueryOperators.AND, new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0") });
		String Data = "[{\"SystemRole\":[";
		// 获取角色所有应用集
		DBCollection SystemRoleDataSet = MongoDataBase.ConditionQuery("SystemRole");
		DBCursor roletable = SystemRoleDataSet.find(rolequery);
		while (roletable.hasNext()) {
			DBObject dbObj = roletable.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"RoleCode\":'" + dbObj.get("RoleCode") + "',\"RoleName\":'"
					+ dbObj.get("RoleName") + "'," + "\"DeleteFlag\":'" + dbObj.get("DeleteFlag") + "'},";
		}
		Data = Data.substring(0, Data.length() - 1);
		Data += "]},{\"SystemRoleConfiguration\":[";
		BasicDBObject query = new BasicDBObject();
		query.append(QueryOperators.OR,
				new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0"), new BasicDBObject("DeleteFlag", "1") });
		DBCursor table = MongoDataBase.ConditionQuery(TableName, query);
		while (table.hasNext()) {
			DBObject dbObj = table.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"SchoolName\":'" + dbObj.get("SchoolName")
					+ "',\"SchoolCode\":'" + dbObj.get("SchoolCode") + "',\"ClassName\":'" + dbObj.get("ClassName")
					+ "'" + "',\"ClassCode\":'" + dbObj.get("ClassCode") + "',\"RoleCode\":'" + dbObj.get("RoleCode")
					+ "'" + "',\"RoleName\":'" + dbObj.get("RoleName") + "',\"UserCode\":'" + dbObj.get("UserCode")
					+ "'" + "',\"UserName\":'" + dbObj.get("UserName") + "',\"UserIcon\":'" + dbObj.get("UserIcon")
					+ "'" + "',\"UserIDNumber\":'" + dbObj.get("UserIDNumber") + "',\"UserSex\":'"
					+ dbObj.get("UserSex") + "'" + "',\"UserDateOfBirth\":'" + dbObj.get("UserDateOfBirth")
					+ "',\"UserAge\":'" + dbObj.get("UserAge") + "'" + "',\"UserBirthday\":'"
					+ dbObj.get("UserBirthday") + "',\"UserBelonging\":'" + dbObj.get("UserBelonging") + "'"
					+ "',\"UserPassword\":'" + dbObj.get("UserPassword") + "',\"DeleteFlag\":'"
					+ dbObj.get("DeleteFlag") + "'},";
		}
		Data = Data.substring(0, Data.length() - 1);
		Data += "]}]";
		roletable = null;
		table = null;
		MongoDataBase.drop();// 关闭数据库连接
		return Data;
	}

	// 插入用户数据
	public static String InsertSystemUserData(HttpServletRequest request) {
		String UserCode = "000000";
		DBCursor roletable = null;
		// 获取用户所有应用集
		DBCollection SystemUserDataSet = MongoDataBase.ConditionQuery(TableName);
		BasicDBObject userquery = new BasicDBObject();
		userquery.append(QueryOperators.AND, new BasicDBObject[] { new BasicDBObject("UserCode", UserCode) });
		do {
			//动态生成不重复UserCode
			UserCode = Integer.toString(new Random().nextInt(999999));
			roletable = SystemUserDataSet.find(userquery);
		} while (roletable.count() == 0);
		BasicDBObject doc = new BasicDBObject();
		doc.append("SchoolName", request.getParameter("SchoolName"));
		doc.append("SchoolCode", request.getParameter("SchoolCode"));
		doc.append("ClassName", request.getParameter("ClassName"));
		doc.append("ClassCode", request.getParameter("ClassCode"));
		doc.append("RoleCode", request.getParameter("RoleCode"));
		doc.append("RoleName", request.getParameter("RoleName"));
		doc.append("UserCode", UserCode);
		doc.append("UserName", request.getParameter("UserName"));
		doc.append("UserIcon", request.getParameter("UserIcon"));
		doc.append("UserIDNumber", request.getParameter("UserIDNumber"));
		doc.append("UserSex", request.getParameter("UserSex"));
		doc.append("UserDateOfBirth", request.getParameter("UserDateOfBirth"));
		doc.append("UserAge", request.getParameter("UserAge"));
		doc.append("UserBirthday", request.getParameter("UserBirthday"));
		doc.append("UserBelonging", request.getParameter("UserBelonging"));
		doc.append("UserPassword", request.getParameter("UserPassword"));
		doc.append("DeleteFlag", request.getParameter("DeleteFlag"));
		if (MongoDataBase.Insert(TableName, doc)) {
			return GetSystemUserList();
		} else {
			return "No";
		}
	}

	// 修改用户数据
	public static String UpdateSystemUserData(HttpServletRequest request) {		
		BasicDBObject query = new BasicDBObject();
		query.append("_id", new ObjectId(request.getParameter("Id")));

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("SchoolName", request.getParameter("SchoolName"));
		newDocument.put("SchoolCode", request.getParameter("SchoolCode"));
		newDocument.put("ClassName", request.getParameter("ClassName"));
		newDocument.put("ClassCode", request.getParameter("ClassCode"));
		newDocument.put("RoleCode", request.getParameter("RoleCode"));
		newDocument.put("RoleName", request.getParameter("RoleName"));
		newDocument.put("UserName", request.getParameter("UserName"));
		newDocument.put("UserIcon", request.getParameter("UserIcon"));
		newDocument.put("UserIDNumber", request.getParameter("UserIDNumber"));
		newDocument.put("UserSex", request.getParameter("UserSex"));
		newDocument.put("UserDateOfBirth", request.getParameter("UserDateOfBirth"));
		newDocument.put("UserAge", request.getParameter("UserAge"));
		newDocument.put("UserBirthday", request.getParameter("UserBirthday"));
		newDocument.put("UserBelonging", request.getParameter("UserBelonging"));
		newDocument.put("UserPassword", request.getParameter("UserPassword"));
		newDocument.put("DeleteFlag", request.getParameter("DeleteFlag"));

		if (MongoDataBase.Update(TableName, query, newDocument)) {
			return GetSystemUserList();
		} else {
			return "No";
		}
	}

	// 删除用户数据
	public static String DeleteSystemUserData(HttpServletRequest request) {
		String Id = request.getParameter("Id");

		BasicDBObject query = new BasicDBObject();
		query.append("_id", new ObjectId(Id));

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("DeleteFlag", "2");

		if (MongoDataBase.Update(TableName, query, newDocument)) {
			return GetSystemUserList();
		} else {
			return "No";
		}
	}

}
