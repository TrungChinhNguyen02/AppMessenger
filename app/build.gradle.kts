plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.appmessenger"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appmessenger"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

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
//    lottie animation
    implementation("com.airbnb.android:lottie:6.3.0")

    implementation("com.hbb20:ccp:2.7.3")

//    botttom navigation bar
    implementation("com.etebarian:meow-bottom-navigation:1.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.10")



    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.play:app-update-ktx:2.1.0")
    implementation("com.google.firebase:firebase-database-ktx:20.3.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //firebse
    implementation("com.google.firebase:firebase-messaging:23.4.0")
    implementation("com.google.firebase:firebase-firestore:24.10.1")
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation ("com.google.firebase:firebase-storage-ktx:20.1.0")

    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation ("com.firebaseui:firebase-ui-firestore:8.0.2")
    //multiDex
    implementation("androidx.multidex:multidex:2.0.1")

    implementation ("com.github.dhaval2404:imagepicker:2.1")

}