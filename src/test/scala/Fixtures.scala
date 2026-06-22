package fixtures

import Opinion._
import Benchmark._

/**
 * Valores de ejemplo tomados textualmente del enunciado (secciones 2.2.1
 * y 2.3.1). Úsenlos en Pruebas.sc para validar cada función contra los
 * resultados que trae el documento del proyecto.
 *
 * Las creencias genéricas (uniformBelief, midlyBelief, allExtremeBelief,
 * allTripleBelief, consensusBelief) y los grafos de influencia (i1, i2)
 * NO se redefinen aquí: ya vienen en el paquete `Benchmark` provisto por
 * el profesor (sección 2.4.3 del enunciado), así que simplemente se
 * importan de ahí para no duplicar código.
 *
 * No son parte de la entrega (Comete/Opinion); son solo material de apoyo
 * para las pruebas del equipo.
 */
object Fixtures {

  // ---------- Valores de distribución de ejemplo ----------

  val likert5: Vector[Double] = Vector(0.0, 0.25, 0.5, 0.75, 1.0)
  val dist1: Vector[Double] = Vector(0.0, 0.25, 0.50, 0.75, 1.0)
  val dist2: Vector[Double] = Vector(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)

  // ---------- Instancias concretas usadas en los ejemplos del enunciado ----------
  // (construidas con las funciones genéricas de Benchmark)

  val sb_ext: SpecificBelief    = allExtremeBelief(100)
  val sb_cons: SpecificBelief   = consensusBelief(0.2)(100)
  val sb_unif: SpecificBelief   = uniformBelief(100)
  val sb_triple: SpecificBelief = allTripleBelief(100)
  val sb_midly: SpecificBelief  = midlyBelief(100)

  val sbu_10: SpecificBelief = uniformBelief(10)
  val sbm_10: SpecificBelief = midlyBelief(10)

  val i1_10: SpecificWeightedGraph = i1(10)
  val i2_10: SpecificWeightedGraph = i2(10)
  val i1_20: SpecificWeightedGraph = i1(20)
  val i2_20: SpecificWeightedGraph = i2(20)
}
