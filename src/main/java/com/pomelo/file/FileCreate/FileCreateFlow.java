package com.pomelo.file.FileCreate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pomelo.file.FileCreate.bean.FileCreateOrderBean;
import com.pomelo.file.FileCreate.dao.FileCreateDao;
import com.pomelo.file.FileCreate.handle.FileCreateHandle;
import com.pomelo.file.FileCreate.util.Md5;
import com.pomelo.file.FileCreate.util.Zip;

/**
 * @author zxz
 *
 */
public class FileCreateFlow {
	FileCreateHandle handle;
	FileWriter fw;
	FileWriter check_fw;
	
	private int ruleId;
	private String filePath;
	private String fileNameFormat;
	private String headerFormat;
	private int headerLength;
	private String recordClassName;
	private String recordSplit;
	private String footerFormat;
	private String suffix;
	private String checkFileNameFormat;
	private String checkFileFormat;
	private String checkSuffix;
	private String sqlParams;
	private boolean upload_null_file;
	
	FileCreateDao fileCreateDao;
	
	public FileCreateFlow(FileCreateOrderBean fileCreateOrder) {
		handle = new FileCreateHandle();
		
		ruleId = fileCreateOrder.getRule_id();
		filePath = fileCreateOrder.getFile_path();
		fileNameFormat = fileCreateOrder.getFile_name_format();
		headerFormat = fileCreateOrder.getHeader_format();
		headerLength = fileCreateOrder.getHeader_length();
		recordClassName = fileCreateOrder.getRecord_class_name();
		recordSplit = fileCreateOrder.getRecord_split();
		footerFormat = fileCreateOrder.getFooter_format();
		suffix = fileCreateOrder.getSuffix();
		checkFileNameFormat = fileCreateOrder.getCheck_file_name_format();
		checkFileFormat = fileCreateOrder.getCheck_file_format();
		checkSuffix = fileCreateOrder.getCheck_suffix();
		sqlParams = fileCreateOrder.getSql_params();
		upload_null_file = fileCreateOrder.isUpload_null_file();
		fileCreateDao = new FileCreateDao(ruleId, recordClassName, sqlParams);
	}
	
	public void operate() throws Exception {
		
		prepareAllFileData();
		
		while(prepareFileData()) {
			
			setFileUtil();
			
			createFile();
			
			preemptHeader();
			
			write();
			
			assignFooter();
			
			assignHeader();
			
			suffix();		
			
			createCheckFile();
			
			assignCheckFileFormat();
			
			checkSuffix();
			
		}
		
		recoverAllFileData();
	}
	
	private void prepareAllFileData() {
		handle.setAllRecordNum(fileCreateDao.lockPartition());
	}
	
	private boolean prepareFileData() {
		return fileCreateDao.lockFile() != 0 || handle.getFileSeq() == 0 && upload_null_file;
	}
	
	private void setFileUtil() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		handle.addFileSeq();
		handle.setRecordNum(0);
		handle.resetCreateDate();
		handle.resetLastDay();
	    handle.resetLastMonth();
		handle.resetWholeFileName(filePath, fileNameFormat);
		handle.resetCheckFileName(filePath, checkFileNameFormat);
		handle.resetFileName(fileNameFormat);
	}
	
	private void createFile() throws IOException {
		fw = new FileWriter(handle.getWholeFileName());
	}
	
	private void preemptHeader() throws IOException {
		if(headerFormat != null && !headerFormat.isEmpty())
			fw.write(String.format("%" + headerLength + "s", "") + recordSplit);
	}
	
	private void write() throws Exception {
		while(fileCreateDao.lockPiece() != 0) {	
			@SuppressWarnings("rawtypes")
			List pieces = fileCreateDao.fetchPiece();
			System.out.println("pieces:" + pieces);
			if(pieces == null) {
				fw.close();
				throw new Exception("获取数据库数据失败");
			}
			
			handle.addRecordNum(pieces.size());
			
			fw.write(StringUtils.join(pieces.iterator(), recordSplit));
			fw.write(recordSplit);
			fw.flush();
			
			fileCreateDao.unlockPiece();
		}
	}
	
	private void assignFooter() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		fw.write(handle.convert(footerFormat));
		fw.close();
	}
	
	private void assignHeader() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		if(headerFormat != null && !headerFormat.isEmpty()) {
			RandomAccessFile raf = new RandomAccessFile(handle.getWholeFileName(), "rw");
			raf.write(handle.convert(headerFormat).getBytes());
			raf.close();
		}
	}
	
	private void recoverAllFileData() {
		fileCreateDao.releasePartition();
	}
	
	private void suffix() throws IOException {
		
		handle.resetOrigFileSize();
		handle.resetOrigFileName();
		handle.setOrigFileMd5(Md5.getMd5ByFile(new File(handle.getWholeFileName())));
		
		if(suffix == null || suffix.isEmpty())
			return ;
		
		String[] suffixs = suffix.split(";");
		
		for(String s : suffixs) {
			switch(s) {
			case "gz":
				File file = Zip.gCompress(new File(handle.getWholeFileName()), true);
				handle.setWholeFileName(file.getAbsolutePath());
				handle.resetCompressFileName(".gz");
				break;
			default:
				break;
			}
		}
		
		handle.setSuffixMd5(Md5.getMd5ByFile(new File(handle.getWholeFileName())));
		
		handle.resetFileSize();
	}
	
	private void createCheckFile() throws IOException {
		if(checkFileNameFormat == null || checkFileNameFormat.isEmpty())
			return ;
		
		check_fw = new FileWriter(handle.getCheckFileName());
		
	}
	
	private void assignCheckFileFormat() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		if(checkFileFormat != null && !checkFileFormat.isEmpty()){
			
			RandomAccessFile check_raf = new RandomAccessFile(handle.getCheckFileName(), "rw");
			check_raf.write(handle.convert(checkFileFormat).getBytes());
			check_raf.close();			
		}		
	}
	
	private void checkSuffix() throws IOException {
		
		if(checkSuffix == null || checkSuffix.isEmpty())
			return ;	
		String[] checkSuffixs = checkSuffix.split(";");
		
		for(String s : checkSuffixs) {
			switch(s) {
			case "gz":
				Zip.gCompress(new File(handle.getCheckFileName()), true);
				handle.resetCompressCheckFileName(".gz");
				break;
			default:
				break;
			}
		}
	}
}
