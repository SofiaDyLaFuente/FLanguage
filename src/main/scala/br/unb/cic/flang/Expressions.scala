package br.unb.cic.flang

sealed trait Expr

case class CInt(v: Integer) extends Expr
case class CBool(v: Boolean) extends Expr
case class Add(lhs: Expr, rhs: Expr) extends Expr
case class Sub(lhs: Expr, rhs: Expr) extends Expr
case class Mul(lhs: Expr, rhs: Expr) extends Expr
case class Div(lhs: Expr, rhs: Expr) extends Expr
case class Id(name: String) extends Expr
case class App(name: String, arg: Expr) extends Expr
case class IfThenElse(cond: Expr, thenBranch: Expr, elseBranch: Expr) extends Expr
case class And(lhs: Expr, rhs: Expr) extends Expr
case class Or(lhs: Expr, rhs: Expr) extends Expr
case class Not(expr: Expr) extends Expr
case class Maior(lhs: Expr, rhs: Expr) extends Expr
case class Menor(lhs: Expr, rhs: Expr) extends Expr
case class MaiorIgual(lhs: Expr, rhs: Expr) extends Expr
case class MenorIgual(lhs: Expr, rhs: Expr) extends Expr
case class Equal(lsh: Expr, rsh: Expr) extends Expr
case class Negativo(expr: Expr) extends Expr