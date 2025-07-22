package com.example.fastdelivery.order.worker.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.fastdelivery.order.domain.repository.OrderRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class OrderSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val orderRepository: OrderRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val userId = inputData.getString("userId")
        Log.d("OrderSyncWorker", "doWork started, userId=$userId")
        if (userId == null) {
            Log.e("OrderSyncWorker", "No userId provided")
            return Result.failure()
        }

        return try {
            val orders = orderRepository.getLocalOrders(userId).first()
            Log.d("OrderSyncWorker", "Found ${orders.size} orders to sync")

            orders.forEach { localOrder ->
                try {
                    orderRepository.placeOrder(localOrder)
                    Log.d("OrderSyncWorker", "Synchronized order: ${localOrder.orderId}")
                } catch (e: Exception) {
                    Log.e("OrderSyncWorker", "Error synchronizing order ${localOrder.orderId}: ${e.message}")
                }
            }

            Result.success()
        } catch (e: Exception) {
            Log.e("OrderSyncWorker", "General synchronization error: ${e.message}")
            Result.retry()
        }
    }
}
