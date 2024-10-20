plugins {
  val kotlinPluginVersion = "2.0.20"
  kotlin("jvm") version kotlinPluginVersion
  kotlin("plugin.spring") version kotlinPluginVersion
  kotlin("plugin.jpa") version kotlinPluginVersion

  id("org.springframework.boot") version "3.3.4"
  id("io.spring.dependency-management") version "1.1.6"

  `java-test-fixtures`
}

group = "com.barbzdev"

version = "0.0.1-SNAPSHOT"

java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }

repositories { mavenCentral() }

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-graphql")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  implementation("org.flywaydb:flyway-core")
  implementation("org.flywaydb:flyway-database-postgresql")

  implementation("org.jetbrains.kotlin:kotlin-reflect")

  runtimeOnly("org.postgresql:postgresql")

  // OpenAPI
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
  implementation("org.springdoc:springdoc-openapi-starter-common:2.1.0")
}

kotlin { compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") } }

tasks.withType<Test> { useJUnitPlatform() }

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      useJUnitJupiter()

      dependencies {
        implementation("io.mockk:mockk:1.13.13")
        implementation("com.ninja-squad:springmockk:4.0.2")
        implementation("com.willowtreeapps.assertk:assertk:0.28.0")
        implementation("org.jetbrains.kotlin:kotlin-test-junit5")
        implementation("org.springframework.boot:spring-boot-starter-test") {
          exclude(module = "mockito-core")
        }
      }
    }

    withType(JvmTestSuite::class).matching { it.name in listOf("integrationTest", "acceptanceTest") }.configureEach {
      useJUnitJupiter()
      dependencies {
        implementation(project())
        implementation("org.testcontainers:postgresql")
        implementation("org.testcontainers:junit-jupiter")

        implementation("org.awaitility:awaitility:4.2.2")

        implementation("org.springframework.boot:spring-boot-testcontainers")
        implementation("org.springframework.cloud:spring-cloud-contract-wiremock:4.1.4")
        implementation("org.springframework.boot:spring-boot-starter-test") {
          exclude(module = "mockito-core")
        }

        implementation(testFixtures(project()))
      }
    }

    val integrationTest by registering(JvmTestSuite::class) {
      targets {
        all {
          testTask.configure {
            mustRunAfter(test)
          }
        }
      }
    }

    val acceptanceTest by registering(JvmTestSuite::class) {
      dependencies {
        implementation("org.springframework:spring-web:6.1.4")
        implementation("io.rest-assured:rest-assured:5.4.0")
        implementation("org.skyscreamer:jsonassert:1.5.1")
      }

      targets {
        all {
          testTask.configure {
            mustRunAfter(integrationTest)
          }
        }
      }
    }
  }
}

tasks.named("check") {
  dependsOn(testing.suites.named("integrationTest"), testing.suites.named("acceptanceTest"))
}
