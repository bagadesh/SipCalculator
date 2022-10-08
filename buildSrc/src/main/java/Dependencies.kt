/**
 * Created by bagadesh on 02/08/22.
 */
object Dependencies {

    object Compose {
        const val Version = "1.2.0"
        private const val constraintLayoutComposeVersion = "1.0.1"
        const val ConstraintLayout = "androidx.constraintlayout:constraintlayout-compose:$constraintLayoutComposeVersion"
    }

    object Hilt {
        private const val hiltVersion = "2.42"
        const val Android = "com.google.dagger:hilt-android:$hiltVersion"
        const val Compiler = "com.google.dagger:hilt-compiler:$hiltVersion"
        const val Core = "com.google.dagger:hilt-core:$hiltVersion"
    }

    object Coroutine {
        private const val coroutineVersion = "1.6.4"
        const val Core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
    }

    object Persistence {
        private const val dataStoreVersion = "1.0.0"
        const val DataStore = "androidx.datastore:datastore-preferences:$dataStoreVersion"
    }

    object Room {
        private const val roomVersion = "2.4.3"
        const val Runtime = "androidx.room:room-runtime:$roomVersion"
        const val Compiler = "androidx.room:room-compiler:$roomVersion"
        const val Ktx = "androidx.room:room-ktx:$roomVersion"
    }

    object Gson {
        private const val gsonVersion = "2.9.1"
        const val gson = "com.google.code.gson:gson:$gsonVersion"
    }

    object Math {
        private const val bigMathVersion = "2.3.0"
        const val BigMath = "ch.obermuhlner:big-math:$bigMathVersion"
    }

    object Accompanist {
        private const val pagerVersion = "0.25.1"
        const val Pager = "com.google.accompanist:accompanist-pager:$pagerVersion"
        const val PagerIndicators = "com.google.accompanist:accompanist-pager-indicators:$pagerVersion"

    }

}