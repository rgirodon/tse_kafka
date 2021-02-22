# launch kafka platform
confluent local services start

# create topic
kafka-topics --create --topic example-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1

# create consumer
kafka-console-consumer --topic example-topic --bootstrap-server localhost:9092

# create producer
kafka-console-producer --topic example-topic --broker-list localhost:9092

# create another consumer
kafka-console-consumer --topic example-topic --bootstrap-server localhost:9092 --from-beginning

# stop kafka platform
confluent local services stop

# delete data
confluent local destroy