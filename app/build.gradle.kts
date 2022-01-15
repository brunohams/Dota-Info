plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = "com.codingwithmitch.dotainfo.CustomTestRunner" // runner customizado
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies{
    implementation(project(Modules.core))
    implementation(project(Modules.heroDomain))
    implementation(project(Modules.heroDataSource))
    implementation(project(Modules.heroInteractors))
    implementation(project(Modules.ui_heroList))
    implementation(project(Modules.ui_heroDetail))

    implementation(Accompanist.animations)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleVmKtx)

    implementation(Navigation.fragment)
    implementation(Navigation.ui)
    implementation(Navigation.support)

    implementation(Coil.coil)

    implementation(Google.material)

    implementation(Hilt.android)
    kapt(Hilt.compiler)

    implementation(SqlDelight.androidDriver)

    debugImplementation(LeakCanary.android)

    androidTestImplementation(project(Modules.heroDataSourceTest))
    androidTestImplementation(AndroidXTest.runner)
    androidTestImplementation(HiltTest.hiltAndroidTesting)
    kaptAndroidTest(Hilt.compiler)
    androidTestImplementation(Junit.junit4)

}










