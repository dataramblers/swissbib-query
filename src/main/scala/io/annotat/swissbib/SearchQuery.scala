package io.annotat.swissbib

import java.net.URLEncoder

import scala.annotation.tailrec

/**
  * @author Sebastian Schüpbach
  * @version 0.1
  *
  *          Created on 6/19/17
  */
case class SearchQuery(db: String = "defaultdb",
                       query: List[QueryToken] = List(QueryToken("", "", "", "")),
                       recordSchema: String = "info:srw/schema/1/marcxml-v1.1-light",
                       maximumRecords: Int = 10,
                       startRecord: Int = 0,
                       recordPacking: String = "XML") {

  override def toString: String = {
    val urlPrefix = "http://sru.swissbib.ch/sru/search/"
    val urlPostfix = "&availableDBs=" + db + "&sortKeys=Submit+query"

    @tailrec def queryBuilder(agg: String, l: List[QueryToken]): String = l match {
      case head :: Nil if agg != "" => head.build(true)
      case head :: Nil => agg + "+" + head.build(true)
      case head :: tail if agg != "" => queryBuilder(agg + "+" + head.build(false), tail)
      case head :: tail => queryBuilder(head.build(false), tail)
      case _ => agg
    }

    urlPrefix + db + "?query=" + queryBuilder("", query) +
      "&operation=searchRetrieve&recordSchema=" + URLTools.urlencode(recordSchema) +
      "&maximumRecords=" + maximumRecords.toString +
      "&startRecord=" + startRecord.toString +
      "&recordPacking=" + recordPacking + urlPostfix
  }

}

case class QueryToken(key: String, relation: String, value: String, operator: String) {

  def build(finalize: Boolean): String = {
    if (value != "")
      key + "+" + URLTools.urlencode(relation) + "+" + URLTools.urlencode(relation) + (if (!finalize) "+" + operator else "")
    else
      ""
  }
}

object URLTools {

  def urlencode(str: String): String = {
    URLEncoder.encode(str, "UTF-8")
  }
}
