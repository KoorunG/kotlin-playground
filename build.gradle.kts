import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlin.concurrent.thread

plugins {
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
	// benchmark용 jmh 플러그인 설치
	id("me.champeau.jmh") version "0.7.2"
	// kDoc을 기반으로 문서를 생성하는 dokka 플러그인
	id("org.jetbrains.dokka") version "1.9.0"
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21"
	kotlin("plugin.jpa") version "1.9.21"
}

// K2 컴파일러 프리뷰버전 사용
kotlin {
	sourceSets.all {
		languageSettings {
			languageVersion = "2.0"
		}
	}
}

// jmh 블럭 설정 (쓰레드 수, warmup 시간 등등...)
jmh {
	threads = 1
	fork = 1
	warmupIterations = 1
	iterations = 1
}

group = "com.koorung"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.reflections:reflections:0.10.2") // 자바의 reflection
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("io.klogging:klogging-jvm:0.5.6")
	testImplementation("io.klogging:klogging-jvm:0.5.6")
	// coroutine 의존성 추가
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1-Beta")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
