# test-motor-apps

Banco de pruebas **desktop** (Compose Multiplatform sobre JVM) para evaluar los
módulos de la librería [motor-apps](https://github.com/egnunez-stack/motor-apps).

## Layout esperado en disco

Ambos repos deben ser hermanos — el build referencia `../motor-apps`:

```
/Users/eduardonunez/apps/
├── motor-apps/        ← la librería (repo motor-apps)
└── test-motor-apps/   ← este repo
```

## Setup

1. Tener clonado `motor-apps` como hermano (ver arriba).
2. Crear `local.properties` en la raíz de este repo apuntando al Android SDK
   (los módulos del motor aplican `com.android.library`, así que AGP lo necesita
   aunque solo corras desktop):

   ```
   sdk.dir=/Users/eduardonunez/Library/Android/sdk
   ```

3. Correr la app:

   ```
   ./gradlew :desktopApp:run
   ```

   Empaquetado nativo (DMG): `./gradlew :desktopApp:packageDmg`

## Cómo está cableado

- `settings.gradle.kts` incluye los módulos `:core:*` de `../motor-apps` como
  subproyectos (mismos paths, así `:core:common → :core:model` sigue andando) y
  el `build-logic` del motor vía `includeBuild`, para que el plugin
  `motorapps.kmp.library` resuelva igual que en motor-apps.
- Los módulos del motor solo declaran targets **Android + iOS**. El
  `build.gradle.kts` raíz de este repo les agrega el target **JVM** sin tocar
  motor-apps, y les inyecta como fuente extra `motor-shims/<módulo>/src/jvmMain`,
  donde viven los `actual` de JVM que al motor le faltan (hoy: `platformName()`
  de `:core:common`).
- `desktopApp` es un módulo JVM puro con Compose Desktop que depende de todos
  los módulos del motor. En `Main.kt` cada módulo tiene una entrada; los que ya
  tienen API pública (`:core:model`, `:core:common`) tienen demo funcionando.

## Limitaciones y camino recomendado

- Un módulo del motor que dependa de APIs Android/iOS solo va a poder evaluarse
  acá cuando tenga `actual` de JVM (agregalo en `motor-shims/` mientras tanto).
- Lo limpio a mediano plazo es que **motor-apps declare el target `jvm()` en
  `KmpLibraryConventionPlugin`** y mueva los `actual` de `motor-shims/` a sus
  módulos (`core/<módulo>/src/jvmMain`). Cuando eso pase, borrá el bloque
  `subprojects { ... }` del `build.gradle.kts` raíz y la carpeta `motor-shims/`.

## Versiones

Alineadas con motor-apps: Kotlin 2.3.21, AGP 8.13.0, Gradle 8.14.3 (wrapper),
Compose Multiplatform 1.12.0, JDK 17+.
