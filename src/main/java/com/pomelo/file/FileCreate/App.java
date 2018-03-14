package com.pomelo.file.FileCreate;

import com.pomelo.file.FileCreate.bean.FileCreateOrderBean;
import com.pomelo.file.FileCreate.dao.FileCreateDao;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws Exception
    {
    	FileCreateOrderBean fileCreateOrder = new FileCreateOrderBean();
    	fileCreateOrder.setRule_id(FileCreateDao.FC_RULE_ID_FOR_EXAMPLE_0);
    	fileCreateOrder.setFile_path("");
    	fileCreateOrder.setFile_name_format("FOR_EXAMPLE_%yyyymmddhh24miss.%03fileseq");
    	fileCreateOrder.setRecord_class_name("com.pomelo.file.FileCreate.bean.RecordBean");
    	fileCreateOrder.setRecord_split("\n");
    	
    	FileCreateFlow fileCreateFlow = new FileCreateFlow(fileCreateOrder);
    	fileCreateFlow.operate();
    }
}
