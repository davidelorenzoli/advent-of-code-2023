plugins {
    kotlin("jvm") version "1.9.20"
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    implementation("org.junit.jupiter:junit-jupiter-params:5.10.1")
    implementation("org.junit.jupiter:junit-jupiter-engine:5.10.1")

    implementation("io.kotest:kotest-assertions-core-jvm:5.8.0")
    implementation("io.kotest:kotest-runner-junit5-jvm:5.8.0")

    // Junit runtime
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.1")
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}
