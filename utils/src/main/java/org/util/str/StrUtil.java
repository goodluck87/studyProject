package org.util.str;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.util.object.ObjectUtil;

/**
 * @author 肖鸿
 *
 */
public final class StrUtil {
	/** 小数(decimal)正则表达式<BR> 小数类型: *.*或*.或.*三种类型,区分正和负 */
	public static final String DECIMAL_REGEX_STR = "^(-|\\+)?((\\d+\\.)|(\\.\\d+)|(\\d+\\.\\d+))$";
	/** 整数(integer)正则表达式<BR> 因为整数Integer无法将+*类型的字符串转换为Integer数字,因此只区分-*和*类型的整数字符串,+*将会返回FALSE*/
	public static final String INTEGER_REGEX_STR = "^-?\\d+$";
	/** 数字正则表达式 <BR> 等价于 INTEGER_REGEX_STR 与 DECIMAL_REGEX_STR的并集*/
	public static final String NUMBER_REGEX_STR = "^(-?\\d+)|((-|\\+)?((\\d+\\.)|(\\.\\d+)|(\\d+\\.\\d+)))$";
	/** 
	 * 提取数字正则表达式 <BR> 
	 * 正则表达式在用于提取时,不要使用^与$边界符号<BR>
	 * ()表示一个元素,|表示或<BR>
	 * 解释: ((-|\\+)?((\\d+\\.\\d+)|(\\d+\\.)|(\\.\\d+)))|(-?\\d+)<BR>
	 * 分为 : 小数部分与整数部分<BR>
	 * 小数部分为: ((-|\\+)?((\\d+\\.\\d+)|(\\d+\\.)|(\\.\\d+)))<BR>
	 * (-或+)?((*.*)或(*.)或(.*))其中*仅表示为数字<BR>
	 * 因为|表示或,等价于"短路与",所以小数必须先判断*.*的类型
	 * 整数部分为: (-?\\d+)<BR>
	 */
	private static final String EXTRACT_NUMBER_REGEX_STR = "((-|\\+)?((\\d+\\.\\d+)|(\\d+\\.)|(\\.\\d+)))|(-?\\d+)";
	
	/**
	 * 若str为NULL或空(空格),则用replace_with替换<BR>
	 * @param str 
	 * @param replace_with
	 * @return str为NULL或空(空格),返回replace_with;反之,返回str
	 */
	public static String nvl(String str, String replace_with){
		return isNullOrEmpty(str) ? replace_with : str;
	}
	
	/**
	 * 将字符串str转换为数字类型,如果字符串非数字类型,则返回整数0
	 * @param <T> 字符串对应数字类型
	 * @param str 
	 * @return 字符串对应数字类型
	 */
	@SuppressWarnings("unchecked")
	public static <T>T strToNum(String str){
		if(isDecimal(str)){
			return (T) Double.valueOf(str);
		}
		if(isInteger(str)){
			return (T) Integer.valueOf(str);
		}
		return (T) Integer.valueOf("0");
	}
	
	/**
	 * 判断字符串是否为小数<BR>
	 * 小数: *.*或.*或*.
	 * @param str
	 * @return 字符串为[*.*或.*或*.](其中*为数字)格式时,返回TRUE否则FALSE
	 */
	public static boolean isDecimal(String str){
		return judgeStrByRegex(str, DECIMAL_REGEX_STR);
	}
	
	/**
	 * 判断字符串是否为整数<BR>
	 * @param str
	 * @return 字符串为整数时,返回TRUE否则FALSE
	 */
	public static boolean isInteger(String str){
		return judgeStrByRegex(str, INTEGER_REGEX_STR);
	}
	
	/**
	 * 判断str是否匹配正则表达式regex
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean judgeStrByRegex(String str, String regex){
		if(isNullOrEmpty(str) || isNullOrEmpty(regex)) return false;
		return Pattern.matches(regex, str);
	}
	
	/**
	 * 判断此字符串<code>str</code>是否为<code>null</code>字符串,忽略大小写
	 * @param str
	 * @return 字符串为<code>null</code>字符串时,返回<code>true</code><BR>
	 */
	public static boolean isNullStr(String str){
		return isNullOrEmpty(str) ? false : "null".equalsIgnoreCase(str.trim());
	}

	/**
	 * 判断此字符串<code>str</code>是否为<code>NULL</code>或空（空格）
	 * @param str
	 * @return 字符串为<code>NULL</code>或空（空格）返回<code>true</code><BR>
	 */
	public static boolean isNullOrEmpty(String str){
		boolean result = false;
		if(ObjectUtil.isNull(str) || "".equals(str.trim())){
			result = true;
		}
		return result;
	}
	
	/**
	 * 当obj为null时，返回空格
	 * @param obj
	 * @return obj非字符串时，返回hashcode
	 */
	public static String toString(Object obj){
		return null==obj ? "" : obj.toString();
	}
	
	/**
	 * 从str字符串中提取出所有数字
	 * @param str
	 * @return 数字型字符串数组
	 */
	public static String[] extractNumFromStr(String str){
		if(isNullOrEmpty(str)) return null;
		Pattern pattern = Pattern.compile(EXTRACT_NUMBER_REGEX_STR);
		Matcher matcher = pattern.matcher(str);
		ArrayList<String> numStrList = new ArrayList<String>();
		while(matcher.find()){
			numStrList.add(matcher.group());
		}
		return numStrList.toArray(new String[]{});
	}
	
	/**
	 * 将str转换为json
	 * @param str
	 * @return
	 */
	public static String string2Json(String str) { 
	    StringBuilder sb = new StringBuilder(str.length()+20); 
	    sb.append('\"'); 
	    for (int i=0; i<str.length(); i++) { 
	        char c = str.charAt(i); 
	        switch (c) { 
	        case '\"': 
	            sb.append("\\\""); 
	            break; 
	        case '\\': 
	            sb.append("\\\\"); 
	            break; 
	        case '/': 
	            sb.append("\\/"); 
	            break; 
	        case '\b': 
	            sb.append("\\b"); 
	            break; 
	        case '\f': 
	            sb.append("\\f"); 
	            break; 
	        case '\n': 
	            sb.append("\\n"); 
	            break; 
	        case '\r': 
	            sb.append("\\r"); 
	            break; 
	        case '\t': 
	            sb.append("\\t"); 
	            break; 
	        default: 
	            sb.append(c); 
	        } 
	    } 
	    sb.append('\"'); 
	    return sb.toString(); 
	 } 
	
}