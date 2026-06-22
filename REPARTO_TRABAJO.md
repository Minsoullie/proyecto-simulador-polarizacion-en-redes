# Reparto del trabajo — Simulador de Polarización en Redes

Criterio del reparto: el proyecto tiene 9 funciones a implementar más el
trabajo de análisis comparativo. Se agruparon de forma que cada persona
tenga una carga de dificultad similar: Persona A se queda con 3 funciones
más cortas (todo `Comete`), Personas B y C con 2 funciones "núcleo" cada
una, y Persona D con 2 funciones de paralelización + todo el trabajo de
benchmarking (que compensa tener menos funciones nuevas).

---

## Persona A — Paquete `Comete` completo

**Archivo:** `src/main/scala/Comete/package.scala`

### Funciones a implementar
1. **`min_p(f, min, max, prec)`**
   - Busca el punto `p ∈ [min,max]` donde `f` (convexa) es mínima.
   - Sugerencia de algoritmo: búsqueda ternaria recursiva — en cada paso
     evaluar `f` en dos puntos interiores del intervalo y descartar el
     tercio donde el mínimo no puede estar.
   - Caso base: si `max - min < prec`, devolver el punto medio.
2. **`rhoCMT_Gen(alpha, beta)`**
   - Construye, para cada `Distribution (Pi, dv)` recibida, la función
     `rho_aux(p) = Σ Pi_i^alpha * |dv_i - p|^beta`.
   - Le aplica `min_p` en `[0,1]` para hallar el mínimo.
   - Depende de `min_p`.
3. **`normalizar(m)`**
   - Devuelve una función que aplica `m` y divide el resultado entre
     `m` aplicada al peor caso: `Pi = (0.5, 0, ..., 0, 0.5)` sobre los
     mismos valores de distribución.

### Verificación
Usar los valores `pi_max`, `pi_min`, `pi_der`, etc. ya incluidos al final
de `Comete/package.scala`, y comparar contra los resultados que trae el
enunciado (sección 2.1.1): `res0 = 0.379`, `res1 = 1.0`, etc.

### Informe (tu responsabilidad)
- Describir los tipos `DistributionValues`, `Frequency`, `Distribution`,
  `PolMeasure` y por qué se distinguen `DistributionValues` de
  `Frequency` aunque sean el mismo tipo de Scala.
- Argumentación de corrección de `min_p` y de `rhoCMT_Gen` (la rúbrica
  las pide explícitamente).
- Señalar qué elementos de programación funcional usaste (recursión,
  alto orden, etc.) en cada función.

### Tarea de cierre
- Redactar el `Readme.txt` final de la entrega (descripción de archivos
  + instrucciones de ejecución).

---

## Persona B — Paquete `Opinion`, parte estática

**Archivo:** `src/main/scala/Opinion/package.scala` (sección "Persona B")

### Funciones a implementar
1. **`rho(alpha, beta)`**
   - A partir de los `DistributionValues d` recibidos, construir los `k`
     intervalos `I_0, ..., I_{k-1}` (fórmula sección 2.2 del enunciado).
   - Convertir la `SpecificBelief` en una `Distribution`: contar la
     fracción de agentes cuya creencia cae en cada intervalo (`Pi^b`), y
     usar `d` como los valores de distribución (`Y^b`).
   - Aplicar `Comete.normalizar(Comete.rhoCMT_Gen(alpha, beta))` sobre
     esa `Distribution`.
   - Depende de las funciones de Persona A.
2. **`showWeightedGraph(swg)`**
   - Convierte la función de influencia `swg` en la matriz
     `IndexedSeq[IndexedSeq[Double]]` correspondiente. Más sencilla que
     `rho`; usar `Vector.tabulate` con el número de agentes embebido en
     `SpecificWeightedGraph`.

### Verificación
Comparar contra `res29`...`res48` (resultados de `rho1`/`rho2` sobre
`sb_ext`, `sb_cons`, `sb_unif`, `sb_triple`, `sb_midly`) y `res23`/`res25`
(matrices de `i1_10`, `i2_10`), todos incluidos en `Pruebas.sc`.

### Informe (tu responsabilidad)
- Describir cómo se modela una red de forma estática: creencias, grafo
  de influencia, y la construcción de los intervalos `I_i`.
- Argumentación de corrección de `rho` (la pide la rúbrica
  explícitamente).
- Señalar qué estructuras inmutables y qué funciones de alto orden
  usaste para construir la distribución a partir de la creencia.

---

## Persona C — Paquete `Opinion`, parte dinámica

**Archivo:** `src/main/scala/Opinion/package.scala` (sección "Persona C")

### Funciones a implementar
1. **`confBiasUpdate(sb, swg)`**
   - Implementa la fórmula del sesgo de confirmación (sección 2.3):
     `nb(i) = b(i) + Σ_{j∈A_i} β_{i,j}·I(j,i)·(b(j)-b(i)) / |A_i|`,
     con `A_i = {j : I(j,i) > 0}` y `β_{i,j} = 1 - |b(j)-b(i)|`.
   - Cuidado: para cada agente `i` hay que recorrer todos los `j` con
     influencia positiva sobre `i` (filtrar primero, luego sumar).
2. **`simulate(fu, swg, b0, t)`**
   - Aplica `fu` repetidamente, `t` veces, a partir de `b0`.
   - Devuelve la secuencia completa `[b0, b1, ..., bt]`.
   - Pista: se puede construir con una recursión que acumule resultados,
     o con un `scanLeft`/iteración sobre `1 to t`.

### Verificación
Comparar contra `res51`...`res58` del enunciado (usando `sbu_10`,
`sbm_10`, `i1_10`), ya incluidos en `Pruebas.sc`.

### Informe (tu responsabilidad)
- Describir el modelo dinámico: función de actualización, sesgo de
  confirmación, y cómo se garantiza que `simulate` es una función pura y
  termina.
- Argumentación de corrección de `confBiasUpdate` y de `simulate` (las
  pide la rúbrica explícitamente).

---

## Persona D — Paralelización + Análisis comparativo

**Archivo:** `src/main/scala/Opinion/package.scala` (sección "Persona D")

### Funciones a implementar
1. **`rhoPar(alpha, beta)`**
   - Versión concurrente de `rho`: paraleliza la construcción de la
     distribución (el conteo de agentes por intervalo es paralelizable
     por datos). Sigue usando `Comete.rhoCMT_Gen` sin modificarla.
   - Depende de que la versión secuencial de Persona B ya esté validada.
2. **`confBiasUpdatePar(sb, swg)`**
   - Versión concurrente de `confBiasUpdate`: paraleliza por datos sobre
     los agentes `i`, y evalúa si conviene paralelizar también la suma
     interna sobre vecinos (paralelismo de tareas) — justificar la
     decisión en el informe según el tamaño esperado de `A_i`.
   - Depende de que la versión secuencial de Persona C ya esté validada.
   - Para paralelismo de tareas tienen disponible `common.task[T](body)`
     y las dos sobrecargas de `common.parallel` (paquete `common`, ya
     real, importado en `Opinion/package.scala`); para paralelismo de
     datos, `scala.collection.parallel.CollectionConverters` (`.par`)
     ya está importado también.

### Trabajo de benchmarking (tu carga adicional)
- Usar `Benchmark.compararMedidasPol`, `Benchmark.compararFuncionesAct` y
  `Benchmark.simEvolucion` (paquete `Benchmark` del profesor, ya
  incluido y real — sección 2.4.3 del enunciado) junto con
  `org.scalameter` para generar las tablas de desempeño secuencial vs.
  paralelo:
  - `rho` vs. `rhoPar`.
  - `confBiasUpdate` vs. `confBiasUpdatePar`.
  - Simulaciones completas (`simulate`), variando tamaño de red y
    número de unidades de tiempo.
- `Benchmark` también trae listas las funciones generadoras de
  creencias y grafos de influencia del enunciado (`uniformBelief`,
  `midlyBelief`, `allExtremeBelief`, `allTripleBelief`,
  `consensusBelief`, `i1`, `i2`) — son las mismas que ya usa
  `Fixtures.scala` en las pruebas, así que no hay que reimplementarlas.
- Verificar primero que `rhoPar`/`confBiasUpdatePar` dan exactamente los
  mismos resultados que sus versiones secuenciales (ver bloque final de
  `Pruebas.sc`) antes de medir tiempos.

### Informe (tu responsabilidad)
- Sección de análisis comparativo: tablas de tiempos y aceleración,
  conclusión sobre a partir de qué tamaño de red vale la pena
  paralelizar cada función.
- Justificación teórica de por qué la paralelización debería (o no)
  mejorar el desempeño en cada caso (overhead de crear tareas vs.
  trabajo por tarea).

---

## Tareas compartidas (los 4 integrantes)

- **Sustentación**: cada persona debe poder explicar y modificar en vivo
  *todo* el código del equipo, no solo su parte — la rúbrica califica
  la capacidad grupal de navegar el código.
- **Informe final**: cada quien entrega su sección redactada; se sugiere
  que Persona A lo compile en el PDF final (su parte de código es la
  más corta).
- **Empaquetado final**: `.zip`/`.tgz` con apellidos de los autores,
  conteniendo el informe en PDF, los paquetes, y el `Readme.txt`.

## Orden de trabajo sugerido (coincide con el enunciado, sección 4)

1. Persona A termina `Comete` primero (todo depende de ella).
2. Personas B y C trabajan en paralelo sobre `Opinion` (parte estática y
   dinámica respectivamente), una vez `Comete` esté lista y probada.
3. Persona D empieza la paralelización solo cuando B y C tengan sus
   versiones secuenciales validadas contra los valores del enunciado.
4. Las 4 personas escriben su sección del informe en paralelo a medida
   que terminan y validan su código.
