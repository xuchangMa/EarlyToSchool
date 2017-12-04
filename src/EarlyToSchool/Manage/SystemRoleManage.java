package EarlyToSchool.Manage;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import Mongodb.MongoDataBase;

public class SystemRoleManage {
	// 查询所有Role数据
	public static String GetRoleList() {
		String TableName = "SystemRole";
		BasicDBObject query = new BasicDBObject();
		query.append(QueryOperators.OR, new BasicDBObject[] { 
				new BasicDBObject("DeleteFlag", "0"),  
                new BasicDBObject("DeleteFlag", "1") }); 
		DBCursor table = MongoDataBase.ConditionQuery(TableName, query);		
		String Data = "[";
		while (table.hasNext()) {
			DBObject dbObj = table.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"RoleCode\":'" + dbObj.get("RoleCode") + "',\"RoleName\":'"
					+ dbObj.get("RoleName") + "'," + "\"DeleteFlag\":'" + dbObj.get("DeleteFlag") + "'},";
		}
		table = null;
		if(Data == "["){
			return "";
		}
		Data = Data.substring(0, Data.length() - 1) + "]";
		MongoDataBase.drop();//关闭数据库连接
		return Data;
	}

	// 插入Role数据
	public static String InsertRoleData(HttpServletRequest request) {
		String RoleCode = request.getParameter("RoleCode");
		String RoleName = request.getParameter("RoleName");
		String DeleteFlag = request.getParameter("DeleteFlag");

		BasicDBObject doc = new BasicDBObject();
		doc.append("RoleCode", RoleCode);
		doc.append("RoleName", RoleName);
		doc.append("DeleteFlag", DeleteFlag);
		if (MongoDataBase.Insert("SystemRole", doc)) {
			return GetRoleList();
		} else {
			return "No";
		}
	}

	// 修改Role数据
	public static String UpdateRoleData(HttpServletRequest request) {
		String Id = request.getParameter("Id");
		String RoleCode = request.getParameter("RoleCode");
		String RoleName = request.getParameter("RoleName");
		String DeleteFlag = request.getParameter("DeleteFlag");
		
		BasicDBObject query = new BasicDBObject();
		query.append("_id", new ObjectId(Id));  

        BasicDBObject newDocument = new BasicDBObject();  
        newDocument.put("RoleCode", RoleCode);  
        newDocument.put("RoleName", RoleName);
        newDocument.put("DeleteFlag", DeleteFlag);             
		
		if (MongoDataBase.Update("SystemRole", query, newDocument)) {
			return GetRoleList();
		} else {
			return "No";
		}
	}

	// 删除Role数据
	public static String DeleteRoleData(HttpServletRequest request) {
		String Id = request.getParameter("Id");
		
		BasicDBObject query = new BasicDBObject();
		query.append("_id", new ObjectId(Id));  

        BasicDBObject newDocument = new BasicDBObject();  
        newDocument.put("DeleteFlag", "2");             
		
		if (MongoDataBase.Update("SystemRole", query, newDocument)) {
			return GetRoleList();
		} else {
			return "No";
		}
	}
}
