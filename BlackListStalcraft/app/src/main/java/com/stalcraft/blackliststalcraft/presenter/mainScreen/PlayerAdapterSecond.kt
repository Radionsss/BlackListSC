package com.stalcraft.blackliststalcraft.presenter.mainScreen

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.stalcraft.blackliststalcraft.databinding.PlayerItemBinding
import com.stalcraft.blackliststalcraft.presenter.utils.BasePlayerAdapter

class PlayerAdapterSecond(private val context: Context, pref: SharedPreferences, requireActivity: FragmentActivity, resources: Resources) : BasePlayerAdapter(context, pref, requireActivity, resources) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        // Можете изменить наименование биндинга на соответствующий вашему представлению элемента списка
        val viewBinding = PlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

}