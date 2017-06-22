package io.annotat.swissbib

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * @author Sebastian Schüpbach
  * @version 0.1
  *
  *          Created on 6/19/17
  */
class SbKafkaProducer(topic: String, brokers: String) {

  val props = new Properties()
  props.put("bootstrap.servers", brokers)
  props.put("group.id", "SwissbibRecords")
  props.put("client.id", "SwissbibRecords")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")


  val producer = new KafkaProducer[String, String](props)

  def send(range: String, msg: String): Unit = {
    val data = new ProducerRecord[String, String](topic, range, msg)
    producer.send(data)
  }

  def close(): Unit = {
    producer.close()
  }
}
