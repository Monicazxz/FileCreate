package com.pomelo.file.FileCreate.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author zxz
 *
 */
public class Zip {
	private static int buffer = 1024 * 4;
	private static byte[] bArray = new byte[buffer];
	public static final int DEAL_WAY_DEL = 1; //源文件处理方式——删除
	
	public static File gCompress(File srcFile, boolean delSrc) throws IOException {
		File tagFile = new File(srcFile.getAbsolutePath() + ".gz");
		
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(tagFile);
		
		GZIPOutputStream out = new GZIPOutputStream(fos);
		
	    int len = 0;
	    while((len = fis.read(bArray, 0, buffer)) != -1) {
	    	out.write(bArray, 0, len);
	    }
	    
	    out.close();
	    fos.close();
	    fis.close();
	    
	    if(delSrc)
	    	srcFile.delete();
	    
	    return tagFile;
	}
	
	/**
	 * @Title: zUnCompress 
	 * @Description: 进行zip解压操作
	 * @param srcFile 源文件（压缩包）
	 * @param destPath  目标路径
	 * @param srcDealWay  源文件处理方式
	 * @return
	 * @throws IOException
	 */
	public static List<File> zUnCompress(File srcFile, String destPath, int srcDealWay) throws IOException {
		List<File> descFiles = new ArrayList<File>();
		
		ZipFile zf = new ZipFile(srcFile);
	    Enumeration<?> e = zf.entries();
	    while (e.hasMoreElements()) {
	        ZipEntry entry = (ZipEntry) e.nextElement();
	        
	        InputStream in = zf.getInputStream(entry); 
	        
			String destFile = (destPath.endsWith("/") ? destPath
					: (new StringBuilder()).append(destPath).append("/").toString()) + entry.getName();
			File file = new File(destFile);
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);  
			
			int len;  
			while ((len = in.read(bArray)) > 0) {  
				out.write(bArray, 0, len);  
			}
			in.close();  
			out.close();  
			
			descFiles.add(file);
	    }
	    zf.close();
	    
	    if(srcDealWay == DEAL_WAY_DEL)
	    	srcFile.delete();
	    
	    return descFiles;
	}
	
	public static List<File> zUnCompress(String srcFile, String destPath, int srcDealWay) throws IOException {
		File file = new File(srcFile);
		
	    return zUnCompress(file, destPath, srcDealWay);
	}
}
