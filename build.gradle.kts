plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "com.example.aicodeassistant"
version = "1.0"

repositories {
    mavenCentral()
}

intellij {
    version = "2023.3"
    type = "IC"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}