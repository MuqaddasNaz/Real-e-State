plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

// Key path =C:\Users\Muqadas Naz\Desktop\Gulf Real Estates\GulfRealEstates Release APK.jks
// Key Path(In Project) = Gulf Real Estates\app\src\main\assets\GulfRealEstates Release APK.jks
// Key alias = key0
// Password = cshouse123

android {
    namespace = "com.gulfrealestates.realestate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gulfrealestates.realestate"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.0.2"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.airbnb.android:lottie:6.1.0")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.google.code.gson:gson:2.11.0")
     //google sign in....
    implementation ("com.google.android.gms:play-services-auth:21.2.0")

    implementation ("androidx.camera:camera-camera2:1.4.0-rc01")

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    //search view....
    implementation ("androidx.appcompat:appcompat:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Firebase
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-crashlytics-buildtools:3.0.2")
}