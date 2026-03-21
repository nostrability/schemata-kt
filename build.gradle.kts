plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.serialization") version "1.9.24"
}

group = "nostrability"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

// Vendor schemas from schemata dist into resources.
// Set -PschemataDistDir=<path> to override the default location.
// Default looks for ../schemata/dist (sibling checkout) or /tmp/schemata-dist.
val schemataDistDir: String = project.findProperty("schemataDistDir")?.toString()
    ?: listOf(
        "${rootProject.projectDir}/../schemata/dist",
        "/tmp/schemata-dist"
    ).firstOrNull { file(it).isDirectory } ?: "${rootProject.projectDir}/../schemata/dist"

val vendorSchemas = tasks.register<Copy>("vendorSchemas") {
    description = "Copy compiled schemas from schemata dist into resources"
    from(schemataDistDir) {
        include("nips/**/*.json")
        include("mips/**/*.json")
        include("@/**/*.json")
    }
    into("${projectDir}/src/main/resources/schemas")
    // Only run if schemas aren't already vendored
    onlyIf {
        !file("${projectDir}/src/main/resources/schemas/nips").isDirectory ||
            fileTree("${projectDir}/src/main/resources/schemas/nips").files.isEmpty()
    }
}

// Ensure schemas are vendored before processing resources
tasks.named("processResources") {
    dependsOn(vendorSchemas)
}
