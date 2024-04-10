import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${tools.versions.kotlin.get()}")
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application

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
project.version = "1.0.0"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val integrationCompile: Configuration by configurations.creating {
    extendsFrom(configurations.testCompileOnly.get())
}

val integrationRuntime: Configuration by configurations.creating {
    extendsFrom(configurations.testRuntimeOnly.get())
}

val integrationImplementation: Configuration by configurations.creating {
    extendsFrom(configurations.testImplementation.get())
}

apply(plugin = "kotlin")
apply(plugin = "kotlin-spring")
apply(plugin = "java")
apply(plugin = "kover")

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation(tools.bundles.kotlin)
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation(libs.kotlinlogging)
    implementation(libs.lib.gem)

    testImplementation(testlibs.bundles.kotest.core)
    testImplementation(testlibs.bundles.kotest.extensions)
    testImplementation(testlibs.bundles.testcontainers)
    testImplementation(testlibs.mockito)
    testImplementation(testlibs.archunit)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly(testlibs.junit)
}

tasks.wrapper {
    gradleVersion = "8.5"
}

repositories {
    mavenLocal()
    mavenCentral()
}

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

sourceSets {
    create("integration") {
        compileClasspath += project.sourceSets["main"].output + project.sourceSets["test"].output
        runtimeClasspath += project.sourceSets["main"].output + project.sourceSets["test"].output
        java.srcDir("src/integration/kotlin")
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = tools.versions.jvm.get()
            freeCompilerArgs = listOf("-Xjvm-default=all", "-Xemit-jvm-type-annotations")
        }
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
        }
        reports {
            junitXml.required = true
        }
        outputs.upToDateWhen { false }
    }

    withType<org.jlleitschuh.gradle.ktlint.tasks.BaseKtLintCheckTask> {
        workerMaxHeapSize.set("512m")
    }

    create<Test>("integration") {
        description = "Runs the integration tests."
        group = "verification"
        testClassesDirs = sourceSets["integration"].output.classesDirs
        classpath = sourceSets["integration"].runtimeClasspath
        mustRunAfter("test")
    }
    check {
        dependsOn("integration")
    }
}