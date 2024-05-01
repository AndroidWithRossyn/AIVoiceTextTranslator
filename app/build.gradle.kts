plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleDevtoolsKsp)
}

android {

    signingConfigs {
        getByName("debug") {
            storeFile =
                file("E:\\Android with Rossyn\\AIVoiceTextTranslator\\keystore\\texttranslate.jks")
            storePassword = "texttranslate"
            keyAlias = "texttranslate"
            keyPassword = "texttranslate"
        }
    }
    
    useLibrary("org.apache.http.legacy")
    namespace = "com.texttranslate.voiceimage"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.texttranslate.voiceimage"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.0.3"
        vectorDrawables.useSupportLibrary = true
        renderscriptTargetApi = 24
        renderscriptSupportModeEnabled = true
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }
}

configurations {
    configureEach {
        exclude(group = "com.google.android.gms", module = "play-services-safetynet")
    }
}

tasks.withType(JavaCompile::class.java).configureEach {
    options.isFork = true
    options.forkOptions.jvmArgs?.plusAssign(
        listOf(
        "--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
        "--add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
        "--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED"
    )
    )
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation (libs.sdp.android)
    implementation(libs.ssp.android)

    //circle image view
    implementation(libs.circleimageview)

    implementation("com.google.android.gms:play-services-vision:20.1.3")

// Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.dhaval2404:imagepicker:2.1")

    implementation("androidx.work:work-runtime:2.9.0")
    implementation("io.github.ParkSangGwon:tedpermission-normal:3.3.0")
}