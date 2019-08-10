package com.ada.learning.kafka;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class MyPartition implements Partitioner {
    private Gson gson = new Gson();

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        log.warn("cluster-{}", gson.toJson(cluster));
        log.warn("size-{}", cluster.availablePartitionsForTopic(topic).size());
        log.warn("size-{}", cluster.partitionsForTopic(topic).size());
        log.warn("size-{}", gson.toJson(cluster.partitionsForTopic(topic)));
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        return ThreadLocalRandom.current().nextInt(partitions.size());
    }

    @Override
    public void close() {
        log.warn("MyPartition.close");
    }

    @Override
    public void configure(Map<String, ?> configs) {
        log.warn("MyPartition.configure-{}", JSON.toJSONString(configs));
    }
}
