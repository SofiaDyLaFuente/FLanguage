package br.unb.cic.flang

import cats.data.StateT
import cats.implicits._

object MonadTransformers {
  type MError[A] = Either[String, A]
  type ErrorAndState[A] = StateT[MError, List[(String, Integer)], A]

  def pure[A](a: A): ErrorAndState[A] = StateT.pure(a)

  def raiseError[A](msg: String): ErrorAndState[A] = StateT.liftF(Left(msg))

  def get: ErrorAndState[List[(String, Integer)]] = StateT.get

  def put(state: List[(String, Integer)]): ErrorAndState[Unit] = StateT.set(state)

  def declareVar(name: String, value: Integer, state: List[(String, Integer)]): List[(String, Integer)] =
    (name, value) :: state

  def lookupVar(name: String): ErrorAndState[Integer] = for {
    state <- get
    value <- state match {
      case List()                      => raiseError(s"Variable $name not found")
      case (n, v) :: tail if n == name => pure(v)
      case _ :: tail                   => put(tail).flatMap(_ => lookupVar(name))
    }
  } yield value
}
