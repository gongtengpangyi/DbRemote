package com.frz.frame.helper;

import com.frz.frame.helper.stringcast.*;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具类
 * 
 * @author GongTengPangYi
 *
 */
public class ReflectHelper {

	/**
	 * 需要被强制转换的参数类型的键值对
	 */
	private static Map<Class<?>, StringCast> stringCastTypeMap = new HashMap<>();

	/**
	 * 初始化这个键值对
	 */
	static {
		stringCastTypeMap.put(Boolean.class, new StringCastBoolean());
		stringCastTypeMap.put(Long.class, new StringCastLong());
		stringCastTypeMap.put(Integer.class, new StringCastInteger());
		stringCastTypeMap.put(Float.class, new StringCastFloat());
		stringCastTypeMap.put(java.util.Date.class,
				StringCastDate.newInstance(StringCastDate.defaultDateFormatStr));
	}

	/**
	 * 类型转换匹配赋值
	 * 
	 * @param clazz
	 *            类
	 * @param stringCast
	 *            类型转换接口
	 */
	public static void setStringCastType(Class<?> clazz, StringCast stringCast) {
		stringCastTypeMap.put(clazz, stringCast);
	}

	public static Object newInstance(Class<?> clazz) throws ReflectException {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ReflectException("\nReflectHelper.newInstance：无法实例化" + ".\n");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ReflectException("\nReflectHelper.newInstance：参数不对" + ".\n");
		}
	}

	/**
	 * 初始化对象
	 * 
	 * @param clazz
	 *            类
	 * @param parameterTypes
	 *            构造函数参数类型
	 * @param params
	 *            构造函数参数
	 * @return 对象
	 * @throws ReflectException
	 *             反射异常
	 */
	public static Object newInstance(Class<?> clazz, Class<?>[] parameterTypes, Object... params)
			throws ReflectException {
		if (parameterTypes.length == 0) {
			return newInstance(clazz);
		} else {
			Constructor<?> constructor;
			try {
				constructor = clazz.getConstructor(parameterTypes);
				return instanceConstructor(constructor, params);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				throw new ReflectException("\nReflectHelper.findMethodByName：无此构造函数：" + ".\n");
			} catch (SecurityException e) {
				e.printStackTrace();
				throw new ReflectException("\nReflectHelper.findMethodByName：无访问权限" + ".\n");
			}
		}
	}

	/**
	 * 用构造函数初始化
	 * 
	 * @param constructor
	 *            构造方法
	 * @param params
	 *            参数
	 * @return 实例化的对象
	 * @throws ReflectException
	 *             反射过程异常
	 */
	public static Object instanceConstructor(Constructor<?> constructor, Object... params) throws ReflectException {
		try {
			return constructor.newInstance(params);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ReflectException("\nReflectHelper.instanceConstructor：无法实例化" + ".\n");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ReflectException("\nReflectHelper.instanceConstructor：参数不对" + ".\n");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new ReflectException("\nReflectHelper.instanceConstructor：无访问权限" + ".\n");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new ReflectException("\nReflectHelper.instanceConstructor：执行目标不对" + ".\n");
		}
	}

	/**
	 * 根据Method对象调用方法
	 * 
	 * @param obj
	 *            执行对象
	 * @param method
	 *            执行方法
	 * @param params
	 *            方法参数
	 * @return 方法调用的返回值
	 * @throws ReflectException
	 *             反射异常
	 */
	public static Object invokeMethod(Object obj, Method method, Object... params) throws ReflectException {
		try {
			return method.invoke(obj, params);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ReflectException("\nReflectHelper.invokeMethod：无访问权限" + ".\n");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new ReflectException("\nReflectHelper.invokeMethod：参数不对" + ".\n");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new ReflectException("\nReflectHelper.invokeMethod：执行目标不对" + ".\n");
		}
	}

	/**
	 * 更据名字来查询方法
	 * 
	 * @param clazz
	 *            类
	 * @param name
	 *            方法名
	 * @param parameterTypes
	 *            方法参数类型数组
	 * @param includeSuper
	 *            是否在父类中查找
	 * @return 方法
	 * @throws ReflectException
	 *             反射过程异常
	 */
	public static Method findMethodByName(Class<?> clazz, String name, Class<?>[] parameterTypes, boolean includeSuper)
			throws ReflectException {
		try {
			return clazz.getDeclaredMethod(name, parameterTypes);
		} catch (NoSuchMethodException e) {
			if (includeSuper && clazz.getGenericSuperclass() != null) {
				return findMethodByName(clazz.getSuperclass(), name, parameterTypes, includeSuper);
			} else {
				e.printStackTrace();
				throw new ReflectException("\nReflectHelper.findMethodByName：无此方法：" + name + ".\n");
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new ReflectException("\nReflectHelper.findMethodByName：无访问权限" + ".\n");
		}
	}

	/**
	 * 更据名字来反射执行方法
	 * 
	 * @param obj
	 *            执行的对象
	 * @param clazz
	 *            类
	 * @param name
	 *            方法名
	 * @param parameterTypes
	 *            方法参数类型数组
	 * @param includeSuper
	 *            是否在父类中查找
	 * @param params
	 *            方法参数
	 * @return 返回值
	 * @throws ReflectException
	 *             反射过程异常
	 */
	public static Object invokeMethodByName(Object obj, Class<?> clazz, String name, Class<?>[] parameterTypes,
			boolean includeSuper, Object... params) throws ReflectException {
		Method method = findMethodByName(clazz, name, parameterTypes, includeSuper);
		if (method != null) {
			return invokeMethod(obj, method, params);
		} else {
			return null;
		}
	}

	/**
	 * 更据名字来反射执行静态方法
	 * @param clazz
	 *            类
	 * @param name
	 *            方法名
	 * @param parameterTypes
	 *            方法参数类型数组
	 * @param includeSuper
	 *            是否在父类中查找
	 * @param params
	 *            方法参数
	 * @return 返回值
	 * @throws ReflectException
	 *             反射过程异常
	 */
	public static Object invokeStaticMethodByName(Class<?> clazz, String name, Class<?>[] parameterTypes,
			boolean includeSuper, Object... params) throws ReflectException {
		return invokeMethodByName(null, clazz, name, parameterTypes, includeSuper, params);
	}

	/**
	 * 获取参数类型
	 * 
	 * @param field
	 *            参数
	 * @return 类型
	 */
	public static Class<?> getParamType(Field field) {
		return field.getType();
	}

	/**
	 * 更据名字获取字段
	 * 
	 * @param clazz
	 *            类
	 * @param name
	 *            字段名
	 * @param includeSuper
	 *            是否在父类查找
	 * @return 字段
	 * @throws ReflectException
	 *             反射过程异常
	 */
	public static Field findFieldByName(Class<?> clazz, String name, boolean includeSuper) throws ReflectException {
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			if (includeSuper && clazz.getGenericSuperclass() != null) {
				return findFieldByName(clazz.getSuperclass(), name, includeSuper);
			} else {
				e.printStackTrace();
				throw new ReflectException("\nReflectHelper.getFieldByName：无此参数：" + name + ".\n");
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new ReflectException("\nReflectHelper.getFieldByName：无访问权限" + ".\n");
		}
	}

	/**
	 * 查询所有字段
	 * 
	 * @param clazz
	 *            类
	 * @param includeSuper
	 *            是否包含父类
	 * @return 字段
	 */
	public static Field[] findFields(Class<?> clazz, boolean includeSuper) {
		Field[] fields = clazz.getDeclaredFields();
		if (includeSuper && clazz.getGenericSuperclass() != null) {
			Field[] superFields = findFields(clazz.getSuperclass(), includeSuper);
			Field[] result = Arrays.copyOf(fields, fields.length + superFields.length);
			System.arraycopy(superFields, 0, result, fields.length, superFields.length);
			return result;
		} else {
			return fields;
		}
	}

	/**
	 * 查询所有方法
	 * 
	 * @param clazz
	 *            类
	 * @param includeSuperIndex
	 *            包含父类的级数(不包含父类则0)
	 * @return 方法
	 */
	public static Method[] findMethods(Class<?> clazz, int includeSuperIndex) {
		Method[] methods = clazz.getDeclaredMethods();
		if (includeSuperIndex-- > 0 && clazz.getGenericSuperclass() != null) {
			Method[] superMethods = findMethods(clazz.getSuperclass(), includeSuperIndex);
			Method[] result = Arrays.copyOf(methods, methods.length + superMethods.length);
			System.arraycopy(superMethods, 0, result, methods.length, superMethods.length);
			return result;
		} else {
			return methods;
		}
	}

	/**
	 * 根据方法名来反射执行对象方法
	 * 
	 * @param obj
	 *            对象
	 * @param clazz
	 *            类
	 * @param methodName
	 *            方法名
	 * @param paramsName
	 *            参数名
	 * @param includeSuper
	 *            是否在父类中查找
	 * @param values
	 *            字符串参数
	 * @return 执行返回值
	 * @throws ReflectException
	 *             反射过程异常
	 */
	public static Object invokeObjectMethodByName(Object obj, Class<?> clazz, String methodName, String[] paramsName,
			boolean includeSuper, String... values) throws ReflectException {
		int length = paramsName.length;
		Class<?>[] parameterTypes = new Class<?>[length];
		Object[] params = new Object[length];
		for (int i = 0; i < length; i++) {
			Field field = findFieldByName(clazz, paramsName[i], includeSuper);
			Class<?> type = getParamType(field);
			parameterTypes[i] = type;
			StringCast stringCast = stringCastTypeMap.get(type);
			params[i] = stringCast != null ? stringCast.cast(values[i]) : values[i];
		}
		return invokeMethodByName(obj, clazz, methodName, parameterTypes, includeSuper, params);
	}

	/**
	 * 强制转化String
	 * @param value
	 * @param type
	 * @return
	 */
	public static Object stringCast(String value, Class<?> type) {
		if (type.equals(String.class)) {
			return value;
		}
		StringCast stringCast = stringCastTypeMap.get(type);
		return stringCast != null ? stringCast.cast(value) : value;
	}

	/**
	 * 根据方法名来反射执行对象方法，不带class对象
	 * 
	 * @param obj
	 *            对象
	 * @param methodName
	 *            方法名
	 * @param paramsName
	 *            参数名
	 * @param includeSuper
	 *            是否在父类中查找
	 * @param values
	 *            字符串参数
	 * @return 执行返回值
	 * @throws ReflectException
	 *             反射过程异常
	 */
	public static Object invokeObjectMethodByName(Object obj, String methodName, String[] paramsName,
			boolean includeSuper, String... values) throws ReflectException {
		return invokeObjectMethodByName(obj, obj.getClass(), methodName, paramsName, includeSuper, values);
	}

	/**
	 * 字符串转换接口
	 * 
	 * @author ASUS
	 *
	 */
	public interface StringCast {

		/**
		 * 字符串转化为其他类型
		 * 
		 * @param value
		 *            元数据
		 * @return 转换后的数据
		 */
		Object cast(String value);
	}

	/**
	 * 动态执行set方法，set的类型必须是String可转化的
	 * 
	 * @param obj
	 *            对象
	 * @param fieldName
	 *            字段名
	 * @param value
	 *            参数值
	 * @param includeSuper
	 *            是否包含父类
	 * @throws ReflectException
	 *             反射过程异常
	 */
	public static void setValue(Object obj, String fieldName, String value, boolean includeSuper)
			throws ReflectException {
		String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String[] paramsName = { fieldName };
		String[] values = { value };
		invokeObjectMethodByName(obj, methodName, paramsName, includeSuper, values);
	}

	/**
	 * 动态执行String不可转化的set方法
	 * 
	 * @param obj
	 *            对象
	 * @param fieldName
	 *            字段名
	 * @param value
	 *            参数值
	 * @param includeSuper
	 *            是否包含父类
	 * @throws ReflectException
	 *             反射过程异常
	 */
	public static void setNotStrCastValue(Object obj, String fieldName, Object value, boolean includeSuper)
			throws ReflectException {
		String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Object[] values = { value };
		Field field = findFieldByName(obj.getClass(), fieldName, includeSuper);
		Class<?>[] types = { field.getType() };
		invokeMethodByName(obj, obj.getClass(), methodName, types, includeSuper, values);
	}

	/**
	 * 动态执行get方法
	 * 
	 * @param obj
	 *            对象
	 * @param fieldName
	 *            字段名
	 * @param includeSuper
	 *            是否包含父类
	 * @return get返回值
	 * @throws ReflectException
	 *             反射过程异常
	 */
	public static Object getValue(Object obj, String fieldName, boolean includeSuper) throws ReflectException {
		String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String[] paramsName = {};
		String[] values = {};
		return invokeObjectMethodByName(obj, methodName, paramsName, includeSuper, values);
	}

	/**
	 * 通过java反射，获得定义类时声明的基类(父类)的泛型参数的类型.
	 * 如类声明：public UserDao extends HibernateDao &lt;com.mass.demo.User&gt; ...,�?
	 * 调用本方法语句getSuperClassGenericType(UserDao.class,0)返回User.class.
	 *
	 * @param clazz - 子类Class
	 * @param index - 基类层级
	 * @return 基类(父类)的泛型参数的类型
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenericType(final Class clazz, final int index){
		Type genericType = clazz.getGenericSuperclass();
		if(!(genericType instanceof ParameterizedType)){
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();
		if(index>=params.length || index < 0){
			return Object.class;
		}
		if(!(params[index] instanceof Class)){
			return Object.class;
		}
		return (Class)params[index];
	}

}
