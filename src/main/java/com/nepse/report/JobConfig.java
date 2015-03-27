package com.nepse.report;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value={	"classpath:database.xml",
		"classpath:batch_context.xml",
		"classpath:job/job-reader.xml", 
		"classpath:dataSource.xml",
		"classpath:job/writeArchivedDataFromWebToCsvJob.xml",
		"classpath:job/UpdateCompanyToCsv.xml",
		"classpath:job/writeOpeningPriceFromWebToCsvJob.xml",
	})
public class JobConfig {

}
