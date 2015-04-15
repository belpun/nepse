package com.nepse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.nepse.data.NepseDataExtractorFromWeb;
import com.nepse.data.service.ArchivedDataService;
import com.nepse.data.service.IArchivedDataService;
import com.nepse.data.service.OpeningPriceService;
import com.nepse.loader.CompanyDataWriter;
import com.nepse.service.data.CompanyDataUpdater;
import com.nepse.service.data.ICompanyDataUpdater;
import com.nepse.utils.FileNameUtils;
import com.nepse.writer.CsvWriter;
import com.nepse.loader.CompanyDataWriterWithModel;

@Configuration
@ImportResource("classpath:dataSource.xml")
public class DefaultConfig {
	
//	@Bean(initMethod = "init")
//	public TemplateManager templateManager() {
//		FreemarkerManager templateEngine = new FreemarkerManager(
//				i18nTextProvider(), "UTF-8", new TemplateLoader[] {
//						
//						new ClassTemplateLoader(FreemarkerManager.class,"/com/bita/template") 
//				});
//		templateEngine.setDebug(developmentMode);
//		Map<String, TemplateModel> sharedVariables = new HashMap<String, TemplateModel>();
//		sharedVariables.put("getCurrentUserName", new CurrentUserFunction());
//                templateEngine.setSharedVariables(sharedVariables);
//		return templateEngine;
//	}
	
	@Bean
	public NepseDataExtractorFromWeb nepseDataExtractorFromWeb() {
		return new NepseDataExtractorFromWeb();
	}
	
	@Bean
	public CsvWriter csvWriter() {
		return new CsvWriter();
	}
	
	@Bean
	public OpeningPriceService writeOpeningPriceToFileFromWebService(){
		return new OpeningPriceService();
	}
	
	@Bean
	public ICompanyDataUpdater companyDataUpdater() {
		return new CompanyDataUpdater();
	}
	
	@Bean
	public IArchivedDataService archivedDataService() {
		return new ArchivedDataService();
	}
	
	@Bean
	public FileNameUtils fileNameUtils() {
		return new FileNameUtils();
	}
	
	@Bean 
	public CompanyDataWriter companyDataWriter() {
		return new CompanyDataWriter();
	}
	
	@Bean 
	public CompanyDataWriterWithModel companyDataWriterWithModel() {
		return new CompanyDataWriterWithModel();
	}

}
