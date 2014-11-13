package com.nepse.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class GenericWriter implements ItemWriter<Object> {

	@Autowired
	private final IGenericRepository genericRepository = null;
	
	@Override
	public void write(List<? extends Object> entities) throws Exception {
		for (Object entity : entities) {
			genericRepository.saveAsAdmin(entity);
		}
	}

}
