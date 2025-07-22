package com.example.fastdelivery.product.domain.usecases

import android.content.Context
import androidx.work.*
import com.example.fastdelivery.product.worker.sync.ProductSyncWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncProductDataUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun execute() {
        val workRequest = OneTimeWorkRequestBuilder<ProductSyncWorker>().build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    fun executePeriodic() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<ProductSyncWorker>(
            1, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "ProductSyncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }
}
