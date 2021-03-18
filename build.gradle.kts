plugins {
    java
    id("io.freefair.lombok") version "5.3.0"
    id("io.qameta.allure") version "2.5"
}

group = "com.demo-rest-assured"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junit5Version = "5.7.0"
val restAssuredVersion = "4.3.3"
val allureVersion = "2.13.8"

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-api:$junit5Version")
    implementation("org.junit.jupiter:junit-jupiter-params:$junit5Version")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junit5Version")

    implementation("io.rest-assured:rest-assured:$restAssuredVersion")
    implementation("io.rest-assured:json-schema-validator:$restAssuredVersion")

    testImplementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.0")
    implementation("com.github.javafaker:javafaker:1.0.2")
}

allure {
    autoconfigure = true
    version = "2.8.1"
}

tasks.test {
    environment("ADMIN_LOGIN", "admin")
    environment("ADMIN_PASSWORD", "password123")

    useJUnitPlatform()
}
