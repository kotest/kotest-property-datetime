object Libs {

   const val kotlinVersion = "1.4.31"
   const val org = "io.kotest.extensions"
   const val dokkaVersion = "0.10.1"

   object Kotest {
      private const val version = "4.6.3"
      const val api = "io.kotest:kotest-framework-api:$version"
      const val property = "io.kotest:kotest-property:$version"
      const val junit5 = "io.kotest:kotest-runner-junit5-jvm:$version"
   }

   object Kotlinx {
      const val kotlintime = "org.jetbrains.kotlinx:kotlinx-datetime:0.3.1"
   }
}
