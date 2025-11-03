plugins {
    id("java-library")
    id("application")
    id("com.gradleup.shadow") version "9.0.0"
}

group = "org.swtthm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.ev3dev-lang-java:ev3dev-lang-java:2.5.3")
}

application {
    mainClass.set("org.swtthm.bandit.BanditMain")
}

tasks {
    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release = 11
    }

    assemble {
        dependsOn(shadowJar)
    }
}