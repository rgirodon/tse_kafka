package org.rygn.first_kafka.avro;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.rygn.first_kafka.avro.domain.Kind;
import org.rygn.first_kafka.avro.domain.Pet;
import org.rygn.first_kafka.avro.domain.Team;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class PetProducer {

	private static final String TOPIC = "teams-topic";
	
	public static void main(String[] args){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        properties.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        try (KafkaProducer<String, Pet> producer = new KafkaProducer<String, Pet>(properties)) {

        	Pet pet1 = new Pet(Kind.DOG, "Taïgo");        	
            ProducerRecord<String, Pet> record1 = new ProducerRecord<String, Pet>(TOPIC, pet1.getName().toString(), pet1);
            
            Pet pet2 = new Pet(Kind.FISH, "Grenade");        	
            ProducerRecord<String, Pet> record2 = new ProducerRecord<String, Pet>(TOPIC, pet2.getName().toString(), pet2);
            
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
