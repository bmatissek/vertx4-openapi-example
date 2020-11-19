import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    id("io.vertx.vertx-plugin") version "1.1.3"
}

version = "0.1.0"

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "11"

repositories {
    mavenCentral()
}

vertx {
     mainVerticle = "main.MainVerticle"

}

dependencies {
    implementation(kotlin("stdlib"))

    val vertxVersion = "4.0.0.CR1"
    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("io.vertx:vertx-web-openapi:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")
}
