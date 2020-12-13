plugins {
    kotlin("jvm") version "1.4.0"
}

group = "sh.zachwal"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.nd4j:nd4j:0.9.1")
    implementation("org.nd4j:nd4j-api:0.9.1")
    implementation("org.nd4j:nd4j-native-platform:0.9.1")

    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}
