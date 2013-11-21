package com.photon.phresco.framework.model;

import java.util.List;

public class LiquibaseUpdateInfo {
	private String dbVersion;
	private String fileName;
	private String author;
	private List<String> sqlStatements;
	private List<String> rollbackStatements;
	public List<String> getSqlStatements() {
		return sqlStatements;
	}
	public void setSqlStatements(List<String> sqlStatements) {
		this.sqlStatements = sqlStatements;
	}
	public List<String> getRollbackStatements() {
		return rollbackStatements;
	}
	public void setRollbackStatements(List<String> rollbackStatements) {
		this.rollbackStatements = rollbackStatements;
	}
	public String getDbVersion() {
		return dbVersion;
	}
	public void setDbVersion(String dbVersion) {
		this.dbVersion = dbVersion;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}
