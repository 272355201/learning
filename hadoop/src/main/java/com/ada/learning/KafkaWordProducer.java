package com.ada.learning;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class KafkaWordProducer {
    @SneakyThrows
    public static void main(String[] args) {
        /**
         *     val Array(brokers, topic, messagesPerSec, wordsPerMessage) = args
         *     // Zookeeper connection properties
         *     val props = new HashMap[String, Object]()
         *     props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
         *     props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
         *       "org.apache.kafka.common.serialization.StringSerializer")
         *     props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
         *       "org.apache.kafka.common.serialization.StringSerializer")
         */
        log.info("start====");
        System.out.println("start====");
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "ubuntu:9092");
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(configs);

        Random random = new Random();
        while (true){
            StringBuilder sb = new StringBuilder(100);
            for (int i = 0; i < 5; i++) {
                sb.append(random.nextInt(10)).append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            String msg = sb.toString();
            log.warn(msg);
            System.out.println(msg);
            ProducerRecord<String, String> record = new ProducerRecord<>("wordsendertest", msg);
            producer.send(record);
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
