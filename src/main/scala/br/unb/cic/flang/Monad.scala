package br.unb.cic.flang

import cats.data.State

package object StateMonad {
  type S = List[(String, Integer)]

  // Atualização das funções para usar o State da Cats
  def pure[A](a: A): State[S, A] = State.pure(a)

  def put(s: S): State[S, Unit] = State.set(s)

  def get(): State[S, S] = State.get

  def declareVar(name: String, value: Integer, state: S): S =
    (name, value) :: state

  def lookupVar(name: String, state: S): Integer = state match {
    case List()                      => ???
    case (n, v) :: tail if n == name => v
    case _ :: tail                   => lookupVar(name, tail)
  }

}
