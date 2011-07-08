package org.bitcex.util

import org.eclipse.jetty.server.UserIdentity
import org.bitcex.model.Price

case class CurrencyConverter[F <: Price[F], T <: Price[T]](from: F, to: T, spread: Double = 0.02) {
  val rate = to.amount / from.amount
  val bidRate = rate * (1 + spread)
  val askRate = rate * (1 - spread)

  def bid(from: F): T = to.create(from.amount * bidRate)

  def inverseBid(to: T): F = from.create(to.amount / askRate)

  def ask(from: F): T = to.create(from.amount * askRate)

  def inverseAsk(to: T): F = from.create(to.amount / bidRate)

  def midpoint(from: F): T = to.create(from.amount *rate)
}


