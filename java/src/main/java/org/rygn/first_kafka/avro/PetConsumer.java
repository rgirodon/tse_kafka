package org.rygn.first_kafka.avro;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.rygn.first_kafka.avro.domain.Pet;
import org.rygn.first_kafka.avro.domain.Team;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

public class PetConsumer {

	private static final String TOPIC = "pets-topic";
	
	public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true); 
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");

        KafkaConsumer<String, Pet> kafkaConsumer = new KafkaConsumer<String, Pet>(properties);
                
        kafkaConsumer.subscribe(Collections.singletonList(TOPIC), new ConsumerRebalanceListener() {
			
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
                ConsumerRecords<String, Pet> records = kafkaConsumer.poll(Duration.ofMillis(10));
                
                for (ConsumerRecord<String, Pet> record: records){
                	
                    System.out.println(String.format("Value: %s", 
                    									record.value().getKind() 
                    										+ " - " + record.value().getName()));
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
