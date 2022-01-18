apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.heroDataSource))
    "implementation"(project(Modules.heroDomain))

    "implementation"(Kotlinx.coroutinesCore)

    "testImplementation"(project(Modules.heroDataSourceTest))
    "testImplementation"(Junit.junit4)
    "testImplementation"(Ktor.ktorClientMock)
    "testImplementation"(Ktor.clientSerialization)

    // Spek2 Library (BDD Testing)
    "testImplementation"(Spek2.framework)
    "testImplementation"(Spek2.runner)
    "testImplementation"(Kotlinx.reflect)
}

tasks.withType<Test> {
    useJUnitPlatform()
}