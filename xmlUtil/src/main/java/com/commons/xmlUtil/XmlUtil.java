package com.commons.xmlUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.util.object.ObjectUtil;
import org.util.str.StrUtil;

public class XmlUtil {
	private static final String ENCODING = "UTF-8";

	/**
	 * @param xml
	 * @param root
	 * @param clasz
	 * @return
	 */
	public static <T>T transXmlToObj(String xml, String root, Class clasz){
		if(StrUtil.isNullOrEmpty(xml))return null;
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);
			return transDocToObj(doc, root, clasz);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T>T parseXmlFileToObject(File file, Class clasz){
		if(null == file || file.isDirectory()) return null;
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
			return transDocToObj(doc, "", clasz);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T>T transDocToObj(Document doc, String root, Class clasz){
		if(null == doc) return null;
		Object obj = null;
		Object value = null;
		String xPath = null;
		Node node = null;
		try {
			obj = clasz.newInstance();
			xPath = StrUtil.toString(root) + "/" + clasz.getSimpleName();
			
			xPath = xPath.toLowerCase();
			
			Field[] fields = clasz.getDeclaredFields();
			for(Field field : fields){
				if(!ObjectUtil.isPrimitive(field.getType().getName())){//判断成员属性是否为基础数据类型
					value = transDocToObj(doc, xPath, field.getType());
					ObjectUtil.setObjFieldValueByFieldName2(obj, field.getName(), value);
				}else{
					node = doc.selectSingleNode(xPath + "/" + field.getName().toLowerCase());
					if(null != node){
						value = node.getText();
						ObjectUtil.setObjFieldValueByFieldName2(obj, field.getName(), ObjectUtil.objToPrimitive(value, field.getType()));
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return (T)obj;
	}
	
	public static String formatXml(String xml){
		if(StrUtil.isNullOrEmpty(xml))return xml;
		String formatXml = "";
		Document doc = null;
		XMLWriter writer = null;
		StringWriter strWriter = new StringWriter();
		try {
			doc = DocumentHelper.parseText(xml);
			OutputFormat format = OutputFormat.createPrettyPrint();	
			format.setEncoding(ENCODING);
			writer = new XMLWriter(strWriter, format);
			writer.write(doc);
			formatXml = strWriter.toString().replaceFirst("[\n|\r]", "");
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != writer){
					writer.close();
				}
				if(null != strWriter){
					strWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return formatXml;
	}
	
	public static void saveObjToFile(Object obj,Class<?> clasz, String path){
		String objXml = transObjToXml(obj, clasz);
		saveXml(objXml, path);
	}
	
	public static void saveXml(String xml, String path){
		if(StrUtil.isNullOrEmpty(xml) || StrUtil.isNullOrEmpty(path))return;
		Document doc = null;
		OutputStreamWriter out = null;
		FileOutputStream fOut = null; 
		XMLWriter writer = null;
		try {
			doc = DocumentHelper.parseText(formatXml(xml));
			fOut = new FileOutputStream(new File(path)); 
			out = new OutputStreamWriter(fOut, ENCODING);
			writer = new XMLWriter(out);
			writer.write(doc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(null != writer){
					writer.close();
				}
				if(null != out){
					out.close();
				}
				if(null != fOut){
					fOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 对象转换为XML
	 * @param obj 要转换的对象(可为NULL)
	 * @param clasz obj的class
	 * @return
	 */
	public static String transObjToXml(Object obj, Class<?> clasz){
		String rootNode = "ROOT";
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement(rootNode);
		try {
			transObjToDoc(root, clasz, obj);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String xml = doc.selectSingleNode("/" + rootNode + "/" + clasz.getSimpleName().toLowerCase()).asXML();
		return formatXml(xml);
	}
	
	/**
	 * @param element
	 * @param obj 要转换的对象(可为NULL)
	 * @param clasz obj的class
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private static void transObjToDoc(Element element, Class<?> clasz, Object obj) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Element tmp = null;
		Object value = null;
		
		String eleName = clasz.getSimpleName().toLowerCase();
		element = element.addElement(eleName);
		
		Field[] fields = clasz.getDeclaredFields();
		for(Field field : fields){
			if(!ObjectUtil.isPrimitive(field.getType().getName())){//判断成员属性是否为基础数据类型
				//属性为对象时,获取当前属性的value
				if(null != obj){
					value = ObjectUtil.getObjFieldValueByFieldName2(obj, field.getName());
				}
				//当前属性不为空值,进行转换xml,否则转换到此为止
				if(null != value){
					transObjToDoc(element, field.getType(), value);
				}else{
					element.addElement(field.getType().getSimpleName().toLowerCase());
				}
			}else{
				tmp = element.addElement(field.getName().toLowerCase());
				if(null != obj){
					tmp.setText(StrUtil.toString(ObjectUtil.getObjFieldValueByFieldName2(obj, field.getName())));
				}
			}
		}
	}
}
