package br.unb.cic.flang

import org.scalatest._
import org.scalatest.flatspec._
import org.scalatest.matchers._

class ParserTest extends AnyFlatSpec with should.Matchers {

  "parse CInt(5)" should "return a CInt(5) expression." in {
    val input = "5"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be (CInt(5))
  }

  "parse CInt(8)" should "return a CInt(5) expression." in {
    val input = "8"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be (CInt(8))
  }

  "parse soma(5, 4)" should "return an Add(CInt(3), CInt(2)) expression." in {
    val input = "soma(3, 2)"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be (Add(CInt(3), CInt(2)))
  }

  "parse soma(50, 32)" should "return an Add(CInt(3), CInt(2)) expression." in {
    val input = "soma(3, 2)"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be (Add(CInt(3), CInt(2)))
  }

  "parse if 1 then 2 else 3" should "return an IfThenElse(CInt(1), CInt(2), CInt(3)) expression." in {
    val input = "if 1 then 2 else 3"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be (IfThenElse(CInt(1), CInt(2), CInt(3)))
  }

  "parse and(true, false)" should "return an And(CBool(true), CBool(false)) expression." in {
    val input = "and(true, false)"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be (And(CBool(true), CBool(false)))
  }

  "parse or(true, false)" should "return an Or(CBool(true), CBool(false)) expression." in {
    val input = "or(true, false)"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be (Or(CBool(true), CBool(false)))
  }

  "parse not(true)" should "return a Not(CBool(true)) expression." in {
    val input = "not(true)"
    val parsedExpr = FLangParser.parse(input)
    parsedExpr should be (Not(CBool(true)))
  }
}