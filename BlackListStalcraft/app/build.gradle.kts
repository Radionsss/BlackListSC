plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
           id("kotlin-parcelize")
   // kotlin("parcelize")
}
android {
    namespace = "com.stalcraft.blackliststalcraft"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.stalcraft.blackliststalcraft"
        minSdk = 24
        targetSdk = 34
        versionCode = 10
        versionName = "1.9"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Dagger - Hilt
    implementation("ru.yoomoney.sdk.kassa.payments:yookassa-android-sdk:6.8.0")
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    // Dagger - Hilt
    //room
    implementation ("androidx.room:room-ktx:2.6.0")
    implementation ("androidx.room:room-runtime:2.6.0")
    kapt ("androidx.room:room-compiler:2.6.0")
    //room
    //navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.3")
    //navigation

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

        // implementation( "org.jetbrains.kotlin:kotlin-parcelize:1.6.10")
         implementation( "com.google.code.gson:gson:2.8.8")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    //preferences

    implementation ("androidx.preference:preference-ktx:1.2.1")
    implementation ("com.google.android.gms:play-services-ads:22.5.0")
    implementation ("com.android.billingclient:billing:6.0.1")
    implementation ("com.android.billingclient:billing-ktx:6.0.1")
    implementation ("com.google.guava:guava:24.1-jre")
    implementation ("com.google.guava:guava:27.0.1-android")
}
kapt {
    correctErrorTypes = true
}