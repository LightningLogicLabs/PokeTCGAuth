val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val dagger_version: String by project
val jedis_version: String by project
val hikari_cp_version: String by project
val postgres_version: String by project
val exposed_version: String by project
val argon2_version: String by project
val spring_framework_security_version: String by project
val bouncy_castle_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "com.poketcgdb"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")

    // Logback
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Jackson JSON Converter
    implementation("io.ktor:ktor-jackson:$ktor_version")

    // Exposed SQL
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")

    // Redis
    implementation("redis.clients:jedis:$jedis_version")

    // Argon2 Dependencies
    implementation("org.springframework.security:spring-security-crypto:${spring_framework_security_version}")
    implementation("org.bouncycastle:bcprov-jdk15on:${bouncy_castle_version}")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.slf4j:jcl-over-slf4j:1.7.30")

    // Postgres Driver
    implementation("org.postgresql:postgresql:$postgres_version")

    // Hikari CP
    implementation("com.zaxxer:HikariCP:$hikari_cp_version")

    // JWT Auth
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")

    // Testing
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
