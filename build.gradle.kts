import org.gradle.jvm.tasks.Jar

plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.0.14"
    id("org.sonarqube") version "7.1.0.6387"
}

group = "org"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

javafx {
    version = "25.0.1"
    modules = listOf("javafx.controls", "javafx.graphics", "javafx.base", "javafx.fxml")
}

dependencies {
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

tasks.register<JavaExec>("runSwing") {
    group = "application"
    description = "Lance l'application en mode Swing"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.elebras1.message.MessageAppLauncher")
    jvmArgs("--enable-preview")
}

tasks.register<JavaExec>("runJavaFx") {
    group = "application"
    description = "Lance l'application en mode JavaFX"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.elebras1.message.MessageAppJavaFxLauncher")
    jvmArgs("--enable-preview")
}