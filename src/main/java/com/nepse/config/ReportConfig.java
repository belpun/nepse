package com.nepse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value={	"classpath:database.xml",
		"classpath:batch_context.xml",
		"classpath:job-reader.xml" 
	})
public class ReportConfig {

}
