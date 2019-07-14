package com.ada.learning;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Minutes;
import org.apache.spark.streaming.Seconds;
import org.apache.spark.streaming.StreamingContext;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.*;
import scala.Tuple2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
public class KafkaWordCount2 {
    /**
     * object KafkaWordCount{
     * def main(args:Array[String]){
     * StreamingExamples.setStreamingLogLevels()
     * val sc = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]")
     * val ssc = new StreamingContext(sc,Seconds(10))
     * ssc.checkpoint("file:///usr/local/spark/mycode/kafka/checkpoint") //ÉèÖÃ¼ì²éµã£¬Èç¹û´æ·ÅÔÚHDFSÉÏÃæ£¬ÔòÐ´³ÉÀàËÆssc.checkpoint("/user/hadoop/checkpoint")ÕâÖÖÐÎÊ½£¬µ«ÊÇ£¬ÒªÆô¶¯hadoop
     * val zkQuorum = "localhost:2181" //Zookeeper·þÎñÆ÷µØÖ·
     * val group = "1"  //topicËùÔÚµÄgroup£¬¿ÉÒÔÉèÖÃÎª×Ô¼ºÏëÒªµÄÃû³Æ£¬±ÈÈç²»ÓÃ1£¬¶øÊÇval group = "test-consumer-group"
     * val topics = "wordsender"  //topicsµÄÃû³Æ
     * val numThreads = 1  //Ã¿¸ötopicµÄ·ÖÇøÊý
     * val topicMap =topics.split(",").map((_,numThreads.toInt)).toMap
     * val lineMap = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap)
     * val lines = lineMap.map(_._2)
     * val words = lines.flatMap(_.split(" "))
     * val pair = words.map(x => (x,1))
     * val wordCounts = pair.reduceByKeyAndWindow(_ + _,_ - _,Minutes(2),Seconds(10),2) //ÕâÐÐ´úÂëµÄº¬ÒåÔÚÏÂÒ»½ÚµÄ´°¿Ú×ª»»²Ù×÷ÖÐ»áÓÐ½éÉÜ
     * wordCounts.print
     * ssc.start
     * ssc.awaitTermination
     * }
     * }
     * @param args
     */
    @SneakyThrows
    public static void main(String[] args) {
        SparkConf sc = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]");
        JavaStreamingContext ssc = new JavaStreamingContext(sc, Seconds.apply(10));
        ssc.checkpoint("file:///usr/local/spark/mycode/kafka/checkpoint");

        Map<String, Integer> map = new HashMap<>();
        map.put("wordsendertest", 1);


        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaWordCount2");

        final ConsumerStrategy<String, String> sub1 = ConsumerStrategies.Subscribe(Arrays.asList("wordsendertest"), configs);
        JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(ssc,  LocationStrategies.PreferConsistent(), sub1);


        JavaDStream<String> lines = stream.map(v1 -> v1.value());
        JavaDStream<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
        JavaDStream<Map<String, Integer>> pair = words.map(word -> {
            HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put(word, 1);
            return hashMap;
        });


        JavaDStream<Map<String, Integer>> pairJavaDStream = pair.reduceByWindow(((p1, p2) -> {
            p2.forEach((k,v) -> {
                if (p1.containsKey(k)){
                    p1.put(k, p1.get(k) + v);
                }else{
                    p1.put(k, v);
                }
            });
            return p1;
        }), ((p1, p2) -> {
                    p2.forEach((k,v) -> {
                        if (p1.containsKey(k)){
                            p1.put(k, p1.get(k) - v);
                        }else{
                            p1.put(k, -v);
                        }
                    });
                    return p1;
                }),
                Minutes.apply(1), Seconds.apply(30));
        pairJavaDStream.foreachRDD(rdd -> rdd.foreach(p -> log.warn("pair-{}", p)));

        ssc.start();
        ssc.awaitTermination();
    }
}
