package org.util.object;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;

import org.util.str.StrUtil;

/**
 * 对象工具类
 * @author 肖鸿
 *
 */
public final class ObjectUtil {
	
	/**
	 * 判断是否为基础数据类型(包含String)
	 * @param obj
	 * @return
	 */
	public static boolean isPrimitive(Object obj){
		if(null == obj) return false;
		Class<?> clasz = obj.getClass();
		String className = clasz.getSimpleName();
		return isPrimitive(className);
	}
	
	/**
	 * 判断是否为基础数据类型(包含String)
	 * @param obj
	 * @return
	 */
	public static boolean isPrimitive(String classType){
		if(StrUtil.isNullOrEmpty(classType)) return false;
		int index = classType.lastIndexOf(".");
		String className = index<0 ? classType.trim() : classType.substring(index).trim();
		if("int".equals(className) || "java.lang.Integer".endsWith(className) ||
		   "short".equals(className) || "java.lang.Short".endsWith(className) ||
		   "long".equals(className) || "java.lang.Long".endsWith(className) ||
		   "byte".equals(className) || "java.lang.Byte".endsWith(className) ||
		   "float".equals(className) || "java.lang.Float".endsWith(className) ||
		   "double".equals(className) || "java.lang.Double".endsWith(className) ||
		   "char".equals(className) || "java.lang.Character".endsWith(className) ||
		   "boolean".equals(className) || "java.lang.Boolean".endsWith(className) ||
		   "java.lang.String".endsWith(className) ){//把String也列为基础数据类型
			return true;
		}
		return false;
	}
	
	/**
	 * 获得对象指定字段值<BR>
	 * 对象obj属性必须实现了get方法才能使用此方法功能
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getObjFieldValueByFieldName1(Object obj, String fieldName){
		if(null == obj || StrUtil.isNullOrEmpty(fieldName)) return null;
		Class<? extends Object> clasz = obj.getClass();
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clasz);
			Method method = pd.getReadMethod();
			return method.invoke(obj);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得对象指定字段值
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getObjFieldValueByFieldName2(Object obj, String fieldName){
		if(null == obj || StrUtil.isNullOrEmpty(fieldName)) return null;
		Class<? extends Object> clasz = obj.getClass();
		Object rs = null;
		try {
			Field field = clasz.getDeclaredField(fieldName);
			field.setAccessible(true);
			rs = field.get(obj);
			field.setAccessible(false);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 赋值对象指定字段值<BR>
	 * 对象obj属性必须实现了set方法才能使用此方法功能
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static void setObjFieldValueByFieldName1(Object obj, String fieldName, Object value){
		if(null == obj || StrUtil.isNullOrEmpty(fieldName)) return;
		Class<? extends Object> clasz = obj.getClass();
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clasz);
			Method method = pd.getWriteMethod();
			method.invoke(obj, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 赋值对象指定字段值
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static void setObjFieldValueByFieldName2(Object obj, String fieldName, Object value){
		if(null == obj || StrUtil.isNullOrEmpty(fieldName)) return ;
		Class<? extends Object> clasz = obj.getClass();
		try {
			Field field = clasz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得对象Obj类Class所有注解annotation所有的值
	 * @param field
	 * @return [ {annotationSimpleName:{annotationMethodName:annotationMethodValue} }]
	 */
	public static HashMap<String, HashMap<String, Object>> getAnnotationValue(Class clasz){
		if(null == clasz) return null;
		Class<? extends Annotation> annotationType = null;
		Annotation[] annotations = clasz.getDeclaredAnnotations();
		HashMap<String, Object> annotationValue = null;
		HashMap<String, HashMap<String, Object>> tAnnotationValue = new HashMap<String, HashMap<String,Object>>();
		try {
			for(Annotation annotation : annotations){
				annotationValue = getAnnotationValue(annotation);
				annotationType = annotation.annotationType();
				tAnnotationValue.put(annotationType.getSimpleName(), annotationValue);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} 
		return tAnnotationValue;
	}
	
	/**
	 * 获得当前字段所有注解annotation所有的值
	 * @param field
	 * @return [ {annotationSimpleName:{annotationMethodName:annotationMethodValue} }]
	 */
	public static HashMap<String, HashMap<String, Object>> getAnnotationValue(Field field){
		if(null == field) return null;
		Class<? extends Annotation> annotationType = null;
		Annotation[] annotations = field.getDeclaredAnnotations();
		HashMap<String, Object> annotationValue = null;
		HashMap<String, HashMap<String, Object>> fieldAnnotationValue = new HashMap<String, HashMap<String,Object>>();
		try {
			for(Annotation annotation : annotations){
				annotationValue = getAnnotationValue(annotation);
				annotationType = annotation.annotationType();
				fieldAnnotationValue.put(annotationType.getSimpleName(), annotationValue);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} 
		return fieldAnnotationValue;
	}
	
	/**
	 * 获得注解annotation的所有字段值
	 * @param annotation
	 * @return
	 */
	public static HashMap<String, Object> getAnnotationValue(Annotation annotation){
		if(null == annotation) return null;
		HashMap<String, Object> annotationValue = new HashMap<String, Object>();
		Class<? extends Annotation> annotationType = annotation.annotationType();
		Method[] methods = annotationType.getDeclaredMethods();
		try {
			for(Method method : methods){
				annotationValue.put(method.getName(), method.invoke(annotation));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return annotationValue;
	}
	
	/**
	 * 判断此对象是否为<code>NULL</code>值
	 * @param object
	 * @return object为NULL：true；object不为NULL：false 
	 */
	public static boolean isNull(Object object){
		return (null == object);
	}
	
	/**
	 * 若object为NULL，则用replace_with替换。两参数不必为同类型<BR>
	 * 如果对象<code>object</code>为<code>NULL</code><BR>
	 * 则用对象<code>replace_with</code>替换<BR>
	 * @param <T> 泛型
	 * @param object 
	 * @param replace_with
	 * @return object为NULL：replace_with<BR>
	 * 		   object不为NULL：object<BR>
	 */
	public static <T>T nvl(T object, T replace_with){
		T t = null;
		if(isNull(object)){
			t = replace_with;
		}
		else{
			t = object;
		}
		return t;
	}
	
}