import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.7.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.3.72" apply true
	id("org.jetbrains.kotlin.plugin.allopen") version "1.3.72" apply true
	id("org.jetbrains.kotlin.plugin.jpa") version "1.3.72" apply true
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
}

group = "info.tduty"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.telegram:telegrambots:4.8.1")
	implementation("com.google.code.gson:gson:2.8.6")
	implementation("com.h2database:h2")
	implementation("org.springframework.boot:spring-boot-starter-web:2.2.6.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.2.6.RELEASE")
	implementation("org.telegram:telegrambots-spring-boot-starter:4.1.2")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.jetbrains.kotlin:kotlin-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

val appName = "telegram2factor-auth"
val appVer by lazy { "0.0.1" }

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.bootJar {
	manifest {
		attributes("Multi-Release" to true)
	}

	archiveBaseName.set(appName)
	archiveVersion.set(appVer)

	if (project.hasProperty("archiveName")) {
		archiveFileName.set(project.properties["archiveName"] as String)
	}
}
