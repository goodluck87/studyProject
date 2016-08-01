package com.commons.xmlUtil;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.junit.Test;

import com.commons.xmlUtil.bean.Connection;

public class TestXmlUtil {

	@Test
	public void testObjectToXml(){
		Connection conn = new Connection();
		conn.setPort("3306");
		conn.setServer("127.0.0.1");
		conn.setName("mysql.yhact");
		conn.setAccess("Native");
		conn.setDatabase("world");
		conn.setUsername("admin");
		conn.setPassword("admin");
		System.out.println(XmlUtil.transObjToXml(conn, Connection.class));
	}
	
	@Test
	public void xmlToObj(){
		Connection conn = new Connection();
		conn.setPort("3306");
		conn.setServer("127.0.0.1");
		conn.setName("mysql.yhact");
		conn.setAccess("Native");
		conn.setDatabase("world");
		conn.setUsername("admin");
		conn.setPassword("admin");
		
		String xml = XmlUtil.transObjToXml(conn, Connection.class);
		System.out.println(xml);
		Connection cn = XmlUtil.transXmlToObj(xml, "/", Connection.class);
				
		System.out.println(cn.getNode().getName());
	}
	
	@Test
	public void testGetXmlNode() throws DocumentException{
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><connection> <name>mysql.yhact</name>  <server>127.0.0.1</server>  <type/>  <access>native</access>  <database>world</database>  <port>3306</port>  <username>admin</username>  <password>admin</password>  <servername/>  <data_tablespace/>  <node/></connection>";
		Document doc = DocumentHelper.parseText(xml);
		System.out.println(doc.selectSingleNode("//connection/node").getText());
		Connection conn = XmlUtil.transXmlToObj(xml, "", Connection.class);
		System.out.println(conn.getDatabase());
	}
}
