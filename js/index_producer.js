const { Kafka } = require('kafkajs')

const kafka = new Kafka({
  clientId: 'my-app',
  brokers: ['localhost:9092'],
});

const producer = kafka.producer();

const run = async () => {
  
  await producer.connect();

  await producer.send({
    topic: 'example-topic',
    messages: [
      { value: 'Message sent from a JS producer !' },
    ],
  });

  await producer.disconnect();
};

run();