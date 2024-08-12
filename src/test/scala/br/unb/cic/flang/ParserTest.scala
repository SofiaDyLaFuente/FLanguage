package br.unb.cic.flang

import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers._

class ParserTest extends AnyFlatSpec with should.Matchers {

  "parse CInt(5)" should "return a CInt(5) expression." in {
    val input = "5"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be(CInt(5))
  }

  "parse CInt(8)" should "return a CInt(5) expression." in {
    val input = "8"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be(CInt(8))
  }

  "parse 3 + 2" should "return an Add(CInt(3), CInt(2)) expression." in {
    val input = "3 + 2"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be(Add(CInt(3), CInt(2)))
  }

  "parse 50 + 30" should "return an Add(CInt(3), CInt(2)) expression." in {
    val input = "50 + 30"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be(Add(CInt(50), CInt(30)))
  }

  "parse if 1 then 2 else 3" should "return an IfThenElse(CInt(1), CInt(2), CInt(3)) expression." in {
    val input = "if 1 then 2 else 3"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be(IfThenElse(CInt(1), CInt(2), CInt(3)))
  }

  "parse true and false" should "return an And(CBool(true), CBool(false)) expression." in {
    val input = "true and false"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be(And(CBool(true), CBool(false)))
  }

  "parse true or false" should "return an Or(CBool(true), CBool(false)) expression." in {
    val input = "true or false"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be(Or(CBool(true), CBool(false)))
  }
  "parse 10 - 5" should "return a Sub(CInt(10), CInt(5)) expression." in {
    val input = "10 - 5"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be(Sub(CInt(10), CInt(5)))
  }

  "parse 8 * 7" should "return a Mul(CInt(8), CInt(7)) expression." in {
    val input = "8 * 7"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be(Mul(CInt(8), CInt(7)))
  }

  "parse 56 / 8" should "return a Div(CInt(56), CInt(8)) expression." in {
    val input = "56 / 8"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be(Div(CInt(56), CInt(8)))
  }

  "parse 3 + 2 * 5" should "return an Add(CInt(3), Mul(CInt(2), CInt(5))) expression." in {
    val input = "3 + 2 * 5"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be(Add(CInt(3), Mul(CInt(2), CInt(5))))
  }

  "parse (3 + 2) * 5" should "return a Mul(Add(CInt(3), CInt(2)), CInt(5)) expression." in {
    val input = "(3 + 2) * 5"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be(Mul(Add(CInt(3), CInt(2)), CInt(5)))
  }
}