from kafka import KafkaConsumer

consumer = KafkaConsumer('example-topic', group_id='test-group-py', bootstrap_servers=['localhost:9092'])

for msg in consumer:
    print(msg)