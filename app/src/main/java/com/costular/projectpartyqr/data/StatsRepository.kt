package com.costular.projectpartyqr.data

import com.costular.projectpartyqr.data.model.Stats


interface StatsRepository {

    suspend fun getStats(): Stats

}

class StatsRepositoryDefault(
    private val userRepository: UserRepository
) : StatsRepository {

    override suspend fun getStats(): Stats {
        val users = userRepository.getUsers()
        val total = users.count()
        val confirmed = users.count { it.confirmed }
        val payed = users.count { it.payed }
        val joined = users.count { it.joinedParty }
        val money = payed * 5

        return Stats(
            total,
            payed,
            confirmed,
            joined,
            money
        )
    }

    private companion object {
        private const val COLLECTION = "users"
    }

}