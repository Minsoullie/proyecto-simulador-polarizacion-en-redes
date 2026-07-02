import Comete._
import common._
import scala.collection.parallel.CollectionConverters._

package object Opinion {



  // Si n es el número de agentes, estos se identifican con los enteros
  // entre 0 y n-1. O sea, el conjunto de Agentes A es implícitamente
  // el conjunto {0, 1, 2, ..., n-1}.

  /** Si b: SpecificBelief, para cada i en Int, b(i) es un número entre 0 y 1
   *  que indica cuánto cree el agente i en la veracidad de la proposición p.
   *  El número de agentes es b.length.
   *  Si existe i: b(i) < 0 o b(i) > 1, b está mal definida.
   */
  type SpecificBelief = Vector[Double]

  /** Si gb: GenericBeliefConf, entonces gb(n) = b tal que b: SpecificBelief
   *  es una creencia específica para n agentes.
   */
  type GenericBeliefConf = Int => SpecificBelief

  /** Si rho: AgentsPolMeasure y sb: SpecificBelief y d: DistributionValues,
   *  rho(sb, d) es la polarización de los agentes de acuerdo a esa medida.
   */
  type AgentsPolMeasure = (SpecificBelief, DistributionValues) => Double

  // Tipos para modelar la evolución de la opinión en una red

  type WeightedGraph = (Int, Int) => Double

  /** (wg, n): la función de influencia wg acompañada del número de agentes n. */
  type SpecificWeightedGraph = (WeightedGraph, Int)

  type GenericWeightedGraph = Int => SpecificWeightedGraph

  type FunctionUpdate = (SpecificBelief, SpecificWeightedGraph) => SpecificBelief

  // ============================================================
  // RESPONSABLE: PERSONA B (estructura estática de la red)
  // ============================================================

  /**
   * rho es la medida de polarización de agentes basada en comete.
   *
   * Pasos sugeridos para implementar la función devuelta:
   *  1. A partir de los DistributionValues `d` recibidos, construir los k
   *     intervalos I_0, ..., I_{k-1} según la fórmula de la sección 2.2.
   *  2. Para cada intervalo, contar qué fracción de agentes (sb) cae en él
   *     -> construye la Frequency (Pi^b).
   *  3. Y^b_i = d_i, para 0 <= i < k.
   *  4. Aplicar Comete.normalizar(Comete.rhoCMT_Gen(alpha, beta)) a la
   *     Distribution (Pi^b, Y^b) resultante.
   */
  def rho(alpha: Double, beta: Double): AgentsPolMeasure = {
    (sb: SpecificBelief, d: DistributionValues) => {
      val n = sb.length
      val k = d.length

      // 1. Construir los límites izquierdos y derechos de cada intervalo
      val lefts = (0 until k).map { i =>
        if (i == 0) 0.0
        else (d(i - 1) + d(i)) / 2.0
      }.toVector

      val rights = (0 until k).map { i =>
        if (i == k - 1) 1.0
        else (d(i) + d(i + 1)) / 2.0
      }.toVector

      // 2. Contar agentes por intervalo
      val counts = sb.foldLeft(Vector.fill(k)(0)) { (acc, belief) =>
        val idx = (0 until k).find { i =>
          if (i == k - 1) lefts(i) <= belief && belief <= rights(i)
          else lefts(i) <= belief && belief < rights(i)
        }.getOrElse(k - 1) // Por seguridad, si no encuentra, asigna al último

        acc.updated(idx, acc(idx) + 1)
      }

      // 3. Calcular frecuencias (Π^b): fracción de agentes en cada intervalo
      val freqs = counts.map(_.toDouble / n)

      // 4. Crear la distribución como una tupla (freqs, d)
      //    Distribution es exactamente (Frequency, DistributionValues)
      val distribution: Distribution = (freqs, d)

      // 5. Aplicar la medida comete normalizada
      val medidaNormalizada = Comete.normalizar(Comete.rhoCMT_Gen(alpha, beta))
      medidaNormalizada(distribution)
    }
  }

  /**
   * Convierte una función de influencia específica (swg) en la matriz
   * asociada al grafo de influencia: una secuencia indexada de secuencias
   * indexadas por cada agente, con la influencia que él tiene sobre los
   * otros agentes.
   */
  def showWeightedGraph(swg: SpecificWeightedGraph): IndexedSeq[IndexedSeq[Double]] = {
    val (wg, n) = swg  // Extraemos la función de influencia y el número de agentes
    Vector.tabulate(n)(i => Vector.tabulate(n)(j => wg(i, j)))
  }

  // ============================================================
  // RESPONSABLE: PERSONA C (evolución dinámica de la red)
  // ============================================================

  /**
   * Aplica la función de actualización del sesgo de confirmación sobre sb,
   * teniendo en cuenta el grafo de influencia swg (sección 2.3):
   *
   *   nb(i) = b(i) + sum_{j en A_i} beta_{i,j} * I(j,i) * (b(j) - b(i)) / |A_i|
   *
   * donde A_i = { j en A : I(j,i) > 0 } y beta_{i,j} = 1 - |b(j) - b(i)|.
   */
  def confBiasUpdate(sb: SpecificBelief, swg: SpecificWeightedGraph): SpecificBelief = {
    // TODO (Persona C)
    ???
  }

  /**
   * Recibe una función de actualización fu, una función de influencia
   * específica swg, una creencia específica b0 y un entero t (unidades de
   * tiempo), y devuelve la secuencia de creencias específicas
   * correspondientes a cada unidad de tiempo: [b0, b1, ..., bt].
   */
  def simulate(
      fu: FunctionUpdate,
      swg: SpecificWeightedGraph,
      b0: SpecificBelief,
      t: Int
  ): IndexedSeq[SpecificBelief] = {
    // TODO (Persona C)
    ???
  }

  // ============================================================
  // RESPONSABLE: PERSONA D (versiones paralelas)
  // ============================================================

  /**
   * Versión concurrente de rho (misma semántica, normalizada para
   * agentes), aprovechando paralelismo de datos en la construcción de la
   * distribución a partir de la creencia. Reutiliza Comete.rhoCMT_Gen.
   */
  def rhoPar(alpha: Double, beta: Double): AgentsPolMeasure = {
    // TODO (Persona D)
    ???
  }

  /**
   * Versión concurrente de confBiasUpdate, calculada de forma paralela
   * (paralelismo de datos sobre los agentes y/o paralelismo de tareas
   * dentro del cálculo de cada agente).
   *
   * El paquete `common` (ya disponible) provee `task` y `parallel` para
   * paralelismo de tareas estilo fork/join (ver `common.parallel[A,B]`,
   * `common.parallel[A,B,C,D]`); úsenlos donde tenga sentido combinarlos
   * con el paralelismo de datos sobre los agentes.
   */
  def confBiasUpdatePar(sb: SpecificBelief, swg: SpecificWeightedGraph): SpecificBelief = {
    // TODO (Persona D)
    ???
  }
}
