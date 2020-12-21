import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("com.etebarian:meow-framework-mvvm:0.9.2")
    implementation("com.auth0.android:jwtdecode:1.4.0")

    kapt("com.github.bumptech.glide:compiler:4.11.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.11.0")
}

object Build {
    const val APPLICATION_ID = "com.communere.testapp"
    const val APP_MODULE = "app"
    const val APP_PACKAGE = "communere"
    const val SRC_MAIN = "src/main/"
    const val OUTPUT_FILE_NAME = "CommunereTest"
}

object Versions {

    const val API = 1
    const val MAJOR = 0
    const val MINOR = 0
    const val PATCH = 3
    val BUILD_PHASE = Phase.ALPHA

    fun generateVersionCode(): Int {
        return API * 10000000 + BUILD_PHASE.ordinal * 1000000 + MAJOR * 10000 + MINOR * 100 + PATCH
    }

    fun generateVersionName(): String {
        val type = if (BUILD_PHASE.alias == "") "" else "-${BUILD_PHASE.alias}"
        return "${MAJOR}.${MINOR}.${PATCH}$type"
    }
}

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version ("1.4.21")
    id("androidx.navigation.safeargs.kotlin")
//    id("com.google.gms.google-services")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = Build.APPLICATION_ID
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = Versions.generateVersionCode()
        versionName = Versions.generateVersionName()

        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
    }

    applicationVariants.all {
        outputs.all {
            val fileName = "${Build.OUTPUT_FILE_NAME}-v${Versions.generateVersionName()}.apk"
            (this as BaseVariantOutputImpl).outputFileName = fileName
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    dataBinding {
        isEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main") {
            setRoot(Build.SRC_MAIN)
            manifest.srcFile("${Build.SRC_MAIN}AndroidManifest.xml")

            java.srcDirs("${Build.SRC_MAIN}kotlin")
            java.includes.add("/${Build.SRC_MAIN}**")
            java.excludes.add("**/build/**")

            res.srcDirs(getAllResourcesSrcDirs(project))
        }
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

fun getAllResourcesSrcDirs(project: Project): ArrayList<String> {
    val list = arrayListOf<String>()
    val path =
        project.rootDir.absolutePath + "\\" + Build.APP_MODULE + "\\src\\main\\kotlin\\" + Build.APP_PACKAGE
    val root = File(path)
    list.add(project.rootDir.absolutePath + "\\" + Build.APP_MODULE + "\\src\\main\\res")
    root.listDirectoriesWithChild().forEach { directory ->
        if (directory.isRes())
            list.add(directory.path)
    }
    return list
}

fun File.listDirectories() = listFiles()!!.filter { it.isDirectory }
fun File.listDirectoriesWithChild(): List<File> {
    val list = ArrayList<File>()

    fun File.findAllDirectories(list: ArrayList<File>) {
        listDirectories().forEach {
            it.findAllDirectories(list)
            list.add(it)
        }
    }

    findAllDirectories(list)
    return list
}

fun File.isRes() = name == "res"

enum class Phase(var alias: String) {
    ALPHA("alpha"),
    BETA("beta"),
    CANARY("canary"),
    RC("rc"),
    STABLE("")
}