val ktorVersion = "2.2.1"
val kotlinVersion = "1.8.0"
val logbackVersion = "1.2.3"
val mockkVersion = "1.13.0"

plugins {
    kotlin("jvm") version "1.7.22"
    id("io.ktor.plugin") version "2.2.1"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment", "--add-opens java.base/java.time=ALL-UNNAMED")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.mockk:mockk:1.13.4")
    implementation ("io.insert-koin:koin-ktor:3.3.1")
    implementation("org.postgresql:postgresql:42.5.3")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.30.1")
    implementation("io.insert-koin:koin-test:3.3.3")
    implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
    implementation("org.apache.kafka:kafka-clients:3.3.1")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("io.ktor:ktor-client-gson:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}
