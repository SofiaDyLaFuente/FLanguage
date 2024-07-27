package br.unb.cic.flang

import cats.data.State
import cats.implicits._

package object StateMonad {
  type S = List[(String, Integer)]

  // Definição da Monad
  type M[A] = State[S, A]

  // Cria uma instância de M que contém o valor a e não altera o estado
  def pure[A](a: A): M[A] = State.pure(a)

  // Usa flatMap para encadear operações na Monad de Estado
  def bind[A, B](m: M[A])(f: A => M[B]): M[B] = m.flatMap(f)

  // Extrai o estado e o resultado.
  def runState[A](stateM: M[A]): S => (S, A) = stateM.run(_).value

  // Atualiza o estado com um novo valor
  def put(s: S): M[Unit] = State.set(s)

  // Retorna o estado atual
  def get(): M[S] = State.get

// Funções Auxiliares:

  // Adiciona uma variável ao estado, retornando o novo estado com o par (name, value) adicionado.
  def declareVar(name: String, value: Integer, state: S): S =
    (name, value) :: state

  // Procura o valor associado a um nome no estado
  def lookupVar(name: String, state: S): Integer = state match {
    case List()                      => ???
    case (n, v) :: tail if n == name => v
    case _ :: tail                   => lookupVar(name, tail)
  }

}
