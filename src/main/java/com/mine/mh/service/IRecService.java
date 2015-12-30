package com.mine.mh.service;

import java.util.List;

/**
 * 
 * @author 李振@mysnspace
 * 
 */

/*
 * 基于物品的推荐
 */
public interface IRecService {

	/**
	 * 用户 POI推荐
	 * 
	 * @param userId
	 *            用户编号
	 * @return String[][]( [20][3])
	 */
	String[][] getPOIRec(long userId);
	/**
	 * 根据景点类型检索景点集
	 * @param poi
	 * @return  String[rows][3]
	 */
	String[][] selByType(String[] poi);

	/**
	 * 根据时空偏好检索景点集
	 * 
	 */
	String[][] calcuPre(double[] prefers);
	
	/**
	 * 根据用户对景点类型偏好推荐
	 */
	String [] getPOITypeRec(long userId);
	/**
	 * 根据poinum查询大众点评类型
	 * @param poinum
	 * @return
	 */
	String [] getDZByTypeRec(int poinum);
	/**
	 * 根据用户输入Poinum查询大众点评
	 */
	String [] getDZDPByPoi(int poinum);
	/*
	 * 关联规则返回的点
	 * 
	 */
	int[] cf_Aprior();
}
