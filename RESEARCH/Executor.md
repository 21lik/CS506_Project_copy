# Research Report for the Java ScheduledExecutorService:

### Time Spent: 
1/2 Hour

### Reason for Research: 
Needed a way to call the random word selection code every 24 hours

### Sources Used: 
https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ScheduledExecutorService.html\
https://www.geeksforgeeks.org/scheduledexecutorservice-interface-in-java/

### Summary:
I researched Java's ScheduledExecutorService through a couple of websites. By doing so I was able to schedule daily word updates.

### Results:
The ScheduledExecutorService interface is a sub-interface of ExecutorService which is in the java.util.concurrent package. Essentially what this allows you to do is define a certain period in seconds. It will then execute a given method every time that period is passed. There are a handful of different ways you can use it by calling different methods to adjust the delay as well as opther attributes.

to instantiate it you have to use the Executors class which creates a new threadpool with the given core pool size like this example from my code:

ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

you can then use it to schedule an event such as this example from my code:

scheduler.scheduleAtFixedRate(() -> {
AssignNewWord();
}, initialDelay, period, TimeUnit.SECONDS);

the scheduleAtFixedRate() method allows you to define a method, AssignNewWord in this case, to execute at a fixed rate which is defined by the period. You can also set an initial delay. For my code i set the period as 24x60x60 to run the method once every 24 hours with a delay of 0 seconds.