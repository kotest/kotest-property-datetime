plugins {
   `java-library`
   `maven-publish`
   signing
}

repositories {
   mavenCentral()
}

val signingKey: String? by project
val signingPassword: String? by project

fun Project.publishing(action: PublishingExtension.() -> Unit) =
   configure(action)

fun Project.signing(configure: SigningExtension.() -> Unit): Unit =
   configure(configure)

fun Project.java(configure: JavaPluginExtension.() -> Unit): Unit =
   configure(configure)


val publications: PublicationContainer = (extensions.getByName("publishing") as PublishingExtension).publications

signing {
   useGpgCmd()
   if (signingKey != null && signingPassword != null) {
      @Suppress("UnstableApiUsage")
      useInMemoryPgpKeys(signingKey, signingPassword)
   }
   if (Ci.isRelease) {
      sign(publications)
   }
}

java {
   withSourcesJar()
}

val javadoc = tasks.named("javadoc")

val javadocJar by tasks.creating(Jar::class) {
   group = JavaBasePlugin.DOCUMENTATION_GROUP
   description = "Assembles java doc to jar"
   archiveClassifier.set("javadoc")
   from(javadoc)
}


publishing {
   repositories {
      maven {
         val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
         val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
         name = "deploy"
         url = if (Ci.isRelease) releasesRepoUrl else snapshotsRepoUrl
         credentials {
            username = System.getenv("OSSRH_USERNAME") ?: ""
            password = System.getenv("OSSRH_PASSWORD") ?: ""
         }
      }
   }

   publications.withType<MavenPublication>().configureEach {
      // Add javadoc so Maven Central will accept the publication
      artifact(javadocJar)

      pom {
         name.set("kotest-property-datetime")
         description.set("Kotest property testing generators for kotlinx-datetime")
         url.set("https://github.com/kotest/kotest-property-datetime")

         scm {
            connection.set("scm:git:https://github.com/kotest/kotest-property-datetime/")
            developerConnection.set("scm:git:https://github.com/sksamuel/")
            url.set("https://github.com/kotest/kotest-property-datetime/")
         }

         licenses {
            license {
               name.set("Apache-2.0")
               url.set("https://opensource.org/licenses/Apache-2.0")
            }
         }

         developers {
            developer {
               id.set("sksamuel")
               name.set("Stephen Samuel")
               email.set("sam@sksamuel.com")
            }
         }
      }
   }
}
