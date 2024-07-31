package br.unb.cic.flang

import br.unb.cic.flang.Declarations._
import br.unb.cic.flang.MonadTransformers._
import cats.implicits._

object Interpreter {
  def eval(expr: Expr, declarations: List[FDeclaration]): ErrorOrState[Integer] = expr match {
    case CInt(v) => pure(v)

    case Add(lhs, rhs) => for {
      l <- eval(lhs, declarations)
      r <- eval(rhs, declarations)
    } yield l + r

    case Mul(lhs, rhs) => for {
      l <- eval(lhs, declarations)
      r <- eval(rhs, declarations)
    } yield l * r

    case Id(name) => lookupVar(name)

    case App(name, arg) => for {
      fdecl <- lookup(name, declarations)
      argValue <- eval(arg, declarations)
      state <- get
      newState = declareVar(fdecl.arg, argValue, state)
      _ <- put(newState)
      result <- eval(fdecl.body, declarations)
    } yield result
  }
}
