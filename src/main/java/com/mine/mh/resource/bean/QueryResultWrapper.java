package com.mine.mh.resource.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 查询结果封装对象
 * 
 * @sample {
 *         "token": "<请求标识:string>",
 *         "message": "<返回信息: Message >",
 *         "result": {
 *         "items": [
 *         {
 *         "xzqh": "<行政区划:District>"
 *         }
 *         ]
 *         }
 *         }
 * @author geosmart
 */
@XmlRootElement
public class QueryResultWrapper implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	 * 请求标识
	 */
	private String token;
	/**
	 * 返回消息
	 */
	private ResponseMessage message;
	/**
	 * 返回数据
	 */
	private Object result;
	
	public QueryResultWrapper(String token)
	{
		super();
		this.token = token;
		this.result = null;
	}
	 
	
	public String getToken()
	{
		return token;
	}
	
	public void setToken(String token)
	{
		this.token = token;
	}
	
	public ResponseMessage getMessage()
	{
		return message;
	}
	
	public void setMessage(ResponseMessage message)
	{
		this.message = message;
	}
	
	public Object getResult()
	{
		return result;
	}
	
	public void setResult(Object result)
	{
		this.result = result;
	}

	public void setResultWithMap(List<?> items)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("items", items);
		result = resultMap;
	}
}
