package com.stalcraft.blackliststalcraft.presenter.add

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.stalcraft.blackliststalcraft.R
import com.stalcraft.blackliststalcraft.core.utils.Constatns.ALREADY_HAVE_PLAYER
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.databinding.FragmentAddPlayerBinding
import com.stalcraft.blackliststalcraft.presenter.utils.ShowDialogHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@AndroidEntryPoint
class AddPlayerFragment : Fragment() {
    private var binding: FragmentAddPlayerBinding? = null
    private val viewModel: AddPlayerViewModel by viewModels()
    private var isGoodPerson = true
    private var percentageAnger = 0
    private val args: AddPlayerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPlayerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action =
                    AddPlayerFragmentDirections.actionAddPlayerFragmentToMainScreenFragment()
                binding?.root?.let { Navigation.findNavController(it).navigate(action) }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding?.apply {
            val currentTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

            val backgroundColorRes = if (currentTheme == Configuration.UI_MODE_NIGHT_YES) R.color.light_blackE else R.color.white
            lifecycleScope.launch {
                viewModel.userResult.collect { result ->
                    when (result) {
                        is MyResult.Loading -> {
                            dimViewCourse.visibility=View.VISIBLE
                            ShowDialogHelper.showDialogLoad(requireContext())
                        }
                        is MyResult.Success -> {
                            dimViewCourse.visibility=View.GONE
                            ShowDialogHelper.dismissDialogLoad()
                            binding?.root?.let { it1 ->
                                Navigation.findNavController(it1)
                                    .navigate(R.id.action_addPlayerFragment_to_mainScreenFragment)
                            }
                        }
                        is MyResult.Failure -> {
                            dimViewCourse.visibility=View.GONE
                            ShowDialogHelper.dismissDialogLoad()
                            if (result.exception.message==ALREADY_HAVE_PLAYER){
                                ShowDialogHelper.showDialogError(requireContext(),getString(R.string.already_player))
                            }else {
                                ShowDialogHelper.showDialogError(requireContext(),getString(R.string.unknown_error_has_occurred))
                            }
                        }

                        null -> {}
                    }
                }
            }
            firstAngerAdd.setOnClickListener {
                firstAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
                percentageAnger = 1
                secondAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        backgroundColorRes
                    )
                )
                thirdsAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        backgroundColorRes
                    )
                )
                fourthAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        backgroundColorRes
                    )
                )
                fifthAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        backgroundColorRes
                    )
                )
            }
            secondAngerAdd.setOnClickListener {
                firstAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
                secondAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yellow
                    )
                )
                thirdsAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        backgroundColorRes
                    )
                )
                fourthAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        backgroundColorRes
                    )
                )
                fifthAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        backgroundColorRes
                    )
                )
                percentageAnger = 2
            }
            thirdsAngerAdd.setOnClickListener {
                firstAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
                secondAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yellow
                    )
                )
                thirdsAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yellow_dark
                    )
                )
                fourthAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        backgroundColorRes
                    )
                )
                fifthAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        backgroundColorRes
                    )
                )
                percentageAnger = 3
            }
            fourthAngerAdd.setOnClickListener {
                firstAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
                secondAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yellow
                    )
                )
                thirdsAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yellow_dark
                    )
                )
                fourthAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.orange
                    )
                )
                fifthAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        backgroundColorRes
                    )
                )
                percentageAnger = 4
            }
            fifthAngerAdd.setOnClickListener {
                firstAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
                secondAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yellow
                    )
                )
                thirdsAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yellow_dark
                    )
                )
                fourthAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.orange
                    )
                )
                fifthAngerAdd.setCardBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                percentageAnger = 5
            }
            if (isGoodPerson) {
                blockDegreeAdd.visibility = View.GONE
                tvDegreeAngerAdd.visibility = View.GONE

            }
            if (args.player != null) {
                tvTitleAdd.text = getString(R.string.edit_player)
                isGoodPerson = args.player?.isGoodPerson!!
                edNickPlayer.setText(args.player?.nick)
                edDescriptionReason.setText(args.player?.reason)
                if (isGoodPerson) {

                    tvGoodOrBadPlayerAdd.text = getString(R.string.good_player)
                    tvGoodOrBadPlayerAdd.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.green
                        )
                    )
                    blockDegreeAdd.visibility = View.GONE
                    tvDegreeAngerAdd.visibility = View.GONE

                } else {
                    tvGoodOrBadPlayerAdd.text = getString(R.string.bad_player)
                    tvGoodOrBadPlayerAdd.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                    blockDegreeAdd.visibility = View.VISIBLE
                    tvDegreeAngerAdd.visibility = View.VISIBLE
                }
            }
            btnGoodOrBadPlayerAdd.setOnClickListener {
                isGoodPersonTreatment(binding)
            }

            btnAdd.setOnClickListener {
                if (!isGoodPerson && percentageAnger==0){
                    percentageAnger++
                }
                val playerEntity = PlayerEntity(
                    if (args.player != null) args.player!!.id else UUID.randomUUID().toString(),
                    nick = edNickPlayer.text.toString().trim(),
                    reason = edDescriptionReason.text.toString(),
                    date = getCurrentDateTime(),
                    isGoodPerson = isGoodPerson,
                    percentageAnger = percentageAnger,
                    author = ""
                )
                if (args.player != null) {
                    viewModel.updatePlayer(playerEntity)
                } else {
                    viewModel.insertPlayer(playerEntity)
                }
            }
        }
    }

    private fun isGoodPersonTreatment(binding: FragmentAddPlayerBinding?) {
        binding?.apply {
            if (isGoodPerson) {
                isGoodPerson = false
                tvGoodOrBadPlayerAdd.text = getString(R.string.bad_player)
                tvGoodOrBadPlayerAdd.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                blockDegreeAdd.visibility = View.VISIBLE
                tvDegreeAngerAdd.visibility = View.VISIBLE

            } else {
                isGoodPerson = true
                tvGoodOrBadPlayerAdd.text = getString(R.string.good_player)
                tvGoodOrBadPlayerAdd.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
                blockDegreeAdd.visibility = View.GONE
                tvDegreeAngerAdd.visibility = View.GONE
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yy")
        return currentDateTime.format(formatter)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}