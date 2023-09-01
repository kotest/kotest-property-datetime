object Libs {

   const val kotlinVersion = "1.6.21"
   const val org = "io.kotest.extensions"
   const val dokkaVersion = "1.9.0"

   object Kotest {
      private const val version = "5.0.3"
      const val api = "io.kotest:kotest-framework-api:$version"
      const val property = "io.kotest:kotest-property:$version"
      const val junit5 = "io.kotest:kotest-runner-junit5:$version"
   }

   object Kotlinx {
      const val kotlintime = "org.jetbrains.kotlinx:kotlinx-datetime:0.3.1"
   }
}
