package org.rygn.first_kafka.basic;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class Consumer {

	public static void main(String[] args) {
		
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group_java");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        
        List<String> topics = new ArrayList<String>();
        topics.add("example-topic");
        
        kafkaConsumer.subscribe(topics, new ConsumerRebalanceListener() {
			
			@Override
			public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
				// nothing to do
				
			}
			
			@Override
			public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
				
				// to be sure that partition are assigned when we reset them
				kafkaConsumer.seekToBeginning(partitions);
			}
		});        
                        
        try{
            while (true){
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10));
                
                for (ConsumerRecord<String, String> record: records){
                	
                    System.out.println(String.format("Topic - %s, Partition - %d, Value: %s", record.topic(), record.partition(), record.value()));
                }                
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            kafkaConsumer.close();
        }
    }
}
