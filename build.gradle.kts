plugins {
    kotlin("jvm") version "1.4.32"
}

group = "com.noahhendrickson"
version = "0.1.3"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("com.destroystokyo.paper", "paper-api", "1.16.5-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")

    testImplementation("org.junit.jupiter", "junit-jupiter", "5.4.2")
    testImplementation("io.mockk","mockk","1.11.0")
    testImplementation("org.assertj", "assertj-core", "3.19.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.noahhendrickson.theboys.TheBoysKt"
    }

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)

    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
