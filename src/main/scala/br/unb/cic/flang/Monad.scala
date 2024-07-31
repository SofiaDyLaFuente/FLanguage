package br.unb.cic.flang

import cats.data.StateT
import cats.implicits._

object MonadTransformers {
  type ErrorOr[A] = Either[String, A]
  type ErrorOrState[A] = StateT[ErrorOr, List[(String, Integer)], A]

  def pure[A](a: A): ErrorOrState[A] = StateT.pure(a)

  def raiseError[A](msg: String): ErrorOrState[A] = StateT.liftF(Left(msg))

  def get: ErrorOrState[List[(String, Integer)]] = StateT.get

  def put(state: List[(String, Integer)]): ErrorOrState[Unit] = StateT.set(state)

  def declareVar(name: String, value: Integer, state: List[(String, Integer)]): List[(String, Integer)] =
    (name, value) :: state

  def lookupVar(name: String): ErrorOrState[Integer] = for {
    state <- get
    value <- state match {
      case List()                      => raiseError(s"Variable $name not found")
      case (n, v) :: tail if n == name => pure(v)
      case _ :: tail                   => put(tail).flatMap(_ => lookupVar(name))
    }
  } yield value
}
