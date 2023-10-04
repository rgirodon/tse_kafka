package org.rygn.first_kafka.avro;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.rygn.first_kafka.avro.domain.Team;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class Producer {

	private static final String TOPIC = "teams-topic";
	
	public static void main(String[] args){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        properties.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        try (KafkaProducer<String, Team> producer = new KafkaProducer<String, Team>(properties)) {

        	Team team1 = new Team("Lakers", "Yellow, purple", "Los Angeles");        	
            ProducerRecord<String, Team> record1 = new ProducerRecord<String, Team>(TOPIC, team1.getName().toString(), team1);
            
            Team team2 = new Team("Knicks", "Blue, orange", "New York");        	
            ProducerRecord<String, Team> record2 = new ProducerRecord<String, Team>(TOPIC, team2.getName().toString(), team2);
            
            producer.send(record1);
            producer.send(record2);

            producer.flush();
            
            System.out.printf("Successfully produced 2 messages to a topic called %s%n", TOPIC);
        } 
        catch (Exception e) {
        	
            e.printStackTrace();
        }
    }
}
