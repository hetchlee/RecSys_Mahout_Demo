package com.mine.mh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mine.mh.dao.IRecDao;
import com.mine.mh.service.IAprioriToolService;
import com.mine.mh.service.IRecService;

/**
 * 
 * @author 李振@mysnspace
 * 
 */

/*
 * 基于物品的推荐
 */
public class RecServiceImpl implements IRecService {
	IAprioriToolService aprioriToolService;
	
	IRecDao recDao;
	int[] poinumsel = new int[20];
	static DataModel model = null;

	// 根据用户ID，推荐景点
	public String[][] getPOIRec(long userId) {
		String[][] sets = new String[20][3];
		try {
			if (model == null) {
				model = recDao.findData();
			}
			UserSimilarity usersimilarity = new CityBlockSimilarity(model);
			UserNeighborhood neighborhood = new NearestNUserNeighborhood(10,
					usersimilarity, model);
			Recommender recommender = new CachingRecommender(
					new GenericUserBasedRecommender(model, neighborhood,
							usersimilarity));
			List<RecommendedItem> recommendations = recommender.recommend(
					userId, 10);
			// 存储景点ID
			int[] cfpoi = new int[12];
			for (int i = 0; i < recommendations.size(); i++) {
				cfpoi[i] = (int) recommendations.get(i).getItemID();
			}
			cfpoi[10] = cf_Aprior()[0];
			cfpoi[11] = cf_Aprior()[1];
			// 根据景点ID返回景点PoiName,Lat,Long
			sets = recDao.findPoiBasedId(cfpoi);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sets;
	}

	// 协同过滤景点和关联规则景点合并
	public int[] cf_Aprior() { 
		return aprioriToolService.printAttachRule(0.7);
	}

	// 根据用户景点偏好，推荐景点类型
	public String[] getPOITypeRec(long userId) {
		String[] sets = new String[20];
		try {
			if (model == null) {
				model = recDao.findData1();
			}
			ItemSimilarity itemsimilarity = new UncenteredCosineSimilarity(
					model);
			Recommender recommender = new GenericItemBasedRecommender(model,
					itemsimilarity);
			List<RecommendedItem> recommendations = recommender.recommend(
					userId, 3);
			// 存储景点ID
			int[] cfpoi = new int[10];
			for (int i = 0; i < recommendations.size(); i++) {
				cfpoi[i] = (int) recommendations.get(i).getItemID();
			}
			// 根据景点ID返回景点PoiName,Lat,Long
			sets = recDao.findPoiTypeNameByPoiTypeNum(cfpoi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sets;
	}

	/*
	 * 根据poinum获取大众点评
	 */
	public String[] getDZByTypeRec(int poinum) {
		String[] sets = new String[20];
		try {
			if (model == null) {
				model = recDao.findData2();
			}
			ItemSimilarity itemsimilarity = new UncenteredCosineSimilarity(
					model);
			Recommender recommender = new CachingRecommender(
					new GenericItemBasedRecommender(model, itemsimilarity));
			List<RecommendedItem> recommendations = recommender.recommend(
					poinum, 2);
			// 存储景点ID
			int[] cfpoi = new int[10];
			for (int i = 0; i < recommendations.size(); i++) {
				cfpoi[i] = (int) recommendations.get(i).getItemID();
			}
			sets = recDao.findDZDPTypeByID(cfpoi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sets;
	}

	public double distance(double[] p1, double[] p2) {
		double dotProduct = 0.0;
		double lengthSquaredp1 = 0.0;
		double lengthSquaredp2 = 0.0;
		for (int i = 0; i < p1.length; i++) {
			lengthSquaredp1 += p1[i] * p1[i];
			lengthSquaredp2 += p2[i] * p2[i];
			dotProduct += p1[i] * p2[i];
		}
		double denominator = Math.sqrt(lengthSquaredp1)
				* Math.sqrt(lengthSquaredp2);
		// 处理浮点数异常
		if (denominator < dotProduct) {
			denominator = dotProduct;
		}
		// 处理零向量
		if (denominator == 0 && dotProduct == 0) {
			return 0;
		}
		// 结果小的相似度高，结果大的相似度底
		return 1.0 - dotProduct / denominator;
	}

	/*
	 * 计算时空偏好
	 */
	public String[][] calcuPre(double[] spPrefence) {
		// 基于用户时空偏好
		double[][] data = new double[200][7];
		String[][] sets = new String[20][3];
		try {
			data = recDao.findPreference();
			// 用户时空偏好向量，前端获取偏好数值，转换为偏好向量
			// double[] p2 = { 0, 1, 0, 0, 1, 0, 0 };
			List lst = new ArrayList();
			for (int i = 0; i < data.length; i++) {
				lst.add(distance(data[i], spPrefence));
			}
			// list转一维数组
			int length = lst.size();
			double[] topTen = new double[length];
			for (int i = 0; i < lst.size(); i++) {
				topTen[i] = (Double) lst.get(i);
			}
			// 选择排序，计算余弦相似度最小的10个数
			int i, j, min;
			double temp;
			for (i = 0; i < topTen.length - 1; i++) {
				min = i;
				for (j = i + 1; j < topTen.length; j++)
					if (topTen[j] < topTen[min])
						min = j;
				temp = topTen[i];
				topTen[i] = topTen[min];
				topTen[min] = temp;
			}
			// 输出余弦较小的前十个地点
			System.out.println("推荐景点：");
			for (int n = 0; n < 20; n++) {
				poinumsel[n] = lst.indexOf(topTen[n]);
			}
			sets = recDao.findPoiBasedId(poinumsel);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sets;
	}

	/*
	 * 根据景点类型获取景点信息集合(包括景点名和经纬度)
	 */
	public String[][] selByType(String[] poi) {
		int rows = recDao.findPoiBasedType(poi).length;
		String[][] sets = new String[rows][3];
		try {
			sets = recDao.findPoiBasedType(poi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sets;
	}

	/*
	 * 根据poinum获取大众点评商户
	 */

	public String[] getDZDPByPoi(int poinum) {
		String[] dzdp = new String[5];
		try {
			dzdp = recDao.findDZDPByPoi(poinum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dzdp;
	}

	public IRecDao getRecDao() {
		return recDao;
	}

	public void setRecDao(IRecDao recDao) {
		this.recDao = recDao;
	}

	public IAprioriToolService getAprioriToolService() {
		return aprioriToolService;
	}

	public void setAprioriToolService(IAprioriToolService aprioriToolService) {
		this.aprioriToolService = aprioriToolService;
	}

}
