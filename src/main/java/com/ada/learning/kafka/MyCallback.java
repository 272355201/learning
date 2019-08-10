package com.ada.learning.kafka;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

@Slf4j
public class MyCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        log.warn("MyCallback-send success-{}", new Gson().toJson(metadata));
        log.warn("MyCallback-send exc-" + String.valueOf(exception));
        if (exception != null){
            exception.printStackTrace();
        }
    }
}
