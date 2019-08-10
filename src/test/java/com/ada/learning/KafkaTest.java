package com.ada.learning;

import com.ada.learning.kafka.KClients;
import com.ada.learning.kafka.MyCallback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.AfterClass;
import org.junit.Test;

public class KafkaTest {
    @Test
    public void test01(){
        Producer<String, String> client = KClients.createClient();
        KClients.sendSomeMsg(client, "test100");
    }

    @Test
    public void test02(){
        Producer<String, String> client = KClients.createClient();
        ProducerRecord<String, String> record = new ProducerRecord<>("test100", "88", "88");
        client.send(record);
    }

    @Test
    public void test03(){
        Producer<String, String> client = KClients.createClient();
        ProducerRecord<String, String> record = new ProducerRecord<>("test100", "88", "88");
        client.send(record, new MyCallback());
    }

    @AfterClass
    public static void after(){
        KClients.createClient().close();
    }
}
