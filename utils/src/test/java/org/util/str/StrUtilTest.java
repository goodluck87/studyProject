package org.util.str;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class StrUtilTest {

	@Test
	public void testReg(){
		String regex = "[^\\|]+";
		CharSequence input = "a|b|c|d|ee|f|g|1|2|3|4|5|6|ee|8|9|||ewq||||";
		Pattern pattern = Pattern.compile(regex);
		String[] strs = pattern.split(input,-1);
		int j=1;
		for(String str : strs){
			System.out.println(j+"======"+str);
			j++;
		}
		Matcher matcher = pattern.matcher(input);
		System.out.println("====================================================="+matcher.groupCount());
		int i=1;
		
		while(matcher.find()){
			System.out.println(i+"====="+matcher.group()+"-----");
			i++;
		}
		System.out.println(matcher.matches());
	}
	
	@Test
	public void testJudgeStrByRegex(){
		System.out.println(StrUtil.judgeStrByRegex("+3.2", StrUtil.NUMBER_REGEX_STR));
	}
	
	@Test
	public void testExtractNumFromStr(){
		String[] ar = StrUtil.extractNumFromStr("-2.4你好3a4ds+55f6的fgfdg6打7的费7爱d7f8a8f+9.9的9gf0sf-发6f4gs3g的6gf9sd发的3放得443开gs3.2fg打3.4w法");
		for(String str : ar){
			System.out.println(str);
		}
	}
}
