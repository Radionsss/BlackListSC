package com.stalcraft.blackliststalcraft.presenter.add

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayoutStates
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.stalcraft.blackliststalcraft.R
import com.stalcraft.blackliststalcraft.core.exception.AddPlayerErrorEnum
import com.stalcraft.blackliststalcraft.data.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.databinding.FragmentAddPlayerBinding
import com.stalcraft.blackliststalcraft.presenter.utils.ShowDialogHelper
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class AddPlayerFragment : Fragment() {
    private var binding: FragmentAddPlayerBinding? = null
    private val viewModel: AddPlayerViewModel by viewModels()
    private var isGoodPerson=true
    private var percentageAnger=1
    private val args: AddPlayerFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddPlayerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = AddPlayerFragmentDirections.actionAddPlayerFragmentToMainScreenFragment()
                binding?.root?.let { Navigation.findNavController(it).navigate(action) }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding?.apply {
            val currentTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

            val backgroundColorRes = if (currentTheme == Configuration.UI_MODE_NIGHT_YES) R.color.light_blackE else R.color.white
            firstAngerAdd.setOnClickListener {
                firstAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                percentageAnger=1
                secondAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColorRes))
                thirdsAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColorRes))
                fourthAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColorRes))
                fifthAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColorRes))
            }
            secondAngerAdd.setOnClickListener {
                firstAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                secondAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow))
                thirdsAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColorRes))
                fourthAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColorRes))
                fifthAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColorRes))
                percentageAnger=2
            }
            thirdsAngerAdd.setOnClickListener {
                firstAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                secondAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow))
                thirdsAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow_dark))
                fourthAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColorRes))
                fifthAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColorRes))
                percentageAnger=3
            }
            fourthAngerAdd.setOnClickListener {
                firstAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                secondAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow))
                thirdsAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow_dark))
                fourthAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange))
                fifthAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColorRes))
                percentageAnger=4
            }
            fifthAngerAdd.setOnClickListener {
                firstAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                secondAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow))
                thirdsAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow_dark))
                fourthAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange))
                fifthAngerAdd.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
                percentageAnger=5
            }
            if(isGoodPerson){
                blockDegreeAdd.visibility=View.GONE
                tvDegreeAngerAdd.visibility=View.GONE

            }
            if (args.player != null) {
                tvTitleAdd.text=getString(R.string.edit_player)
                isGoodPerson = args.player?.isGoodPerson!!
                edNickPlayer.setText(args.player?.nick)
                edDescriptionReason.setText(args.player?.reason)
                if (isGoodPerson) {

                    tvGoodOrBadPlayerAdd.text = getString(R.string.good_player)
                    tvGoodOrBadPlayerAdd.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                    blockDegreeAdd.visibility = View.GONE
                    tvDegreeAngerAdd.visibility = View.GONE

                } else {
                    tvGoodOrBadPlayerAdd.text = getString(R.string.bad_player)
                    tvGoodOrBadPlayerAdd.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    blockDegreeAdd.visibility = View.VISIBLE
                    tvDegreeAngerAdd.visibility = View.VISIBLE
                }
            }
            btnGoodOrBadPlayerAdd.setOnClickListener {
                isGoodPersonTreatment(binding)
            }

            btnAdd.setOnClickListener {
                val playerEntity= PlayerEntity(args.player?.id,nick = edNickPlayer.text.toString(), reason = edDescriptionReason.text.toString(), date = getCurrentDateTime(),isGoodPerson = isGoodPerson,percentageAnger=percentageAnger)
                if (args.player != null) {
                     viewModel.updatePlayer(playerEntity)
                    binding?.root?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_addPlayerFragment_to_mainScreenFragment) }
                }else {
                    insertPlayerTreatmentResult(playerEntity)
                }
            }
        }
    }

    private fun isGoodPersonTreatment(binding: FragmentAddPlayerBinding?) {
        binding?.apply {
            if (isGoodPerson) {
                isGoodPerson = false
                tvGoodOrBadPlayerAdd.text = getString(R.string.bad_player)
                tvGoodOrBadPlayerAdd.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                blockDegreeAdd.visibility = View.VISIBLE
                tvDegreeAngerAdd.visibility = View.VISIBLE

            } else {
                isGoodPerson = true
                tvGoodOrBadPlayerAdd.text = getString(R.string.good_player)
                tvGoodOrBadPlayerAdd.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                blockDegreeAdd.visibility = View.GONE
                tvDegreeAngerAdd.visibility = View.GONE
            }
        }
    }

    private fun insertPlayerTreatmentResult(playerEntity: PlayerEntity) {
        viewModel.insertPlayer(playerEntity) {
            Log.d("dwdw",it.toString())
            when (it){
                AddPlayerErrorEnum.SUCCESS -> {
                    binding?.root?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_addPlayerFragment_to_mainScreenFragment) }
                }
                AddPlayerErrorEnum.NICK_EMPTY ->{
                    increaseBottomMargin(true)
                    val snackbar = view?.let {snack->
                        Snackbar.make(
                            snack,
                            R.string.nick_empty_snack_bar,
                            Snackbar.LENGTH_SHORT
                        )
                    }
                    snackbar?.show()
                    snackbar?.addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                increaseBottomMargin(false)
                            }
                        }
                    })
                }
                AddPlayerErrorEnum.ERROR ->{
                    ShowDialogHelper.showDialogUnknownError(requireContext(),{insertPlayerTreatmentResult(playerEntity)}){}
                }
                AddPlayerErrorEnum.UNKNOWN_ERROR ->{
                    ShowDialogHelper.showDialogUnknownError(requireContext(),{insertPlayerTreatmentResult(playerEntity)}){}
                }
                AddPlayerErrorEnum.NULL_POINT_ERROR -> {
                    ShowDialogHelper.showDialogUnknownError(requireContext(),{insertPlayerTreatmentResult(playerEntity)}){}
                }
            }
        }
    }

    private fun increaseBottomMargin(isNewMargin: Boolean) {
        val layoutParams = binding?.btnAdd?.layoutParams as ConstraintLayout.LayoutParams
        val newMargin = resources.getDimensionPixelSize(R.dimen.increased_bottom_margin)
        val oldMargin = resources.getDimensionPixelSize(R.dimen.original_size)

        // Увеличиваем отступ от низа
        if (isNewMargin) {
            layoutParams.bottomMargin = newMargin
        }else{
            layoutParams.bottomMargin = oldMargin
        }
        binding?.btnAdd?.layoutParams = layoutParams
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yy")
        return currentDateTime.format(formatter)
    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}