package com.stalcraft.blackliststalcraft.presenter.utils


import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.stalcraft.blackliststalcraft.R

object ShowDialogHelper {
    private var isDialogDelete = false
    private var isDialogLoad = false
    private var isDialogThanks = false
    private var isDialogChoose = false
    private var unknownError = false
    private var isDialogIndicateAngePlayer = false
    private var dialog: Dialog? = null

    fun showDialogRemovePlayer(
        context: Context,
        dialogDisMiss: (() -> Unit),
        delete: (() -> Unit),
    ) {
        if (!isDialogDelete) {
            isDialogDelete = true
            dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialog?.setContentView(R.layout.dialog_remove_player)
            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog?.setCancelable(false)
            val btnCancel = dialog?.findViewById<LinearLayout>(R.id.btnCancel)
            val btnDelete = dialog?.findViewById<CardView>(R.id.btnDelete)


            btnCancel?.setOnClickListener {
                dialog?.dismiss()
                dialog = null
                dialogDisMiss.invoke()
            }
            btnDelete?.setOnClickListener {

                dialog?.dismiss()
                dialog = null
                delete.invoke()
            }
            dialog?.show()
            dialog?.setOnDismissListener {
                isDialogDelete = false
            }
        }
    }

    fun showDialogLoad(
        context: Context,
    ) {
        if (!isDialogLoad) {
            isDialogLoad = true
            dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialog?.setContentView(R.layout.load_dialog)
            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog?.setCancelable(false)
            val loadAnim = dialog?.findViewById<LottieAnimationView>(R.id.lottieAnimationView)
            val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
            animator.duration = 2000 // Общая длительность анимации
            animator.repeatCount = ValueAnimator.INFINITE
            animator.addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                val newSpeed = calculateSpeed(progress)
                loadAnim?.speed = newSpeed
            }
            animator.start()
            dialog?.show()
            dialog?.setOnDismissListener {
                isDialogLoad = false
            }
        }
    }

    private fun calculateSpeed(progress: Float): Float {
        return if (progress < 0.5f) {
            10.0f - progress * 18.0f
        } else {
            1.0f + (progress - 0.5f) * 18.0f
        }
    }

    fun dismissDialogLoad() {
        isDialogLoad = false
        dialog?.dismiss()
        dialog = null
    }

    fun showDialogThanks(
        context: Context,
        ad: (() -> Unit),
        google: (() -> Unit),
        close: (() -> Unit)
    ) {
        if (!isDialogThanks) {
            isDialogThanks = true
            dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialog?.setContentView(R.layout.dialog_thanks)
            dialog?.setCancelable(false)
            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            val btnGoogle = dialog?.findViewById<LinearLayout>(R.id.btnGoogle)
            val btnWatchAd = dialog?.findViewById<CardView>(R.id.btnWatchAd)
            val btnClose = dialog?.findViewById<ImageButton>(R.id.btnClose)


            btnGoogle?.setOnClickListener {
                dialog?.dismiss()
                dialog = null
                google.invoke()
            }
            btnWatchAd?.setOnClickListener {
                dialog?.dismiss()
                dialog = null
                ad.invoke()
            }
            btnClose?.setOnClickListener {
                dialog?.dismiss()
                dialog = null
                close.invoke()
            }
            dialog?.show()
            dialog?.setOnDismissListener {
                isDialogThanks = false
            }
        }
    }

    fun showDialogThanksChoose(
        context: Context,
        fiveRub: (() -> Unit),
        twentyRub: (() -> Unit),
        oneHundredRub: (() -> Unit),
        twoHundredRub: (() -> Unit),
        close: (() -> Unit)
    ) {
        if (!isDialogChoose) {
            isDialogChoose = true
            dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialog?.setContentView(R.layout.dialog_choose_thanks)
            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            val btnFiveRub = dialog?.findViewById<CardView>(R.id.btnFiveRub)
            val btnTwentyRub = dialog?.findViewById<CardView>(R.id.btnTwentyRub)
            val btnOneHundredRub = dialog?.findViewById<CardView>(R.id.btnOneHundredRub)
            val btnTwoHundredRub = dialog?.findViewById<CardView>(R.id.btnTwoHundredRub)
            val btnClose = dialog?.findViewById<ImageButton>(R.id.btnClose)


            btnFiveRub?.setOnClickListener {
                fiveRub.invoke()
            }
            btnTwentyRub?.setOnClickListener {
                twentyRub.invoke()
            }
            btnOneHundredRub?.setOnClickListener {
                oneHundredRub.invoke()
            }
            btnTwoHundredRub?.setOnClickListener {
                twoHundredRub.invoke()
            }
            btnClose?.setOnClickListener {
                dialog?.dismiss()
                dialog = null
                close.invoke()
            }
            dialog?.setOnDismissListener {
                dialog?.dismiss()
                dialog = null
                close.invoke()
                isDialogChoose = false
            }
            dialog?.show()
        }
    }

    fun showDialogError(
        context: Context,
        text:String
    ) {
        if (!unknownError) {
            unknownError = true
            dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialog?.setContentView(R.layout.dialog_network_error)
            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            val btnTryAgainUnknownError = dialog?.findViewById<CardView>(R.id.btnTryAgainUnknownError)
            val tvError = dialog?.findViewById<TextView>(R.id.tvError)
            tvError?.text=text
            btnTryAgainUnknownError?.setOnClickListener {
                dialog?.dismiss()
                dialog = null
            }
            val lottieAnimationView: LottieAnimationView? = dialog?.findViewById(R.id.lottieAnimationView)
            lottieAnimationView?.alpha = 0f

            val alphaAnimator = ValueAnimator.ofFloat(0f, 1f)
            alphaAnimator.addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Float
                lottieAnimationView?.alpha = animatedValue
            }
            alphaAnimator.duration = 3000

            val speedAnimator = ValueAnimator.ofFloat(20f, 0f)
            speedAnimator.interpolator = AccelerateDecelerateInterpolator()
            speedAnimator.addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Float
                lottieAnimationView?.speed = animatedValue
            }
            speedAnimator.duration = 3000
            alphaAnimator.start()
            speedAnimator.start()
            dialog?.setCancelable(false)
            dialog?.setOnDismissListener {
                dialog?.dismiss()
                dialog = null
                unknownError = false
            }
            dialog?.show()
        }
    }

    fun showDialogIndicateAngePlayer(
        context: Context,
        resources: Resources,
        dialogDisMiss: (() -> Unit),
        countAnger: ((Int) -> Unit),
    ) {
        if (!isDialogIndicateAngePlayer) {
            isDialogIndicateAngePlayer = true
            dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            dialog?.setContentView(R.layout.indicate_anger_dialog)
            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            var countAngerNow = 1
            val btnReadyDialog = dialog?.findViewById<CardView>(R.id.btnReadyDialog)
            val btnFirstAnger = dialog?.findViewById<CardView>(R.id.firstAngerDialog)
            val btnSecondAnger = dialog?.findViewById<CardView>(R.id.secondAngerDialog)
            val btnThirdAnger = dialog?.findViewById<CardView>(R.id.thirdsAngerDialog)
            val btnFourthAnger = dialog?.findViewById<CardView>(R.id.fourthAngerDialog)
            val btnFifthAnger = dialog?.findViewById<CardView>(R.id.fifthAngerDialog)
            val btnClose = dialog?.findViewById<ImageButton>(R.id.btnCloseIndicateAngePlayer)
            val currentTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

            val backgroundColorRes =
                if (currentTheme == Configuration.UI_MODE_NIGHT_YES) R.color.light_blackE else R.color.white
            btnSecondAnger?.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    backgroundColorRes
                )
            )
            btnThirdAnger?.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    backgroundColorRes
                )
            )
            btnFourthAnger?.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    backgroundColorRes
                )
            )
            btnFifthAnger?.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    backgroundColorRes
                )
            )
            btnFirstAnger?.setOnClickListener {
                btnFirstAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green))
                btnSecondAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        backgroundColorRes
                    )
                )
                btnThirdAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        backgroundColorRes
                    )
                )
                btnFourthAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        backgroundColorRes
                    )
                )
                btnFifthAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        backgroundColorRes
                    )
                )
                countAngerNow = 1
            }

            btnSecondAnger?.setOnClickListener {
                btnFirstAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.green
                    )
                )
                btnSecondAnger.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.yellow
                    )
                )
                btnThirdAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        backgroundColorRes
                    )
                )
                btnFourthAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        backgroundColorRes
                    )
                )
                btnFifthAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        backgroundColorRes
                    )
                )
                countAngerNow = 2
            }

            btnThirdAnger?.setOnClickListener {
                btnFirstAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.green
                    )
                )
                btnSecondAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.yellow
                    )
                )
                btnThirdAnger.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.yellow_dark
                    )
                )
                btnFourthAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        backgroundColorRes
                    )
                )
                btnFifthAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        backgroundColorRes
                    )
                )
                countAngerNow = 3
            }

            btnFourthAnger?.setOnClickListener {
                btnFirstAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.green
                    )
                )
                btnSecondAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.yellow
                    )
                )
                btnThirdAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.yellow_dark
                    )
                )
                btnFourthAnger.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.orange
                    )
                )
                btnFifthAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        backgroundColorRes
                    )
                )
                countAngerNow = 4
            }

            btnFifthAnger?.setOnClickListener {
                btnFirstAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.green
                    )
                )
                btnSecondAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.yellow
                    )
                )
                btnThirdAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.yellow_dark
                    )
                )
                btnFourthAnger?.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.orange
                    )
                )
                btnFifthAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red))
                countAngerNow = 5
            }

            btnReadyDialog?.setOnClickListener {
                dialog?.dismiss()
                dialog = null
                countAnger.invoke(countAngerNow)
            }

            btnClose?.setOnClickListener {
                dialog?.dismiss()
                dialog = null
                dialogDisMiss.invoke()
            }

            dialog?.setOnDismissListener {
                dialogDisMiss.invoke()
                isDialogIndicateAngePlayer = false
            }

            dialog?.show()
        }
    }
}