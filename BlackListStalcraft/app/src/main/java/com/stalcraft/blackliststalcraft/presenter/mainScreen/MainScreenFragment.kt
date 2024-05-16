package com.stalcraft.blackliststalcraft.presenter.mainScreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager


import com.stalcraft.blackliststalcraft.R
import com.stalcraft.blackliststalcraft.core.utils.Constatns.REQUEST_CODE_TOKENIZE
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.databinding.FragmentMainScreenBinding
import com.stalcraft.blackliststalcraft.domain.binding.BillingManager
import com.stalcraft.blackliststalcraft.domain.binding.ProdTypesState
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.presenter.setting.SettingActivity

import com.stalcraft.blackliststalcraft.presenter.utils.ShowDialogHelper
import com.stalcraft.blackliststalcraft.presenter.utils.ShowDialogHelper.showDialogThanks
import com.stalcraft.blackliststalcraft.presenter.utils.ShowDialogHelper.showDialogThanksChoose
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.yoomoney.sdk.kassa.payments.Checkout.createTokenizeIntent
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.GooglePayParameters
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentParameters
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.SavePaymentMethod
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.TestParameters
import java.math.BigDecimal
import java.util.Currency
import java.util.concurrent.atomic.AtomicBoolean

@AndroidEntryPoint
class MainScreenFragment : Fragment() {

    private var billingManager: BillingManager? = null
    private var binding: FragmentMainScreenBinding? = null
    private val viewModel: MainScreenViewModel by viewModels()
    private var haveSelectedPlayers = false
    private var adapter: PlayerAdapterGoods? = null
    private var adapterSecond: PlayerAdapterBads? = null
    private var adapterAll: PlayerAdapterAll? = null
    private var pref: SharedPreferences? = null
    private var isGoodPlayers: Boolean = true

    // private var googleMobileAdsConsentManager: GoogleAdManager? = null
    //   private var rewardedAd: RewardedAd? = null
    private var isMobileAdsInitializeCalled = AtomicBoolean(false)
    private var isLoading = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        //   googleMobileAdsConsentManager = GoogleAdManager(requireActivity())
        billingManager = BillingManager(requireActivity() as AppCompatActivity)
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.recyclerView?.let { registerForContextMenu(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        adapter =
            pref?.let { PlayerAdapterGoods(requireContext(), it, requireActivity(), resources) }
        adapterSecond =
            pref?.let { PlayerAdapterBads(requireContext(), it, requireActivity(), resources) }
        adapterAll =
            pref?.let { PlayerAdapterAll(requireContext(), it, requireActivity(), resources) }
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter
        binding?.recyclerViewSecond?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerViewSecond?.adapter = adapterSecond
        binding?.recyclerViewAll?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerViewAll?.adapter = adapterAll

        viewModel.getBadAllPlayer()
        viewModel.getAllPlayer()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.badPlayers.collect { users ->
                    adapterSecond?.updateAdapter(users)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userUpdateResult.collect { result ->
                    result?.let { myResult ->
                        when (myResult) {
                            is MyResult.Success -> {
                                binding?.dimViewCourse?.visibility = View.GONE
                                ShowDialogHelper.dismissDialogLoad()

                            }

                            is MyResult.Failure -> {
                                ShowDialogHelper.showDialogError(requireContext(),getString(R.string.unknown_error_has_occurred))

                            }

                            MyResult.Loading -> {
                                binding?.dimViewCourse?.visibility = View.VISIBLE
                                ShowDialogHelper.showDialogLoad(requireContext())
                            }
                        }
                    }
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.goodsPlayers.collect { users ->
                    adapter?.updateAdapter(users)
                }
            }
        }



        binding?.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.usersResult.collect { result ->
                          when (result) {
                            is MyResult.Loading -> {
                                dimViewCourse.visibility = View.VISIBLE
                                ShowDialogHelper.showDialogLoad(requireContext())
                            }

                            is MyResult.Success -> {
                                val players: List<PlayerEntity> = result.data
                                adapterAll?.updateAdapter(players)
                                tvGoodOrBadPlayerAdd.text = getString(R.string.all_players)
                                dimViewCourse.visibility = View.GONE
                                ShowDialogHelper.dismissDialogLoad()
                                recyclerView.visibility = View.INVISIBLE
                                recyclerViewSecond.visibility = View.INVISIBLE
                                recyclerViewAll.visibility = View.VISIBLE
                            }

                            is MyResult.Failure -> {
                                dimViewCourse.visibility = View.GONE
                                ShowDialogHelper.dismissDialogLoad()
                                ShowDialogHelper.showDialogError(requireContext(),getString(R.string.unknown_error_has_occurred))
                            }

                            null -> {

                            }
                        }
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.deleteResult.collect { result ->
                        when (result) {
                            is MyResult.Loading -> {
                                dimViewCourse.visibility = View.VISIBLE
                                ShowDialogHelper.showDialogLoad(requireContext())
                            }

                            is MyResult.Success -> {
                                dimViewCourse.visibility = View.GONE
                                ShowDialogHelper.dismissDialogLoad()
                            }

                            is MyResult.Failure -> {
                                ShowDialogHelper.showDialogError(requireContext(),getString(R.string.unknown_error_has_occurred))
                            }

                            null -> {}
                        }
                    }
                }
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    setToolBar(false, binding)
                    adapter?.updateCheckBoxVisibility(false)
                    if (!text.isNullOrBlank()) {
                        val searchText = text.trim()
                        if (tvGoodOrBadPlayerAdd.text==getString(R.string.all_players)){
                            viewModel.searchPlayers(searchText)
                        }else{
                            if (isGoodPlayers) {
                                viewModel.searchGoodPlayers(searchText)
                            } else {
                                viewModel.searchBadPlayers(searchText)

                            }
                        }
                    }
                    if (text != null) {
                        if (text.isEmpty()) {
                            viewModel.getBadAllPlayer()
                            viewModel.getAllPlayer()
                            if (tvGoodOrBadPlayerAdd.text==getString(R.string.all_players)) viewModel.getAllPlayersFromAllUsers()
                        }
                    }
                    return true
                }

            })
            btnGoodOrBadPlayer.setOnClickListener {
                if (haveSelectedPlayers) {
                    Toast.makeText(
                        requireContext(),
                        "У вас есть  выбранные элементы",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (isGoodPlayers) {
                        isGoodPlayers = false
                        tvGoodOrBadPlayerAdd.text = getString(R.string.bad_players)
                        tvGoodOrBadPlayerAdd.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red
                            )
                        )
                        recyclerViewAll.visibility = View.INVISIBLE
                        recyclerView.visibility = View.INVISIBLE
                        recyclerViewSecond.visibility = View.VISIBLE
                    } else {
                        isGoodPlayers = true

                        tvGoodOrBadPlayerAdd.text = getString(R.string.good_players)
                        tvGoodOrBadPlayerAdd.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                        recyclerViewAll.visibility = View.INVISIBLE
                        recyclerViewSecond.visibility = View.INVISIBLE
                        recyclerView.visibility = View.VISIBLE
                    }
                }
            }

            editPlayer()
            changePlayers(binding)
            haveSelectedPlayers()

            searchView.setOnSearchClickListener {
                btnMoney.visibility = View.GONE
              //  btnSettings.visibility = View.GONE
                btnAdd.visibility = View.GONE
                btnAllPlayers.visibility = View.GONE
                expandSearchView()

            }
            searchView.setOnCloseListener {
                btnMoney.visibility = View.VISIBLE
            //    btnSettings.visibility = View.VISIBLE
                btnAdd.visibility = View.VISIBLE
                btnAllPlayers.visibility = View.VISIBLE
                false
            }
            btnAllPlayers.setOnClickListener {
                viewModel.getAllPlayersFromAllUsers()
            }
            btnMoney.setOnClickListener {
                dimViewCourse.visibility = View.VISIBLE
                showDialogThanks(requireContext(), {
                    dimViewCourse.visibility = View.GONE
                    //showRewardedVideo()
                }, {
                    Log.d("wwdwddwdwwd", "r")
                    showDialogThanksChoose(requireContext(),
                        { billingManager?.billingSetup(ProdTypesState.FIVE_RUB) },
                        { billingManager?.billingSetup(ProdTypesState.TWENTY_RUB) },
                        { billingManager?.billingSetup(ProdTypesState.HUNDRED_RUB) },
                        { billingManager?.billingSetup(ProdTypesState.TWO_HUNDRED_RUB) },
                        { dimViewCourse.visibility = View.GONE })
                }, { dimViewCourse.visibility = View.GONE })


                /*  showDialogThanks(requireContext(), {
                      dimViewCourse.visibility = View.GONE
                      showRewardedVideo()
                  }, {
                      Log.d("wwdwddwdwwd", "r")
                      showDialogThanksChoose(requireContext(),
                          {
                              viewModel.getProvider{
                                     if(it.ruBillingProvider){
                                         buyStartYookassa(ProdTypesState.FIVE_RUB,"")
                                     }else{
                                         billingManager?.billingSetup(ProdTypesState.FIVE_RUB)
                                     }
                              }
                          },
                          {
                              viewModel.getProvider{
                              if(it.ruBillingProvider){

                              }else{
                                  billingManager?.billingSetup(ProdTypesState.TWENTY_RUB)
                              }
                          }
                          },
                          {
                              viewModel.getProvider{
                              if(it.ruBillingProvider){

                              }else{
                                  billingManager?.billingSetup(ProdTypesState.HUNDRED_RUB)
                              }
                          }
                             },
                          {
                              viewModel.getProvider{
                              if(it.ruBillingProvider){

                              }else{
                                  billingManager?.billingSetup(ProdTypesState.TWO_HUNDRED_RUB)
                              }
                          }
                          },
                          { dimViewCourse.visibility = View.GONE })
                  },{dimViewCourse.visibility = View.GONE })*/
            }

            btnDeletePlayers.setOnClickListener {
                adapter?.updateCheckBoxVisibility(false)
                binding?.dimViewCourse?.visibility = View.VISIBLE
                ShowDialogHelper.showDialogRemovePlayer(requireContext(),
                    {
                        binding?.dimViewCourse?.visibility = View.GONE
                        adapter?.updateCheckBoxVisibility(true)
                    }) {
                    binding?.dimViewCourse?.visibility = View.GONE
                    tvTitleApp.visibility = View.VISIBLE
                    binding?.btnDeletePlayers?.visibility = View.GONE
                    adapter?.deleteSelectedPlayers { viewModel.deletePlayerById(it) }
                    adapterSecond?.deleteSelectedPlayers { viewModel.deletePlayerById(it) }
                }
            }
            btnSettings.setOnClickListener {
                startActivity(Intent(requireContext(), SettingActivity::class.java))
            }
            btnAdd.setOnClickListener {
                val action =
                    MainScreenFragmentDirections.actionMainScreenFragmentToAddPlayerFragment(null)
                binding?.root?.let { it1 -> findNavController(it1).navigate(action) }
            }
            //Log.d("efeffwfwwfefewfwewfwe", "googleMobileAdsConsentManager$googleMobileAdsConsentManager")
//            googleMobileAdsConsentManager?.gatherConsent { error ->
//                if (error != null) {
//                    Log.d("efeffwfwwfefewfwewfwe", "error ${error.errorCode}: ${error.message}")
//                }
//                Log.d("efeffwfwwfefewfwewfwe", "googleMobileAdsConsentManager$googleMobileAdsConsentManager")
//                if (googleMobileAdsConsentManager?.canRequestAds == true) {
//                 //   initializeMobileAdsSdk()
//                }
//
//                if (googleMobileAdsConsentManager?.isPrivacyOptionsRequired == true) {
//                    requireActivity().invalidateOptionsMenu()
//                }
//            }
        }
    }
//    private fun showRewardedVideo() {
//          Log.d("efeffwfwwfefewfwewfwe", "rewardedAd$rewardedAd")
//        if (rewardedAd != null) {
//            rewardedAd?.fullScreenContentCallback =
//                object : FullScreenContentCallback() {
//                    override fun onAdDismissedFullScreenContent() {
//                        Log.d("TAG", "Ad was dismissed.")
//                        rewardedAd = null
//                        if (googleMobileAdsConsentManager?.canRequestAds == true) {
//                            loadRewardedAd()
//                        }
//                    }
//
//                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
//                        Log.d("TAG", "Ad failed to show.")
//
//                        rewardedAd = null
//                    }
//
//                    override fun onAdShowedFullScreenContent() {
//                        Log.d("TAG", "Ad showed fullscreen content.")
//                    }
//                }
//
//            rewardedAd?.show(
//                requireActivity(),
//                OnUserEarnedRewardListener { rewardItem ->
//                    Toast.makeText(requireContext(), "Reward + $rewardItem", Toast.LENGTH_SHORT).show()
//                }
//            )
//        }
//    }


    //    private fun initializeMobileAdsSdk() {
//        if (isMobileAdsInitializeCalled.getAndSet(true)) { return }
//        MobileAds.initialize(requireContext()) {}
//        loadRewardedAd()
//    }
//    private fun loadRewardedAd() {
//        if (rewardedAd == null) {
//            isLoading = true
//            val adRequest = AdRequest.Builder().build()
//            RewardedAd.load(requireContext(), AD_UNIT_ID, adRequest, object : RewardedAdLoadCallback() {
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    Log.d("TAG", adError.message)
//                    isLoading = false
//                    rewardedAd = null
//                }
//
//                override fun onAdLoaded(ad: RewardedAd) {
//                    Log.d("TAG", "Ad was loaded.")
//                    rewardedAd = ad
//                    isLoading = false
//                }
//            }
//            )
//        }
//    }
    private fun editPlayer() {
        adapter?.editPlayer = {
            val action =
                MainScreenFragmentDirections.actionMainScreenFragmentToAddPlayerFragment(it)
            binding?.root?.let { it1 -> findNavController(it1).navigate(action) }
        }
        adapterSecond?.editPlayer = {
            val action =
                MainScreenFragmentDirections.actionMainScreenFragmentToAddPlayerFragment(it)
            binding?.root?.let { it1 -> findNavController(it1).navigate(action) }
        }
    }

    private fun changePlayers(binding: FragmentMainScreenBinding?) {
        var playerStateLocal = false
        adapter?.changePlayersGetState = { playerState ->
            playerStateLocal = playerState
        }
        adapterSecond?.changePlayersGetState = { playerState ->
            playerStateLocal = playerState
        }

        adapter?.changePlayer = { id ->
            checkPlayerState(id, playerStateLocal)
        }
        adapterSecond?.changePlayer = { id ->
            checkPlayerState(id, playerStateLocal)
        }
    }

    private fun checkPlayerState(id: String, playerStateLocal: Boolean) {
        if (playerStateLocal) {
            binding?.dimViewCourse?.visibility = View.VISIBLE
            ShowDialogHelper.showDialogIndicateAngePlayer(requireContext(), resources, {
                binding?.dimViewCourse?.visibility = View.GONE
            }) { countAnger ->
                viewModel.updateIsGoodPersonAndPercentageAnger(id, false, countAnger)
            }
        } else {
            viewModel.updateIsGoodPersonAndPercentageAnger(id, true, 0)
        }
    }

    private fun haveSelectedPlayers() {
        adapter?.haveSelectedPlayers = {
            Log.d("dadadadadadaadadad", haveSelectedPlayers.toString() + "wdwwddwwdwd")
            setToolBar(it, binding)
            haveSelectedPlayers = it
        }
        adapterSecond?.haveSelectedPlayers = {
            setToolBar(it, binding)
            haveSelectedPlayers = it
        }
    }

    private fun setToolBar(isHaveSelectedPlayers: Boolean, binding: FragmentMainScreenBinding?) {
        binding?.apply {
            if (isHaveSelectedPlayers) {
                tvTitleApp.visibility = View.INVISIBLE
                btnDeletePlayers.visibility = View.VISIBLE
            } else {
                tvTitleApp.visibility = View.VISIBLE
                btnDeletePlayers.visibility = View.INVISIBLE
            }
        }
    }

    private fun expandSearchView() {
        binding?.apply {
            val scaleX = ObjectAnimator.ofFloat(searchView, "scaleX", 0f, 1f)
            val scaleY = ObjectAnimator.ofFloat(searchView, "scaleY", 0f, 1f)
            val searchViewAnimatorSet = AnimatorSet()
            searchViewAnimatorSet.playTogether(scaleX, scaleY)
            searchViewAnimatorSet.duration = 1000
            searchViewAnimatorSet.interpolator = AccelerateDecelerateInterpolator()
            searchViewAnimatorSet.start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_TOKENIZE) {
            when (resultCode) {
                Activity.RESULT_OK -> showToken(data)
                Activity.RESULT_CANCELED -> showError()
            }
        }
    }

    private fun showToken(data: Intent?) {
        /*  if (data != null) {
              Toast.makeText(
                  requireContext(),
                  String.format(Locale.getDefault(), getString(R.string.tokenization_success)),
                  Toast.LENGTH_LONG
              ).show()
          } else {
              showError()
          }*/
    }

    private fun showError() {
        //  Toast.makeText(this, R.string.tokenization_canceled, Toast.LENGTH_SHORT).show()
    }

    private fun onTokenizeButtonCLick(
        price: Double,
        title: String,
        subtitle: String,
        phoneNumber: String
    ) {

        val paymentMethodTypes = setOf(
            PaymentMethodType.GOOGLE_PAY,
            PaymentMethodType.BANK_CARD,
            PaymentMethodType.SBERBANK,
            PaymentMethodType.YOO_MONEY,
            PaymentMethodType.SBP,
        )
        val paymentParameters = PaymentParameters(
            amount = Amount(BigDecimal.valueOf(price), Currency.getInstance("RUB")),
            title = title,
            subtitle = subtitle,
            clientApplicationKey = "BuildConfig.MERCHANT_TOKEN",
            shopId = "BuildConfig.SHOP_ID",
            savePaymentMethod = SavePaymentMethod.OFF,
            paymentMethodTypes = paymentMethodTypes,
            gatewayId = " BuildConfig.GATEWAY_ID",
            customReturnUrl = "getString(R.string.test_redirect_url)",
            userPhoneNumber = phoneNumber,
            googlePayParameters = GooglePayParameters(),
            authCenterClientId = "BuildConfig.CLIENT_ID"
        )

        val intent = createTokenizeIntent(
            requireContext(),
            paymentParameters,
            TestParameters(showLogs = true)
        )
        startActivityForResult(intent, REQUEST_CODE_TOKENIZE)
    }


    private fun buyStartYookassa(prodTypestate: ProdTypesState, phoneNumber: String) {
        when (prodTypestate) {
            ProdTypesState.FIVE_RUB -> {
                onTokenizeButtonCLick(5.0, "five_rub", "five_rub_sub", phoneNumber)
            }

            ProdTypesState.TWENTY_RUB -> {
                onTokenizeButtonCLick(20.0, "five_rub", "five_rub_sub", phoneNumber)
            }

            ProdTypesState.HUNDRED_RUB -> {
                onTokenizeButtonCLick(100.0, "five_rub", "five_rub_sub", phoneNumber)
            }

            ProdTypesState.TWO_HUNDRED_RUB -> {
                onTokenizeButtonCLick(200.0, "five_rub", "five_rub_sub", phoneNumber)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}