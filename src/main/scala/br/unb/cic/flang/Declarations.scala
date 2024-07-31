package br.unb.cic.flang

import br.unb.cic.flang.MonadTransformers._
import cats.implicits._

case class FDeclaration(name: String, arg: String, body: Expr)

object Declarations {
  def lookup(
              name: String,
              declarations: List[FDeclaration]
            ): ErrorOrState[FDeclaration] = declarations match {
    case Nil => raiseError(s"Function $name is not declared")
    case (f @ FDeclaration(n, a, b)) :: _ if n == name => pure(f)
    case _ :: fs => lookup(name, fs)
  }
}
