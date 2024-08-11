package br.unb.cic.flang

import scala.io.StdIn
import scala.util.parsing.combinator._

object REPL {

  // Armazena as definições das funções
  var declarations: List[FDeclaration] = List()

  // Adiciona uma nova definição à lista de declarações
  def addDefinition(name: String, body: Expr): Unit = {
    declarations = declarations :+ FDeclaration(name, "x", body)
  }

  // Função principal do REPL
  def startRepl(): Unit = {
    var continue = true
    while (continue) {
      println("Digite uma expressao em FLang (ou 'sair' para sair):")
      val input = StdIn.readLine().trim
      input match {
        case "sair" => continue = false
        case _ =>
          try {
            val parsedExpr = FLangParser.parse(input)
            val result = Interpreter.eval(parsedExpr, declarations).runA(List())
            println(s"Resultado: $result")
          } catch {
            case e: RuntimeException => println(s"Erro: ${e.getMessage}")
          }
      }
    }
  }

  // Método main
  def main(args: Array[String]): Unit = {
    startRepl()
  }
}