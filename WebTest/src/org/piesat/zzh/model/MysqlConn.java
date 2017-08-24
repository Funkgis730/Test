package org.piesat.zzh.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author zzh
 * @获取数据库链接
 *
 */

public class MysqlConn {
	
	private static final String username="root";
	private static final String password="foreverhank1";
	private static final String url="jdbc:mysql://localhost:3306/test??useUnicode=true&characterEncoding=UTF8";

	public static Connection getConnection(){
		 Connection conn = null;
			 try {
				Class.forName("com.mysql.jdbc.Driver");
				conn=DriverManager.getConnection(url, username, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return conn;
	}
}
