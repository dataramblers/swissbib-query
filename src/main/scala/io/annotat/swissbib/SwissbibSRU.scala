package io.annotat.swissbib

import java.time.LocalDate

import scala.io.Source

/**
  * @author Sebastian Schüpbach
  * @version 0.1
  *
  *          Created on 6/19/17
  */
object SwissbibSRU extends App {

  val records = args(0).toInt
  val chunksize = args(1).toInt
  val kafkaTopic = "swissbib"
  val kafkaBrokers = "localhost:9092"

  val producer = new SbKafkaProducer(kafkaTopic, kafkaBrokers)

  for (i <- Range(0, records, chunksize)) {
    val sq = SearchQuery(startRecord = i, maximumRecords = chunksize).toString
    val res = Source.fromURL(sq, "UTF-8").mkString
    val localDate = LocalDate.now().toString
    producer.send(localDate, res)
  }

  producer.close()

}
