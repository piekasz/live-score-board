plugins {
    java
    groovy
    id("com.diffplug.spotless") version "7.0.4"
}

group = "pl.ppiekarski"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

dependencies {

    testImplementation("org.apache.groovy:groovy:4.0.21")
    testImplementation("org.spockframework:spock-core:2.3-groovy-4.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

spotless {
    java {
        target("**/*.java") // Target all Java files
        trimTrailingWhitespace()
        endWithNewline()
    }
}