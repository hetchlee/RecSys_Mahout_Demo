package com.mine.mh.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mine.mh.resource.bean.PostData;
import com.mine.mh.resource.bean.PostParamWrapper;
import com.mine.mh.resource.bean.QueryResultWrapper;
import com.mine.mh.resource.bean.ResponseMessage;
import com.mine.mh.service.IRecService;
import com.mine.mh.util.SpringContextUtil;

/***
 * 推荐算法检索服务-rest
 * 
 * @author lizhen
 */
@Path("/rest/test")
public class TestResource {
	private static Logger log = LoggerFactory.getLogger(TestResource.class);
	IRecService recService = SpringContextUtil.getBean("recService");

	/**
	 * get userid
	 */
	@Path("/v100/get")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response identify(@QueryParam("userId") Long userId,
			@QueryParam("token") String token) {
		QueryResultWrapper resultWrapper = new QueryResultWrapper(token);
		try {
			// TODO 调用服务实现
			// recService.baseItemCf(model);
			String[][] sets = recService.getPOIRec(userId);
			resultWrapper.setResult(sets);
			ResponseMessage message = new ResponseMessage(Response.Status.OK);
			resultWrapper.setMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseMessage message = new ResponseMessage(
					Response.Status.INTERNAL_SERVER_ERROR);
			resultWrapper.setMessage(message);
		}
		return Response.ok().entity(resultWrapper).build();
	}
 
	/**
	 * post示例
	 * 
	 */
	@Path("/v100/post")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response querySucOrParentAddress(PostParamWrapper paramWrapper) {
		QueryResultWrapper resultWrapper = new QueryResultWrapper(
				paramWrapper.getToken());
		PostData postData = paramWrapper.getData();
		try {
			Map<String, Object> paramMap = postData.getParam();
			Map<String, Object> filterMap = postData.getFilter();
			List<Map<String, Object>> itemMapList = (List<Map<String, Object>>) (List<?>) postData
					.getItems();
			System.out.println(itemMapList);
			// TODO service实现
			Map<String, Object> entity1 = new HashMap<String, Object>();
			entity1.put("sceneryName", "001");
			entity1.put("longitude", 118);
			entity1.put("latitude", 32);
			resultWrapper.setResult(entity1);
			ResponseMessage message = new ResponseMessage(Response.Status.OK);
			resultWrapper.setMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseMessage message = new ResponseMessage(
					Response.Status.INTERNAL_SERVER_ERROR);
			resultWrapper.setMessage(message);
		}
		return Response.ok().entity(resultWrapper).build();
	}

}
