package com.nepse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.nepse.data.NepseDataExtractorFromWeb;
import com.nepse.data.service.WriteOpeningPriceToFileFromWebService;
import com.nepse.writer.CsvWriter;

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
	public WriteOpeningPriceToFileFromWebService writeOpeningPriceToFileFromWebService(){
		return new WriteOpeningPriceToFileFromWebService();
	}

}
