package org.bitcex

/*
trait  Currency {
  def >(bd:BigDecimal) = amount > bd
  def -= (bd:BigDecimal) = copy(amount - bd)
}
*/

sealed abstract class Fund[T] {
  val amount: BigDecimal

  def create(amount: BigDecimal): T

  def >(fund: Fund[T]) = amount > fund.amount

  def <(fund: Fund[T]) = amount < fund.amount

  def -(fund: Fund[T]) = create(amount - fund.amount)

  def +(fund: Fund[T]) = create(amount + fund.amount)

  def *(factor: BigDecimal) = create(amount * factor)
}

case class SEK(amount: BigDecimal) extends Fund[SEK] {
  def create(amount: BigDecimal) = copy(amount);
}

case class EUR(amount: BigDecimal) extends Fund[EUR] {
  def create(amount: BigDecimal) = copy(amount);
}

case class USD(amount: BigDecimal) extends Fund[USD] {
  def create(amount: BigDecimal) = copy(amount);
}

case class BTC(amount: BigDecimal) extends Fund[BTC] {
  def create(amount: BigDecimal) = copy(amount)
}


case class CurrencyConverter[F <: Fund[F], T <: Fund[T]](val from: F, val to: T, spread: Double = 0.02) {
  val rate = to.amount / from.amount
  val bidRate = rate * (1 + spread)
  val askRate = rate * (1 - spread)

  def bid(from: F): T = to.create(from.amount * bidRate)

  def inverseBid(to: T): F = from.create(to.amount / askRate)

  def ask(from: F): T = to.create(from.amount * askRate)

  def inverseAsk(to: T): F = from.create(to.amount / bidRate)

  def midpoint(to: T): F = from.create(to.amount / rate)
}


abstract class Order[T, S] {
  val amount: Fund[T]
  val price: Fund[S]

  def total: S = price * amount.amount
}

case class SellOrderSEK(amount: BTC, price: SEK) extends Order[BTC, SEK]

case class BuyOrderSEK(amount: BTC, price: SEK) extends Order[BTC, SEK]

