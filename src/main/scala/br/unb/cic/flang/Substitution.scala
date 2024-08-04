package br.unb.cic.flang

object Substitution {

  def substitute(what: Expr, name: String, in: Expr): Expr = in match {
    case c @ CInt(v)        => c /* this is the trivial case */
    case b @ CBool(v) => b // Novo caso para booleanos

    case Id(n) if n == name => what /* this is the interesting case */
    case id @ Id(_) =>
      id /* the following cases 'traverse' the sub expressions */

    case Add(lhs, rhs) =>
      Add(substitute(what, name, lhs), substitute(what, name, rhs))

    case Mul(lhs, rhs) =>
      Mul(substitute(what, name, lhs), substitute(what, name, rhs))

    case App(n, arg) => App(n, substitute(what, n, arg))

    case IfThenElse(cond, thenBranch, elseBranch) =>
      IfThenElse(
        substitute(what, name, cond),
        substitute(what, name, thenBranch),
        substitute(what, name, elseBranch)
      )

    case And(lhs, rhs) =>
      And(substitute(what, name, lhs), substitute(what, name, rhs))

    case Or(lhs, rhs) =>
      Or(substitute(what, name, lhs), substitute(what, name, rhs))

    case Not(expr) =>
      Not(substitute(what, name, expr))
  }
}
