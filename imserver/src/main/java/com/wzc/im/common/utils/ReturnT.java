package com.wzc.im.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * common return
 * @author xuxueli 2015-12-4 16:32:31
 */
public class ReturnT<T> implements Serializable {
	public static final long serialVersionUID = 42L;

	public static final int SUCCESS_CODE = 200;
	public static final int FAIL_CODE = 500;
	public static final ReturnT SUCCESS = new ReturnT<>(SUCCESS_CODE,"操作成功");
	public static final ReturnT FAIL = new ReturnT<>(FAIL_CODE, "操作失败");
	
	private int code;
	private String msg;
	private T data;
	
	public ReturnT(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public ReturnT(T data) {
		this.code = SUCCESS_CODE;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public ReturnT setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public ReturnT setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return data;
	}

	public ReturnT setData(T data) {
		this.data = data;
		return this;
	}

	public String toJsonStr(){
		return JSON.toJSONStringWithDateFormat(this,"yyyy-MM-dd HH:mm:ss");
	}
}
