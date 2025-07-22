package com.example.fastdelivery.product.worker.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.fastdelivery.product.domain.repository.ProductRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ProductSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val productRepository: ProductRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            productRepository.getLocalProducts().collect { products ->
                products.forEach { product ->
                    productRepository.syncProductWithServer(product)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
