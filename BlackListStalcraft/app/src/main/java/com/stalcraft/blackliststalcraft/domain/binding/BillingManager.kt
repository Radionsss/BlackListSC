package com.stalcraft.blackliststalcraft.domain.binding


import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.consumePurchase
import com.google.common.collect.ImmutableList
import com.stalcraft.blackliststalcraft.core.utils.Constatns.TWO_HUNDRED_ID
import com.stalcraft.blackliststalcraft.core.utils.Constatns.HUNDRED_RUB_ID
import com.stalcraft.blackliststalcraft.core.utils.Constatns.ONE_RUB_ID
import com.stalcraft.blackliststalcraft.core.utils.Constatns.TEN_RUB_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BillingManager(val context: AppCompatActivity) {


    var courseBuyWithNumber: ((Int) -> Unit)? = null
    var theneBuyWithUniqueId: ((Int) -> Unit)? = null
    var addAndropointsCount: ((Int) -> Unit)? = null
    var infinityAndropoints: (() -> Unit)? = null
    var premiumBuyWithTerm: ((Int) -> Unit)? = null

    private var billingClient: BillingClient? = null
    private var whatProdBuy: ProdTypesState? = null
    private var purchase: Purchase? = null
    private var courseBuyNumber: Int? = null
    private var uniqueThemeIdBuy: Int? = null


    private fun queryProduct(productId: String) {
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                ImmutableList.of(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(
                            BillingClient.ProductType.INAPP
                        )
                        .build()
                )
            )
            .build()

        billingClient?.queryProductDetailsAsync(
            queryProductDetailsParams
        ) { billingResult, productDetailsList ->
            if (productDetailsList.isNotEmpty()) {
                productDetailsList?.get(0)?.let {
                    makePurchase(it)
                }
                productDetailsList?.get(1)?.let {
                    makePurchase(it)
                }
                productDetailsList?.get(2)?.let {
                    makePurchase(it)
                }
                productDetailsList?.get(3)?.let {
                    makePurchase(it)
                }
            } else {
                Log.i("TAG", "onProductDetailsResponse: No products")
            }
        }
    }

    fun billingSetup(
        whatBuy: ProdTypesState,
        courseNumber: Int? = null,
        uniqueThemeID: Int? = null
    ) {
        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()



        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(
                billingResult: BillingResult
            ) {
                if (billingResult.responseCode ==
                    BillingClient.BillingResponseCode.OK
                ) {
                    courseNumber?.let {
                        courseBuyNumber = it
                    }
                    uniqueThemeID?.let {
                        uniqueThemeIdBuy = it
                    }
                    whatProdBuy = whatBuy
                    when (whatBuy) {
                        ProdTypesState.FIVE_RUB -> {
                            queryProduct(ONE_RUB_ID)
                        }

                        ProdTypesState.TWENTY_RUB -> {
                            queryProduct(TEN_RUB_ID)
                        }

                        ProdTypesState.HUNDRED_RUB -> {
                            queryProduct(HUNDRED_RUB_ID)
                        }

                        ProdTypesState.TWO_HUNDRED_RUB -> {
                            queryProduct(TWO_HUNDRED_ID)
                        }
                    }
                    Log.i("TAG", "OnBillingSetupFinish connected")

                } else {
                    Log.i("TAG", "OnBillingSetupFinish failed")
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.i("TAG", "OnBillingSetupFinish connection lost")
            }
        })
    }

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode ==
                BillingClient.BillingResponseCode.OK
                && purchases != null
            ) {
                for (purchase in purchases) {
                    completePurchase(purchase)

                }
            } else if (billingResult.responseCode ==
                BillingClient.BillingResponseCode.USER_CANCELED
            ) {
                Log.i("TAG", "onPurchasesUpdated: Purchase Canceled")
            } else {
                Log.i("TAG", "onPurchasesUpdated: Error")
            }
        }

    private fun completePurchase(item: Purchase) {
        purchase = item
        if (purchase?.purchaseState == Purchase.PurchaseState.PURCHASED) {

            when (whatProdBuy) {
                ProdTypesState.FIVE_RUB -> {
                    queryProduct(ONE_RUB_ID)
                    consumePurchase(item)
                }

                ProdTypesState.TWENTY_RUB -> {
                    queryProduct(TEN_RUB_ID)
                    consumePurchase(item)
                }

                ProdTypesState.HUNDRED_RUB -> {
                    queryProduct(HUNDRED_RUB_ID)
                    consumePurchase(item)
                }

                ProdTypesState.TWO_HUNDRED_RUB -> {
                    queryProduct(TWO_HUNDRED_ID)
                    consumePurchase(item)
                }

                else -> {}
            }
        }
    }

    private fun makePurchase(productDetails: ProductDetails) {
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                ImmutableList.of(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .build()
                )
            )
            .build()

        billingClient?.launchBillingFlow(context, billingFlowParams)
    }

    private fun consumePurchase(purchase: Purchase) {

        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            val result = billingClient?.consumePurchase(consumeParams)
            if (result?.billingResult?.responseCode ==
                BillingClient.BillingResponseCode.OK
            ) {
//                runOnUiThread() {
//                    Toast.makeText(this@MainActivity,"Покупка успешна потрачена",Toast.LENGTH_LONG).show()
//
//                }
            }
        }
    }


}