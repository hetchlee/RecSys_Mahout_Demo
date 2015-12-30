package com.mine.mh.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import org.apache.commons.lang.NumberUtils;
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
@Path("/rest")
public class RecResource {
	private static Logger log = LoggerFactory.getLogger(RecResource.class);
	IRecService recService = SpringContextUtil.getBean("recService");

	@Path("/v100/poi/query/by_userId")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryByUserId(@QueryParam("userId") Long userId,
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
	 * get dzdpbypoinum
	 */
	@Path("/v100/dzdp/query/by_poinum")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryBypoinum(@QueryParam("poinum") int poinum,
			@QueryParam("token") String token) {
		QueryResultWrapper resultWrapper = new QueryResultWrapper(token);
		try {
			String[] sets = recService.getDZDPByPoi(poinum);
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
	 * get poibytype
	 */
	@Path("/v100/poitype/query/by_type")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryByType(@QueryParam("type") String type,
			@QueryParam("token") String token) {
		QueryResultWrapper resultWrapper = new QueryResultWrapper(token);
		try {
			String[] preferStrs = type.split("\\,"); 
			String[] preferArray = new String[preferStrs.length];
			for(int i=0;i<preferStrs.length;i++){
				preferArray[i]=String.valueOf(preferStrs[i]);
			} 
			String[][] sets = recService.selByType(preferArray);
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
	 * get DZDPby_prefer
	 */
	@Path("/v100/poi/query/by_prefer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryByPrefer(@QueryParam("prefer") String prefer,
			@QueryParam("token") String token) {
		QueryResultWrapper resultWrapper = new QueryResultWrapper(token);
		try {
			String[] preferStrs = prefer.split("\\,"); 
			double[] preferArray = new double[preferStrs.length];
			for(int i=0;i<preferStrs.length;i++){
				preferArray[i]=Double.valueOf(preferStrs[i]);
			} 
			String[][] sets = recService.calcuPre(preferArray);
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
	 * get POITypeBy_userid
	 */
	@Path("/v100/poitype/query/by_uid")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryPoiTypeByUserId(@QueryParam("uid") Long uid,
			@QueryParam("token") String token) {		
		QueryResultWrapper resultWrapper = new QueryResultWrapper(token);
		try {
			String[] sets = recService.getPOITypeRec(uid);
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
	@Path("/v100/jd/query")
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
