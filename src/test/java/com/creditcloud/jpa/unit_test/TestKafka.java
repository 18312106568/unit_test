package com.creditcloud.jpa.unit_test;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Future;

public class TestKafka {

    @Test
    public void testProducer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.11.207:9092");
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = null;
        try {
            producer = new KafkaProducer<>(properties);
//            producer.send(new ProducerRecord<String, String>("HelloWorld","aaaaaaaaaaaa"));
            for (int i = 0; i < 1; i++) {
                String msg = "This is Message " + i;
                ProducerRecord record = new ProducerRecord("helloworld1", msg);
                Future future = producer.send(record);
                Object result = future.get();
                System.out.println("Sent:" + result+result.getClass());
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            producer.close();
        }
    }


    @Test
    public void testConsumer(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.11.207:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"group-3");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Arrays.asList("helloworld1"));
        while (true) {
            Duration duration = Duration.ofMillis(1000000);
            ConsumerRecords<String, String> records = kafkaConsumer.poll(duration);
            //ConsumerRecords<String, String> records = kafkaConsumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, value = %s", record.offset(), record.value());
                System.out.println("=====================>");
            }
        }
    }
}
