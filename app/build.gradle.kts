import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.pocketfm"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    packaging {
        resources {
            excludes += "META-INF/*"
        }
    }

    defaultConfig {
        applicationId = "com.example.pocketfm"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        val apiKey = project.findProperty("LISTEN_API_KEY")?.toString()
//            ?: throw GradleException("API KEY NOT FOUND")
//
//        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        val apiKey = properties.getProperty("LISTEN_API_KEY") ?: ""

        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }
//    defaultConfig {
//
//        val properties = Properties()
//        properties.load(project.rootProject.file("local.properties").inputStream())
//
//        val apiKey = properties.getProperty("LISTEN_API_KEY") ?: ""
//
//        buildConfigField("String", "API_KEY", "\"$apiKey\"")
//    }

    buildFeatures{
        viewBinding = true
        dataBinding = true
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.appdistribution.gradle)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation("com.listennotes:podcast-api:1.1.6")

//    kapt("groupId:artifactId:version")
    ksp("androidx.room:room-compiler:2.8.4")

// Lifecycle (MVVM)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.10.0")

// RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.4.0")

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    implementation("androidx.media3:media3-exoplayer:1.10.0")
    implementation("androidx.media3:media3-ui:1.10.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    implementation("com.squareup.okhttp3:logging-interceptor:5.3.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    ksp("com.github.bumptech.glide:compiler:4.16.0")

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("com.firebaseui:firebase-ui-auth:9.0.0")
    implementation("com.google.android.gms:play-services-auth:21.0.1")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:34.12.0"))
}