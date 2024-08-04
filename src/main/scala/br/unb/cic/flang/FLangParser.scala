//package br.unb.cic.flang
//
//import cats.parse.{Parser, Parser0}
//import cats.parse.Rfc5234.{alpha, digit}
//
//object FLangParser {
//  // Ignora espaços em branco ao redor
//  val whitespace: Parser[Unit] = Parser.charIn(" \t\r\n").rep.void
//  val whitespace0: Parser0[Unit] = whitespace.rep0.void
//
//  // Inteiros
//  val int: Parser[Expr] = digit.rep.string.map(s => CInt(s.toInt)).surroundedBy(whitespace0)
//
//  // Booleanos
//  val bool: Parser[Expr] = (Parser.string("verdadeiro") | Parser.string("falso")).map {
//    case "verdadeiro" => CBool(true)
//    case "falso" => CBool(false)
//  }.surroundedBy(whitespace0)
//
//  // Identificadores
//  val identifier: Parser[Expr] = (alpha ~ alpha.orElse(digit).rep0).string.map(Id).surroundedBy(whitespace0)
//
//  // Parser para expressão genérica
//  lazy val expr: Parser[Expr] = add | mul | ifThenElse | bool | int | identifier
//
//  // Adição
//  val add: Parser[Expr] = (
//    Parser.string("soma(") *> expr.surroundedBy(whitespace0) ~ (Parser.char(',') *> expr.surroundedBy(whitespace0) <* Parser.char(')'))
//    ).map { case (lhs, rhs) => Add(lhs, rhs) }
//
//  // Multiplicação
//  val mul: Parser[Expr] = (
//    Parser.string("multiplica(") *> expr.surroundedBy(whitespace0) ~ (Parser.char(',') *> expr.surroundedBy(whitespace0) <* Parser.char(')'))
//    ).map { case (lhs, rhs) => Mul(lhs, rhs) }
//
//  // IfThenElse
//  val ifThenElse: Parser[Expr] = (
//    Parser.string("se(") *> expr.surroundedBy(whitespace0) ~
//      (Parser.string(", ") *> expr.surroundedBy(whitespace0) <* Parser.string(", entao(")) ~
//      (expr.surroundedBy(whitespace0) <* Parser.string("), senao(")) ~
//      (expr.surroundedBy(whitespace0) <* Parser.char(')'))
//    ).map { case (((cond, thenBranch), elseBranch)) => IfThenElse(cond, thenBranch, elseBranch) }
//  // Função para analisar uma string em uma expressão
//  def parse(input: String): Either[Parser.Error, Expr] = expr.parseAll(input)
//}

