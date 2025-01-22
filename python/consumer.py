# pip install kafka-python

from kafka import KafkaConsumer

consumer = KafkaConsumer('example-topic', group_id='test-group-py', bootstrap_servers=['localhost:9092'], auto_offset_reset='earliest', enable_auto_commit=False)

for msg in consumer:
    print(msg)