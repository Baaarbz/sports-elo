package com.barbzdev.sportselo.infrastructure

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@Suppress("VariableNaming")
@AnalyzeClasses(packages = ["com.barbzdev.f1elo"], importOptions = [DoNotIncludeTests::class])
class ControllerArchitectureShould {
  @ArchTest
  val `classes with name ending in Controller should be annotated with @RestController` =
    classes()
      .that()
      .haveSimpleNameEndingWith("Controller")
      .should()
      .beAnnotatedWith(RestController::class.java)
      .orShould()
      .beAnnotatedWith(RestControllerAdvice::class.java)
      .orShould()
      .beInterfaces()

  @ArchTest
  val `classes that are annotated with @RestController should reside in a controller package` =
    classes().that().areAnnotatedWith(RestController::class.java).should().resideInAPackage("..controller..")
}
