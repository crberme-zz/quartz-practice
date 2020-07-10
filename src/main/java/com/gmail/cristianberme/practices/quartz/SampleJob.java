package com.gmail.cristianberme.practices.quartz;

import java.util.concurrent.ThreadLocalRandom;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleJob implements Job {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info(String.format("Here's a random number! %d", ThreadLocalRandom.current().nextInt(0, 10)));
	}

}
