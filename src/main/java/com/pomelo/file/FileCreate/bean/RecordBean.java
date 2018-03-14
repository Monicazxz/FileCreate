package com.pomelo.file.FileCreate.bean;

public class RecordBean {
	private String name;
	private int age;
	
	@Override
	public String toString() {
		return name + "|" + age;
	}
}
