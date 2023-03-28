import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.9"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.openapi.generator") version "6.4.0"
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.spring") version "1.8.10"
    kotlin("plugin.jpa") version "1.8.10"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    jacoco
    id("org.sonarqube") version "3.5.0.2730"
    id("org.unbroken-dome.test-sets") version "4.0.0" apply false
    id("application")
}

group = "com.hubla.challenge"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
var detektVersion = "1.22.0"

sonarqube {
    properties {
        property("sonar.verbose", "true")
        property("sonar.tests", "src")
        property("sonar.test.inclusions", "src/*test/**/*.kt")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.charSet", "UTF-8'")
        property("sonar.host.url", System.getenv()["SONAR_HOST_URL"] ?: "https://sonarcloud.io")
        property("sonar.organization", System.getenv()["SONAR_ORGANIZATION"] ?: "")
        property("sonar.projectKey", System.getenv()["SONAR_PROJECT_KEY"] ?: "")
        property("sonar.login", System.getenv()["SONAR_PROJECT_TOKEN"] ?: "")
        property("sonar.coverage.jacoco.xmlReportPaths", "path.xml")
        property("sonar.coverage.exclusions","**/test/**/*.*, **/model/**/*.*, **/config/**/*.*")
    }
}
jacoco {
    toolVersion = "0.8.8"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

detekt {
    toolVersion = "1.22.0"
    config = files("$rootDir/detekt-config.yml")

    source = files(
        "src/main/kotlin",
        "src/test/kotlin"
    )
    autoCorrect = true
    allRules = true
}
repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    implementation("io.springfox:springfox-swagger2:3.0.0")
    implementation("io.springfox:springfox-bean-validators:3.0.0")
    implementation("org.springdoc:springdoc-openapi-data-rest:1.6.15")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.15")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.15")
    implementation("mysql:mysql-connector-java:8.0.32")
    implementation("org.apache.commons:commons-io:1.3.2")
    runtimeOnly("mysql:mysql-connector-java:8.0.32")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation(kotlin("test"))
    implementation(kotlin("script-runtime"))
    detekt("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
    detekt("io.gitlab.arturbosch.detekt:detekt-cli:$detektVersion")

    testCompileOnly(group = "org.springframework.boot", name = "spring-boot-test", version = "2.5.5")
    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test", version = "2.5.5")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.7.2")
    testImplementation(group = "io.mockk", name = "mockk", version = "1.12.0")
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = "5.7.2")
    testImplementation(group = "org.testcontainers", name = "testcontainers", version = "1.16.3")
    testImplementation(group = "org.testcontainers", name = "junit-jupiter", version = "1.16.3")
    testImplementation(group = "org.testcontainers", name = "mysql", version = "1.16.3")
    testImplementation(group = "org.testcontainers", name = "mockserver", version = "1.16.3")
    testImplementation(group = "org.mock-server", name = "mockserver-client-java", version = "5.11.2")
    testImplementation(group = "org.awaitility", name = "awaitility", version = "4.0.3")
}

apply(plugin = "org.jetbrains.kotlin.jvm")
apply(plugin = "jacoco")
apply(plugin = "io.gitlab.arturbosch.detekt")
apply(plugin = "org.sonarqube")

apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")
apply(plugin = "org.unbroken-dome.test-sets")

buildscript {
    repositories {
        gradlePluginPortal()
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.detekt {
    allRules = true
    source = fileTree("src/main/kotlin")
    source = fileTree("src/test/kotlin")
}

subprojects {
    sonarqube {
        properties {
            property("sonar.branch", "Foo")
            property("sonar.coverage.exclusions", "**/com/hubla/challenge/core/model/*")
        }
    }
}

