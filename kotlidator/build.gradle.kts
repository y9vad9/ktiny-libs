plugins {
    id(libs.plugins.conventions.multiplatform.library.get().pluginId)
}

mavenPublishing {
    coordinates(
        groupId = "com.y9vad9.ktiny",
        artifactId = "kotlidator",
        version = System.getenv("LIB_VERSION") ?: return@mavenPublishing,
    )

    pom {
        name.set("Kotlin Validation Library")
        description.set("Kotlin validation library with type-safe results.")
    }
}