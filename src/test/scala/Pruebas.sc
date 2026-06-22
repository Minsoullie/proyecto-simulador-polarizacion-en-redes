// Pruebas.sc
//
// Worksheet de IntelliJ para probar manualmente cada función contra los
// resultados que trae el enunciado. Ábranlo con el plugin de Scala de
// IntelliJ (botón "Evaluate Worksheet"), no se ejecuta con `sbt test`.
//
// Cada bloque indica de quién es la función probada y qué resultado
// debería dar según el documento del proyecto.

import Comete._
import Opinion._
import fixtures.Fixtures._

// ============================================================
// PERSONA A — paquete Comete (sección 2.1.1)
// ============================================================

val cmt1 = rhoCMT_Gen(1.2, 1.2)
cmt1((pi_max, likert5))         // esperado: 0.379
cmt1((pi_min, likert5))         // esperado: 0.0
cmt1((pi_der, likert5))         // esperado: 0.327
cmt1((pi_izq, likert5))         // esperado: 0.327
cmt1((pi_int1, likert5))        // esperado: 0.165
cmt1((pi_int2, likert5))        // esperado: 0.165
cmt1((pi_int3, likert5))        // esperado: 0.237
cmt1((pi_cons_centro, likert5)) // esperado: 0.0
cmt1((pi_cons_der, likert5))    // esperado: 0.0
cmt1((pi_cons_izq, likert5))    // esperado: 0.0

val cmt1_norm = normalizar(cmt1)
cmt1_norm((pi_max, likert5))  // esperado: 1.0
cmt1_norm((pi_min, likert5))  // esperado: 0.0
cmt1_norm((pi_der, likert5))  // esperado: 0.863
cmt1_norm((pi_izq, likert5))  // esperado: 0.863
cmt1_norm((pi_int1, likert5)) // esperado: 0.435
cmt1_norm((pi_int2, likert5)) // esperado: 0.435
cmt1_norm((pi_int3, likert5)) // esperado: 0.625

// ============================================================
// PERSONA B — paquete Opinion, parte estática (sección 2.2.1)
// ============================================================

val rho1 = rho(1.2, 1.2)
val rho2 = rho(2.0, 1.0)

rho1(sb_ext, dist1)    // esperado: 1.0
rho2(sb_ext, dist1)    // esperado: 1.0
rho1(sb_ext, dist2)    // esperado: 1.0
rho2(sb_ext, dist2)    // esperado: 1.0

rho1(sb_cons, dist1)   // esperado: 0.0
rho2(sb_cons, dist1)   // esperado: 0.0

rho1(sb_unif, dist1)   // esperado: 0.380
rho2(sb_unif, dist1)   // esperado: 0.188

rho1(sb_triple, dist1) // esperado: 0.617
rho2(sb_triple, dist1) // esperado: 0.448

rho1(sb_midly, dist1)  // esperado: 0.784
rho2(sb_midly, dist1)  // esperado: 0.580

showWeightedGraph(i1_10) // comparar contra res23 del enunciado
showWeightedGraph(i2_10) // comparar contra res25 del enunciado

// ============================================================
// PERSONA C — paquete Opinion, parte dinámica (sección 2.3.2 y 2.3.3)
// ============================================================

confBiasUpdate(sbu_10, i1_10)
rho1(sbu_10, dist1)                          // esperado: 0.383
rho1(confBiasUpdate(sbu_10, i1_10), dist1)   // esperado: 0.38

confBiasUpdate(sbm_10, i1_10)
rho1(sbm_10, dist1)                          // esperado: 0.435
rho1(confBiasUpdate(sbm_10, i1_10), dist1)   // esperado: 0.435

for {
  b <- simulate(confBiasUpdate, i1_10, sbu_10, 2)
} yield (b, rho1(b, dist1))
// esperado (polarizaciones): 0.383, 0.38, 0.335

for {
  b <- simulate(confBiasUpdate, i1_10, sbm_10, 2)
} yield (b, rho1(b, dist1))
// esperado (polarizaciones): 0.435, 0.435, 0.377

// ============================================================
// PERSONA D — versiones paralelas: deben coincidir con las secuenciales
// ============================================================

val rhoParV = rhoPar(1.2, 1.2)
rhoParV(sb_unif, dist1) == rho1(sb_unif, dist1)   // debe dar true
rhoParV(sb_triple, dist1) == rho1(sb_triple, dist1) // debe dar true

confBiasUpdatePar(sbu_10, i1_10) == confBiasUpdate(sbu_10, i1_10) // debe dar true
confBiasUpdatePar(sbm_10, i1_10) == confBiasUpdate(sbm_10, i1_10) // debe dar true

// A partir de aquí, Persona D: usar Benchmark.compararMedidasPol,
// Benchmark.compararFuncionesAct y Benchmark.simEvolucion (sección 2.4.3
// del enunciado) para generar las tablas de desempeño del informe.
