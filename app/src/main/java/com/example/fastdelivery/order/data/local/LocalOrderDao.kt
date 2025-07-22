package com.example.fastdelivery.order.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: LocalOrderEntity)

    @Query("SELECT * FROM LocalOrderEntity WHERE userId = :userId ORDER BY timestamp DESC")
    fun getOrdersForUser(userId: String): Flow<List<LocalOrderEntity>>
}
