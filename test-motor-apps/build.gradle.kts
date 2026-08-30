import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
}

// Los módulos del motor declaran solo targets Android + iOS (KmpLibraryConventionPlugin).
// El banco de pruebas les agrega el target JVM para poder consumirlos desde desktop,
// sin modificar motor-apps. Si un módulo tiene `expect` sin `actual` de JVM, el actual
// vive en este repo, bajo motor-shims/<path-del-módulo>/src/jvmMain/kotlin.
subprojects {
    if (path.startsWith(":core:")) {
        val shimDir = rootDir.resolve("motor-shims" + path.replace(":", "/") + "/src/jvmMain/kotlin")
        plugins.withId("org.jetbrains.kotlin.multiplatform") {
            extensions.configure<KotlinMultiplatformExtension> {
                jvm()
                sourceSets.named("jvmMain") {
                    kotlin.srcDir(shimDir)
                }
            }
        }
    }
}
