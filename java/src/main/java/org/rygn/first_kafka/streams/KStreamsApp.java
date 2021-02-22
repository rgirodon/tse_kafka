package org.rygn.first_kafka.streams;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;

public class KStreamsApp {
	
	public static void main(String[] args) throws Exception {
		
		Properties props = new Properties();
	    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "rygn_first_kstreams_app");
	    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        Serde<String> stringSerde = Serdes.String();

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> simpleFirstStream = builder.stream("src-topic", Consumed.with(stringSerde, stringSerde));
        
        KStream<String, String> upperCasedStream = simpleFirstStream.mapValues(
        		
        	new ValueMapper<String, String>() {

				@Override
				public String apply(String value) {
	
					return value.toUpperCase();
				}
        	}
        );
                
        upperCasedStream.to("tgt-topic", Produced.with(stringSerde, stringSerde));

        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), props);
        kafkaStreams.start();
        
        Thread.sleep(35000);
        
        kafkaStreams.close();
	}

}
