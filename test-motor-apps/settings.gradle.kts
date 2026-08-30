pluginManagement {
    // Convention plugin del motor: "motorapps.kmp.library"
    includeBuild("../motor-apps/build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "test-motor-apps"

include(":desktopApp")

// Los módulos del motor se incluyen como subproyectos desde el checkout hermano,
// conservando sus paths (:core:xxx) para que las dependencias internas
// (p. ej. :core:common -> :core:model) sigan resolviendo.
val motorAppsDir = file("../motor-apps")
require(motorAppsDir.isDirectory) {
    "No se encontró motor-apps en ${motorAppsDir.absolutePath}. " +
        "Cloná ambos repos como hermanos: apps/motor-apps y apps/test-motor-apps."
}

val motorModules = listOf(
    "model",
    "common",
    "ads",
    "ai",
    "analytics",
    "auth",
    "billings",
    "device-identity",
    "interactive-maps",
    "navigate",
    "network",
    "notifications-firebase-cloud",
    "storage",
    "themes",
)

motorModules.forEach { name ->
    include(":core:$name")
    project(":core:$name").projectDir = motorAppsDir.resolve("core/$name")
}
project(":core").projectDir = motorAppsDir.resolve("core")
