package br.unb.cic.flang

import br.unb.cic.flang.Declarations._
import br.unb.cic.flang.Interpreter._
import br.unb.cic.flang.StateMonad._
import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers._

class InterpreterTest extends AnyFlatSpec with should.Matchers {

  val inc = FDeclaration("inc", "x", Add(Id("x"), CInt(1)))

  val declarations = List(inc)

  val initialState: S = List()

  "eval CInt(5)" should "return an integer value 5." in {
    val c5 = CInt(5)
    val result = eval(c5, declarations).runA(initialState).value
    result should be (5)
  }

  "eval Add(CInt(5), CInt(10)) " should "return an integer value 15." in {
    val c5  = CInt(5)
    val c10 = CInt(10)
    val add = Add(c5, c10)
    val result = eval(add, declarations).runA(initialState).value
    result should be (15)
  }

  "eval Add(CInt(5), Add(CInt(5), CInt(10))) " should "return an integer value 20." in {
    val c5 = CInt(5)
    val c10 = CInt(10)
    val add = Add(c5, Add(c5, c10))
    val result = eval(add, declarations).runA(initialState).value
    result should be(20)
  }

  "eval Mul(CInt(5), CInt(10))" should "return an integer value 50" in {
    val c5 = CInt(5)
    val c10 = CInt(10)
    val mul = Mul(c5, c10)
    val result = eval(mul, declarations).runA(initialState).value
    result should be(50)
  }

  "eval App(inc, 99) " should "return an integer value 100" in {
    val app = App("inc", CInt(99))
    val result = eval(app, declarations).runA(initialState).value
    result should be (100)
  }
}