val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
plugins {
    application
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.5.31"
}

group = "com.acuscorp"
version = "0.0.1"

application {
    mainClass.set("com.example.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {

    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:1.2.9")
    implementation("io.ktor:ktor-server-call-logging:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-html-builder:$ktor_version")

    testImplementation("io.ktor:ktor-server-test-host:2.3.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.20")

    implementation("io.insert-koin:koin-ktor:3.3.1")
    implementation("io.insert-koin:koin-logger-slf4j:3.3.1")

    implementation("io.ktor:ktor-server-default-headers:$ktor_version")

    implementation("io.ktor:ktor-server-status-pages:$ktor_version")

}