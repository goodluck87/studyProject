package org.util.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 日期时间工具类<BR>
 * 默认模式为yyyy-MM-dd HH:mm:ss
 * 日期和时间格式由日期和时间模式字符串指定。<BR>
 * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）:<BR>
 * <table border="1">
 * <tr><th>字母 </th><th>日期或时间元素</th><th>表示</th><th>示例</th><tr>
 * <tr><td align="center">G</td><td align="center">Era标志符</td><td align="center">Text</td><td align="center">AD</td><tr>
 * <tr><td align="center">y</td><td align="center">年</td><td align="center">Year</td><td align="center">1996; 96</td><tr>
 * <tr><td align="center">M</td><td align="center">年中的月份</td><td align="center">Month</td><td align="center">July; Jul; 07</td><tr>
 * <tr><td align="center">w</td><td align="center">年中的周数</td><td align="center">Number</td><td align="center">27</td><tr>
 * <tr><td align="center">W</td><td align="center">月份中的周数</td><td align="center">Number</td><td align="center">2</td><tr>
 * <tr><td align="center">D</td><td align="center">年中的天数 </td><td align="center">Number</td><td align="center">189</td><tr>
 * <tr><td align="center">d</td><td align="center">月份中的天数</td><td align="center">Number</td><td align="center">10</td><tr>
 * <tr><td align="center">F</td><td align="center">月份中的星期</td><td align="center">Number</td><td align="center">2</td><tr>
 * <tr><td align="center">E</td><td align="center">星期中的天数</td><td align="center">Text</td><td align="center">Tuesday; Tue</td><tr>
 * <tr><td align="center">a</td><td align="center">Am/pm 标记</td><td align="center">Text</td><td align="center">PM</td><tr>
 * <tr><td align="center">H</td><td align="center">一天中的小时数（0-23）</td><td align="center">Number</td><td align="center">0</td><tr>
 * <tr><td align="center">k</td><td align="center"> 一天中的小时数（1-24）</td><td align="center">Number</td><td align="center">24</td><tr>
 * <tr><td align="center">K</td><td align="center">am/pm 中的小时数（0-11）</td><td align="center">Number</td><td align="center">0</td><tr>
 * <tr><td align="center">h</td><td align="center">am/pm 中的小时数（1-12）</td><td align="center">Number</td><td align="center">12</td><tr>
 * <tr><td align="center">m</td><td align="center">小时中的分钟数</td><td align="center">Number</td><td align="center">30</td><tr>
 * <tr><td align="center">s</td><td align="center">分钟中的秒数</td><td align="center">Number</td><td align="center">55</td><tr>
 * <tr><td align="center">S</td><td align="center">毫秒数</td><td align="center">Number</td><td align="center">978</td><tr>
 * <tr><td align="center">z</td><td align="center">时区</td><td align="center">General time zone</td><td align="center">Pacific Standard Time; PST; GMT-08:00</td><tr>
 * <tr><td align="center">Z</td><td align="center">时区</td><td align="center">RFC 822 time zone</td><td align="center">-0800</td><tr>
 * </table>
 * @author 肖鸿
 *
 */
public final class CalendarUtil {

	/**
	 * 用于格式化的常量：yyyy-MM-dd HH:mm:ss
	 */
	public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECEND = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 用于格式化的常量：yyyy-MM-dd HH:mm
	 */
	public static final String YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";
	
	/**
	 * 用于格式化的常量：yyyy-MM-dd
	 */
	public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
	/**
	 * 用于格式化的常量： HH:mm:ss
	 */
	public static final String HOUR_MINUTE_SECEND = "HH:mm:ss";
	
	private static SimpleDateFormat dateFormat = null;
	
	private static final Calendar calendar = new GregorianCalendar();
	/**
	 * 根据传入的字符串时间，获得Date类型<BR>
	 * 此方法将根据传入的字符串时间，自动匹配与之对应的模式，无需人为输入匹配模式<BR>
	 * 不能识别输入时间日期的正确性，需人为确认<BR>
	 * 传入的时间需满足：若是纯数字，则月与日必须是两位数，即870615或19870615成功，而1987615或87615失败<BR>
	 * 必需包含年月日信息
	 * @param datetime
	 * @return 转化失败为NULL，成功为Date
	 */
	public static Date getDateFromStr(String datetime){
		Date date = null;
		String pattern = null;
		try {
			pattern = parseStringTimeToPattern(datetime);
			dateFormat = new SimpleDateFormat(pattern);
			date = dateFormat.parse(datetime);
		} catch (ParseException e) {
			System.err.println("字符串格式化时间错误！");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return date;
	}
	
	
	/**
	 * 按照自己设计的匹配模式获得时间
	 * @param datetime
	 * @param pattern
	 * @return
	 */
	public static Date getDateFromStr(String datetime, String pattern){
		Date date = null;
		dateFormat = new SimpleDateFormat(pattern);
		try {
			date = dateFormat.parse(datetime);
		} catch (ParseException e) {
			System.err.println("字符串格式化时间错误！");
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 时间Date类型转化为字符串类型<BR>
	 * 默认格式yyyy-MM-dd HH:mm:ss<BR>
	 * @param date
	 * @return
	 */
	public static String dateToSring(Date date){
		dateFormat = new SimpleDateFormat(YEAR_MONTH_DAY_HOUR_MINUTE_SECEND);
		return dateFormat.format(date);
	}
	
	/**
	 * 时间Date类型转化为字符串类型<BR>
	 * @param date
	 * @param pattern 自定义模式
	 * @return
	 */
	public static String dateToSring(Date date, String pattern){
		dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	
	/**
	 * 根据传入的时间解析出与之对应的模式(pattern)<BR>
	 * 解析失败则返回NULL<BR>
	 * @param time 传入的字符串时间
	 * @return 根据传入的时间格式而解析出的模式。解析失败返回NULL
	 * @throws Exception 
	 */
	private static String parseStringTimeToPattern(String time) throws Exception{
		StringBuffer patternStr = new StringBuffer();
		Pattern pattern = null;
		Matcher matcher = null;
		int index = 0;
		String[] patterns = {
			"yyyy","MM","dd","HH","mm","ss"	
		};
		String[] regexes = {
				"^([0-9]{1,4})(\\D*)([0-9]{1,2})(\\D*)([0-9]{1,2})(\\D*)$",
				"^([0-9]{1,4})(\\D*)([0-9]{1,2})(\\D*)([0-9]{1,2})(\\D*)([0-9]{1,2})(\\D*)([0-9]{1,2})(\\D*)([0-9]{1,2})(\\D*)$"
		};
		for(int i=0; i<regexes.length; i++){
			pattern = Pattern.compile(regexes[i]);
			matcher = pattern.matcher(time);
			if(matcher.find()){
				for(int j=1; j<=matcher.groupCount(); j++){
					if(j % 2 != 0){
						patternStr.append(patterns[index++]);
					}
					else{
						patternStr.append(matcher.group(j));
					}
				}
				break;
			}
		}
		/*
		 * 若传入的时间为纯数字类型，则需判断传入时间字符串长度与解析出的模式字符串长度是否相等
		 */
		pattern = Pattern.compile("^\\d+$");
		matcher = pattern.matcher(time);
		if(matcher.matches()){
			if(time.length() != patternStr.toString().length()){
				throw new Exception("输入时间:\"" + time + "\"不匹配模式:\"" + patternStr.toString() + "\"\n" +
									"建议使用自己匹配模式方法：getDateFromStr(String datetime, String pattern)");
			}
		}
		return patternStr.toString();
	}
	
	/**
	 * 根据当前日期，计算出当前为此年中的第几周
	 * @param date 当前日期
	 * @return 第几周
	 */
	public static int getWeekOfYearByDate(Date date){
		int weekOfYear = 0;
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setTime(date);
		calendar.get(Calendar.DAY_OF_WEEK);
		weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		return weekOfYear;
	}
	
}