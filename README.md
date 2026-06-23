<<<<<<< HEAD
# Simulador de Polarización en Redes

Proyecto de curso — Fundamentos de Programación Funcional y Concurrente
Escuela de Ingeniería de Sistemas y Computación, Universidad del Valle.

## Estructura del repositorio

```
proyecto-polarizacion/
├── .gitignore
├── build.sbt
├── project/build.properties
├── src/
│   ├── main/scala/
│   │   ├── Comete/package.scala      <- Persona A
│   │   ├── Opinion/package.scala     <- Personas B, C y D
│   │   ├── common/package.scala      <- provisto por el profesor (real)
│   │   └── Benchmark/package.scala   <- provisto por el profesor (real)
│   └── test/scala/
│       ├── Fixtures.scala            <- creencias y grafos de ejemplo del enunciado
│       └── Pruebas.sc                <- worksheet con casos de prueba esperados
├── informe/                          <- aquí va el PDF final del informe
└── REPARTO_TRABAJO.md                <- detalle de quién hace qué
```

## Cómo abrir el proyecto

1. Abrir la carpeta `proyecto-polarizacion` directamente en IntelliJ IDEA
   (con el plugin de Scala instalado). IntelliJ detecta el `build.sbt`
   automáticamente y la estructura quedará igual a la del enunciado
   (carpetas `Comete`, `Opinion`, `common`, `Benchmark` bajo `main/scala`,
   y `Pruebas.sc` bajo `test/scala`).
2. Esperar a que sbt resuelva las dependencias: `scalameter-core`
   (medición de tiempos, usada por `Benchmark`), `plotly-render`
   (genera los .html de `Benchmark.simEvolucion`) y
   `scala-parallel-collections` (usada en `rhoPar`/`confBiasUpdatePar`).
   La primera resolución puede tardar unos minutos.
3. Los paquetes `common` y `Benchmark` ya son los oficiales del profesor
   (no hay que pedir nada más); `common` provee `task`/`parallel` para
   paralelismo de tareas, y `Benchmark` provee `compararMedidasPol`,
   `compararFuncionesAct` y `simEvolucion` para el análisis comparativo.
4. Para probar manualmente: abrir `src/test/scala/Pruebas.sc` y usar
   "Evaluate Worksheet" (clic derecho o el botón verde de play).
5. Para Persona D: usar `Benchmark.compararMedidasPol`,
   `Benchmark.compararFuncionesAct` y `Benchmark.simEvolucion` desde el
   worksheet o desde `sbt console` para generar las tablas del informe.

## Cómo correr desde la terminal

```bash
sbt compile      # compila Comete, Opinion, common y Benchmark
sbt console      # consola interactiva con los paquetes en el classpath,
                  # ideal para llamar Benchmark.compararMedidasPol, etc.
```

## Cómo subir esto a GitHub

El repo ya quedó inicializado con `git init` y un primer commit. Para
subirlo, cada integrante (o quien tenga el repo remoto) corre:

```bash
git remote add origin <URL-de-su-repositorio-vacío-en-GitHub>
git branch -M main
git push -u origin main
```

Después, cada uno clona el repo (`git clone <URL>`) y trabaja sobre su
parte siguiendo `REPARTO_TRABAJO.md`. Se sugiere una rama por persona
(`git checkout -b persona-a`, etc.) y Pull Requests hacia `main` para
revisar los cambios entre todos antes de integrar.

## Convención de los stubs

Cada función pendiente en `Comete/package.scala` y `Opinion/package.scala`
tiene:
- Un comentario `// TODO (Persona X)` indicando el responsable.
- La documentación de qué debe hacer, tomada o adaptada del enunciado.
- Un cuerpo `???` (placeholder de Scala) que debe reemplazarse por la
  implementación real.

No cambien las firmas de los métodos (nombres, tipos de parámetros, tipo
de retorno): el profesor probará importando estos paquetes tal cual están
especificados.

## Reparto de trabajo

Ver [`REPARTO_TRABAJO.md`](./REPARTO_TRABAJO.md) para el detalle completo
de qué le corresponde a cada persona del grupo, tanto en código como en
informe.

## Entrega

- Fecha límite: **2 de julio de 2026, 23:59**.
- Empaquetar en un solo archivo comprimido cuyo nombre contenga los
  apellidos de los autores (p. ej. `Diaz.zip`), incluyendo:
  - El informe en PDF (carpeta `informe/`).
  - Los paquetes `Comete`, `Opinion`, `common` y `Benchmark`.
  - Un `Readme.txt` (no `.md`) describiendo los archivos entregados y las
    instrucciones de ejecución — responsabilidad de Persona A.
=======
# proyecto-simulador-polarizacion-en-redes
>>>>>>> 8741ad1446b2ccfe76574f89c1b6f09c7d79e310
