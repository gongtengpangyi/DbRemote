package com.frz.frame.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONUtil {
	/*
	 * 不用JSONObject来from的属性类型
	 */
	public static final String TYPES = "java.lang.String; java.lang.Integer; "
			+ "java.lang.Long; java.lang.Float; java.lang.Boolean;";
	
	/**
	 * JSON填参
	 * @param json JSONObject对象
	 * @param key 参数名
	 * @param object 参数值
	 * @return 填完参数的JSONObject对象
	 */
	public static JSONObject putObject(JSONObject json, String key, Object object) {
		try {
			if (TYPES.indexOf(object.getClass().getName()) >= 0) {
				json.put(key, object);					
			} else {
				json.put(key, JSONObject.fromObject(object));										
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 根据参数来获取一个类的JSON对象
	 * @param object 类
	 * @param selector 这是一个自定义的选择器，选择的标准如下：
	 *  "all"表示当前类的所有参数，其他情况下则按照当前参数来传，所有的参数需要有对应的get函数
	 *  参数之间用空格隔开，如"name password"则表示取到当前类getName和getPassword的值，
	 *  并且值在JSON文件中存放的键名为"name"和"password"，此外如果参数有点隔开，则表示当前参数为一个类
	 *  如"user.name"则表示取到getUser方法的返回值（User类对象）的getName方法返回值
	 *  例如 "params user.name user.core.param1 user.core.param2"
	 *  当层级"."较多的时候，为了方便起见，我们还提供了根据快捷的方式，可以在.后的参数统一打上"{"和"}"，
	 *  并且括号内的第一个字符为分隔符如"{,params,user.name,user.core.param1,user.core.param2}"
	 *  但是这个分隔符必须不能是"."、"{"、"}"、"*"和26字母,也不能是上一级目录，否则就会报错
	 *  可以把上面那个多层次的JSON匹配符简单写成"{ params user.{,name,core.{:param1:param2}}}"
	 * @return JSON文件
	 */
	public static JSONObject fromObject(Object object, String selector) {
		JSONObject json = new JSONObject();
		String cut = " ";
		try {
			if(selector.startsWith("{")&&selector.endsWith("}")) {
				cut = selector.substring(1, 2);
				if(cut.matches("[a-zA-Z]|[}]|[{]|[.]|[*]|[!]")) {
					// 分隔符不能是特定符号
					throw new Exception("wrong cut char : " + cut);
				}
				selector = selector.substring(2, selector.length()-1);
			}
			String[] strs = selector.split(cut);
			Map<String, String> parms = new HashMap<String, String> ();
			for (String str : strs) {
				if (str==null) {
					continue;
				} else if (str.equals("*")) {
					json = JSONObject.fromObject(object);
					continue;
				} else if (str.indexOf(".") < 0) {
					if(str.startsWith("!")) {
						String key = str.substring(1);
						if(json.get(key)==null) {
							continue;
						}
						json.remove(key);
					} else {						
						Object obj = ReflectHelper.getValue(object, str, true);
						json = putObject(json, str, obj);
					}
				} else {
					int i = str.indexOf(".");
					String key = str.substring(0, i);
					String value = str.substring(i+1);
					if(parms.get(key)!=null) {
						value = parms.get(key) + " " + value;
					}
					parms.put(key, value);
				}
			}
			// 循环下一层
			for (String key : parms.keySet()) {
				json.put(key, fromObject(ReflectHelper.getValue(object, key, true), parms.get(key)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return json;
	}
	
	/**
	 * 根据参数来获取一个集合的JSONArray
	 * @param list 集合
	 * @param selector 这是一个自定义的选择器，选择的标准如下：
	 *  "all"表示当前类的所有参数，其他情况下则按照当前参数来传，所有的参数需要有对应的get函数
	 *  参数之间用空格隔开，如"name password"则表示取到当前类getName和getPassword的值，
	 *  并且值在JSON文件中存放的键名为"name"和"password"，此外如果参数有点隔开，则表示当前参数为一个类
	 *  如"user.name"则表示取到getUser方法的返回值（User类对象）的getName方法返回值
	 *  例如 "params user.name user.core.param1 user.core.param2"
	 *  当层级"."较多的时候，为了方便起见，我们还提供了根据快捷的方式，可以在.后的参数统一打上"{"和"}"，
	 *  并且括号内的第一个字符为分隔符如"{,params,user.name,user.core.param1,user.core.param2}"
	 *  但是这个分隔符必须不能是"."、"{"、"}"、"*"和26字母,也不能是上一级目录，否则就会报错
	 *  可以把上面那个多层次的JSON匹配符简单写成"{ params user.{,name,core.{:param1:param2}}}"
	 * @return JSON文件
	 */
	public static JSONArray fromList(List<?> list, String selector, int type) {
		JSONArray array = new JSONArray();
		if (list != null) {
			for (Object object : list) {
				JSONObject jsonObject = changeKey(JSONUtil.fromObject(object, selector), type);
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONArray fromList(List<?> list, String selector) {
		return fromList(list, selector, TYPE_NONE);
	}


	public static final int TYPE_NONE = 0;

	public static final int TYPE_CAMEL = 1;

	public static final int TYPE_UNDERLINE = 2;

	/**
	 * 改变json的key
	 * @param jsonObject json
	 * @param type 结果key的类型
	 * @return
	 */
	public static JSONObject changeKey(JSONObject jsonObject, int type) {
		if (type == TYPE_NONE) {
			return jsonObject;
		}
		JSONObject result = new JSONObject();
		for (Object key : jsonObject.keySet()) {
			String strKey = (String)key;
			if (type == TYPE_CAMEL) {
				result.put(underlineToCamel(strKey), jsonObject.get(strKey));
			} else if (type == TYPE_UNDERLINE) {
				result.put(camelToUnderline(strKey), jsonObject.get(strKey));
			}
		}
		return result;
	}

	private static String camelToUnderline(String param){
		if (param==null||"".equals(param.trim())){
			return "";
		}
		int len=param.length();
		StringBuilder sb=new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c=param.charAt(i);
			if (Character.isUpperCase(c)){
				sb.append('_');
				sb.append(Character.toLowerCase(c));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	private static String underlineToCamel(String param){
		if (param==null||"".equals(param.trim())){
			return "";
		}
		int len=param.length();
		StringBuilder sb=new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c=param.charAt(i);
			if (c == '_'){
				if (++i<len){
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
