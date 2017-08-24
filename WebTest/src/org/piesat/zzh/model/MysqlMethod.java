package org.piesat.zzh.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * 
 * @author zzh
 * @category 数据库操作方法
 *
 */
public class MysqlMethod {
	public Connection conn=null;
	public MysqlMethod(){
		
	}
	
	/**
	 * 
	 * @param conn 数据库链接
	 */
	public MysqlMethod(Connection conn){
		this.conn=conn;
	}
	
	/**
	 * 
	 * @param u 用户属性类
	 * @return  数据库更新列表数量
	 * @throws SQLException
	 */
	public int insert(User u) throws SQLException {
		Connection conn = this.conn;
		String sql = "INSERT user_info(account,username,password,gender,age,email,major,school,favorites,picture) VALUES (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		String[] favorites = u.getFavorites();

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, u.getAccount());
		pstmt.setString(2, u.getUsername());
		pstmt.setString(3, u.getPassword());
		pstmt.setString(4, u.getGender());
		pstmt.setString(5, u.getAge());
		pstmt.setString(6, u.getEmail());
		pstmt.setString(7, u.getMajor());
		pstmt.setString(8, u.getSchool());
		pstmt.setString(9, favorites[0]);
		pstmt.setString(10, u.getPicture());

		int flag = pstmt.executeUpdate();
		pstmt.close();
		return flag;
	}
	
/**
 * 
 * @param acc 账户
 * @return
 * @throws SQLException
 */
	public boolean searchUser(String acc) throws SQLException {
		Connection conn = this.conn;
		String sql = "select account from user_info";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		ArrayList<String> accountList = new ArrayList<String>();

		while (rs.next()) {
			String account = rs.getString("account");
			accountList.add(account);
		}

		Iterator<String> it = accountList.iterator();

		while (it.hasNext()) {
			String a = (String) it.next();
			if (a.equals(acc)) {
				return true;
			}
		}
		rs.close();
		return false;
	}

	/**
	 * 
	 * @param user 用户名
	 * @return 账户对应密码
	 * @throws SQLException
	 */
	public String searchPassword(String acc) throws SQLException {
		String pass = null;
		Connection conn = this.conn;
		String sql = "select password from user_info where account = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, acc);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pass = rs.getString("password");
		}
		return pass;
	}
	
	/**
	 * 
	 * @param username 查询用户账户
	 * @return 用户属性
	 * @throws SQLException
	 */
	public User getUserInfo(String acc) throws SQLException{
		User user_info=new User();
		user_info.setAccount(acc);
		Connection conn = this.conn;
		String sql = "select username,password,gender,age,email,major,school,picture from user_info where account = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, acc);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()){
			user_info.setUsername(rs.getString("username"));
			user_info.setAge(rs.getString("age"));
			user_info.setEmail(rs.getString("email"));
			user_info.setGender(rs.getString("gender"));
			user_info.setMajor(rs.getString("major"));
			user_info.setSchool(rs.getString("school"));
			user_info.setPassword(rs.getString("password"));
			user_info.setPicture(rs.getString("picture"));
		}
		return user_info;
	}
	
	/**
	 * @category 关闭连接
	 */
	public void connectionClose(){
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
