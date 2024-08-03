package br.unb.cic.flang

import br.unb.cic.flang.Declarations._
import br.unb.cic.flang.MonadTransformers._
import cats.implicits._

object Interpreter {
  def eval(expr: Expr, declarations: List[FDeclaration]): ErrorAndState[Integer] = expr match {

    case CInt(v) => pure(v)

    case CBool(v) => pure(if (v) 1 else 0)

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

    case IfThenElse(cond, thenBranch, elseBranch) => for {
      condValue <- eval(cond, declarations)
      booleanCond <- if (condValue == 0) pure(false) else if (condValue == 1) pure(true) else raiseError("Condition in IfThenElse is not a boolean")
      result <- if (booleanCond) eval(thenBranch, declarations) else eval(elseBranch, declarations)
    } yield result

    case And(lhs, rhs) => for {
      l <- eval(lhs, declarations)
      r <- eval(rhs, declarations)
    } yield if (l == 1 && r == 1) 1 else 0

    case Or(lhs, rhs) => for {
      l <- eval(lhs, declarations)
      r <- eval(rhs, declarations)
    } yield if (l == 1 || r == 1) 1 else 0

    case Not(expr) => for {
      value <- eval(expr, declarations)
    } yield if (value == 0) 1 else 0
  }
}