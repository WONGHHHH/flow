package com.ts.flow.context;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.ts.flow.enums.ResponseEnum;

/**
 * @Title: Context.java
 * @Description: 链路节点上下文
 * @author wh
 * @date 2021年6月2日  
 * @version V1.0
 */
public class DataContext extends HashMap<String, Object> implements Serializable {

	private static final long serialVersionUID = 8279268644304773184L;
	
	private boolean stop = false;
	
	public DataContext() {
		this.put("code", ResponseEnum.SUCCESS.getCode());
		this.put("msg", ResponseEnum.SUCCESS.getMsg());
	}
	
	public <T> DataContext(T t) {
		this.put("code", ResponseEnum.SUCCESS.getCode());
		this.put("msg", ResponseEnum.SUCCESS.getMsg());
		this.putAll(toMap(t));
	}
	
	public DataContext(ResponseEnum responseEnum) {
		this.put("code", responseEnum.getCode());
		this.put("msg", responseEnum.getMsg());
	}
	
	public void buildParam(ResponseEnum responseEnum) {
		this.put("code", responseEnum.getCode());
		this.put("msg", responseEnum.getMsg());
	}
	
	
	/**
	 * @return the stop
	 */
	public boolean isStop() {
		return stop;
	}

	/**
	 * @param stop the stop to set
	 */
	public void stop(boolean stop) {
		this.stop = stop;
	}

	private static <T> Map<String,Object> toMap(T t){
		Map<String,Object> map=new HashMap<String,Object>();
		Field[] fields = t.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			String name = field.getName();
			try {
				map.put(name, field.get(t));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}

