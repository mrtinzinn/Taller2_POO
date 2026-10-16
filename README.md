# 🍻 La Fonda del Papu

## 📌 Descripción del proyecto

**La Fonda del Papu** es un programa desarrollado en Java que permite registrar y administrar el consumo de comidas y bebidas de distintos participantes de una fonda.

El sistema permite gestionar registros de terremotos y choripanes, consultar información, modificar o eliminar registros y obtener estadísticas relacionadas con el consumo.

## 🎯 Objetivos

- Registrar participantes y sus consumos.
- Leer y almacenar información mediante archivos de texto.
- Agregar, editar y eliminar registros.
- Consultar los consumos registrados.
- Calcular estadísticas generales e individuales.

## ⚙️ Tecnologías utilizadas

- **Lenguaje:** Java.
- **Entorno de desarrollo:** Eclipse.
- **Archivos de texto:** almacenamiento de participantes y consumos.
- **Control de versiones:** Git y GitHub.

## 🍹 Funcionalidades

### 1. Visualización de registros
Permite consultar los consumos registrados y revisar la información asociada a cada comida o bebida.

### 2. Administración de registros
Permite agregar nuevos consumos, editar registros existentes y eliminar aquellos que ya no se necesitan.

### 3. Estadísticas
Permite obtener información sobre el consumo, incluyendo:

- Cantidad de choripanes registrados.
- Cantidad de terremotos y litros consumidos.
- Consumo de choripanes por participante.
- Consumo de terremotos por participante.
- Cálculo de calorías.
- Cantidad de días en que se cumple el criterio establecido para el consumo de terremoto.

## 📂 Estructura del proyecto

```text
La-Fonda-del-Papu/
├── src/
│   └── taller2/
│       ├── Main.java
│       └── Comida.java
├── Participantes.txt
├── Consumido.txt
└── README.md
```

*La estructura puede variar dependiendo de cómo esté organizado el proyecto en Eclipse.*

## 📄 Formato de los archivos

### Participantes.txt

Contiene los nombres de los participantes, uno por línea.

```text
Martin
Ana
Pedro
```

### Consumido.txt

Cada línea representa un consumo. Los datos se separan mediante punto y coma (`;`).

**Terremoto:**

```text
Terremoto;Martin;2;2026-09-16
```

Formato: `tipo;consumidor;litros;fecha`

**Choripán:**

```text
Choripan;Martin;9;4;2026-09-16
```

Formato: `tipo;consumidor;largo;ancho;fecha`

Las fechas utilizan el formato `AAAA-MM-DD`.

## ▶️ Cómo ejecutar el programa

1. Descargar o clonar el repositorio.
2. Abrir Eclipse e importar el proyecto Java.
3. Verificar que los archivos `Participantes.txt` y `Consumido.txt` estén ubicados en la carpeta desde la cual el programa los lee.
4. Ejecutar la clase `Main.java`.
5. Utilizar el menú de la consola para acceder a las funcionalidades.

## 👨‍💻 Autor

**Martin Hofer**
**Vicente Jara**
## 📚 Contexto académico

Proyecto desarrollado como parte de actividades académicas de programación en Java, aplicando conceptos de programación orientada a objetos, manejo de archivos, arreglos y validación de datos.
