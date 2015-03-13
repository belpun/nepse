package com.nepse.report;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value={	"classpath:database.xml",
		"classpath:batch_context.xml",
		"classpath:job-reader.xml", 
		"classpath:dataSource.xml", 
		
	})
public class ReportConfig {

}
