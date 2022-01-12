apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.components))
    "implementation"(project(Modules.heroDomain))
    "implementation"(project(Modules.heroInteractors))

    "implementation"(Coil.coil)

    "implementation"(SqlDelight.androidDriver)

    "androidTestImplementation"(project(Modules.heroDataSourceTest))
    "androidTestImplementation"(Junit.junit4)
}