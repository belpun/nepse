package com.nepse.job;


public class InitilizeCompanyHistoricDataFromWebToCsvJob {

	public static void main(String[] args) {
		
		new JobLauncherApp().launch("loadArchiveDataFromWebJob");
	
	}
}
