kafka-topics --create --topic src-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1

kafka-topics --create --topic tgt-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1

kafka-console-producer --topic src-topic --broker-list localhost:9092

kafka-console-consumer --topic tgt-topic --bootstrap-server localhost:9092