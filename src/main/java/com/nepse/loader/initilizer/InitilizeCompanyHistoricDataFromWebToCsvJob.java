package com.nepse.loader.initilizer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.nepse.config.DefaultDatabaseConfig;
import com.nepse.report.JobConfig;

public class InitilizeCompanyHistoricDataFromWebToCsvJob {

	public static void main(String[] args) {
	
		ApplicationContext context = new AnnotationConfigApplicationContext(DefaultDatabaseConfig.class, JobConfig.class);
		 
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("loadArchiveDataFromWebJob");
	 
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
