package com.pomelo.file.FileCreate.handle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.io.File;

/**
* @ClassName: FileCreateHandle
* @Description: 文件创建句柄类
* @author newland 
*/
public class FileCreateHandle {
	public static final Map<String, Method> FORMAT_RELA = new TreeMap<String, Method>(
			new Comparator<String>() {
				public int compare(String obj1, String obj2) {
					return obj2.compareTo(obj1);
				}
			});
	
	static {
		try {
			FORMAT_RELA.put("%filename", FileCreateHandle.class.getDeclaredMethod("getFileName"));
			FORMAT_RELA.put("%filesize", FileCreateHandle.class.getDeclaredMethod("getFileSize"));
			FORMAT_RELA.put("%origfilemd5", FileCreateHandle.class.getDeclaredMethod("getOrigFileMd5"));
			FORMAT_RELA.put("%origfilename", FileCreateHandle.class.getDeclaredMethod("getOrigFileName"));
			FORMAT_RELA.put("%origfilesize", FileCreateHandle.class.getDeclaredMethod("getOrigFileSize"));
			FORMAT_RELA.put("%lstyyyymm", FileCreateHandle.class.getDeclaredMethod("getLastMonthYYYYMM"));
			FORMAT_RELA.put("%lstyyyymmdd", FileCreateHandle.class.getDeclaredMethod("getLastDayYYYYMMDD"));
			FORMAT_RELA.put("%recordsum", FileCreateHandle.class.getDeclaredMethod("getRecordSum"));
			FORMAT_RELA.put("%suffixmd5", FileCreateHandle.class.getDeclaredMethod("getSuffixMd5"));
			FORMAT_RELA.put("%yyyymm", FileCreateHandle.class.getDeclaredMethod("getCreateDateYYYYMM"));
			FORMAT_RELA.put("%yyyymmdd", FileCreateHandle.class.getDeclaredMethod("getCreateDateYYYYMMDD"));
			FORMAT_RELA.put("%yyyymmddhh24miss", FileCreateHandle.class.getDeclaredMethod("getCreateDate"));
			FORMAT_RELA.put("%010recordsum", FileCreateHandle.class.getDeclaredMethod("get10RecordSum"));			
			FORMAT_RELA.put("%03fileseq", FileCreateHandle.class.getDeclaredMethod("get3FileSeq"));			
			FORMAT_RELA.put("%03filesum", FileCreateHandle.class.getDeclaredMethod("get3FileSum"));			
			FORMAT_RELA.put("%09recordsum", FileCreateHandle.class.getDeclaredMethod("get9RecordSum"));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int fileSeq;
	private int recordNum;
	private String createDate;
	private String lastDay;
	private String lastMonth;
	private String wholeFileName;
	private String checkFileName ;
	private int allRecordNum;
	private int fileNum;
	private String origFileName ;
	private long origFileSize;
	private long fileSize;
	private String fileName;
	private String suffixMd5;
	private String origFileMd5 ;

	public int getFileSeq() {
		return fileSeq;
	}

	public void setFileSeq(int fileSeq) {
		this.fileSeq = fileSeq;
	}

	public void addFileSeq() {
		this.fileSeq++;
	}

	public int getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}

	public void addRecordNum(int recordNum) {
		this.recordNum += recordNum;
	}
	
	public String getCreateDateYYYYMM() {
		return createDate.substring(0, 6);
	}
	
	public String getCreateDateYYYYMMDD() {
		return createDate.substring(0, 8);
	}
	
	public String getLastMonthYYYYMM() {
		return lastMonth.substring(0, 6);
	}
	
	public String getLastDayYYYYMMDD() {
		return lastDay.substring(0, 8);
	}
	
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public void resetCreateDate() {
		Date nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		this.createDate = simpleDateFormat.format(nowTime);
	}

	public void resetLastDay() {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.MINUTE, -1440);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		this.lastDay = simpleDateFormat.format(rightNow.getTime());
	}
	
	public void resetLastMonth() {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.MONTH, -1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		this.lastMonth = simpleDateFormat.format(rightNow.getTime());
}

	public String getWholeFileName() {
		return wholeFileName;
	}

	public void setWholeFileName(String wholeFileName) {
		this.wholeFileName = wholeFileName;
	}
	
	public String getCheckFileName() {
		return checkFileName;
	}

	public void setCheckFileName(String checkFileName) {
		this.checkFileName = checkFileName;
	}
	
	public void resetWholeFileName(String filePath, String fileNameFormat) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.wholeFileName = filePath + convert(fileNameFormat);
	}
	
	public void resetCheckFileName(String filePath, String checkfileNameFormat) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.checkFileName = filePath + convert(checkfileNameFormat);
	}
	
	public void resetCompressCheckFileName(String compress) {
		this.checkFileName = this.checkFileName + compress;
	}

	public int getAllRecordNum() {
		return allRecordNum;
	}

	public void setAllRecordNum(int allRecordNum) {
		this.allRecordNum = allRecordNum;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void resetFileName(String fileNameFormat) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.fileName = convert(fileNameFormat);
	}
	
	public void resetCompressFileName(String compress) {
		this.fileName = this.fileName + compress;
	}
	
	public int getFileNum() {
		return fileNum;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}
	
	public String getOrigFileName() {
		return origFileName;
	}

	public void setOrigFileName(String origFileName) {
		this.origFileName = origFileName;
	}

	public void resetOrigFileName() {
		this.origFileName = this.fileName;
	}
	
	public String getOrigFileSize() {
		return String.valueOf(origFileSize);
	}

	public void setOrigFileSize(int origFileSize) {
		this.origFileSize = origFileSize;
	}
	
	public void resetOrigFileSize() {		
		File f = new File(wholeFileName);
		if(f.exists() && f.isFile()){
			this.origFileSize = f.length();
		}
		else{
			this.origFileSize = 0;
		}
	}
	
	public String getFileSize() {
		return String.valueOf(fileSize);
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	
	public void resetFileSize() {		
		File f = new File(wholeFileName);
		if(f.exists() && f.isFile()){
			this.fileSize = f.length();
		}
		else{
			this.fileSize = 0;
		}
	}

	public String getSuffixMd5() {
		return suffixMd5;
	}

	public void setSuffixMd5(String suffixMd5) {
		this.suffixMd5 = suffixMd5;
	}

	public String getOrigFileMd5() {
	
		return origFileMd5;
	}

	public void setOrigFileMd5(String origFileMd5) {
		this.origFileMd5 = origFileMd5;
	}
	
	public String convert(String format) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		if(format == null)
			return "";
		
		for(Map.Entry<String, Method> entry : FORMAT_RELA.entrySet()) {
			if(format.contains(entry.getKey())) {
				entry.getValue().setAccessible(true);
				format = format.replaceAll(entry.getKey(), (String) entry.getValue().invoke(this));	
			}
		}
		
		return format;
	}
	
	@SuppressWarnings("unused")
	private String get10RecordSum() {
		return String.format("%010d", getRecordNum());
	}
	
	@SuppressWarnings("unused")
	private String get3FileSeq() {
		return String.format("%03d", getFileSeq());
	}
	
	@SuppressWarnings("unused")
	private String get3FileSum() {
		if(getFileNum() == 0) {
			setFileNum(getRecordNum() == 0?1:(getAllRecordNum() + getRecordNum() - 1) / getRecordNum());
		}
		return String.format("%03d", getFileNum());
	}
	
	@SuppressWarnings("unused")
	private String get9RecordSum() {
		return String.format("%09d", getRecordNum());
	}
	
	@SuppressWarnings("unused")
	private String getRecordSum() {
		return String.format("%d", getRecordNum());
	}
}
