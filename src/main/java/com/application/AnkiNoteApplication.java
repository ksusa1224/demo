package com.application;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import com.common.Log;
import com.common.SystemOutToLog;
import com.dao.SQliteDAO;
import com.slime.SlimeSerif;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class AnkiNoteApplication extends SpringBootServletInitializer {

	//public static String LOG = "/usr/local/anki_note/application/spring_log/console.log";
	
	public static void main(String[] args) {
		// 月次ログDBがなければ作成
		SQliteDAO dao = new SQliteDAO();
		dao.create_log_db();
		
		try
		{
			// H2のサーバーを起動
			Server server = Server.createTcpServer("-tcpAllowOthers").start();
		}
		catch(Exception ex)
		{
			Log log = new Log();
			log.insert_error_log(ex.toString(), ex.getMessage());
			ex.printStackTrace();
		}
		
        SpringApplication.run(AnkiNoteApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(AnkiNoteApplication.class);
	}
}
