package scaladog.api.metrics

import scaladog.api.DDPickle

private[scaladog] case class PostMetricsRequest(series: Seq[Series])

private[scaladog] object PostMetricsRequest {
  implicit val writer: DDPickle.Writer[PostMetricsRequest] = DDPickle.macroW
}
