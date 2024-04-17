var myProperty: String = "initialized"


plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.foobarapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.foobarapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.github.bumptech.glide:glide:4.13.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.13.0")
    implementation ("com.google.android.material:material:1.11.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.squareup.retrofit2:retrofit:2.3.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

}