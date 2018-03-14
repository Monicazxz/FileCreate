package com.pomelo.file.FileCreate.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pomelo.file.FileCreate.bean.StringBean;

/**
 * @author zhangxianzhao
 *
 */
public class FileCreateDao {
	public static final int FC_RULE_ID_FOR_EXAMPLE_0 = 1000;
	public static final int FC_RULE_ID_FOR_EXAMPLE_1 = 1001;
	
	public static final Map<Integer, FileCreateElement> RULE_SQL_RELA = new HashMap<Integer, FileCreateElement>();
	
	static {
		RULE_SQL_RELA.put(FC_RULE_ID_FOR_EXAMPLE_0, new FileCreateElement(
				 "20000085", "20000086", "20000089", "20000087", "20000090", null
				));
		RULE_SQL_RELA.put(FC_RULE_ID_FOR_EXAMPLE_1, new FileCreateElement(
				 "20002141", "20002142", "20002143", "20002144", "20002145", "20002146"
				));
	}
	
	private int domePartNum;
	private int domeFileNum;
	private int domePieceNum;
	private int domeUnpieceNum;
	private int domeRepartNum;
	
	// Warming:The code below can be coded only by the top of the class.
	
	/**
	 * @author zhangxianzhao
	 *
	 */
	public static class FileCreateElement {
		public String lockPartitionSqlId;
		public String lockFileSqlId;
		public String lockPieceSqlId;
		public String fetchPieceSqlId;
		public String unlockPieceSqlId;
		public String releasePartitionSqlId;
		
		public FileCreateElement(String lockPartitionSqlId, String lockFileSqlId, String lockPieceSqlId, String fetchPieceSqlId, String unlockPieceSqlId, String releasePartitionSqlId) {
			this.lockPartitionSqlId = lockPartitionSqlId;
			this.lockFileSqlId = lockFileSqlId;
			this.lockPieceSqlId = lockPieceSqlId;
			this.fetchPieceSqlId = fetchPieceSqlId;
			this.unlockPieceSqlId = unlockPieceSqlId;
			this.releasePartitionSqlId = releasePartitionSqlId;
		}
	}
	
	private int ruleId;
	private Class<?> recordBean;
	Map<String, String> requestMap;
	
	public FileCreateDao(int ruleId, String recordClassName, String sqlParams) {
		this.ruleId = ruleId;
		
		try {
			if(recordClassName == null || recordClassName.isEmpty())
				recordBean = StringBean.class;
			else
				recordBean = Class.forName(recordClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			recordBean = StringBean.class;
		}
		
		requestMap = string2Map(sqlParams, ";", ":");
	}
	
	public int lockPartition() {		
		domePartNum = 111;
		
		System.out.println("update " + RULE_SQL_RELA.get(ruleId).lockPartitionSqlId + requestMap);
		
		System.out.println("commit");
		
		return domePartNum;
	}
	
	public int lockFile() {
		if(domePartNum == 0) {
			domeFileNum = 0;
			return domeFileNum;
		}

		domeFileNum += domePartNum > 9?9:domePartNum;
		domePartNum -= domePartNum > 9?9:domePartNum;
		
		System.out.println("update " + RULE_SQL_RELA.get(ruleId).lockFileSqlId + requestMap);
		
		System.out.println("commit");
		
		return domeFileNum;
	}
	
	public int lockPiece() {
		if(domeFileNum == 0) {
			domePieceNum = 0;
			return domePieceNum;
		}

		domePieceNum += domeFileNum > 4?4:domeFileNum;
		domeFileNum -= domeFileNum > 4?4:domeFileNum;
		
		System.out.println("update " + RULE_SQL_RELA.get(ruleId).lockPieceSqlId + requestMap);
		
		System.out.println("commit");
		System.out.println("lockPieceSqlId.num= " + 2);
		
		return domePieceNum;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> fetchPiece() throws InstantiationException, IllegalAccessException {
		domeUnpieceNum = domePieceNum;
		domePieceNum = 0;
		
		System.out.println("select " + RULE_SQL_RELA.get(ruleId).fetchPieceSqlId + requestMap);
		
		return Arrays.asList((T)recordBean.newInstance(), (T)recordBean.newInstance(), (T)recordBean.newInstance(), (T)recordBean.newInstance()).subList(0, domeUnpieceNum);
	}
	
	public int unlockPiece() {
		domeRepartNum += domeUnpieceNum;
		
		System.out.println("update " + RULE_SQL_RELA.get(ruleId).unlockPieceSqlId + requestMap);
		
		System.out.println("commit");
		
		return domeUnpieceNum;
	}
	
	public int releasePartition() {
		if(RULE_SQL_RELA.get(ruleId).releasePartitionSqlId == null || "".equals(RULE_SQL_RELA.get(ruleId).releasePartitionSqlId))
			return 0;

		System.out.println("update " + RULE_SQL_RELA.get(ruleId).releasePartitionSqlId + requestMap);
		
		System.out.println("commit");
		
		return domeRepartNum;
	}
	
	public Map<String, String> string2Map(String str, String entryDelim, String kvDelim) {
		Map<String, String> requestMap = new HashMap<String, String>();
		if(str != null && !str.isEmpty()) {
			String[] entrys = str.split(entryDelim);
			for(String entry : entrys) {
				String[] kvs = entry.split(kvDelim);
				requestMap.put(kvs[0], kvs[1]);
			}
		}
		return requestMap;
	}
}
