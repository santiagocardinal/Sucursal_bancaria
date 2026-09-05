[![Santiago Cardinal](https://img.shields.io/badge/GitHub-Santiago_Cardinal-8E412E?logo=github&logoColor=black)](https://github.com/santiagocardinal)
[![Sebastián Chapper](https://img.shields.io/badge/GitHub-Sebastián_Chapper-BA6F4D?logo=github&logoColor=black)](https://github.com/sebachapper)
[![Nicolás Leyton](https://img.shields.io/badge/GitHub-Nicolás_Leyton-E6CEBC?logo=github&logoColor=black)](https://github.com/Nicosley)
[![Hernan López](https://img.shields.io/badge/GitHub-Hernan_López-A2A182?logo=github&logoColor=black)](https://github.com/HL0pez)
[![Rodrigo Montiel](https://img.shields.io/badge/GitHub-Rodrigo_Montiel-687259?logo=github&logoColor=black)](https://github.com/rodrigomontiel-44)
[![Luján Uhalde](https://img.shields.io/badge/GitHub-Luján_Uhalde-F4ECE2?logo=github&logoColor=black)](https://github.com/Lujan448)

---


# Sucursal bancaria

# $\textcolor{#1F6FEB}{\textsf{Proyecto Integrador 1 (Primer Hito) — Diseño e Implementación de Estructuras Lineales}}$

## $\textcolor{#1F6FEB}{\textsf{Aspectos evaluados}}$

Lo que evaluamos es: **¿Pueden entender, justificar, probar y modificar el código que entregaron?**

| Dimensión | Qué se evalúa |
|---|---|
| Implementación | Que las estructuras funcionen correctamente y respeten su TDA |
| Diseño | Que hayan tomado buenas decisiones sobre representación, encapsulamiento y reutilización |
| Complejidad | Que puedan explicar costo temporal y espacial de las operaciones |
| Testing | Que hayan diseñado casos que realmente permitan detectar errores |
| Comprensión/defensa | Que cada integrante pueda explicar y modificar su código |

> **Nota:** El uso de herramientas de IA está permitido durante el desarrollo del proyecto. Sin embargo, cada integrante debe ser capaz de comprender, explicar, justificar, probar y modificar las implementaciones entregadas por sí mismo.

## $\textcolor{#1F6FEB}{\textsf{Problema integrador}}$

Diseñen una biblioteca de estructuras lineales y utilícenla para resolver un problema real, justificando cómo la representación elegida afecta las operaciones y su complejidad.

## $\textcolor{#1F6FEB}{\textsf{1. Introducción}}$

En este proyecto deberán desarrollar una pequeña biblioteca de estructuras lineales y utilizarla para resolver un problema de gestión de información.

El objetivo es implementar estructuras que funcionen, así como también comprender cómo distintas representaciones afectan las operaciones, la complejidad y el comportamiento de un sistema. El sistema deberá utilizar las estructuras lineales desarrolladas por el grupo y no podrá utilizar directamente las implementaciones equivalentes disponibles en las bibliotecas estándar de Java para resolver las operaciones principales.

El proyecto se realizará en grupos ya establecidos y tendrá fecha de entrega: **23/08/2026**

Durante el desarrollo podrán utilizar herramientas de Inteligencia Artificial generativa y agentes de programación. Sin embargo, cada integrante deberá ser capaz de comprender, explicar, justificar, probar y modificar el código entregado.

El proyecto se organiza en 3 desafíos.

## $\textcolor{#1F6FEB}{\textsf{2. Objetivos}}$

Al finalizar el proyecto deberán ser capaces de:

* implementar estructuras lineales a partir de su especificación;
* distinguir entre un TDA y su implementación;
* utilizar diferentes representaciones para resolver un mismo problema;
* identificar y mantener invariantes de las estructuras;
* analizar la complejidad temporal y espacial de las operaciones;
* seleccionar una estructura adecuada según los requerimientos de un problema;
* identificar oportunidades de optimización;
* diseñar casos de prueba, incluyendo casos borde;
* comparar experimentalmente diferentes implementaciones;
* trabajar colaborativamente en un proyecto de software.

## $\textcolor{#1F6FEB}{\textsf{3. Desafíos}}$

### $\textcolor{#1F6FEB}{\textsf{Desafío 1 — CONSTRUIR "Caja de Herramientas"}}$

El primer desafío consiste en construir una biblioteca propia de estructuras lineales. Deberán implementar las estructuras indicadas en las interfaces proporcionadas para el proyecto.

Entre las estructuras a desarrollar se encuentran:

* Lista sobre array
* Lista simplemente enlazada
* Lista doblemente enlazada
* Lista circular simplemente enlazada
* Lista circular doblemente enlazada
* Pila
* Cola
* Pila o cola con prioridad

Para cada estructura deberán asegurarse de que funcione correctamente en diferentes situaciones, incluyendo:

* estructura vacía;
* un único elemento;
* varios elementos;
* inserciones;
* eliminaciones;
* búsquedas;
* casos borde.

Además, deberán desarrollar pruebas que permitan comprobar el correcto funcionamiento de sus implementaciones.

### $\textcolor{#1F6FEB}{\textsf{Desafío 2 — MODELAR: Sucursal bancaria}}$

#### $\textcolor{#1F6FEB}{\textsf{Escenario}}$

Una sucursal bancaria necesita organizar la información relativa a los productos bancarios de sus clientes, así como la atención en mostrador. Los mostradores están agrupados en sectores dedicados a distintos tipos de atención, como cuentas personales, préstamos, ejecutivos de cuenta, etc. Por auditoría, el sistema debe registrar todas las interacciones de los clientes con el banco, así como una copia de cualquier documentación firmada o entregada en dichos trámites. Algunas de estas interacciones pueden resultar en un nuevo producto registrado a nombre del cliente, o en una modificación a uno ya existente.

Su tarea es diseñar e implementar el sistema de gestión de la sucursal bancaria utilizando las estructuras lineales desarrolladas en el Desafío 1.

#### $\textcolor{#1F6FEB}{\textsf{¿Qué debe hacer el sistema?}}$

El sistema deberá resolver las necesidades planteadas en el escenario. El grupo deberá analizar qué información necesita representar, qué operaciones deben estar disponibles y qué estructuras resultan adecuadas para cada necesidad. El sistema deberá permitir, como mínimo:

* **Registrar un cliente:** Cuando llega un cliente se debe registrar información que permita identificarlo y conocer los productos que tiene contratados.
* **Gestionar la atención en mostrador:** Los clientes que están esperando a ser atendidos deben ser gestionados de acuerdo con los criterios de prioridad habituales en salas de espera.
* **Auditoría:** registro de todas las interacciones de los clientes con los empleados de la sucursal, así como cambios en sus productos.
* **Registro documental:** Registro de la documentación relevante para el otorgamiento de distintos productos, incluyendo fecha de presentación y vigencia si aplica.
* **Consultar información:** El sistema deberá permitir realizar consultas que consideren útiles para el funcionamiento de la sucursal bancaria.

El grupo deberá definir al menos cinco operaciones/consultas que considere relevantes para el funcionamiento de la sucursal bancaria y justificar su elección. Las operaciones deberán requerir el uso de las estructuras desarrolladas y no podrán consistir únicamente en mostrar información directamente almacenada.

#### $\textcolor{#1F6FEB}{\textsf{Implementación}}$

Una vez definido el modelo, deberán implementar el sistema utilizando exclusivamente las estructuras desarrolladas por el grupo en el Desafío 1. El sistema deberá demostrar el funcionamiento de las decisiones tomadas mediante un conjunto de casos de prueba (orientados a la solución diseñada y además de las pruebas desarrolladas para validar la biblioteca de estructuras).

#### $\textcolor{#1F6FEB}{\textsf{Análisis de complejidad}}$

Para las operaciones principales del sistema deberán indicar la complejidad temporal de las operaciones de la solución implementada. Deberán explicar por qué la operación tiene ese costo en función de la estructura elegida.

> **Nota:** En este desafío no se evalúa solamente que el sistema funcione. Se evaluará especialmente la capacidad del grupo para transformar los requerimientos de un problema real en una representación mediante estructuras de datos, justificando las decisiones tomadas. Una misma situación puede resolverse utilizando diferentes estructuras de datos, pero no todas son igualmente adecuadas.

### $\textcolor{#1F6FEB}{\textsf{Desafío 3 — OPTIMIZAR}}$

Una implementación que funciona no necesariamente es una implementación eficiente. En este desafío deberán analizar situaciones en las que determinadas operaciones pueden convertirse en un cuello de botella. Deberán identificar el problema y proponer una solución.

Una vez implementado el sistema, deberán identificar al menos una operación cuya eficiencia pueda mejorarse mediante un cambio en la representación o en la estructura utilizada. Después:

1. identificar la operación;
2. medir/analizar su costo;
3. proponer una alternativa;
4. implementarla;
5. comparar ambas soluciones.

## $\textcolor{#1F6FEB}{\textsf{4. Requisitos generales}}$

El proyecto deberá desarrollarse en **Java**. Deberán entregar:

### $\textcolor{#1F6FEB}{\textsf{Código fuente}}$

El proyecto completo, incluyendo:

* implementaciones;
* interfaces;
* aplicación;
* pruebas;
* archivos necesarios para ejecutar el proyecto.

### $\textcolor{#1F6FEB}{\textsf{Informe}}$

El informe deberá incluir:

* descripción general de la solución;
* estructuras utilizadas en la implementación;
* principales decisiones de diseño;
* análisis de complejidad;
* decisiones tomadas en el Desafío 2;
* optimizaciones realizadas en el Desafío 3;
* resultados de los experimentos realizados;
* registro del uso de herramientas de IA.

### $\textcolor{#1F6FEB}{\textsf{Defensa}}$

Al finalizar el proyecto se realizará una instancia de defensa.

La defensa será grupal e individual.

Durante y después de la defensa podrán realizarse preguntas sobre cualquier parte del proyecto y podrán solicitarse pequeñas modificaciones sobre el código.

## $\textcolor{#1F6FEB}{\textsf{5. Evaluación}}$

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

---

# $\textcolor{#CF222E}{\textsf{Proyecto Integrador 1 (Segundo Hito) — Diseño e Implementación de Estructuras Jerárquicas}}$

## $\textcolor{#CF222E}{\textsf{Aspectos evaluados}}$

Lo que evaluamos es: **¿Pueden entender, justificar, probar y modificar el código que entregaron?**

| Dimensión | Qué se evalúa |
|---|---|
| Implementación | Que las estructuras funcionen correctamente y respeten su TDA |
| Diseño | Que hayan tomado buenas decisiones sobre representación, encapsulamiento y reutilización |
| Integración | Que la solución extienda lo construido en el primer hito sin romper su comportamiento |
| Complejidad | Que puedan explicar costo temporal y espacial de las operaciones |
| Testing | Que hayan diseñado casos que realmente permitan detectar errores |
| Comprensión/defensa | Que cada integrante pueda explicar y modificar su código |

> **Nota:** El uso de herramientas de IA está permitido durante el desarrollo del proyecto. Sin embargo, cada integrante debe ser capaz de comprender, explicar, justificar, probar y modificar las implementaciones entregadas por sí mismo.

## $\textcolor{#CF222E}{\textsf{Problema integrador}}$

Extiendan su biblioteca de estructuras con estructuras jerárquicas y utilícenla para resolver requerimientos que las estructuras lineales no resuelven adecuadamente, justificando cómo la representación elegida afecta las operaciones y su complejidad.

## $\textcolor{#CF222E}{\textsf{1. Introducción}}$

En este segundo hito deberán extender la biblioteca de estructuras desarrollada en el primer hito con estructuras jerárquicas, y utilizarla para incorporar nuevos requerimientos al sistema que ya construyeron.

El objetivo es reconocer cuándo un problema tiene estructura jerárquica, representarlo como tal y comprender qué operaciones se vuelven posibles —o dejan de ser costosas— al hacerlo. El sistema deberá utilizar las estructuras desarrolladas por el grupo y **no podrá utilizar directamente** las implementaciones equivalentes disponibles en las bibliotecas estándar de Java (`TreeMap`, `TreeSet`, `PriorityQueue` u otras) para resolver las operaciones principales. Sí podrán utilizar `Comparable` y `Comparator` para definir criterios de orden, y podrán implementar las operaciones de forma recursiva o iterativa, siempre que justifiquen la decisión.

El proyecto se realizará en los mismos grupos y tendrá fecha de entrega: **09/09/2026**.

Este hito parte de la solución entregada en el primer hito. Los grupos cuya entrega anterior haya quedado incompleta deberán completarla antes de incorporar los nuevos requerimientos, acordando con el docente el punto de partida.

Durante el desarrollo podrán utilizar herramientas de Inteligencia Artificial generativa y agentes de programación. Sin embargo, cada integrante deberá ser capaz de comprender, explicar, justificar, probar y modificar el código entregado.

El proyecto se organiza en **3 desafíos**.

## $\textcolor{#CF222E}{\textsf{2. Objetivos}}$

Al finalizar el proyecto deberán ser capaces de:

* implementar estructuras jerárquicas a partir de su especificación;
* reconocer cuándo un problema tiene estructura jerárquica y representarlo como tal;
* distinguir entre un TDA y su implementación;
* implementar y seleccionar el recorrido adecuado para cada operación;
* comprender la relación entre árboles generales y árboles binarios;
* identificar y mantener invariantes de las estructuras;
* analizar la complejidad temporal y espacial de las operaciones, distinguiendo caso promedio y peor caso;
* explicar el efecto de la altura del árbol sobre el costo de las operaciones;
* extender una solución existente sin romper las funcionalidades ya entregadas;
* diseñar casos de prueba, incluyendo casos borde;
* comparar experimentalmente diferentes implementaciones;
* trabajar colaborativamente en un proyecto de software.

## $\textcolor{#CF222E}{\textsf{3. Desafíos}}$

### $\textcolor{#CF222E}{\textsf{Desafío 1 — CONSTRUIR: la caja de herramientas crece}}$

El primer desafío consiste en extender la biblioteca propia de estructuras con las estructuras jerárquicas indicadas en las interfaces proporcionadas para el proyecto.

Entre las estructuras a desarrollar se encuentran:

* Árbol binario
* Árbol binario de búsqueda
* Árbol binario de búsqueda balanceado
* Árbol general n-ario
* Montículo binario (cola de prioridad)
* Recorridos: preorden, inorden, postorden y por niveles

El recorrido por niveles deberá apoyarse en las estructuras lineales desarrolladas en el primer hito. Para cada estructura deberán asegurarse de que funcione correctamente en diferentes situaciones, incluyendo:

* estructura vacía;
* un único nodo;
* árbol degenerado y árbol balanceado;
* inserciones;
* eliminaciones, incluyendo todos los casos del borrado en un árbol de búsqueda;
* búsquedas;
* recorridos;
* casos borde.

Además, deberán desarrollar pruebas que permitan comprobar el correcto funcionamiento de sus implementaciones, incluyendo casos que evidencien la diferencia de comportamiento entre un árbol balanceado y uno degenerado.

### $\textcolor{#CF222E}{\textsf{Desafío 2 — MODELAR: Sucursal Bancaria}}$

#### $\textcolor{#CF222E}{\textsf{Escenario}}$

La sucursal bancaria incorpora dos cambios comerciales que el sistema actual no está en condiciones de soportar.

El primero es la **venta de paquetes**. Un producto contratado puede ser, en realidad, un conjunto de productos: una cuenta con subcuentas en distintas monedas, una tarjeta con adicionales, un seguro asociado. Cada uno de esos componentes puede a su vez estar compuesto por otros, y la profundidad depende del paquete comercializado. Contratar un paquete da de alta todo su contenido, pero el cliente puede dar de baja componentes individuales sin perder el resto, y la sucursal necesita informar la posición consolidada de un cliente considerando todo lo que sus productos contienen.

El segundo cambio es que las **comisiones dejan de ser importes fijos**. El área comercial define fórmulas —combinaciones de saldos, cantidades y porcentajes con operadores aritméticos y paréntesis— que se cargan en el sistema como texto y se modifican sin intervención del área de sistemas. El sistema debe evaluarlas para liquidar el mes, volver a mostrarlas tal como fueron definidas para incluirlas en el contrato del cliente, y permitir simular el efecto de un cambio sobre la cartera antes de aplicarlo.

Se mantienen vigentes las necesidades planteadas en el primer hito: registro de clientes y sus productos, atención en mostrador, auditoría de las interacciones y registro documental.

Su tarea es extender el sistema de gestión de la sucursal bancaria utilizando las estructuras desarrolladas en el Desafío 1.

#### $\textcolor{#CF222E}{\textsf{¿Qué debe hacer el sistema?}}$

El sistema deberá resolver las necesidades planteadas en el escenario. El grupo deberá analizar qué información necesita representar, qué operaciones deben estar disponibles y qué estructuras resultan adecuadas para cada necesidad. El sistema deberá permitir, como mínimo:

* **Mantener las funcionalidades del primer hito**, adaptadas al nuevo modelo donde corresponda.
* **Productos compuestos**: registrar productos que contienen otros productos, sin fijar de antemano cuántos niveles de composición admite un paquete.
* **Alta y baja de paquetes**: contratar un paquete da de alta todo su contenido; dar de baja un componente individual debe regirse por una regla explícita, definida y justificada por el grupo, sobre qué ocurre con aquello que ese componente contiene.
* **Posición consolidada**: informar saldos y totales de un producto contratado considerando todo lo que lo compone, así como de cualquiera de sus partes por separado. Cuando los componentes estén expresados en distintas monedas, la consolidación deberá hacerse por moneda o convertirse a una moneda de referencia; el criterio deberá ser definido y justificado por el grupo.
* **Fórmulas de comisión**: cargar una fórmula a partir de su texto, conservarla y evaluarla contra los datos de un cliente. Las fórmulas deberán admitir, como mínimo, las cuatro operaciones aritméticas, paréntesis y un conjunto acotado de variables definido por el grupo —por ejemplo, saldo promedio, cantidad de movimientos o cantidad de productos contratados—, cuyos valores se obtienen de los datos del cliente. El sistema deberá además poder volver a mostrar la fórmula sin paréntesis redundantes: si se cargó `a + (b * c)`, deberá mostrarse como `a + b * c`.
* **Liquidación y simulación**: aplicar las fórmulas vigentes a la cartera de clientes, y simular el efecto de una fórmula nueva sin modificar los datos.
* **Auditoría de los cambios**: el alta y la baja de productos, así como los cambios de fórmula, deberán quedar registrados en el mecanismo de auditoría del primer hito.
* **Búsqueda de clientes y productos**: localizar un cliente por su documento y un producto por su número de cuenta sin recorrer la totalidad de los registros, y listar la cartera ordenada por documento.
* **Atención en mostrador**: la espera del primer hito deberá gestionarse mediante la estructura de prioridad desarrollada en el Desafío 1, permitiendo modificar la prioridad de un cliente que ya está esperando.
* **Consultar información**: el sistema deberá permitir realizar consultas que se consideren útiles para el funcionamiento de la sucursal bancaria.

El grupo deberá definir **al menos cinco operaciones/consultas** que considere relevantes para el funcionamiento de la sucursal bancaria y justificar su elección. Las operaciones deberán requerir el uso de las estructuras desarrolladas y no podrán consistir únicamente en mostrar información directamente almacenada. **Al menos tres de ellas** deberán ser consultas que la solución entregada en el primer hito no podría responder sin recorrer la totalidad de los registros.

#### $\textcolor{#CF222E}{\textsf{Implementación}}$

Una vez definido el modelo, deberán implementar las nuevas funcionalidades utilizando exclusivamente las estructuras desarrolladas por el grupo. Para la carga de las fórmulas podrán apoyarse en las estructuras lineales del primer hito. El sistema deberá demostrar el funcionamiento de las decisiones tomadas mediante un conjunto de casos de prueba (orientados a la solución diseñada y además de las pruebas desarrolladas para validar la biblioteca de estructuras).

#### $\textcolor{#CF222E}{\textsf{Análisis de complejidad}}$

Para las operaciones principales del sistema deberán indicar la complejidad temporal de las operaciones de la solución implementada. Deberán explicar por qué la operación tiene ese costo en función de la estructura elegida, y en qué se diferencia del costo que tenía la operación equivalente en el primer hito.

> **Nota:** En este desafío no se evalúa solamente que el sistema funcione. Se evaluará especialmente la capacidad del grupo para reconocer la estructura jerárquica presente en el problema y representarla, justificando las decisiones tomadas. Una misma situación puede resolverse utilizando diferentes estructuras de datos, pero no todas son igualmente adecuadas.

### $\textcolor{#CF222E}{\textsf{Desafío 3 — OPTIMIZAR}}$

Una implementación que funciona no necesariamente es una implementación eficiente. A diferencia del primer hito, aquí no es necesario buscar un cuello de botella hipotético: la solución que ustedes mismos entregaron contiene operaciones cuyo costo crece con el tamaño total del sistema y que la nueva representación permite acotar.

Deberán:

1. identificar al menos una operación de la solución del primer hito cuyo costo dependa del recorrido completo de una estructura;
2. medir/analizar su costo;
3. proponer una alternativa basada en las estructuras del Desafío 1;
4. implementarla;
5. comparar ambas soluciones experimentalmente, con volúmenes crecientes de datos y casos que incluyan el peor caso de cada implementación.

La operación elegida puede ser una de las que el Desafío 2 ya exige resolver con las nuevas estructuras; en ese caso los puntos 3 y 4 ya están cubiertos y el trabajo del desafío consiste en la medición y la comparación.

Las mediciones deberán presentarse en el informe junto con la metodología utilizada: cómo se generaron los datos, qué se midió y cuántas repeticiones se realizaron.

La implementación de referencia deberá ser la efectivamente entregada en el primer hito, o una versión corregida de ella. **No se aceptará** una implementación construida especialmente para el experimento con el fin de exagerar la mejora obtenida.

## $\textcolor{#CF222E}{\textsf{4. Requisitos generales}}$

El proyecto deberá desarrollarse en **Java**. Deberán entregar:

### $\textcolor{#CF222E}{\textsf{Código fuente}}$

El proyecto completo, incluyendo:

* implementaciones;
* interfaces;
* aplicación;
* pruebas;
* archivos necesarios para ejecutar el proyecto.

### $\textcolor{#CF222E}{\textsf{Informe}}$

El informe deberá incluir:

* descripción general de la solución;
* relación con el primer hito: qué se conservó, qué se reemplazó y por qué;
* estructuras utilizadas en la implementación e invariantes que mantienen;
* principales decisiones de diseño;
* análisis de complejidad;
* decisiones tomadas en el Desafío 2;
* optimizaciones realizadas en el Desafío 3;
* resultados de los experimentos realizados;
* registro del uso de herramientas de IA.

### $\textcolor{#CF222E}{\textsf{Defensa}}$

Al finalizar el proyecto se realizará una instancia de defensa. La defensa será grupal e individual.

Durante y después de la defensa podrán realizarse preguntas sobre cualquier parte del proyecto y podrán solicitarse pequeñas modificaciones sobre el código.

## $\textcolor{#CF222E}{\textsf{5. Evaluación}}$

La evaluación del trabajo considerará tanto las decisiones de diseño y su implementación como su justificación, además del análisis e informe que respalden dichas decisiones.

| Criterio | Peso |
|---|---|
| Implementación y diseño de las estructuras | 20% |
| Solución implementada y justificación de estructuras utilizadas | 15% |
| Integración con la solución del primer hito | 5% |
| Correctitud y testing | 10% |
| Análisis de complejidad | 5% |
| Optimización y análisis experimental | 10% |
| Informe y calidad del proyecto | 15% |
| Presentación grupal | 20% |
| **Total** | **100%** |

La calificación individual final de cada alumno será la evaluación grupal del trabajo ponderada por su nota individual de la defensa. Se espera que todos los integrantes del equipo de trabajo conozcan en profundidad la solución propuesta y sean capaces de responder preguntas específicas del diseño de la solución.
