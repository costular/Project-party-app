package com.costular.projectpartyqr.presentation.stats

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.costular.common.util.extensions.observe
import com.costular.projectpartyqr.R
import com.costular.projectpartyqr.common.BaseFragment
import com.costular.projectpartyqr.data.model.Stat
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_stats.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatsFragment : BaseFragment(R.layout.fragment_stats) {

    val adapter: StatsAdapter = StatsAdapter()

    val statsViewModel: StatsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        bindEvents()
    }

    private fun initRecycler() {
        with(recyclerStats) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                HorizontalDividerItemDecoration
                    .Builder(requireContext())
                    .colorResId(R.color.divider)
                    .build()
            )
            adapter = this@StatsFragment.adapter
        }
    }

    private fun bindEvents() {
        with (statsViewModel) {
            observe(statsEvent) {
                showStats(it!!)
            }
        }
    }

    private fun showStats(stats: List<Stat>) {
        adapter.submit(stats)
    }


}