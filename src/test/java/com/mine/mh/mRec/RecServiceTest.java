package com.mine.mh.mRec;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ibm.icu.util.BytesTrie.Result;
import com.mine.mh.dao.IRecDao;
import com.mine.mh.service.IRecService;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class RecServiceTest {

	@Autowired
	IRecService recService;

	@Autowired
	IRecDao recDao;

	@Test
	public void findPoiBasedId() {
		int[] ids = new int[] { 32 };
		String[][] result = recDao.findPoiBasedId(ids);
		System.out.println(result);
	}

	@Test
	public void getPOIRec() {
		System.out.println("基于物品推荐结果：");
		long userId = 3217684423L;
		recService.getPOIRec(userId);
	}

	@Test
	public void cf_Aprior() {
		System.out.println("关联规则结果：");

		int[] result = recService.cf_Aprior();
		for (int i = 0; i < result.length; i++) {
		System.out.println(result[i]);	
		}
			 
		 
	}
}
