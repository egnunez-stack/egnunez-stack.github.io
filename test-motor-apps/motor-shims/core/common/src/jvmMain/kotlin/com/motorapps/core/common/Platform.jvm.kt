package com.motorapps.core.common

/**
 * `actual` de JVM para el banco de pruebas desktop.
 * motor-apps aún no declara el target JVM; cuando lo haga, este archivo
 * debe mudarse a core/common/src/jvmMain en motor-apps y borrarse de acá.
 */
actual fun platformName(): String =
    "${System.getProperty("os.name")} ${System.getProperty("os.version")} " +
        "(JVM ${System.getProperty("java.version")})"
