plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.nunes.notas"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.nunes.notas"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    //Dependency Hilt
    implementation("com.google.dagger:hilt-android:2.57")
    implementation("androidx.test:core-ktx:1.7.0")
    testImplementation("junit:junit:4.13.2")
    kapt("com.google.dagger:hilt-android-compiler:2.57")

    //Dependency LiveData + ViewModel
    val lifecycle_version = "2.7.0"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    //Fragment KTX
    val fragment_version = "1.6.2"
    implementation("androidx.fragment:fragment-ktx:$fragment_version")

    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    testImplementation("junit:junit:4.13.2")
    //Truth
    testImplementation("com.google.truth:truth:1.4.4")
    //Mockito
    testImplementation("org.mockito:mockito-core:5.18.0")
    //Arch Core
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    //Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    //Espresso for test RecyclerView
    androidTestImplementation( "androidx.test.espresso:espresso-contrib:3.7.0")
}

kapt {
    correctErrorTypes = true
}