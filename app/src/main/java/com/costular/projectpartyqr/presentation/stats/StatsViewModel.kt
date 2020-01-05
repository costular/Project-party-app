package com.costular.projectpartyqr.presentation.stats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.costular.projectpartyqr.R
import com.costular.projectpartyqr.data.StatsRepository
import com.costular.projectpartyqr.data.model.Stat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatsViewModel(
    private val statsRepository: StatsRepository
) : ViewModel() {

    val statsEvent: MutableLiveData<List<Stat>> = MutableLiveData()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            val stats = withContext(Dispatchers.IO) { statsRepository.getStats() }
            val statLists = withContext(Dispatchers.Default) {
                listOf(
                    Stat(R.drawable.ic_invitados, "Invitados", stats.total.toString()),
                    Stat(R.drawable.ic_invitados, "Invitados confirmados", "${stats.confirmed}/${stats.total}"),
                    Stat(R.drawable.ic_invitados, "Invitados que han pagado", "${stats.payed}/${stats.total}"),
                    Stat(R.drawable.ic_invitados, "Invitados que han entrado", "${stats.joined}/${stats.total}"),
                    Stat(R.drawable.ic_money_payed, "Dinero recaudado", "${stats.totalMoney}€")
                )
            }
            statsEvent.value = statLists
        }
    }

}