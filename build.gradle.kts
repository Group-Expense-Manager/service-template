import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${tools.versions.kotlin.get()}")
    }
}

plugins {
    id("application")
    id("java")
    id("maven-publish")

    alias(tools.plugins.dependency.management)
    alias(tools.plugins.spring.boot)
    alias(tools.plugins.kover)
    alias(tools.plugins.ktlint.core)
    alias(tools.plugins.ktlint.idea)
    alias(tools.plugins.scmversion)
    alias(tools.plugins.kotlin.jvm)
    alias(tools.plugins.kotlin.spring)

    idea
}

application {
    mainClass.set("pl.edu.agh.gem.AppRunner")
}

scm {
    version {
        type = "threeDigits"
        initialVersion = "1.0.0"
    }
}

project.group = "pl.edu.agh.gem"
project.version = scm.version.version

configurations {
    implementation.get().exclude("commons-logging:commons-logging")
    implementation.get().exclude("org.slf4j:slf4j-log4j12")
    implementation.get().exclude("org.slf4j:slf4j-jcl")
    implementation.get().exclude("log4j:log4j")
}

apply(plugin = "kotlin")
apply(plugin = "kotlin-spring")
apply(plugin = "maven-publish")
apply(plugin = "java")
apply(plugin = "kover")

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation(tools.bundles.kotlin)
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation(libs.kotlinlogging)

    testImplementation(testlibs.bundles.kotest.core)
    testImplementation(testlibs.bundles.kotest.extensions)
    testImplementation(testlibs.bundles.testcontainers)
    testImplementation(testlibs.mockito)
    testImplementation(testlibs.archunit)
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.assertj", module = "assertj-core")
    }

    testRuntimeOnly(testlibs.junit)
}

tasks.wrapper {
    gradleVersion = "8.5"
}

repositories {
    mavenLocal()
    mavenCentral()
}

//publishing {
//    publications {
//        create<MavenPublication>("mavenJava") {
//            artifact(tasks["provisioningPackage"])
//            artifact(tasks.distZip) {
//                classifier = "deploy"
//            }
//        }
//    }
//}

//configure<PublishingExtension> {
//    repositories.create("example") {
//        url = "https://artifactory.example.com"
//    }
//    applyDefaultPublication = false
//    apply()
//}

kotlin {
    jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(tools.versions.jvm.get()))
    }
}

ktlint {
    reporters {
        reporter(ReporterType.PLAIN)
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = tools.versions.jvm.get()
            // fixes for problems with Kotlin-Java interoperability
            freeCompilerArgs = listOf("-Xjvm-default=all", "-Xemit-jvm-type-annotations")
        }
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
        }
    }

    withType<org.jlleitschuh.gradle.ktlint.tasks.BaseKtLintCheckTask> {
        workerMaxHeapSize.set("512m")
    }
}

sourceSets.create("integration") {
    java.srcDirs("src/integration/kotlin")
    compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
    runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output
}

configurations.named("integrationImplementation") {
    extendsFrom(configurations["implementation"])
    extendsFrom(configurations["testImplementation"])
}

val integration = tasks.create("integration", Test::class.java) {
    description = "Runs the integration tests."
    group = "Verification"
    testClassesDirs = sourceSets.getByName("integration").output.classesDirs
    classpath = sourceSets.getByName("integration").runtimeClasspath
}

tasks.build.get().dependsOn(integration)
integration.mustRunAfter(tasks.test)

idea {
    module {
        testSourceDirs = testSourceDirs + file("src/integration/kotlin")
    }
}
