package br.unb.cic.flang

import cats.data.StateT
import cats.implicits._
import br.unb.cic.flang.Declarations._
import br.unb.cic.flang.MonadTransformers._

object Interpreter {
  def eval(expr: Expr, declarations: List[FDeclaration]): ErrorOrState[Int] = expr match {
    case CInt(v) => pure(v)

    case Add(lhs, rhs) => for {
      l <- eval(lhs, declarations)
      r <- eval(rhs, declarations)
    } yield l + r

    case Mul(lhs, rhs) => for {
      l <- eval(lhs, declarations)
      r <- eval(rhs, declarations)
    } yield l * r

    case Id(name) => for {
      state <- get
      value <- StateT.liftF(lookupVar(name, state))
    } yield value

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
