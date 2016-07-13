package org.util.object;


/**
 * 对象工具类
 * @author 肖鸿
 *
 */
public final class ObjectUtil {

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