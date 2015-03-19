package com.nepse.loader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.nepse.config.DefaultDatabaseConfig;
import com.nepse.dao.JDBCCompanyRepository;
import com.nepse.report.JobConfig;

public class CompanyLoaderFromCsv {

	public static void main(String[] args) {

//		String[] springConfig  = 
//			{	"classpath:database.xml",
//				"classpath:batch_context.xml",
//				"classpath:job-reader.xml" 
//			};
//	 
//		ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
		
		ApplicationContext context = new AnnotationConfigApplicationContext(DefaultDatabaseConfig.class, JobConfig.class);
	 
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("CompanyLoadJob");
	 
		try {
	 
			JobExecution execution = jobLauncher.run(job, new JobParameters());
			System.out.println("Exit Status : " + execution.getStatus());
			if(execution.getStatus().equals("FAILED")) {
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//	 
		System.out.println("Done");
		((ConfigurableApplicationContext)context).close();
	 
	  }
	

}
