package org.bitcex.camel

import org.slf4j.LoggerFactory
import akka.actor.{ActorRef, Actor}
import akka.camel.{Producer, Consumer, Message}
import org.bitcex.model.{SEK, Ticker}
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired

@Component
@Autowired
class IndexActor(ticker: ActorRef, velocity: ActorRef) extends Actor with Consumer {

  val log = LoggerFactory.getLogger(getClass)

//  val uri = "jetty:http://0.0.0.0:8877/"
  def endpointUri =  "servlet:///"

  log.info("Buyer started at {}", endpointUri)


  def receive = {
    case msg: Message => {
      ticker.forward(GetTicker(Some(velocity)))
    }
    case msg => self.reply("Uh?" + msg)
  }
}

@Component
class VelocityActor extends Actor with Producer {

  val log = LoggerFactory.getLogger(getClass)

  def endpointUri = "velocity:index.vm"

  override protected def receiveBeforeProduce = {
    case Some(ticker: Ticker[SEK]) => {
      val headers = Map("ask" -> ticker.ask.rounded,
        "last" -> ticker.last.rounded,
        "bid" -> ticker.bid.rounded)
      Message("should be replaced by velocity template", headers)
    }
    case msg => log.info("Ignored messsage {}", msg)
  }
}