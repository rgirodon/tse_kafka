# launch kafka platform
docker compose up

# connect to broker
docker exec -it broker sh

# list topics
kafka-topics --list --bootstrap-server localhost:9092

# create topic
kafka-topics --create --topic example-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1

# create consumer
kafka-console-consumer --topic example-topic --bootstrap-server localhost:9092

# create producer
kafka-console-producer --topic example-topic --broker-list localhost:9092

# create another consumer
kafka-console-consumer --topic example-topic --bootstrap-server localhost:9092 --from-beginning

# delete topic
kafka-topics --delete --topic example-topic --bootstrap-server localhost:9092

# stop kafka platform
docker compose down