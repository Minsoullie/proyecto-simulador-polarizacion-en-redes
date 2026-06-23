package object Comete {

  /** Valores reales ordenados entre 0 y 1, incluidos 0 y 1, de una distribución. */
  type DistributionValues = Vector[Double]

  /** Pik es una frecuencia de longitud k si Pik.length = k, 0 <= Pik(i) <= 1
   *  para 0 <= i <= k-1, y Pik.sum == 1
   */
  type Frequency = Vector[Double]

  /** (Pi, dv) es una distribución si Pi es una Frequency y dv son los
   *  valores de distribución, y Pi y dv son de la misma longitud.
   */
  type Distribution = (Frequency, DistributionValues)

  /** Tipo que debe tener cualquier medida de polarización. */
  type PolMeasure = Distribution => Double

  // ============================================================
  // RESPONSABLE: PERSONA A
  // ============================================================

  /**
   * Devuelve el punto p en [min, max] tal que f(p) es mínimo,
   * suponiendo que f es convexa en [min, max].
   * Si max - min < prec, devuelve el punto medio de [min, max].
   *
   * Sugerencia: búsqueda ternaria recursiva. En cada paso evalúa f
   * en dos puntos interiores del intervalo y descarta el tercio
   * donde el mínimo no puede estar (aprovechando la convexidad).
   */
  def min_p(f: Double => Double, min: Double, max: Double, prec: Double): Double = {
    // TODO (Persona A)
    ???
  }

  /**
   * Dados alpha y beta, devuelve la función que calcula la medida de
   * polarización "comete" parametrizada en esos valores:
   *
   *   rho_cmt(Pi) = min { rho_aux(p) : p en [0,1] }
   *   rho_aux(p)  = sum_i  Pi_i^alpha * |y_i - p|^beta
   *
   * Sugerencia: para cada Distribution (Pi, dv) que reciba la función
   * resultante, construir rho_aux como Double => Double y aplicarle min_p
   * en el intervalo [0,1] con una precisión razonable (p.ej. 1e-4 o similar).
   */
  def rhoCMT_Gen(alpha: Double, beta: Double): PolMeasure = {
    // TODO (Persona A) - usar min_p internamente
    ???
  }

  /**
   * Recibe una medida de polarización m y devuelve la correspondiente
   * medida normalizada: divide el resultado de m por el resultado de
   * aplicar m al peor caso de polarización (Pi = (0.5, 0, ..., 0, 0.5)
   * sobre los mismos valores de distribución).
   */
  def normalizar(m: PolMeasure): PolMeasure = {
    // TODO (Persona A)
    ???
  }

  // ============================================================
  // VALORES DE EJEMPLO DEL ENUNCIADO (sección 2.1.1) - útiles para
  // probar min_p, rhoCMT_Gen y normalizar antes de integrar con Opinion.
  // ============================================================

  val pi_max         = Vector(0.5, 0.0, 0.0, 0.0, 0.5)
  val pi_min         = Vector(0.0, 0.0, 1.0, 0.0, 0.0)
  val pi_der         = Vector(0.4, 0.0, 0.0, 0.0, 0.6)
  val pi_izq         = Vector(0.6, 0.0, 0.0, 0.0, 0.4)
  val pi_int1        = Vector(0.0, 0.5, 0.0, 0.5, 0.0)
  val pi_int2        = Vector(0.25, 0.0, 0.5, 0.0, 0.25)
  val pi_int3        = Vector(0.25, 0.25, 0.0, 0.25, 0.25)
  val pi_cons_centro = pi_min
  val pi_cons_der    = Vector(0.0, 0.0, 0.0, 0.0, 1.0)
  val pi_cons_izq    = Vector(1.0, 0.0, 0.0, 0.0, 0.0)
  val likert5        = Vector(0.0, 0.25, 0.5, 0.75, 1.0)

  // Resultados esperados (sin normalizar), con alpha = beta = 1.2, para
  // verificar tu implementación de rhoCMT_Gen contra el enunciado:
  //   cmt1(pi_max,  likert5) = 0.379
  //   cmt1(pi_min,  likert5) = 0.0
  //   cmt1(pi_der,  likert5) = 0.327
  //   cmt1(pi_izq,  likert5) = 0.327
  //   cmt1(pi_int1, likert5) = 0.165
  //   cmt1(pi_int2, likert5) = 0.165
  //   cmt1(pi_int3, likert5) = 0.237
  //   cmt1(pi_cons_centro, likert5) = 0.0
  //   cmt1(pi_cons_der,    likert5) = 0.0
  //   cmt1(pi_cons_izq,    likert5) = 0.0
  //
  // Resultados esperados (normalizados):
  //   cmt1_norm(pi_max,  likert5) = 1.0
  //   cmt1_norm(pi_min,  likert5) = 0.0
  //   cmt1_norm(pi_der,  likert5) = 0.863
  //   cmt1_norm(pi_izq,  likert5) = 0.863
  //   cmt1_norm(pi_int1, likert5) = 0.435
  //   cmt1_norm(pi_int2, likert5) = 0.435
  //   cmt1_norm(pi_int3, likert5) = 0.625
}
