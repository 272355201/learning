package com.ada.learning.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class KClients {
    private static Producer<String, String> producer;

    public static Producer<String, String> createClient(){
        if (producer == null){
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("acks", "all");
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("partitioner.class", MyPartition.class.getName());
            props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, Collections.singletonList(MyIntercepor.class.getName()));

            producer = new KafkaProducer<>(props);
        }


        return producer;
    }

    public static void sendSomeMsg(Producer<String, String> kPro, String topic){
        for (int i = 0; i < 100; i++){
            kPro.send(new ProducerRecord<String, String>(topic, Integer.toString(i), Integer.toString(i)));
        }
    }

    public static KafkaConsumer<String,String> startConsumer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "broker1:9092,broker2:9092");
        props.put("group.id", "CountryCounter");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return new KafkaConsumer<String,String>(props);
    }
}
