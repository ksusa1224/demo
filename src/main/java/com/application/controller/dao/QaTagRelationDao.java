package com.application.controller.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.application.model.dao.QaTagRelationModel;
import com.application.model.dao.SeitouModel;
import com.common.Constant;
import com.common.Log;
import com.common.StopWatch;
import com.common.StringBuilderPlus;
import com.dao.SQliteDAO;

public class QaTagRelationDao {

	/**
	 * 最大行を得る
	 * @param db_name
	 * @return
	 */
	public int get_max_row_no(String db_name)
	{	
		int max_row_no = 0;
		
		SQliteDAO dao = new SQliteDAO();
		
		StringBuilderPlus sql = new StringBuilderPlus();
		sql.appendLine("select max(row_no) as row_no from qa_tag_relation limit 1;");
		dao.loadDriver();
		
	    Connection connection = null;
		String db_save_path = Constant.SQLITE_OWNER_DB_FOLDEDR_PATH + "/";
		String connection_str = "jdbc:sqlite:" 
				  				+ db_save_path
				  				+ db_name;
	    try
	    {
	      // DBが存在していたら接続、存在していなければ作成
	      connection = DriverManager.getConnection(connection_str);
	      Statement stmt = connection.createStatement();
	      ResultSet rs = stmt.executeQuery(sql.toString());
	      while (rs.next()) 
	      {
	    	  max_row_no = rs.getInt("row_no");
	    	  System.out.println(max_row_no);
	      }
	    }
	    catch(Exception ex)
	    {
			Log log = new Log();
			log.insert_error_log("ERROR", ex.getStackTrace().toString());
		    System.err.println(ex.getMessage());
	    }
	    finally
	    {
	      dao.close(connection);
	    }	    
		
		return max_row_no;
	}
	
	public QaTagRelationModel select_qa_tag_relation(String db_name, String qa_id)
	{		
		SQliteDAO dao = new SQliteDAO();
		
		StringBuilderPlus sql = new StringBuilderPlus();
		sql.appendLine("select ");
	    // 行番号
		sql.appendLine("row_no,");
	    // QA ID
		sql.appendLine("qa_id,");
	    // タグID
		sql.appendLine("tag_id,");
	    // タグ内でのQAの順番
		sql.appendLine("junban,");
	    // 公開範囲
		sql.appendLine("koukai_level,");
	    // 作成者
		sql.appendLine("  create_owner,");
	    // 更新者
		sql.appendLine("  update_owner,");
	    // レコード作成日時（H2DBのtimestampと同じフォーマットにする）
		sql.appendLine("  create_timestamp,");
	    // レコード更新日時（H2DBのtimestampと同じフォーマットにする）
		sql.appendLine("  update_timestamp");
		sql.appendLine(" from qa_tag_relation");
		sql.appendLine(" order by junban asc;");
		
		dao.loadDriver();
		
	    Connection connection = null;
		String db_save_path = Constant.SQLITE_OWNER_DB_FOLDEDR_PATH + "/";
		String connection_str = "jdbc:sqlite:" 
				  				+ db_save_path
				  				+ db_name;
		QaTagRelationModel qa_tag_relation = new QaTagRelationModel();
		
		try
	    {
	      // DBが存在していたら接続、存在していなければ作成
	      connection = DriverManager.getConnection(connection_str);
	      Statement stmt = connection.createStatement();
	      ResultSet rs = stmt.executeQuery(sql.toString());
	      while (rs.next()) 
	      {
	    	    // 行番号
	    	    qa_tag_relation.setRow_no(rs.getInt("row_no"));
	    	    // QA ID
	    	    qa_tag_relation.setQa_id(rs.getString("qa_id"));
	    	    // タグID
	    	    qa_tag_relation.setTag_id(rs.getString("tag_id"));
	    	    // タグ内でのQAの順番
	    	    qa_tag_relation.setJunban(rs.getInt("junban"));
	    	    // 公開範囲
	    	    qa_tag_relation.setKoukai_level(rs.getInt("koukai_level"));
		        // 作成者
	    		qa_tag_relation.setCreate_owner(rs.getString("create_owner"));
		        // 更新者
	    		qa_tag_relation.setUpdate_owner(rs.getString("update_owner"));
		        // レコード作成日時（H2DBのtimestampと同じフォーマットにする）
	    		qa_tag_relation.setUpdate_timestamp(rs.getString("create_timestamp"));
		        // レコード更新日時（H2DBのtimestampと同じフォーマットにする）
	    		qa_tag_relation.setUpdate_timestamp(rs.getString("update_timestamp"));
	      }
	    }
	    catch(Exception ex)
	    {
			Log log = new Log();
			log.insert_error_log("ERROR", ex.getStackTrace().toString());
		    System.err.println(ex.getMessage());
	    }
	    finally
	    {
	      dao.close(connection);
	    }	    
		
		return qa_tag_relation;
	}
	
	/**
	 * @param db_name
	 * @param seitou_list
	 * @return
	 */
//	public List<SeitouModel> select_seitou_list(String db_name, List<SeitouModel> seitou_list, String qa_id)
//	{		
//		SQliteDAO dao = new SQliteDAO();
//		
//		StringBuilderPlus sql = new StringBuilderPlus();
//		sql.appendLine("select ");
//	    // 行番号
//		sql.appendLine("  row_no,");
//	    // 正答ID
//		sql.appendLine("  s_id,");
//	    // QA ID
//		sql.appendLine("  qa_id,");
//	    // QA内での正答の順番
//		sql.appendLine("  junban,");
//	    // 正答が文字であるかのフラグ
//		sql.appendLine("  is_text_flg,");
//	    // 正答がバイナリであるかのフラグ
//		sql.appendLine("  is_binary_flg,");
//	    // 正答
//		sql.appendLine("  seitou,");
//	    // 正答が画像などのバイナリである場合に格納する
//		sql.appendLine("  seitou_binary,");
//	    // 重要度（５段階）
//		sql.appendLine("  juyoudo,");
//	    // 難易度（５段階）
//		sql.appendLine("  nanido,");
//	    // 言語
//		sql.appendLine("  language,");
//	    // テキスト読み上げデータ
//		sql.appendLine("  yomiage,");
//	    // 削除フラグ
//		sql.appendLine("  del_flg,");
//	    // 作成者
//		sql.appendLine("  create_owner,");
//	    // 更新者
//		sql.appendLine("  update_owner,");
//	    // レコード作成日時（H2DBのtimestampと同じフォーマットにする）
//		sql.appendLine("  create_timestamp,");
//	    // レコード更新日時（H2DBのtimestampと同じフォーマットにする）
//		sql.appendLine("  update_timestamp");
//		sql.appendLine(" from seitou");
//		sql.appendLine(" where qa_id = '" + qa_id + "'");
//		sql.appendLine(" and del_flg = 0");
//		sql.appendLine(" order by junban asc;");
//		
//		dao.loadDriver();
//		
//	    Connection connection = null;
//		String db_save_path = Constant.SQLITE_OWNER_DB_FOLDEDR_PATH + "/";
//		String connection_str = "jdbc:sqlite:" 
//				  				+ db_save_path
//				  				+ db_name;
//	    try
//	    {
//	      // DBが存在していたら接続、存在していなければ作成
//	      connection = DriverManager.getConnection(connection_str);
//	      Statement stmt = connection.createStatement();
//	      ResultSet rs = stmt.executeQuery(sql.toString());
//	      while (rs.next()) 
//	      {
//	    	  SeitouModel seitou = new SeitouModel();
//		      // 行番号
//	    	  seitou.setRow_no(rs.getInt("row_no"));
//		      // 正答ID
//	    	  seitou.setS_id(rs.getString("s_id"));
//		      // QA ID
//	    	  seitou.setQa_id(rs.getString("qa_id"));
//		      // QA内での正答の順番
//	    	  seitou.setJunban(rs.getInt("junban"));
//	    	  // 正答が文字であるかのフラグ
//	    	  seitou.setIs_text_flg(rs.getInt("is_text_flg"));
//	    	  // 正答がバイナリであるかのフラグ
//	    	  seitou.setIs_binary_flg(rs.getInt("is_binary_flg"));
//		      // 正答
//	    	  seitou.setSeitou(rs.getString("seitou"));
//	    	  // 正答が画像などのバイナリである場合に格納する
//	    	  seitou.setSeitou_binary(rs.getBytes("seitou_binary"));
//		      // 重要度（５段階）
//	    	  seitou.setJuyoudo(rs.getInt("juyoudo"));
//		      // 難易度（５段階）
//	    	  seitou.setNanido(rs.getInt("nanido"));
//	    	  // 言語
//	    	  seitou.setLanguage(rs.getString("language"));
//	    	  // テキスト読み上げデータ
//	    	  seitou.setYomiage(rs.getBytes("yomiage"));
//	    	  // 削除フラグ
//		      seitou.setDel_flg(rs.getInt("del_flg"));
//		      // 作成者
//		      seitou.setCreate_owner(rs.getString("create_owner"));
//		      // 更新者
//		      seitou.setUpdate_owner(rs.getString("update_owner"));
//		      // レコード作成日時（H2DBのtimestampと同じフォーマットにする）
//		      seitou.setUpdate_timestamp(rs.getString("create_timestamp"));
//		      // レコード更新日時（H2DBのtimestampと同じフォーマットにする）
//		      seitou.setUpdate_timestamp(rs.getString("update_timestamp"));
//
//		      seitou_list.add(seitou);
//	      }
//	    }
//	    catch(Exception ex)
//	    {
//			Log log = new Log();
//			log.insert_error_log("ERROR", ex.getStackTrace().toString());
//		    System.err.println(ex.getMessage());
//	    }
//	    finally
//	    {
//	      dao.close(connection);
//	    }	    
//		
//		return seitou_list;
//	}
	
	/**
	 * QAタグ関連テーブルに１件レコードを追加する
	 * @param seitou
	 * @return
	 */
	public void insert_qa_tag_relation(String db_name, QaTagRelationModel qa_tag_relation)
	{
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();

		SQliteDAO dao = new SQliteDAO();
	    Connection connection = null;
		String db_save_path = Constant.SQLITE_OWNER_DB_FOLDEDR_PATH + "/";
		String connection_str = "jdbc:sqlite:" 
				  				+ db_save_path
				  				+ db_name;
		
		StringBuilderPlus sql = new StringBuilderPlus();
		sql.appendLine("replace into qa_tag_relation (");
		// 行番号
		sql.appendLine("  row_no,");
		// QA ID
		sql.appendLine("	qa_id,");
	    // タグID
		sql.appendLine("	tag_id,");
	    // タグ内でのQAの順番
		sql.appendLine("	junban,");
	    // 公開範囲
		sql.appendLine("	koukai_level,");
		// 作成者
		sql.appendLine("  create_owner,");
		// 更新者
		sql.appendLine("  update_owner,");
		// レコード作成日時（H2DBのtimestampと同じフォーマットにする）
		sql.appendLine("	create_timestamp,");
		// レコード更新日時（H2DBのtimestampと同じフォーマットにする）
		sql.appendLine("	update_timestamp");
		sql.appendLine(") ");
		
		sql.appendLine("values (");
	    // 行番号
		sql.appendLine("" + qa_tag_relation.getRow_no() + ",");
	    // QA ID
		sql.appendLine("'" + qa_tag_relation.getQa_id() + "',");
	    // タグID
		sql.appendLine("'" + qa_tag_relation.getTag_id() + "',");
	    // タグ内でのQAの順番
		sql.appendLine("" + qa_tag_relation.getJunban() + ",");
	    // 公開範囲
		sql.appendLine("" + qa_tag_relation.getKoukai_level() + ",");
		// 作成者
		sql.appendLine("'" + qa_tag_relation.getCreate_owner() + "',");
		// 更新者
		sql.appendLine("'" + qa_tag_relation.getUpdate_owner() + "',");
		// レコード作成日時（H2DBのtimestampと同じフォーマットにする）
		sql.appendLine("'" + qa_tag_relation.getCreate_timestamp() + "',");
		// レコード更新日時（H2DBのtimestampと同じフォーマットにする）
		sql.appendLine("'" + qa_tag_relation.getUpdate_timestamp() + "'");
		sql.appendLine(");");
	    
		try
	    {
	      // DBが存在していたら接続、存在していなければ作成
	      connection = DriverManager.getConnection(connection_str);
	      Statement stmt = connection.createStatement();

	      //1行ずつコミットしない
	      stmt.getConnection().setAutoCommit(false);
	      
	      /**
	       *  SQL実行
	       */
	      dao.transaction(stmt, sql);
	    }
	    catch(Exception ex)
	    {
			Log log = new Log();
			log.insert_error_log("ERROR", ex.getMessage());
		    System.err.println(ex.getMessage());
		    ex.printStackTrace();
	    }
	    finally
	    {
		  stopwatch.stop(new Object(){}.getClass().getEnclosingMethod().getName());
	      dao.close(connection);
	    }	    
		
	}	
	
	/**
	 * QAタグ関連テーブルを１件更新する
	 * @param seitou
	 * @return 
	 */
	public void update_seitou(String db_name, SeitouModel seitou)
	{
		SQliteDAO dao = new SQliteDAO();
	    Connection connection = null;
		String db_save_path = Constant.SQLITE_OWNER_DB_FOLDEDR_PATH + "/";
		String connection_str = "jdbc:sqlite:" 
				  				+ db_save_path
				  				+ db_name;
		
		StringBuilderPlus sql = new StringBuilderPlus();
				
		sql.appendLine("update seitou ");
		sql.appendLine("values (");
	    // 行番号
		sql.appendLine("  row_no = " + seitou.getRow_no() + ",");
	    // 正答ID
		sql.appendLine("  s_id = '" + seitou.getS_id() + "',");
	    // QA ID
		sql.appendLine("  qa_id = '" + seitou.getQa_id() + "',");
	    // QA内での正答の順番
		sql.appendLine("  junban = " + seitou.getJunban() + ",");
		// 正答が文字であるかのフラグ
		sql.appendLine("  is_text_flg = " + seitou.getIs_text_flg() + ",");
		// 正答がバイナリであるかのフラグ
		sql.appendLine("  is_binary_flg = " + seitou.getIs_binary_flg() + ",");
	    // 正答
		sql.appendLine("  seitou = '" + seitou.getSeitou() + "',");
		// 正答が画像などのバイナリである場合に格納する
		sql.appendLine("  seitou_binary = " + seitou.getSeitou_binary() + ",");		
	    // 重要度（５段階）
		sql.appendLine("  juyoudo = " + seitou.getJuyoudo() + ",");
	    // 難易度（５段階）
		sql.appendLine("  nanido = " + seitou.getNanido() + ",");
	    // 言語
		sql.appendLine("  language = '" + seitou.getLanguage() + "',");
		// テキスト読み上げデータ
		sql.appendLine("  yomiage = " + seitou.getYomiage() + ",");
		// 削除フラグ
		sql.appendLine("  del_flg = " + seitou.getDel_flg() + ",");
		// 作成者
		sql.appendLine("  create_owner = '" + seitou.getCreate_owner() + "',");
		// 更新者
		sql.appendLine("  update_owner = '" + seitou.getUpdate_owner() + "',");
		// レコード作成日時（H2DBのtimestampと同じフォーマットにする）
		sql.appendLine("  create_timestamp = '" + seitou.getCreate_timestamp() + "',");
		// レコード更新日時（H2DBのtimestampと同じフォーマットにする）
		sql.appendLine("  update_timestamp = '" + seitou.getUpdate_timestamp() + "'");
		sql.appendLine(");");
		try
	    {
	      // DBが存在していたら接続、存在していなければ作成
	      connection = DriverManager.getConnection(connection_str);
	      Statement stmt = connection.createStatement();

	      //1行ずつコミットしない
	      stmt.getConnection().setAutoCommit(false);
	      
	      /**
	       *  SQL実行
	       */
	      dao.transaction(stmt, sql);
	    }
	    catch(Exception ex)
	    {
			Log log = new Log();
			log.insert_error_log("ERROR", ex.getStackTrace().toString());
		    System.err.println(ex.getMessage());
	    }
	    finally
	    {
	      dao.close(connection);
	    }		
	}

}