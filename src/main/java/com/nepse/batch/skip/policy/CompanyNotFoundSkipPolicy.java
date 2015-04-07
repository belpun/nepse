package com.nepse.batch.skip.policy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

import com.nepse.exception.CompanyDataNotFound;

public class CompanyNotFoundSkipPolicy implements SkipPolicy {
	
	Logger logger = LoggerFactory.getLogger(CompanyNotFoundSkipPolicy.class);

	private int skipLimit;

	public int getSkipLimit() {
		return skipLimit;
	}

	public void setSkipLimit(int skipLimit) {
		this.skipLimit = skipLimit;
	}

	@Override
	public boolean shouldSkip(Throwable t, int skipCount)
			throws SkipLimitExceededException {

		if (t == null) {
			return false;
		}

		if (skipCount > skipLimit) {
			logger.info("skip limit exceed for the skip", t.getMessage());
			return false;
		}

		while (t != null) {

			if (t instanceof CompanyDataNotFound) {
				return true;
			}
			
			t = t.getCause();
		}
		return false;
	}

}
