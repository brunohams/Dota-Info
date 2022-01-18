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

    "testImplementation"("io.cucumber:cucumber-java8:7.0.0")
    "testImplementation"("io.cucumber:cucumber-junit:7.0.0")
}