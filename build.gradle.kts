plugins {
    java

    id("io.freefair.lombok") version "6.3.0"
    id("io.qameta.allure") version "2.9.6"
}

group = "com.demo-rest-assured"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val allureVersion = "2.22.0"

dependencies {
    // slf4j to avoid warning http://www.slf4j.org/codes.html#StaticLoggerBinder
    val slf4jVersion = "1.7.32"
    runtimeOnly("org.slf4j:slf4j-api:$slf4jVersion")
    runtimeOnly("org.slf4j:slf4j-simple:$slf4jVersion")

    // JUnit5
    val junit5Version = "5.8.2"
    implementation("org.junit.jupiter:junit-jupiter-api:$junit5Version")
    implementation("org.junit.jupiter:junit-jupiter-params:$junit5Version")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junit5Version")

    // Allure
    implementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    implementation("io.qameta.allure:allure-junit5:$allureVersion")

    // Jackson
    val jacksonVersion = "2.13.1"
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    // Other
    implementation("io.rest-assured:rest-assured:4.4.0")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

allure {
    version.set(allureVersion)
}

tasks.test {
    environment("ADMIN_LOGIN", "admin")
    environment("ADMIN_PASSWORD", "password123")

    useJUnitPlatform()
}
