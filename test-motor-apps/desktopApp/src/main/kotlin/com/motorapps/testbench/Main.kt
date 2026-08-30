package com.motorapps.testbench

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.motorapps.core.common.MotorEngine
import com.motorapps.core.common.platformName
import com.motorapps.core.model.AppConfig

/** Configuración con la que el banco de pruebas inicializa el motor. */
private val testConfig = AppConfig(
    appId = "com.motorapps.testbench",
    appName = "Test Motor Apps",
    version = "1.0.0",
)

private val engine = MotorEngine(testConfig)

/** Un módulo del motor evaluable desde el banco de pruebas. */
private data class ModuleEntry(
    val path: String,
    val descripcion: String,
    val demo: (@Composable () -> Unit)? = null,
)

private val modules = listOf(
    ModuleEntry(":core:model", "Tipos base (AppConfig)") {
        Text("AppConfig usado por el banco de pruebas:", style = MaterialTheme.typography.titleMedium)
        Text("appId = ${testConfig.appId}")
        Text("appName = ${testConfig.appName}")
        Text("version = ${testConfig.version}")
    },
    ModuleEntry(":core:common", "Punto de entrada (MotorEngine)") {
        Text("MotorEngine.describe():", style = MaterialTheme.typography.titleMedium)
        Text(engine.describe())
        Text("platformName():", style = MaterialTheme.typography.titleMedium)
        Text(platformName())
    },
    ModuleEntry(":core:ads", "Módulo en construcción"),
    ModuleEntry(":core:ai", "Módulo en construcción"),
    ModuleEntry(":core:analytics", "Módulo en construcción"),
    ModuleEntry(":core:auth", "Módulo en construcción"),
    ModuleEntry(":core:billings", "Módulo en construcción"),
    ModuleEntry(":core:device-identity", "Módulo en construcción"),
    ModuleEntry(":core:interactive-maps", "Módulo en construcción"),
    ModuleEntry(":core:navigate", "Módulo en construcción"),
    ModuleEntry(":core:network", "Módulo en construcción"),
    ModuleEntry(":core:notifications-firebase-cloud", "Módulo en construcción"),
    ModuleEntry(":core:storage", "Módulo en construcción"),
    ModuleEntry(":core:themes", "Módulo en construcción"),
)

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Motor Apps — Test Bench") {
        MaterialTheme(colorScheme = darkColorScheme()) {
            Surface(modifier = Modifier.fillMaxSize()) {
                TestBench()
            }
        }
    }
}

@Composable
private fun TestBench() {
    var selected by remember { mutableStateOf(modules.first()) }

    Row(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.width(280.dp).fillMaxHeight().padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(modules) { module ->
                Card(onClick = { selected = module }, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(module.path, style = MaterialTheme.typography.titleSmall)
                        Text(module.descripcion, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(selected.path, style = MaterialTheme.typography.headlineSmall)
            HorizontalDivider()
            val demo = selected.demo
            if (demo != null) {
                demo()
            } else {
                Text("Este módulo todavía es un placeholder en motor-apps (Placeholder.kt).")
                Text("Cuando tenga API pública, agregale una demo acá en Main.kt.")
            }
        }
    }
}
