plugins {
   id("kotest-publishing-conventions")
   kotlin("multiplatform").version(Libs.kotlinVersion)
   id("io.kotest.multiplatform") version Libs.Kotest.version
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

   js {
      browser()
      nodejs()
   }

   // Requires Kotest 5.9 which is not available yet
   // wasmJs {
   //    browser()
   //    nodejs()
   // }

   // Native targets
   linuxX64()
   linuxArm64()

   mingwX64()

   macosX64()
   macosArm64()

   tvosX64()
   tvosArm64()
   tvosSimulatorArm64()

   watchosArm32()
   watchosArm64()
   watchosX64()
   watchosSimulatorArm64()

   iosX64()
   iosArm64()
   iosSimulatorArm64()

   sourceSets {
      val commonMain by getting {
         dependencies {
            api(Libs.Kotest.property)
            implementation(Libs.Kotlinx.kotlintime)
         }
      }

      val commonTest by getting {
         dependencies {
            implementation(Libs.Kotest.assertionsCore)
            implementation(Libs.Kotest.frameworkEngine)
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
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
