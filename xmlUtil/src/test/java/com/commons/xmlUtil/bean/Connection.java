package com.commons.xmlUtil.bean;

public class Connection {

	private String name;
	private String server;
	private String type;
	private String access;
	private String database;
	private String port;
	private String username;
	private String password;
	private String servername;
	private String data_tablespace;
	private String index_tablespace;
	
	private Node node = new Node();
	
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
	}
	public String getData_tablespace() {
		return data_tablespace;
	}
	public void setData_tablespace(String data_tablespace) {
		this.data_tablespace = data_tablespace;
	}
	public String getIndex_tablespace() {
		return index_tablespace;
	}
	public void setIndex_tablespace(String index_tablespace) {
		this.index_tablespace = index_tablespace;
	}
	
}
