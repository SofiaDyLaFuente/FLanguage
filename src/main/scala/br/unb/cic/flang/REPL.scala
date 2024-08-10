package br.unb.cic.flang

import scala.io.{Source, StdIn}
import scala.util.parsing.combinator._

object REPL {

  // Armazena as definições das funções
  var declarations: List[FDeclaration] = List()

  // Função para carregar definições a partir de um arquivo
  def loadFile(filePath: String): Unit = {
    val source = Source.fromFile(filePath)
    val lines = source.getLines().mkString("\n")
    source.close()

    processDefinitions(lines)
  }

  // Função para processar o conteúdo das definições
  def processDefinitions(content: String): Unit = {
    val definitions = content.split("\n").map(_.trim).filter(_.nonEmpty)
    definitions.foreach { line =>
      val Array(name, expr) = line.split("=").map(_.trim)
      val parsedExpr = FLangParser.parse(expr)
      addDefinition(name, parsedExpr)
    }
  }

  // Adiciona uma nova definição à lista de declarações
  def addDefinition(name: String, body: Expr): Unit = {
    declarations = declarations :+ FDeclaration(name, "x", body)
  }

  // Função principal do REPL
  def startRepl(): Unit = {
    println("Digite o caminho do arquivo para carregar definições (ou 'sair' para sair):")
    val filePath = StdIn.readLine().trim
    if (filePath.nonEmpty && filePath != "sair") {
      loadFile(filePath)
    }

    var continue = true
    while (continue) {
      println("Digite uma expressão em FLang (ou 'sair' para sair):")
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

  // Método main explicitamente definido
  def main(args: Array[String]): Unit = {
    startRepl()
  }
}
