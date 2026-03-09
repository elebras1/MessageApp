import org.gradle.jvm.tasks.Jar

plugins {
    id("java")
    id("org.sonarqube") version "7.1.0.6387"
}

group = "org"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openjfx:javafx-controls:25.0.2")
    implementation("org.openjfx:javafx-graphics:25.0.2")
    implementation("org.openjfx:javafx-base:25.0.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<Jar>("jar") {
    manifest {
        attributes["Main-Class"] = "com.elebras1.message.MessageAppLauncher"
    }
}
