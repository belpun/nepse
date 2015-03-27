package com.nepse.loader.initilizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.nepse.data.NepseDataExtractorFromWeb;

public class WriteCompanyToCsvTasklet implements Tasklet{

	private static final String fileName = "src/main/resources/companySymbol.csv";
	private static final String HEADER = "name,symbol";
	
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		NepseDataExtractorFromWeb extractor = new NepseDataExtractorFromWeb();
		File file = new File(fileName);
		
		BufferedWriter bw = null;
		
		try{
			if(!file.exists()) {
				file.createNewFile();
				bw = new BufferedWriter(new FileWriter(file));
				bw.append(HEADER);
			} 
		} finally {
			if(bw != null) {
				bw.flush();
				bw.close();
			}
		}
		
		BufferedReader br =null;
		Map<String, String> csvCompanyMap = new HashMap<String, String>();
		
		
		try{
			br =new BufferedReader(new FileReader(file));
			
			String line = null;
			br.readLine();
			while((line =br.readLine()) != null) {
				String[] split = line.split(",");
				csvCompanyMap.put(split[1], split[0]);
			}
			
			} finally {
				if(br != null) {
					br.close();
				}
		}
		
		Map<String, String> webCompanyMap = extractor.getAllCompanyMap();
		
		FileWriter fw = null;
		
		try{
			fw = new FileWriter(file, true);
			
			for(String key : webCompanyMap.keySet()) {
				if (!csvCompanyMap.containsKey(key)) {
					fw.write(webCompanyMap.get(key) + "," + key +"\n");
				}
			}
			
		} finally {
			if(fw != null) {
				fw.flush();
				fw.close();
			}
	}
		
		return RepeatStatus.FINISHED;
	}

}
