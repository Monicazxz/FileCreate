package com.pomelo.file.FileCreate.bean;


/**
 * 描述:文件生成
 * @author zxz
 * @version 1.0.0
 */

public class FileCreateOrderBean {
	private int rule_id;
	private String file_path;
	private String file_name_format;
	private String header_format;
	private int header_length;
	private String record_class_name;
	private String record_split;
	private String footer_format;
	private String suffix;
	private String check_file_name_format;
	private String check_file_format;
	private String check_suffix;
	private String sql_params;
	private boolean upload_null_file;
	
	public boolean isUpload_null_file() {
		return upload_null_file;
	}

	public void setUpload_null_file(boolean upload_null_file) {
		this.upload_null_file = upload_null_file;
	}

	public int getRule_id() {
		return rule_id;
	}

	public void setRule_id(int rule_id) {
		this.rule_id = rule_id;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_name_format() {
		return file_name_format;
	}

	public void setFile_name_format(String file_name_format) {
		this.file_name_format = file_name_format;
	}

	public String getHeader_format() {
		return header_format;
	}

	public void setHeader_format(String header_format) {
		this.header_format = header_format;
	}

	public int getHeader_length() {
		return header_length;
	}

	public void setHeader_length(int header_length) {
		this.header_length = header_length;
	}

	public String getRecord_class_name() {
		return record_class_name;
	}

	public void setRecord_class_name(String record_class_name) {
		this.record_class_name = record_class_name;
	}

	public String getRecord_split() {
		return record_split;
	}

	public void setRecord_split(String record_split) {
		this.record_split = record_split;
	}

	public String getFooter_format() {
		return footer_format;
	}

	public void setFooter_format(String footer_format) {
		this.footer_format = footer_format;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public String getCheck_file_name_format() {
		return check_file_name_format;
	}

	public void setCheck_file_name_format(String check_file_name_format) {
		this.check_file_name_format = check_file_name_format;
	}
	
	public String getCheck_file_format() {
		return check_file_format;
	}

	public void setCheck_file_format(String check_file_format) {
		this.check_file_format = check_file_format;
	}
	
	public String getCheck_suffix() {
		return check_suffix;
	}

	public void setCheck_suffix(String check_suffix) {
		this.check_suffix = check_suffix;
	}

	public String getSql_params() {
		return sql_params;
	}

	public void setSql_params(String sql_params) {
		this.sql_params = sql_params;
	}

}
