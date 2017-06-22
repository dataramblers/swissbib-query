# swissbib-query

A small tool for querying data in bibliographic records of Swissbib and delivering it to a Kafka cluster.

## Set up project

In project root run:

    sbt assembly
    
Afterwards you should encounter an executable jar file in the target folder

    target/scala-2.12/swissbib-query-assembly-0.1.0.jar

## Running

Set up Kafka cluster in pseudo-distributed mode:

**a) Start Servers**


    # Zookeeper Server 
    bin/zookeeper-server-start.sh config/zookeeper.properties
    # Kafka Server
    bin/kafka-server-start.sh config/server.properties


**b) Create the swissbib topic**

    bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic swissbib
    

**c) Run the producer (i.e. the jar file)**

    java -jar target/scala-2.12/swissbib-query-assembly-0.1.0.jar <no of records> <chunk size>
    
**d) Fire up a consumer to see the results**

    bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic swissbib --from-beginning
