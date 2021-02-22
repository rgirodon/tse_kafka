# launch kafka platform
confluent local services start

# create topic
kafka-topics --create --topic connect-test --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1

# list all bundled connectors
confluent local services connect connector list

# load connector file-source
confluent local services connect connector load file-source

# see status of connector file-source
confluent local services connect connector status file-source

# create a command line consumer for topic connect-test
kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic connect-test --from-beginning

# load connector file-sink
confluent local services connect connector load file-sink

# see status of connector file-sink
confluent local services connect connector status file-sink

# unload connector file-source
confluent local services connect connector unload file-source

# unload connector file-sink
confluent local services connect connector unload file-sink

# stop kafka platform
confluent local services stop

# delete data
confluent local destroy