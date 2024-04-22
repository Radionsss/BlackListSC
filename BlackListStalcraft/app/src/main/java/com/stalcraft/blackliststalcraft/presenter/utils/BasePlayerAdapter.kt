package com.stalcraft.blackliststalcraft.presenter.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stalcraft.blackliststalcraft.presenter.utils.TextUtils.setTextSize
import com.stalcraft.blackliststalcraft.R
import com.stalcraft.blackliststalcraft.core.utils.Constatns
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.databinding.PlayerItemBinding

import java.util.*
import kotlin.math.log

abstract class BasePlayerAdapter(
    private val context: Context,
    val pref: SharedPreferences,
    private val requireActivity: FragmentActivity,
    val resources: Resources
) : RecyclerView.Adapter<BasePlayerAdapter.PlayerHolder>() {
    val playerArray = ArrayList<PlayerEntity>()
    var changePlayersGetState: ((Boolean) -> Unit)? = null
    var changePlayer: ((String) -> Unit)? = null
    var haveSelectedPlayers: ((Boolean) -> Unit)? = null
    var editPlayer: ((PlayerEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val viewBinding =
            PlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerHolder(
            viewBinding,
            resources,
            context,
            pref,
            changePlayer,
            changePlayersGetState,
            haveSelectedPlayers,
            editPlayer,
            this
        )
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.setData(playerArray[position])
    }

    override fun getItemCount(): Int {
        return playerArray.size
    }

    class PlayerHolder(
        private val binding: PlayerItemBinding,
        private val resources: Resources,
        private val context: Context,
        private val pref: SharedPreferences,
        private var changePlayer: ((String) -> Unit)?,
        private var changePlayersGetState: ((Boolean) -> Unit)?,
        private var haveSelectedPlayers: ((Boolean) -> Unit)?,
        private var editPlayer: ((PlayerEntity) -> Unit)?,
        private var basePlayerAdapter: BasePlayerAdapter
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(player: PlayerEntity) {
            binding.apply {
                val currentTheme =
                    resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                val backgroundColorRes =
                    if (currentTheme == Configuration.UI_MODE_NIGHT_YES) R.color.light_blackE else R.color.white
                binding.apply {
                    tvNickPlayer.text = player.nick
                    tvDescriptionReason.text = player.reason
                    tvDate.text = player.date
                    if (player.author!=""){
                        tvAuthorText.text = player.author
                        tvAuthorText.visibility = View.VISIBLE
                        tvAuthor.visibility = View.VISIBLE
                        btnEdit.visibility=View.GONE
                        btnSwap.visibility=View.GONE
                    }
                    if (player.isNeedShowCheckBox) {
                        checkBoxCard.visibility = View.VISIBLE
                    } else {
                        checkBoxCard.visibility = View.GONE
                    }
                    tvDate.setTextSize(pref.getString(Constatns.TEXT_KEY, Constatns.TEXT_SIZE))
                    tvReason.setTextSize(pref.getString(Constatns.TEXT_KEY, Constatns.TEXT_SIZE))
                    tvDescriptionReason.setTextSize(
                        pref.getString(
                            Constatns.TEXT_KEY,
                            Constatns.TEXT_SIZE
                        )
                    )
                    tvNick.setTextSize(pref.getString(Constatns.TEXT_KEY, Constatns.TEXT_SIZE))
                    tvNickPlayer.setTextSize(
                        pref.getString(
                            Constatns.TEXT_KEY,
                            Constatns.TEXT_SIZE
                        )
                    )
                    if (!player.isGoodPerson||player.percentageAnger>0) {
                        tvPercentageAngerItem.visibility = View.VISIBLE
                        blockDegree.visibility = View.VISIBLE
                        when (player.percentageAnger) {
                            1 -> {
                                firstAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.green
                                    )
                                )
                                secondAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        backgroundColorRes
                                    )
                                )
                                thirdsAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        backgroundColorRes
                                    )
                                )
                                fourthAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        backgroundColorRes
                                    )
                                )
                                fifthAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        backgroundColorRes
                                    )
                                )
                            }

                            2 -> {
                                firstAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.green
                                    )
                                )
                                secondAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.yellow
                                    )
                                )
                                thirdsAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        backgroundColorRes
                                    )
                                )
                                fourthAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        backgroundColorRes
                                    )
                                )
                                fifthAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        backgroundColorRes
                                    )
                                )
                            }

                            3 -> {
                                firstAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.green
                                    )
                                )
                                secondAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.yellow
                                    )
                                )
                                thirdsAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.yellow_dark
                                    )
                                )
                                fourthAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        backgroundColorRes
                                    )
                                )
                                fifthAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        backgroundColorRes
                                    )
                                )
                            }

                            4 -> {
                                firstAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.green
                                    )
                                )
                                secondAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.yellow
                                    )
                                )
                                thirdsAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.yellow_dark
                                    )
                                )
                                fourthAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.orange
                                    )
                                )
                                fifthAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        backgroundColorRes
                                    )
                                )
                            }

                            5 -> {
                                firstAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.green
                                    )
                                )
                                secondAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.yellow
                                    )
                                )
                                thirdsAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.yellow_dark
                                    )
                                )
                                fourthAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.orange
                                    )
                                )
                                fifthAnger.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.red
                                    )
                                )
                            }
                        }
                    } else {
                        tvPercentageAngerItem.visibility = View.GONE
                        blockDegree.visibility = View.GONE
                    }
                    cardPlayer.setOnLongClickListener {
                        if (player.author=="") {
                            player.isSelected = true
                            basePlayerAdapter.updateCheckBoxVisibility(true)
                            checkBoxCard.isChecked = true
                        }
                        true
                    }
                    btnSwap.setOnClickListener {
                        changePlayersGetState?.invoke( player.isGoodPerson)
                        changePlayer?.invoke(player.id)

                    }
               /*     noSelectedPlayers={
                        if (it) {
                          //  checkBoxCard.visibility = View.VISIBLE
                            checkBoxCard.isChecked = false
                        }
                    }*/

                    checkBoxCard.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            player.isSelected = true
                            haveSelectedPlayers?.invoke(true)
                        } else {
                            player.isSelected = false
                            val selectedPlayersDelete = basePlayerAdapter.playerArray.filter { it.isSelected }
                            if (selectedPlayersDelete.isEmpty()) {
                                haveSelectedPlayers?.invoke(false)
                                basePlayerAdapter.updateCheckBoxVisibility(false)
                            }
                        }
                    }
                    btnEdit.setOnClickListener {
                        editPlayer?.invoke(player)
                    }
                }
            }
        }
    }

    fun updateAdapter(newList: List<PlayerEntity>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilHelper(playerArray, newList))
        playerArray.clear()
        playerArray.addAll(newList)

            diffResult.dispatchUpdatesTo(this)


    }

    fun updateCheckBoxVisibility(isVisible: Boolean) {
            for (item in playerArray) {
                item.isNeedShowCheckBox = isVisible
            }
            notifyDataSetChanged()
    }


  /*  fun updateAdapter(newList: List<PlayerEntity>) {
        val adsListTemp = ArrayList<PlayerEntity>()
        adsListTemp.addAll(newList)
        val difResult = DiffUtil.calculateDiff(DiffUtilHelper(playerArray, adsListTemp))
        requireActivity.runOnUiThread {
            difResult.dispatchUpdatesTo(this)
        }
        playerArray.clear()
        playerArray.addAll(adsListTemp)

    }*/
    fun deleteSelectedPlayers(id: ((String) -> Unit)) {
        haveSelectedPlayers?.invoke(false)
        val selectedPlayersDelete = playerArray.filter { it.isSelected }
        val selectedPlayersDeleteArray = playerArray.filter { !it.isSelected }
        updateAdapter(selectedPlayersDeleteArray)
        selectedPlayersDelete.forEach {
           id.invoke(it.id)
        }

    }
}

class DiffUtilHelper(private val oldList: List<PlayerEntity>, private val newList: List<PlayerEntity>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}