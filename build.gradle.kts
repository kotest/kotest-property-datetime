plugins {
   `java-library`
   signing
   `maven-publish`
   kotlin("multiplatform").version(Libs.kotlinVersion)
}

repositories {
   mavenLocal()
   mavenCentral()
   maven {
      url = uri("https://oss.sonatype.org/content/repositories/snapshots")
   }
}

group = Libs.org
version = Ci.version

kotlin {
   jvm {
      compilations.all {
         kotlinOptions {
            jvmTarget = "1.8"
         }
      }
   }

   js(IR) {
      browser()
      nodejs()
   }

   sourceSets {
      val commonMain by getting {
         dependencies {
            api(Libs.Kotest.property)
            implementation(Libs.Kotlinx.kotlintime)
         }
      }

      val jvmTest by getting {
         dependencies {
            implementation(Libs.Kotest.junit5)
         }
      }

      all {
         languageSettings.apply {
            optIn("kotlin.time.ExperimentalTime")
            optIn("kotlin.experimental.ExperimentalTypeInference")
         }
      }
   }
}

tasks.named<Test>("jvmTest") {
   useJUnitPlatform()
   filter {
      isFailOnNoMatchingTests = false
   }
   testLogging {
      showExceptions = true
      showStandardStreams = true
      events = setOf(
         org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
         org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
      )
      exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
   }
}

apply("./publish-mpp.gradle.kts")
