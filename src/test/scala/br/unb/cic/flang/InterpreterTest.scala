package br.unb.cic.flang

import br.unb.cic.flang.Declarations._
import br.unb.cic.flang.Interpreter._
import br.unb.cic.flang.MonadTransformers._
import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers._

class InterpreterTest extends AnyFlatSpec with should.Matchers {

  val inc = FDeclaration("inc", "x", Add(Id("x"), CInt(1)))
  val bug = FDeclaration("bug", "x", Add(Id("y"), CInt(1)))

  val declarations = List(inc, bug)

  val initialState: List[(String, Integer)] = List()

  "eval CInt(5)" should "return an integer value 5." in {
    val c5 = CInt(5)
    val result = eval(c5, declarations).runA(initialState)
    result should be (Right(5))
  }

  "eval CBool(true)" should "return an integer value 1." in {
    val ctrue = CBool(true)
    val result = eval(ctrue, declarations).runA(initialState)
    result should be (Right(1))
  }

  "eval CBool(false)" should "return an integer value 0." in {
    val cfalse = CBool(false)
    val result = eval(cfalse, declarations).runA(initialState)
    result should be (Right(0))
  }

  "eval Add(CInt(5), CInt(10)) " should "return an integer value 15." in {
    val c5  = CInt(5)
    val c10 = CInt(10)
    val add = Add(c5, c10)
    val result = eval(add, declarations).runA(initialState)
    result should be (Right(15))
  }

  "eval Add(CInt(5), Add(CInt(5), CInt(10))) " should "return an integer value 20." in {
    val c5 = CInt(5)
    val c10 = CInt(10)
    val add = Add(c5, Add(c5, c10))
    val result = eval(add, declarations).runA(initialState)
    result should be(Right(20))
  }

  "eval Mul(CInt(5), CInt(10))" should "return an integer value 50" in {
    val c5 = CInt(5)
    val c10 = CInt(10)
    val mul = Mul(c5, CInt(10))
    val result = eval(mul, declarations).runA(initialState)
    result should be(Right(50))
  }

  "eval App(inc, 99) " should "return an integer value 100" in {
    val app = App("inc", CInt(99))
    val result = eval(app, declarations).runA(initialState)
    result should be (Right(100))
  }

  "eval App(foo, 10) " should "raise an error." in {
    val app = App("foo", CInt(10))
    val result = eval(app, declarations).runA(initialState)
    result should be (Left("Function foo is not declared"))
  }

  "eval Add(5, App(bug, 10)) " should "raise an error." in {
    val c5  = CInt(5)
    val app = App("bug", CInt(10))
    val add = Add(c5, app)
    val result = eval(add, declarations).runA(initialState)
    result should be (Left("Variable y not found"))
  }

  "eval IfThenElse(CBool(true), CInt(1), CInt(2))" should "return 1" in {
    val ite = IfThenElse(CBool(true), CInt(1), CInt(2))
    val result = eval(ite, declarations).runA(initialState)
    result should be (Right(1))
  }

  "eval IfThenElse(CBool(false), CInt(1), CInt(2))" should "return 2" in {
    val condicional = IfThenElse(CBool(false), CInt(1), CInt(2))
    val result = eval(condicional, declarations).runA(initialState)
    result should be (Right(2))
  }

  "eval And(CBool(true), CBool(true))" should "return 1" in {
    val andExpr = And(CBool(true), CBool(true))
    val result = eval(andExpr, declarations).runA(initialState)
    result should be (Right(1))
  }

  "eval Or(CBool(true), CBool(false))" should "return 1" in {
    val orExpr = Or(CBool(true), CBool(false))
    val result = eval(orExpr, declarations).runA(initialState)
    result should be (Right(1))
  }

  "eval Not(CBool(true))" should "return 0" in {
    val notExpr = Not(CBool(true))
    val result = eval(notExpr, declarations).runA(initialState)
    result should be (Right(0))
  }

  "eval Not(CBool(false))" should "return 1" in {
    val notExpr = Not(CBool(false))
    val result = eval(notExpr, declarations).runA(initialState)
    result should be (Right(1))
  }
}
