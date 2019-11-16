package com.wzc.im.common.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author WANGZIC
 */
public class Imt {

	public static String appendStr(String oldStr,Object obj){
		return StrUtil.isBlank(oldStr)? String.valueOf(obj) : oldStr + "," + obj;
	}

	public static String delStr(String oldStr,Object obj){
		String res = "";
		if(oldStr != null && oldStr.trim().length()>0){
			List<String> resList = new ArrayList<>();
			for(String str:oldStr.split(",")){
				if(!str.equals(String.valueOf(obj))){
					resList.add(str);
				}
			}
			if(!resList.isEmpty()){
				StringBuilder sb = new StringBuilder();
				for(String str:resList){
					sb.append(str).append(",");
				}
				res = sb.toString().substring(0,sb.length()-1);
			}
		}
		return res;
	}
}
