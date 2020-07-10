package com.gmail.cristianberme.practices.quartz;

import java.util.Scanner;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		try {
			// Obtain an instance of the scheduler
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			try {
				// Start the scheduler
				scheduler.start();
				
				// Schedule some tasks
				// First, create a job for the tasks to run
				JobDetail job = JobBuilder.newJob(SampleJob.class)
						.withIdentity("sampleJob1", "sampleJobs")
						.build();
				
				// Then create a trigger to run the job when one or more conditions meet
				// In this case the job will run every 3 seconds
				Trigger trigger = TriggerBuilder.newTrigger()
						.withIdentity("sampleTrigger1", "sampleJobs")
						.startNow()
						.withSchedule(SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(3)
								.repeatForever())
						.build();
				
				// Finally, schedule the job using the scheduler created earlier
				scheduler.scheduleJob(job, trigger);
				
				// Run the scheduler until the user presses return
				waitForReturn();
			} finally {
				if (scheduler.isStarted()) {
					// Stop the scheduler and all its currently scheduled tasks
					scheduler.shutdown();
				}
			}
		} catch (SchedulerException e) {
			LOGGER.error(e.getClass().getSimpleName() + ": " + e.getMessage());
		}
	}
	
	private static void waitForReturn() {
		try (Scanner scanner = new Scanner(System.in)) {
			LOGGER.info("The scheduler is now running, press enter to stop it.");
			scanner.nextLine();
			LOGGER.info("Stopping...");
		}
	}

}
