package com.example.fastdelivery.order.domain.usecases

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.example.fastdelivery.order.worker.sync.OrderSyncWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncOrdersUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun execute(userId: String) {
        val input = Data.Builder()
            .putString("userId", userId)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<OrderSyncWorker>()
            .setInputData(input)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    fun executePeriodic(userId: String) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val input = Data.Builder()
            .putString("userId", userId)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<OrderSyncWorker>(
            1, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInputData(input)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "OrderSyncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }
}
