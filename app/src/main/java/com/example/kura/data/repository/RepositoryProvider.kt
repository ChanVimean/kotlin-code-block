package com.example.kura.data.repository

object RepositoryProvider {
    // The single source of truth for which repository the app uses.
    // Swap this ONE line when the API is ready:
    val componentRepository: ComponentRepository = FakeComponentRepository()

    // LATER, when ApiComponentRepository is built:
    // val componentRepository: ComponentRepository = ApiComponentRepository()
}