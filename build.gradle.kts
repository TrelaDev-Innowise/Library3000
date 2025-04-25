plugins {
    id("java")
    alias(libs.plugins.shadow)
}

dependencies {
    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(libs.spring.context)
    implementation(libs.jackson.csv)
    implementation(libs.spring.boot.aop)
    testImplementation(libs.spring.test)
}
group = "dev.trela.testing"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(libs.spring.context)
    implementation(libs.jackson.csv)
    implementation(libs.spring.boot.aop)
    testImplementation(libs.spring.test)
}

tasks.test {
    useJUnitPlatform()
}


tasks.shadowJar {
    archiveClassifier.set("")
    manifest {
        attributes["Main-Class"] = "dev.trela.testing.Library3000App"
    }
}

tasks.register("checkPlugins") {
    doLast {
        println("Loaded plugins: " + project.plugins.map { it.javaClass.name })
    }
}
