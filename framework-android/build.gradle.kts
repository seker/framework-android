plugins {
    alias(libs.plugins.androidLibrary)
    `maven-publish`
}

group = "io.github.seker"
version = "1.0.3-SNAPSHOT"

android {
    namespace = "seker.framework.android"

    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    sourceSets {
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
    lint {
    }
    testOptions {
    }

    publishing {
        singleVariant("release") {
            // 让官方托管源码包生成，完美包含 Java + Kotlin + 自动生成的代码
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.seker.asynctask.android)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

publishing {
    // publish 到 output
    publications.create<MavenPublication>("Framework") {
        afterEvaluate {
            from(components["release"])
        }

        // 1. 三要素配置在最外层（使用等号赋值）
        groupId = project.group.toString()
        artifactId = "framework-android"
        version = project.version.toString()

        // 2. 规范的 POM 元数据配置（内部所有属性全部改用 .set()）
        pom {
            name.set("framework-android")
            description.set("framework for android")
            url.set("https://github.com/seker/framework-android")

            // 提示：历史代码中的 withXml 依赖节点硬编码已被上面的 from(components["release"]) 完美托管，可以直接安全地删除！

            licenses {
                license {
                    name.set("GNU LESSER GENERAL PUBLIC LICENSE Version 2.1")
                    url.set("https://github.com/seker/framework-android/blob/main/LICENSE")
                }
            }

            developers {
                developer {
                    id.set("xinjian")
                    name.set("xinjian.liu")
                    email.set("04070628@163.com")
                }
            }

            scm {
                connection.set("scm:git:git@github.com:seker/framework-android.git")
                developerConnection.set("scm:git:ssh://github.com:seker/framework-android.git")
                url.set("https://github.com/seker/framework-android")
            }
        }
    }
}