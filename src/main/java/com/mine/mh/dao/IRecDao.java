package com.mine.mh.dao;

import java.sql.Connection;

import org.apache.mahout.cf.taste.model.DataModel;

/**
 * 
 * @author 李振@mysnspace
 * 
 */

/*
 * 基于物品的推荐
 */
public interface IRecDao {

	/*
	 * 验证连接数据库
	 */
	public Connection getConnection();

	/*
	 * 查询数据,返回数据模型
	 */
	public DataModel findData();
	/*
	 * 查询用户对景点类型偏好数据
	 */
	public DataModel findData1();
	/*
	 * 查询大众点评类型数据
	 */
	public DataModel findData2();
	/*
	 * 从MySql中时空向量数据提取
	 */
	public double[][] findPreference();

	// 返回地点名
	public String[][] findPoiBasedId(int[] id);
	
	//根据景点类型编号

	public String[] findPoiTypeNameByPoiTypeNum(int [] id);
	
	public int[] calPoinum(int[] id);

	// 根据景点类型返回景点集
	public String[][] findPoiBasedType(String[] type);

	// 根据景点类型获取PoiNum
	public int[] findPoiNumBasedType(String[] type);
	//根据大众点评ID获取大众点评类型
	public String[] findDZDPTypeByID(int [] id);
	//根据poinum检索大众点评商户
	public String[] findDZDPByPoi(int id);
	//用户一
	public String[] getU1PoiFromFourUser();
	//用户二
	public String[] getU2PoiFromFourUser();
	//用户三
	public String[] getU3PoiFromFourUser();
	//用户四
	public String[] getU4PoiFromFourUser();
	

}
