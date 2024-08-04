//package br.unb.cic.flang
//
//import br.unb.cic.flang.FLangParser._
//import org.scalatest._
//import org.scalatest.flatspec._
//import org.scalatest.matchers._
//
//class ParserTest extends AnyFlatSpec with should.Matchers {
//
//  "Parser for integer literals" should "parse '5' as CInt(5)" in {
//    val result = parse("5")
//    result should be(Right(CInt(5)))
//  }
//
//  "Parser for boolean literals" should "parse 'verdadeiro' as CBool(true)" in {
//    val result = parse("verdadeiro")
//    result should be(Right(CBool(true)))
//  }
//
//  "Parser for boolean literals" should "parse 'falso' as CBool(false)" in {
//    val result = parse("falso")
//    result should be(Right(CBool(false)))
//  }
//
//  "Parser for addition expressions" should "parse 'soma(3, 7)' as Add(CInt(3), CInt(7))" in {
//    val result = parse("soma(3, 7)")
//    result should be(Right(Add(CInt(3), CInt(7))))
//  }
//
//  "Parser for multiplication expressions" should "parse 'multiplica(3, 7)' as Mul(CInt(3), CInt(7))" in {
//    val result = parse("multiplica(3, 7)")
//    result should be(Right(Mul(CInt(3), CInt(7))))
//  }
//
//  "Parser for if-then-else expressions" should "parse 'se(verdadeiro, entao(1), senao(2))' as IfThenElse(CBool(true), CInt(1), CInt(2))" in {
//    val result = parse("se(verdadeiro, entao(1), senao(2))")
//    result should be(Right(IfThenElse(CBool(true), CInt(1), CInt(2))))
//  }
//
//  "Parser for and expressions" should "parse 'e(verdadeiro, falso)' as And(CBool(true), CBool(false))" in {
//    val result = parse("e(verdadeiro, falso)")
//    result should be(Right(And(CBool(true), CBool(false))))
//  }
//
//  "Parser for or expressions" should "parse 'ou(verdadeiro, falso)' as Or(CBool(true), CBool(false))" in {
//    val result = parse("ou(verdadeiro, falso)")
//    result should be(Right(Or(CBool(true), CBool(false))))
//  }
//
//  "Parser for not expressions" should "parse 'nao(verdadeiro)' as Not(CBool(true))" in {
//    val result = parse("nao(verdadeiro)")
//    result should be(Right(Not(CBool(true))))
//  }
//
//  "Parser for function applications" should "parse 'inc(5)' as App('inc', CInt(5))" in {
//    val result = parse("inc(5)")
//    result should be(Right(App("inc", CInt(5))))
//  }
//
//  "Parser for undefined functions" should "return a Left for undefined functions" in {
//    val result = parse("undefinedFunction(5)")
//    result shouldBe a[Left[_, _]]
//  }
//
//  "Parser for invalid syntax" should "return a Left for invalid syntax" in {
//    val result = parse("se(verdadeiro, entao(1), senao)")
//    result shouldBe a[Left[_, _]]
//  }
//}