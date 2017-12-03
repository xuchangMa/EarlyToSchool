package EarlyToSchool.Manage;

import javax.servlet.http.HttpServletRequest;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import Mongodb.MongoDataBase;

public class IndexManage {
	// 查询所有应用数据数据
	public static String GetSystemApplicationSetList(HttpServletRequest request) {
		// 定义表名
		String TableName = "SystemRoleConfiguration";
		//获取用户编码
		String UserCode = request.getParameter("UserCode");
		String RoleCode = "1001";
		//获取配置应用集
		DBCollection ApplicationSetDataSet = MongoDataBase.ConditionQuery(TableName);
		BasicDBObject query = new BasicDBObject();
		query.append(QueryOperators.OR,
				new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0"), new BasicDBObject("DeleteFlag", "1") });
		DBCursor table = MongoDataBase.ConditionQuery(TableName, query);
		String Data = "[";
		while (table.hasNext()) {
			DBObject dbObj = table.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"ApplicationCode\":'" + dbObj.get("ApplicationCode")
					+ "',\"ApplicationName\":'" + dbObj.get("ApplicationName") + "'," + "\"ApplicationIcon\":'"
					+ dbObj.get("ApplicationIcon") + "',\"ApplicationGrade\":'" + dbObj.get("ApplicationGrade")
					+ "',\"MountFlag\":'" + dbObj.get("MountFlag") + "'," + "\"ApplicationURL\":'"
					+ dbObj.get("ApplicationURL") + "',\"ApplicationPosition\":'" + dbObj.get("ApplicationPosition")
					+ "',\"DeleteFlag\":'" + dbObj.get("DeleteFlag") + "'},";
		}
		table = null;
		if (Data == "[") {
			return "";
		}
		Data = Data.substring(0, Data.length() - 1) + "]";
		return Data;
	}
}
