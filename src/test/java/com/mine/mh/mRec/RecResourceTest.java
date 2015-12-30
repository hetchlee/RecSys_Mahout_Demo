package com.mine.mh.mRec;

import java.util.UUID;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mine.mh.resource.RecResource;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class RecResourceTest { 
	RecResource resource=null;
	String token = UUID.randomUUID().toString();

	@Before
	public void beforeTest() {
		resource = new RecResource();
		System.out.println("測試開始.....");
	}
	
	@Test
	public void queryBypoinum() {
		int  poinum = 5;
		Response result = resource.queryBypoinum(poinum, token);
		System.out.println(result.getEntity());
	}

	
	
	

}
