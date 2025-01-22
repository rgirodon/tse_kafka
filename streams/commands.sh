kafka-topics --create --topic src-streams-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1

kafka-topics --create --topic tgt-streams-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1

kafka-console-producer --topic src-streams-topic --broker-list localhost:9092

kafka-console-consumer --topic tgt-streams-topic --bootstrap-server localhost:9092

kafka-topics --delete --topic src-streams-topic --bootstrap-server localhost:9092

kafka-topics --delete --topic tgt-streams-topic --bootstrap-server localhost:9092