package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Kateryna Basova KHNUE
 * @version 21 of June 2020
 *
 * Classname Main
 * The main class where all actions will be executed
 *
 * Module 4. Mulithreading.
 * 1. Use the file from the previous task - logs.txt.
 * 2. Create a class that manages logs in this file.
 * 3. Create a method that finds all the ERROR logs for a specific date and write them
 *    into a specific file (name = ERROR + Date  + .log).
 * 4. In your main class develop a functionality to create 5 such a files for 5 different days.
 *    Launch them in consistent way (one after another).
 * 5. Repeat the above  task in parallel way. Multi-threading.
 * 6. Compare the results.
 **/
public class Main {

    public static void main(String[] args) throws IOException {

        // -------------------------------------------------------------------------------------
        // 1. Use the file from the previous task - logs.txt.
        String path = "/home/kate/Downloads/logs.txt";

        // create a log manager object
        LogsManager manager = new LogsManager(path);

        // -------------------------------------------------------------------------------------
        // 3. Create a method that finds all the ERROR logs for a specific date and write them
        //    into a specific file (name = ERROR  + Date  + .log).
        // -------------------------------------------------------------------------------------
        // 4. In your main class develop a functionality to create  5 such a files for 5 different days.
        //    Launch them in consistent way (one after another).

        // create start timestamp
        LocalDateTime start1 = LocalDateTime.now();

        System.out.println("Getting Error lines by date and writing them to the file");
        // get Error by given dates and write to specific file
        manager.getErrorByDate("2019-10-21");
        manager.getErrorByDate("2019-11-13");
        manager.getErrorByDate("2020-01-03");
        manager.getErrorByDate("2020-01-08");
        manager.getErrorByDate("2020-02-04");

        // create finish1 timestamp
        LocalDateTime finish1 = LocalDateTime.now();

        long executionTime1 = ChronoUnit.MILLIS.between(start1, finish1);
        System.out.println("Exetuion time: : " + executionTime1 + " MS.");

        // -------------------------------------------------------------------------------------
        // 5. Repeat the above task in parallel way. Multi-threading.
        System.out.println("---------------------------------");
        System.out.println("Repeat the above task in parallel way. Multi-threading");


        // create start timestamp
        LocalDateTime start2 = LocalDateTime.now();

        // creating threads
        MyThread thread1 = new MyThread("2019-10-21", path);
        thread1.start();
        MyThread thread2 = new MyThread("2019-11-13", path);
        thread2.start();
        MyThread thread3 = new MyThread("2020-01-03", path);
        thread3.start();
        MyThread thread4 = new MyThread("2020-01-08", path);
        thread4.start();
        MyThread thread5 = new MyThread("2020-02-04", path);
        thread5.start();

        // main thread will wait until all treads finished execution
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // create finish timestamp
        LocalDateTime finish2 = LocalDateTime.now();

        long executionTime2 = ChronoUnit.MILLIS.between(start2, finish2);
        System.out.println("Exetuion time: : " + executionTime2 + " MS.");

        /**
         * Comparing the duration results of both methods
         */
        if (executionTime1 > executionTime2) {
            System.out.println("Execution was quicklier during with Multi-Threading calls");
        } else if (executionTime1 < executionTime2) {
            System.out.println("Execution was quicklier during with consequent calls.");
        } else {
            System.out.println("Both cases take the same execution time");
        }

    }


    /**
     * Classname: MyThread
     * A custom static class which extends class Thread
     */
    static class MyThread extends Thread {

        private String date; // date for log
        private String filePath; // path to log file

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        /**
         * Constructor with parameters
         */
        MyThread(String date, String filePath){
            this.date = date;
            this.filePath = filePath;
        }

        /**
         *  A method which initialize LogsManager class and calls its method "getErrorByDate"
         */
        @Override
        public void run() {
            LogsManager manager = new LogsManager(this.filePath);
            try {
                manager.getErrorByDate(this.date);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
