package org.bitcex.model

import org.specs.Specification

class CurrencySpec extends Specification {

  "A SEK fund " should {

    "Have an >  method " in {
      SEK(10) > SEK(9) must beTrue
    }

    "Have an >=  method " in {
      SEK(10) >= SEK(9) must beTrue
      SEK(10) >= SEK(10) must beTrue
    }

    "Have an == method" in {
      SEK(3.4) == SEK(3.4) must beTrue
    }

    "Have an < method" in {
      SEK(5) < SEK(5.0000001) must beTrue
    }

     "Have an <= method" in {
      SEK(5) <= SEK(5) must beTrue
      SEK(5) <= SEK(5.01) must beTrue
    }


    "Have a - method" in {
      SEK(5) - SEK(1) must_== SEK(4)
    }

    "Have a + method" in {
      SEK(5) + SEK(1) must_== SEK(6)
    }

    "Have a * Price[T] method" in {
       SEK(5) * SEK(2) must_== SEK(10)
     }

     "Have a * method" in {
       SEK(5) * 2 must_== SEK(10)
     }

    "Have a / Price[T] method" in {
       SEK(5) / SEK(2) must_== SEK(2.5)
     }

     "Have a / method" in {
       SEK(5) / 2 must_== SEK(2.5)
     }

    "Hava a unary negate method" in {
      -SEK(5) must_== SEK(-5)
    }


    "Have a min method" in {
      SEK(5).min(SEK(6)) must_== SEK(5)
    }

    "Have a max method" in {
      SEK(5).max(SEK(6)) must_== SEK(6)
    }

    "Have a  signum method" in {
      SEK(-3).signum must_== -1
      SEK(0).signum must_== 0
      SEK(20).signum must_== 1
    }
  }
}