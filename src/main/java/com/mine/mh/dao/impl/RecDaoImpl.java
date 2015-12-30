package com.mine.mh.dao.impl;

import java.sql.*;

import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;

import com.mine.mh.dao.IRecDao;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/*
 * 用于连接MySQL获取数据
 */

public class RecDaoImpl implements IRecDao {
	String host;
	String port;
	String dbName;
	String tableName1;
	String tableName2;
	String tableName3; 
	String user1; 
	String user2;
	String user3;
	String user4; 
	String user;
	String psd;

	static Connection con = null;

	/*
	 * 验证连接数据库
	 */
	public Connection getConnection() {
		try {
			if (con == null) {
				Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动
				String conStr = "jdbc:mysql://" + getHost() + ":" + getPort()
						+ "/" + getDbName();
				System.out.println(conStr);
				con = DriverManager.getConnection(conStr, getUser(), getPsd());// 创建数据连接
			}
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return con; // 返回所建立的数据库连接
	}

	/*
	 * 查询四个用户数据,返回数据模型
	 */
	public DataModel findData() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName(getHost());
		dataSource.setUser(getUser());
		dataSource.setPassword(getPsd());
		dataSource.setDatabaseName(getDbName());
		JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource,
				getTableName1(), "Userid", "PoiNum", "Score", null);
		DataModel model = dataModel;
		return model;
	}

	/*
	 * 查询用户对景点类型偏好数据
	 */
	public DataModel findData1() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName(getHost());
		dataSource.setUser(getUser());
		dataSource.setPassword(getPsd());
		dataSource.setDatabaseName(getDbName());
		JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource,
				getTableName2(), "Userid", "PoiNum", "Score", null);
		DataModel model = dataModel;
		return model;
	}
	/*
	 * 查询大众点评类型数据
	 */
	public DataModel findData2() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName(getHost());
		dataSource.setUser(getUser());
		dataSource.setPassword(getPsd());
		dataSource.setDatabaseName(getDbName());
		JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource,
				getTableName3(), "poinum", "Type_ID", "Score", null);
		DataModel model = dataModel;
		return model;
	}
	/*
	 * 从MySql中时空向量数据提取
	 */
	public double[][] findPreference() {
		double[][] data = null;
		try {
			Statement statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery("select * from id_fortime");
			rs.last();
			int rowcount = rs.getRow();
			data = new double[rowcount][7];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				data[i][0] = rs.getDouble("Spring");
				data[i][1] = rs.getDouble("Summer");
				data[i][2] = rs.getDouble("Autumn");
				data[i][3] = rs.getDouble("Winter");
				data[i][4] = rs.getDouble("AM");
				data[i][5] = rs.getDouble("PM");
				data[i][6] = rs.getDouble("AtNight");
				i++;
			}
			// con.close();
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return data;
	}

	// 返回地点名
	public String[][] findPoiBasedId(int[] id) {

		String[][] poi = null;
		try {
			
			Statement statement = getConnection().createStatement();
			// 从id_fortime中找出poinum
			int[] poinum = new int[12];
			poinum = id;

			String sql = "select * from id_type where ";
			for (int i = 0; i < poinum.length; i++) {
				sql += " PoiNum = '" + poinum[i] + "'" + " or ";
			}

			String sqlR = sql.substring(0, sql.length() - 3);
			ResultSet rs = statement.executeQuery(sqlR);
			rs.last();
			int rowcount = rs.getRow();
			rs.beforeFirst();
			poi = new String[rowcount][3];
			int i = 0;
			while (rs.next()) {
				poi[i][0] = rs.getString("PoiName");
				poi[i][1] = rs.getString("Lat");
				poi[i][2] = rs.getString("Long");
				i++;
			}
			// con.close();
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return poi;
	}

	//根据景点类型返回景点类型名
	public String[] findPoiTypeNameByPoiTypeNum(int [] id){
		String[] poi = null;
		try {
			
			Statement statement = getConnection().createStatement();
			// 从id_fortime中找出poinum
			int[] poinum = new int[10];
			poinum = id;

			String sql = "select * from poi_type where ";
			for (int i = 0; i < poinum.length; i++) {
				sql += " ID = '" + poinum[i] + "'" + " or ";
			}

			String sqlR = sql.substring(0, sql.length() - 3);
			ResultSet rs = statement.executeQuery(sqlR);
			rs.last();
			int rowcount = rs.getRow();
			rs.beforeFirst();
			poi = new String[rowcount];
			int i = 0;
			while (rs.next()) {
				poi[i] = rs.getString("Type_User");
				i++;
			}
			// con.close();
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return poi;
	}
	
	//根据大众点评ID获取大众点评
	public String[] findDZDPTypeByID(int [] id){
		String[] poi = null;
		try {
			
			Statement statement = getConnection().createStatement();
			// 从id_fortime中找出poinum
			int[] poinum = new int[10];
			poinum = id;

			String sql = "select * from dp_id_type where ";
			for (int i = 0; i < poinum.length; i++) {
				sql += " ID = '" + poinum[i] + "'" + " or ";
			}

			String sqlR = sql.substring(0, sql.length() - 3);
			ResultSet rs = statement.executeQuery(sqlR);
			rs.last();
			int rowcount = rs.getRow();
			rs.beforeFirst();
			poi = new String[rowcount];
			int i = 0;
			while (rs.next()) {
				poi[i] = rs.getString("Business_Type");
				i++;
			}
			// con.close();
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return poi;
	}
	public int[] calPoinum(int[] id) {
		int[] poiNum = null;
		try {
			Connection con = getConnection();
			int j = 0;
			Statement statement = con.createStatement();
			// 从id_fortime中找出poinum
			String sql = "select * from id_fortime where ";
			for (int i = 0; i < id.length; i++) {
				sql += " ID = '" + id[i] + "'" + " or ";
			}
			String sqlR = sql.substring(0, sql.length() - 3);
			ResultSet rs = statement.executeQuery(sqlR);
			rs.last();
			int rowcount = rs.getRow();
			rs.beforeFirst();
			poiNum = new int[rowcount];
			int i = 0;
			while (rs.next()) {
				poiNum[i] = rs.getInt("PoiNum");
				i++;
			}
//			con.close();

		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return poiNum;
	}

	// 根据景点类型返回景点集
	public String[][] findPoiBasedType(String[] type) {
		String[][] poi = null;
		try {
			Connection con = getConnection();
			Statement statement = con.createStatement();
			// String sql =
			// String.format("select * from id_type where Type_User='%s'",type);
			// 多条件查询SQL语句
			String sql = "select * from id_type where ";
			for (int i = 0; i < type.length; i++) {
				sql += " Type_User = '" + type[i] + "'" + " or ";
			}
			String sqlR = sql.substring(0, sql.length() - 3);
			ResultSet rs = statement.executeQuery(sqlR);
			rs.last();
			int rowcount = rs.getRow();
			rs.beforeFirst();
			poi = new String[rowcount][3];
			int i = 0;
			while (rs.next()) {
				poi[i][0] = rs.getString("PoiName");
				poi[i][1] = rs.getString("Lat");
				poi[i][2] = rs.getString("Long");
				i++;
			}

			// con.close();
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return poi;
	}

	// 根据景点类型获取PoiNum
	public int[] findPoiNumBasedType(String[] type) {

		int[] poinum = null;
		try {

			Connection con = getConnection();
			Statement statement = con.createStatement();
			// 多条件查询SQL语句
			String sql = "select * from id_type where ";
			for (int i = 0; i < type.length; i++) {
				sql += " Type_User = '" + type[i] + "'" + " or ";
			}
			String sqlR = sql.substring(0, sql.length() - 3);
			ResultSet rs = statement.executeQuery(sqlR);
			rs.last();
			int rowcount = rs.getRow();
			rs.beforeFirst();
			poinum = new int[rowcount];
			int i = 0;
			while (rs.next()) {
				poinum[i] = rs.getInt("PoiNum");
				i++;
			}

			// con.close();
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return poinum;
	}

	
	//根据poinum检索大众点评商户
	public String[] findDZDPByPoi(int id){
		String[] poi = null;
		try {
			Connection con = getConnection();
			Statement statement = con.createStatement();
			String sql =String.format("select * from poitodzdp where PoiNum ='%s'",id);
			ResultSet rs = statement.executeQuery(sql);
			rs.last();
			int rowcount = rs.getRow();
			rs.beforeFirst();
			poi = new String[rowcount];
			int i = 0;
			while (rs.next()) {
				poi[i] = rs.getString("Name");
				i++;
			}

			// con.close();
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return poi;
	}
	/*
	 * 获取1846960885去过的所有景点
	 */
	public String[] getU1PoiFromFourUser(){
		String[] poinum = null ; 
		try {
			Statement statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery("select * from user1");
			rs.last();
			int rowcount = rs.getRow();
			rs.beforeFirst();
			poinum = new String[rowcount];
			int i = 0;
			while (rs.next()) {
				poinum[i] = rs.getString("PoiNum");
				i++;
			}
//			 con.close();
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		 return poinum;
	}
	/*
	 * 获取2803671224去过的所有景点
	 */
	public String[] getU2PoiFromFourUser(){
		String[] poinum=null; 
		try {
			Statement statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery("select * from user2");
			rs.last();
			int rowcount = rs.getRow();
			rs.beforeFirst();
			poinum = new String[rowcount];
			int i = 0;
			while (rs.next()) {
				poinum[i] = rs.getString("PoiNum");
				i++;
			}
			// con.close();
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		 return poinum;
	}
	/*
	 * 获取3206803303去过的景点集
	 */
	public String[] getU3PoiFromFourUser(){
		String[] poinum=null; 
		try {
			Statement statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery("select * from user3");
			rs.last();
			int rowcount = rs.getRow();
			rs.beforeFirst();
			poinum = new String[rowcount];
			int i = 0;
			while (rs.next()) {
				poinum[i] = rs.getString("PoiNum");
				i++;
			}
			 //con.close();
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		 return poinum;
	}
	/*
	 * 获取3260308611去过的景点集
	 */
	public String[] getU4PoiFromFourUser(){
		String[] poinum=null; 
		try {
			Statement statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery("select * from user4");
			rs.last();
			int rowcount = rs.getRow();
			rs.beforeFirst();
			poinum = new String[rowcount];
			int i = 0;
			while (rs.next()) {
				poinum[i] = rs.getString("PoiNum");
				i++;
			}
			// con.close();
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		 return poinum;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPsd() {
		return psd;
	}

	public void setPsd(String psd) {
		this.psd = psd;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTableName1() {
		return tableName1;
	}

	public void setTableName1(String tableName1) {
		this.tableName1 = tableName1;
	}
	public String getTableName2() {
		return tableName2;
	}

	public void setTableName2(String tableName2) {
		this.tableName2 = tableName2;
	}
	public String getTableName3() {
		return tableName3;
	}

	public void setTableName3(String tableName3) {
		this.tableName3 = tableName3;
	}

	public String getUser1() {
		return user1;
	}

	public void setUser1(String user1) {
		this.user1 = user1;
	}

	public String getUser2() {
		return user2;
	}

	public void setUser2(String user2) {
		this.user2 = user2;
	}

	public String getUser3() {
		return user3;
	}

	public void setUser3(String user3) {
		this.user3 = user3;
	}

	public String getUser4() {
		return user4;
	}

	public void setUser4(String user4) {
		this.user4 = user4;
	}



}