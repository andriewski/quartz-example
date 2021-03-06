package by.mark.quartz;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Lesson1 {

    public static void main(String[] args) throws SchedulerException, InterruptedException {

        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

        Scheduler sched = schedFact.getScheduler();

        sched.start();

        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(HelloJob.class)
            .withIdentity("myJob", "group1")
            .build();

        // Trigger the job to run now, and then every 40 seconds
        Trigger trigger = newTrigger()
            .withIdentity("myTrigger", "group1")
            .startNow()
            .withSchedule(simpleSchedule()
                .withIntervalInSeconds(40)
                .repeatForever())
            .build();

        // Tell quartz to schedule the job using our trigger


        sched.scheduleJob(job, trigger);

        Thread.sleep(60000);
    }

    private static class HelloJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Hello quartz!");
        }
    }
}
