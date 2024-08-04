package br.unb.cic.flang

import cats.parse.Rfc5234.{alpha, digit}
import cats.parse.{Parser, Parser0}

object FLangParser {

  import cats.parse.Parser._

  val whitespace: Parser[Unit] = charIn(" \t\r\n").rep.void
  val whitespace0: Parser0[Unit] = whitespace.rep0.void

  val int: Parser[Expr] = digit.rep.string.map(s => CInt(s.toInt)).surroundedBy(whitespace0)

  val bool: Parser[Expr] =
    (string("verdadeiro").map(_ => CBool(true)) |
      string("falso").map(_ => CBool(false))
      ).surroundedBy(whitespace)

  val identifier: Parser[Expr] = (alpha ~ (alpha.orElse(digit).rep0)).string.map(Id).surroundedBy(whitespace0)

  val add: Parser[Expr] = (
    string("soma(") *> expr.surroundedBy(whitespace0) ~
      (char(',') *> expr.surroundedBy(whitespace0) <* char(')'))
    ).map { case (lhs, rhs) => Add(lhs, rhs) }

  val mul: Parser[Expr] = (
    string("multiplica(") *> expr.surroundedBy(whitespace0) ~
      (char(',') *> expr.surroundedBy(whitespace0) <* char(')'))
    ).map { case (lhs, rhs) => Mul(lhs, rhs) }

  val ifThenElse: Parser[Expr] = for {
    _ <- string("se(")
    cond <- expr.surroundedBy(whitespace0)
    _ <- string(", ")
    thenBranch <- expr.surroundedBy(whitespace0)
    _ <- string(", ")
    _ <- string("entao(")
    thenBranchParsed <- expr.surroundedBy(whitespace0)
    _ <- string("), ")
    _ <- string("senao(")
    elseBranch <- expr.surroundedBy(whitespace0)
    _ <- char(')')
  } yield IfThenElse(cond, thenBranchParsed, elseBranch)

  val and: Parser[Expr] = (
    string("e(") *> expr.surroundedBy(whitespace0) ~
      (char(',') *> expr.surroundedBy(whitespace0) <* char(')'))
    ).map { case (lhs, rhs) => And(lhs, rhs) }

  val or: Parser[Expr] = (
    string("ou(") *> expr.surroundedBy(whitespace0) ~
      (char(',') *> expr.surroundedBy(whitespace0) <* char(')'))
    ).map { case (lhs, rhs) => Or(lhs, rhs) }

  val not: Parser[Expr] = (
    string("nao(") *> expr.surroundedBy(whitespace0) <* char(')')
    ).map(Not)

  lazy val expr: Parser[Expr] =
    add | mul | ifThenElse | bool | int | identifier | and | or | not

  // Função para analisar uma string em uma expressão
  def parse(input: String): Either[Parser.Error, Expr] = {
    val result = expr.parseAll(input)
    result
  }
}