package br.unb.cic.flang

import scala.util.parsing.combinator._

class FLParser extends RegexParsers {

  // Espaços em branco opcionais
  override def skipWhitespace = true

  // Parser para expressões
  def expr: Parser[Expr] =  ifThenElse | and | or | not | add | mul | bool | const | ident | app

  // Parser para inteiros
  def const: Parser[Expr] = """(0|[1-9]\d*)""".r ^^ { case n => CInt(n.toInt) }

  // Parser para booleanos
  def bool: Parser[Expr] = ("true" | "false") ^^ {
    case "true" => CBool(true)
    case "false" => CBool(false)
  }

  // Parser para identificadores
  def ident: Parser[Expr] = """[a-zA-Z_]\w*""".r ^^ { case name => Id(name) }

  // Parser para soma: soma(3, 2)
  def add: Parser[Expr] = "soma" ~ "(" ~ expr ~ "," ~ expr ~ ")" ^^ {
    case _ ~ _ ~ lhs ~ _ ~ rhs ~ _ => Add(lhs, rhs)
  }

  // Parser para multiplicação: mul(3, 2)
  def mul: Parser[Expr] = "mul" ~ "(" ~ expr ~ "," ~ expr ~ ")" ^^ {
    case _ ~ _ ~ lhs ~ _ ~ rhs ~ _ => Mul(lhs, rhs)
  }

  // Parser para if-then-else
  def ifThenElse: Parser[Expr] =
    ("if" ~> expr) ~ ("then" ~> expr) ~ ("else" ~> expr) ^^ {
      case cond ~ thenExpr ~ elseExpr => IfThenElse(cond, thenExpr, elseExpr)
    }

  // Parser para operação AND: and(expr1, expr2)
  def and: Parser[Expr] = "and" ~ "(" ~ expr ~ "," ~ expr ~ ")" ^^ {
    case _ ~ _ ~ lhs ~ _ ~ rhs ~ _ => And(lhs, rhs)
  }

  // Parser para operação OR: or(expr1, expr2)
  def or: Parser[Expr] = "or" ~ "(" ~ expr ~ "," ~ expr ~ ")" ^^ {
    case _ ~ _ ~ lhs ~ _ ~ rhs ~ _ => Or(lhs, rhs)
  }

  // Parser para operação NOT: not(expr)
  def not: Parser[Expr] = "not" ~ "(" ~ expr ~ ")" ^^ {
    case _ ~ _ ~ expr ~ _ => Not(expr)
  }

  // Parser para aplicação de função: funcao(3)
  def app: Parser[Expr] = ident ~ "(" ~ expr ~ ")" ^^ {
    case Id(name) ~ _ ~ arg ~ _ => App(name, arg)
  }
}

object FLangParser {
  def parse(input: String): Expr = {
    val parser = new FLParser
    parser.parseAll(parser.expr, input) match {
      case parser.Success(result: Expr, _) => result
      case parser.NoSuccess(msg, _) => throw new RuntimeException(s"Parsing failed: $msg")
    }
  }
}
