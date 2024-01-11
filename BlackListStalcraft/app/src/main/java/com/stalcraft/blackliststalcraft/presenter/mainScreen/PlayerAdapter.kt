package com.stalcraft.blackliststalcraft.presenter.mainScreen

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.stalcraft.blackliststalcraft.databinding.PlayerItemBinding
import com.stalcraft.blackliststalcraft.presenter.utils.BasePlayerAdapter


class PlayerAdapter(
    private val context: Context, pref: SharedPreferences, requireActivity: FragmentActivity, resources: Resources
)  : BasePlayerAdapter(context, pref, requireActivity, resources) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasePlayerAdapter.PlayerHolder {
        val viewBinding =
            PlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasePlayerAdapter.PlayerHolder(
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

    override fun onBindViewHolder(holder: BasePlayerAdapter.PlayerHolder, position: Int) {
        holder.setData(playerArray[position])
    }
}

/*

class PlayerAdapter(private val context: Context, private val pref: SharedPreferences, private val requireActivity: FragmentActivity, private val resources: Resources) : RecyclerView.Adapter<PlayerAdapter.PlayerHolder>() {
    var changeSelectedPlayers: ((Boolean) -> Unit)? = null
    var changePlayer: ((Int) -> Unit)? = null
    // var longClick: (() -> Unit)? = null
    var noSelectedPlayers: ((Boolean) -> Unit)? = null
    var isHaveSelectedPlayers: ((Boolean) -> Unit)? = null
    private var counter = 0
    private var isNeedShowCheckBar = false
    private var isNeedShowCheckBartest = false
    private var dataList: List<PlayerEntity> = emptyList()

    inner class PlayerHolder(val binding: PlayerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        return PlayerHolder(
            PlayerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return diffList.currentList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        val currentPlayer = diffList.currentList[position]
        val currentTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val backgroundColorRes = if (currentTheme == Configuration.UI_MODE_NIGHT_YES) R.color.light_blackE else R.color.white
        holder.binding.apply {
            tvNickPlayer.text = currentPlayer.nick
            tvDescriptionReason.text = currentPlayer.reason
            tvDate.text = currentPlayer.date
            if (  diffList.currentList[position].isNeedShowCheckBox) {
                checkBoxCard.visibility =View.VISIBLE
                isNeedShowCheckBartest=true
            }else{
                isNeedShowCheckBartest=false
                //     checkBox.visibility =View.INVISIBLE
            }
            if (isNeedShowCheckBartest){
                checkBoxCard.visibility =View.VISIBLE
            } else{
                checkBoxCard.visibility =View.GONE
            }
            tvDate.setTextSize(pref.getString(Constatns.TEXT_KEY, Constatns.TEXT_SIZE))
            tvReason.setTextSize(pref.getString(Constatns.TEXT_KEY, Constatns.TEXT_SIZE))
            tvDescriptionReason.setTextSize(pref.getString(Constatns.TEXT_KEY, Constatns.TEXT_SIZE))
            tvNick.setTextSize(pref.getString(Constatns.TEXT_KEY, Constatns.TEXT_SIZE))
            tvNickPlayer.setTextSize(pref.getString(Constatns.TEXT_KEY, Constatns.TEXT_SIZE))
            if (!currentPlayer.isGoodPerson) {
                tvPercentageAngerItem.visibility = View.VISIBLE
                blockDegree.visibility = View.VISIBLE
                when (currentPlayer.percentageAnger) {
                    1 -> {
                        firstAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green))
                        secondAnger.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
                        thirdsAnger.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
                        fourthAnger.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
                        fifthAnger.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
                    }

                    2 -> {
                        firstAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green))
                        secondAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                        thirdsAnger.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
                        fourthAnger.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
                        fifthAnger.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
                    }

                    3 -> {
                        firstAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green))
                        secondAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                        thirdsAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellow_dark))
                        fourthAnger.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
                        fifthAnger.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
                    }

                    4 -> {
                        firstAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green))
                        secondAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                        thirdsAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellow_dark))
                        fourthAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange))
                        fifthAnger.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
                    }

                    5 -> {
                        firstAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green))
                        secondAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                        thirdsAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellow_dark))
                        fourthAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange))
                        fifthAnger.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red))
                    }
                }
            } else {
                tvPercentageAngerItem.visibility = View.INVISIBLE
                blockDegree.visibility = View.GONE
            }

            //    checkBox.isChecked = currentPlayer.isSelected
            */
/* if (isNeedShowCheckBar) {
                 requireActivity.runOnUiThread {
                //     checkBox.visibility = View.VISIBLE
                    checkBox.isChecked = true
                 }
             } else {
                 requireActivity.runOnUiThread {
                        //checkBox.isChecked = false
                     // checkBox.isChecked = true
                  //   checkBox.visibility = View.INVISIBLE
                 }
             }
 *//*


            cardPlayer.setOnLongClickListener {
                isNeedShowCheckBar = true
                diffList.currentList[position].isSelected = true
                checkBoxCard.isChecked = true
                Log.d("dwdwrrr", checkBoxCard.isChecked.toString())
                Log.d("dwdwrrr", diffList.currentList[position].toString())
                Log.d("dwdwrrr", it.toString())
                */
/*    requireActivity.runOnUiThread {
                        notifyItemChanged(position)
                    }*//*

                val players = diffList.currentList
                for (item in players) {
                    item.isNeedShowCheckBox=true
                }
                notifyDataSetChanged()
                */
/* checkBox.isChecked = true

                 requireActivity.runOnUiThread {
                     notifyDataSetChanged()
                 }*//*

                true
            }
            btnSwap.setOnClickListener {
                currentPlayer.id?.let { it1 -> changePlayer?.invoke(it1) }
                changeSelectedPlayers?.invoke(currentPlayer.isGoodPerson)
                //    Log.d("dwdwrrr", currentPlayer.isGoodPerson.toString())

                requireActivity.runOnUiThread {
                    notifyDataSetChanged()
                }
                //  diffList.submitList(diffList.currentList)

            }
            checkBoxCard.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {

                    //      diffList.currentList[position].isSelected = true
                    noSelectedPlayers?.invoke(false)
                    diffList.currentList[position].isSelected = true
                    isHaveSelectedPlayers?.invoke(true)
                    checkBoxCard.visibility = View.VISIBLE
                    //    notifyDataSetChanged()
                    counter++
                } else {
                    diffList.currentList[position].isSelected = false
                    if (counter !=0) {
                        counter--
                    }
                    if (counter == 0) {
                        val players = diffList.currentList
                        for (item in players) {
                            item.isNeedShowCheckBox=false
                        }
                        isHaveSelectedPlayers?.invoke(false)
                        isNeedShowCheckBar = false
                        noSelectedPlayers?.invoke(true)
                        notifyDataSetChanged()
                    }
                }
                Log.d("ewewewewewewe",counter.toString())

                Log.d("ewewewewewewe", "-------")
                Log.d("ewewewewewewe", diffList.currentList.toString())
                Log.d("ewewewewewewe", "-------")
            }
            // Log.d("ewewewewewewe", diffList.currentList.toString())
            */
/* if (!isNeedShowCheckBar){
                 checkBox.isChecked = false
             }*//*

        }
    }

    */
/*    fun disappearCheckBox(){
            counter = 0
            isNeedShowCheckBar = false
            val player = diffList.currentList
            for (item in player) {
                item.isSelected = false
            }
        }*//*

    fun nullificationCounter(){
        counter = 0
    }
    fun deleteSelectedPlayers(id: ((Int) -> Unit)) {
        counter = 0
        noSelectedPlayers?.invoke(true)
        val selectedPlayers = diffList.currentList.filter { it.isSelected }
        selectedPlayers.forEach {
            it.id?.let { it1 -> id.invoke(it1) }
        }
        val players = diffList.currentList
        for (item in players) {
            item.isNeedShowCheckBox = false
        }

        notifyDataSetChanged()
        diffList.submitList(diffList.currentList.filter { !it.isSelected })
        Log.d("wqqqqwqwqwqwq",counter.toString())
    }

    private val diffCall = object : DiffUtil.ItemCallback<PlayerEntity>() {
        override fun areItemsTheSame(oldItem: PlayerEntity, newItem: PlayerEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlayerEntity, newItem: PlayerEntity): Boolean {
            return oldItem == newItem
        }
    }

    val diffList = AsyncListDiffer(this, diffCall)
}*/
