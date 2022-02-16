import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "com.calderasoftware"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

val tinkerPop = {value: String -> "org.apache.tinkerpop:gremlin-$value:3.5.2"}
val springBoot = {value: String -> "org.springframework.boot:spring-boot-$value:2.6.3"}
val kotlin = {value: String-> "org.jetbrains.kotlin:kotlin-$value:1.6.10"}


dependencies {
	implementation(springBoot("starter-web"))
	implementation(springBoot("starter"))
	implementation(kotlin("reflect"))
	implementation(kotlin("stdlib-jdk8"))

	// TinkerPop
	implementation(tinkerPop("core"))
	implementation(tinkerPop("driver"))
	implementation(tinkerPop("groovy"))

	testImplementation(springBoot("starter-test"))
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
