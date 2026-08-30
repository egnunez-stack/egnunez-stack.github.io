import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)

    // Módulos del motor bajo evaluación
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:ads"))
    implementation(project(":core:ai"))
    implementation(project(":core:analytics"))
    implementation(project(":core:auth"))
    implementation(project(":core:billings"))
    implementation(project(":core:device-identity"))
    implementation(project(":core:interactive-maps"))
    implementation(project(":core:navigate"))
    implementation(project(":core:network"))
    implementation(project(":core:notifications-firebase-cloud"))
    implementation(project(":core:storage"))
    implementation(project(":core:themes"))
}

compose.desktop {
    application {
        mainClass = "com.motorapps.testbench.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg)
            packageName = "test-motor-apps"
            packageVersion = "1.0.0"
        }
    }
}
