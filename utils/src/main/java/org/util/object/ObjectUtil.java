package org.util.object;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 对象工具类
 * @author 肖鸿
 *
 */
public final class ObjectUtil {
	
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