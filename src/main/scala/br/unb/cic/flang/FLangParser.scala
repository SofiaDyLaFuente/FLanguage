package br.unb.cic.flang

import scala.util.parsing.combinator._


class FLParser extends RegexParsers {

  // Espaços em branco opcionais
  override def skipWhitespace = true

  // Parser para expressões
  def expr: Parser[Expr] =  ifThenElse | or

  // Expressões mais simples (números, identificadores, booleanos, etc.)
  def simpleExpr: Parser[Expr] = const | bool | ident | app | "(" ~> expr <~ ")"

  // Parser para inteiros
  def const: Parser[Expr] = """(0|[1-9]\d*)""".r ^^ { case n => CInt(n.toInt) }

  // Parser para booleanos
  def bool: Parser[Expr] = ("true" | "false") ^^ {
    case "true" => CBool(true)
    case "false" => CBool(false)
  }

  // Parser para identificadores
  def ident: Parser[Expr] = """[a-zA-Z_]\w*""".r ^^ { case name => Id(name) }


  // Parser para if-then-else
  def ifThenElse: Parser[Expr] =
    ("if" ~> expr) ~ ("then" ~> expr) ~ ("else" ~> expr) ^^ {
      case cond ~ thenExpr ~ elseExpr => IfThenElse(cond, thenExpr, elseExpr)
    }

  // Parser para termos (soma e subtração)
  def term: Parser[Expr] = factor ~ rep(("+" | "-") ~ factor) ^^ {
    case lhs ~ rhs => rhs.foldLeft(lhs) {
      case (left, "+" ~ right) => Add(left, right)
      case (left, "-" ~ right) => Sub(left, right)
    }
  }

  // Parser para fatores (multiplicação e divisão)
  def factor: Parser[Expr] = simpleExpr ~ rep(("*" | "/") ~ simpleExpr) ^^ {
    case lhs ~ rhs => rhs.foldLeft(lhs) {
      case (left, "*" ~ right) => Mul(left, right)
      case (left, "/" ~ right) => Div(left, right)
    }
  }

  // Parser para or
  def or: Parser[Expr] = and ~ rep("or" ~ and) ^^ {
    case lhs ~ rhs => rhs.foldLeft(lhs) {
      case (left, _ ~ right) => Or(left, right)
    }
  }

  // Parser para and
  def and: Parser[Expr] = term ~ rep("and" ~ term) ^^ {
    case lhs ~ rhs => rhs.foldLeft(lhs) {
      case (left, _ ~ right) => And(left, right)
    }
  }

//  // Parser para comparação (<, >, <=, >=)
//  def comparison: Parser[Expr] = simpleExpr ~ (("<=" | ">=" | "<" | ">" ) ~ simpleExpr) ^^ {
//    case lhs ~ (op ~ rhs) => op match {
//      case "<=" => MenorIgual(lhs, rhs)
//      case ">=" => MaiorIgual(lhs, rhs)
//      case "<" => Menor(lhs, rhs)
//      case ">" => Maior(lhs, rhs)
//      case _ => throw new MatchError(s"Operador de comparação não reconhecido: $op")
//    }
//  }

//  // Parser para negação
//  def negation: Parser[Expr] = "-" ~> simpleExpr ^^ { Negativo(_) }
//

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