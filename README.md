# Sucursal_bancaria
# Proyecto Integrador 1 (Primer Hito) — Diseño e Implementación de Estructuras Lineales

## Aspectos evaluados

Lo que evaluamos es ¿Pueden entender, justificar, probar y modificar el código que entregaron?

| Dimensión | Qué se evalúa |
|---|---|
| Implementación | Que las estructuras funcionen correctamente y respeten su TDA |
| Diseño | Que hayan tomado buenas decisiones sobre representación, encapsulamiento y reutilización |
| Complejidad | Que puedan explicar costo temporal y espacial de las operaciones |
| Testing | Que hayan diseñado casos que realmente permitan detectar errores |
| Comprensión/defensa | Que cada integrante pueda explicar y modificar su código |

> **Nota:** El uso de herramientas de IA está permitido durante el desarrollo del proyecto. Sin embargo, cada integrante debe ser capaz de comprender, explicar, justificar, probar y modificar las implementaciones entregadas por sí mismo.

## Problema integrador

Diseñen una biblioteca de estructuras lineales y utilícenla para resolver un problema real, justificando cómo la representación elegida afecta las operaciones y su complejidad.

## 1. Introducción

En este proyecto deberán desarrollar una pequeña biblioteca de estructuras lineales y utilizarla para resolver un problema de gestión de información.

El objetivo es implementar estructuras que funcionen, así como también comprender cómo distintas representaciones afectan las operaciones, la complejidad y el comportamiento de un sistema. El sistema deberá utilizar las estructuras lineales desarrolladas por el grupo y no podrá utilizar directamente las implementaciones equivalentes disponibles en las bibliotecas estándar de Java para resolver las operaciones principales.

El proyecto se realizará en grupos ya establecidos y tendrá fecha de entrega: **23/08/2026**

Durante el desarrollo podrán utilizar herramientas de Inteligencia Artificial generativa y agentes de programación. Sin embargo, cada integrante deberá ser capaz de comprender, explicar, justificar, probar y modificar el código entregado.

El proyecto se organiza en 3 desafíos.

## 2. Objetivos

Al finalizar el proyecto deberán ser capaces de:

- implementar estructuras lineales a partir de su especificación;
- distinguir entre un TDA y su implementación;
- utilizar diferentes representaciones para resolver un mismo problema;
- identificar y mantener invariantes de las estructuras;
- analizar la complejidad temporal y espacial de las operaciones;
- seleccionar una estructura adecuada según los requerimientos de un problema;
- identificar oportunidades de optimización;
- diseñar casos de prueba, incluyendo casos borde;
- comparar experimentalmente diferentes implementaciones;
- trabajar colaborativamente en un proyecto de software.

## 3. Desafíos

### Desafío 1 — CONSTRUIR "Caja de Herramientas"

El primer desafío consiste en construir una biblioteca propia de estructuras lineales. Deberán implementar las estructuras indicadas en las interfaces proporcionadas para el proyecto.

Entre las estructuras a desarrollar se encuentran:

- Lista sobre array
- Lista simplemente enlazada
- Lista doblemente enlazada
- Lista circular simplemente enlazada
- Lista circular doblemente enlazada
- Pila
- Cola
- Pila o cola con prioridad

Para cada estructura deberán asegurarse de que funcione correctamente en diferentes situaciones, incluyendo:

- estructura vacía;
- un único elemento;
- varios elementos;
- inserciones;
- eliminaciones;
- búsquedas;
- casos borde.

Además, deberán desarrollar pruebas que permitan comprobar el correcto funcionamiento de sus implementaciones.

### Desafío 2 — MODELAR: Sucursal bancaria

#### Escenario

Una sucursal bancaria necesita organizar la información relativa a los productos bancarios de sus clientes, así como la atención en mostrador. Los mostradores están agrupados en sectores dedicados a distintos tipos de atención, como cuentas personales, préstamos, ejecutivos de cuenta, etc. Por auditoría, el sistema debe registrar todas las interacciones de los clientes con el banco, así como una copia de cualquier documentación firmada o entregada en dichos trámites. Algunas de estas interacciones pueden resultar en un nuevo producto registrado a nombre del cliente, o en una modificación a uno ya existente.

Su tarea es diseñar e implementar el sistema de gestión de la sucursal bancaria utilizando las estructuras lineales desarrolladas en el Desafío 1.

#### ¿Qué debe hacer el sistema?

El sistema deberá resolver las necesidades planteadas en el escenario. El grupo deberá analizar qué información necesita representar, qué operaciones deben estar disponibles y qué estructuras resultan adecuadas para cada necesidad. El sistema deberá permitir, como mínimo:

- **Registrar un cliente:** Cuando llega un cliente se debe registrar información que permita identificarlo y conocer los productos que tiene contratados.
- **Gestionar la atención en mostrador:** Los clientes que están esperando a ser atendidos deben ser gestionados de acuerdo con los criterios de prioridad habituales en salas de espera.
- **Auditoría:** registro de todas las interacciones de los clientes con los empleados de la sucursal, así como cambios en sus productos.
- **Registro documental:** Registro de la documentación relevante para el otorgamiento de distintos productos, incluyendo fecha de presentación y vigencia si aplica.
- **Consultar información:** El sistema deberá permitir realizar consultas que consideren útiles para el funcionamiento de la sucursal bancaria.

El grupo deberá definir al menos cinco operaciones/consultas que considere relevantes para el funcionamiento de la sucursal bancaria y justificar su elección. Las operaciones deberán requerir el uso de las estructuras desarrolladas y no podrán consistir únicamente en mostrar información directamente almacenada.

#### Implementación

Una vez definido el modelo, deberán implementar el sistema utilizando exclusivamente las estructuras desarrolladas por el grupo en el Desafío 1. El sistema deberá demostrar el funcionamiento de las decisiones tomadas mediante un conjunto de casos de prueba (orientados a la solución diseñada y además de las pruebas desarrolladas para validar la biblioteca de estructuras).

#### Análisis de complejidad

Para las operaciones principales del sistema deberán indicar la complejidad temporal de las operaciones de la solución implementada. Deberán explicar por qué la operación tiene ese costo en función de la estructura elegida.

> **Nota:** En este desafío no se evalúa solamente que el sistema funcione. Se evaluará especialmente la capacidad del grupo para transformar los requerimientos de un problema real en una representación mediante estructuras de datos, justificando las decisiones tomadas. Una misma situación puede resolverse utilizando diferentes estructuras de datos, pero no todas son igualmente adecuadas.

### Desafío 3 — OPTIMIZAR

Una implementación que funciona no necesariamente es una implementación eficiente. En este desafío deberán analizar situaciones en las que determinadas operaciones pueden convertirse en un cuello de botella. Deberán identificar el problema y proponer una solución.

Una vez implementado el sistema, deberán identificar al menos una operación cuya eficiencia pueda mejorarse mediante un cambio en la representación o en la estructura utilizada. Después:

1. identificar la operación;
2. medir/analizar su costo;
3. proponer una alternativa;
4. implementarla;
5. comparar ambas soluciones.

## 4. Requisitos generales

El proyecto deberá desarrollarse en **Java**. Deberán entregar:

### Código fuente

El proyecto completo, incluyendo:

- implementaciones;
- interfaces;
- aplicación;
- pruebas;
- archivos necesarios para ejecutar el proyecto.

### Informe

El informe deberá incluir:

- descripción general de la solución;
- estructuras utilizadas en la implementación;
- principales decisiones de diseño;
- análisis de complejidad;
- decisiones tomadas en el Desafío 2;
- optimizaciones realizadas en el Desafío 3;
- resultados de los experimentos realizados;
- registro del uso de herramientas de IA.

### Defensa

Al finalizar el proyecto se realizará una instancia de defensa.

La defensa será grupal e individual.

Durante y después de la defensa podrán realizarse preguntas sobre cualquier parte del proyecto y podrán solicitarse pequeñas modificaciones sobre el código.

## 5. Evaluación

La evaluación del trabajo considerará tanto las decisiones de diseño y su implementación como su justificación, además del análisis e informe que respalden dichas decisiones.

| Criterio | Peso |
|---|---|
| Implementación y diseño de las estructuras | 20% |
| Solución implementada y justificación de estructuras utilizadas | 20% |
| Correctitud y testing | 10% |
| Análisis de complejidad | 5% |
| Optimización y análisis experimental | 10% |
| Informe y calidad del proyecto | 15% |
| Presentación grupal | 20% |
| **Total** | **100%** |

La calificación individual final de cada alumno será la evaluación grupal del trabajo ponderada por su nota individual de la defensa. Se espera que todos los integrantes del equipo de trabajo conozcan en profundidad la solución propuesta y sean capaces de responder preguntas específicas del diseño de la solución.