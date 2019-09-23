package com.hwagain.eagle.util;
	
/*
 * 操作返回接口类
 * 
 * @author
 * 	黎昌盛 208-05-22
 */
public class ApiResult {
	
	int status;
	String msg;
	Object data;

	public ApiResult()
	{
		status = 0;
		msg = "";
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public static ApiResult getErrorResult(String msg, Object data)
	{
		ApiResult result = new ApiResult();
		result.setStatus(0);
		result.setMsg(msg);
		result.setData(data);
		
		return result;
	}

	public static ApiResult getSuccessResult(String msg, Object data)
	{
		ApiResult result = new ApiResult();
		result.setStatus(1);
		result.setMsg(msg);
		result.setData(data);
		
		return result;
	}

}
