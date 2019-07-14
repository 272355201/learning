package com.ada.learning;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

@Slf4j
public class SparkSimpleApp {
    public static void main(String[] args) {
        String logFile = "file:///usr/local/spark/README.md";
        logFile = "hdfs://localhost:9000/user/root/README.md";
        JavaSparkContext sc = new JavaSparkContext();
        JavaRDD<String> logData = sc.textFile(logFile).cache();
        long a = logData.filter(line -> line.contains("a")).count();
        long b = logData.filter(line -> line.contains("b")).count();

        log.warn("file -{}, lines with a:-{},b:-{}",logFile, a,b);
    }
}
