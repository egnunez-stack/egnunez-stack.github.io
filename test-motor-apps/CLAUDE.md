# test-motor-apps

## Qué es este proyecto

App **desktop** (Compose Multiplatform sobre JVM puro) que sirve de banco de
pruebas para los módulos de la librería KMP **motor-apps**
(egnunez-stack/motor-apps). No es un producto: es la herramienta para evaluar
la API de cada módulo del motor a medida que se implementa.

- Idioma de trabajo con el usuario: **español**.
- Desarrollo local del usuario: macOS, repo en `/Users/eduardonunez/apps/test-motor-apps`,
  con motor-apps clonado como hermano en `/Users/eduardonunez/apps/motor-apps`
  (**requisito**: el build referencia `../motor-apps`).

## Versiones (fuente de verdad: `gradle/libs.versions.toml`)

Deben mantenerse **alineadas con motor-apps**:

| Herramienta | Versión |
|---|---|
| Kotlin | 2.3.21 |
| AGP | 8.13.0 |
| Gradle (wrapper) | 8.14.3 |
| Compose Multiplatform | 1.12.0 |
| compileSdk / minSdk | 36 / 24 (los usa el convention plugin del motor) |

## Estructura

```
settings.gradle.kts   → incluye :desktopApp + los :core:* de ../motor-apps
                        (projectDir remapeado) y el build-logic del motor
build.gradle.kts      → agrega el target jvm() a los módulos del motor e
                        inyecta motor-shims/ como fuente jvmMain extra
desktopApp/           → app Compose Desktop (JVM puro), Main.kt con una
                        entrada/demo por módulo del motor
motor-shims/          → actuals de JVM que a motor-apps le faltan
                        (espejando la ruta del módulo)
```

## Convenciones

- Los `actual` de JVM para módulos del motor van en
  `motor-shims/core/<módulo>/src/jvmMain/kotlin`, con el mismo package del
  módulo. Son temporales: cuando motor-apps declare `jvm()` en su convention
  plugin, se mudan allá y se borra el bloque `subprojects` del root.
- Cada módulo nuevo del motor se registra en tres lugares: la lista
  `motorModules` de `settings.gradle.kts`, las `dependencies` de
  `desktopApp/build.gradle.kts` y la lista `modules` de `Main.kt`.
- **Flujo git: SIEMPRE trabajar sobre la rama `develop`, nunca directo sobre
  `main`** (igual que motor-apps). Ramas `claude/*` salen de `develop`.

## Builds y validación

- Correr la app: `./gradlew :desktopApp:run`. Requiere `local.properties` con
  `sdk.dir` (AGP se configura aunque solo se compile desktop).
- **En sesiones remotas de Claude Code el build NO funciona**: el proxy bloquea
  `dl.google.com` (AGP y Android SDK irresolubles). Validar pidiéndole al
  usuario que corra `./gradlew :desktopApp:run` en su Mac.
