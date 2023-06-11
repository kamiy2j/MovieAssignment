import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
	namespace = ProjectConfig.namespace
	compileSdk  = ProjectConfig.compileSdk

	defaultConfig {
		applicationId = ProjectConfig.appId
		minSdk = ProjectConfig.minSdk
		targetSdk = ProjectConfig.targetSdk
		versionCode = ProjectConfig.versionCode
		versionName = ProjectConfig.versionName

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {
		release {
            isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
					"proguard-rules.pro")
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
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = Compose.kotlinCompilerExtensionVersion
	}
	packagingOptions {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {
	implementation (platform(Kotlin.bom))

	implementation (Compose.activityCompose)
	implementation (platform(Compose.composeBom))
	implementation(Compose.composeUi)
	implementation (Compose.composeUiGraphics)
	implementation (Compose.composeUiToolingPreview)
	implementation (Compose.composeMaterial3)
    implementation(Compose.navigationCompose)
    implementation (Compose.composeMaterial)
    implementation(Compose.pagingRuntime)
    implementation(Compose.pagingCompose)
    debugImplementation (Compose.uiTooling)
    debugImplementation (Compose.uiTestManifest)

    implementation(DaggerHilt.hiltAndroid)
    implementation(DaggerHilt.hiltNavigation)
    kapt(DaggerHilt.hiltCompiler)

    implementation(Coil.coilCompose)

    implementation(Retrofit.retrofit)
    implementation(Retrofit.moshiConverter)
    implementation(Retrofit.okHttpLoggingInterceptor)

    implementation(Coroutines.coroutinesCore)
    implementation(Coroutines.coroutinesAndroid)

    implementation(AndroidX.coreKtx)
    implementation (AndroidX.lifecycleExtension)
    implementation (AndroidX.lifecycleRuntimeKtx)
    implementation (AndroidX.lifecycleLiveDataKtx)
    implementation (AndroidX.lifecycleViewModelKtx)

    testImplementation(Testing.okhttpMockWebserver)
    testImplementation(Testing.coroutinesTest)
    testImplementation(Testing.truth)
    testImplementation(Testing.mockk)
    testImplementation(Testing.mockkAndroid)
    testImplementation(Testing.turbine)
    testImplementation (Testing.junit)
    androidTestImplementation (Testing.junitExt)
	androidTestImplementation (Testing.expresso)
	androidTestImplementation (platform(Testing.composeBom))
	androidTestImplementation (Testing.junitUiTest)
}