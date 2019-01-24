# Spring Boot RabbitMQ Consumer

Project Subscribe that implements the messaging pattern Publish/Subscribe.

1. Requirements:
	* Java >= 1.8
	* RabbitMQ

2. Clone project:
```
git clone https://github.com/dabliuw22/sb-rabbitmq-listener.git
```

3. Run two instances and add VM values :
	* Instance 1: `-Dserver.port=8081 -Drabbitmq.queue.name=myFanoutQueueOne`
	* Instance 1: `-Dserver.port=8082 -Drabbitmq.queue.name=myFanoutQueueTwo`

4. Run the Publish service