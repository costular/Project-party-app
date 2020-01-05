package com.costular.projectpartyqr.presentation.users

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.costular.common.util.extensions.observe
import com.costular.projectpartyqr.R
import com.costular.projectpartyqr.common.BaseFragment
import com.costular.projectpartyqr.data.model.User
import com.costular.projectpartyqr.presentation.generate.ViewQrFragment
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_users.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : BaseFragment(R.layout.fragment_users) {

    val usersViewModel: UsersViewModel by viewModel()
    val adapter: UsersAdapter by lazy(LazyThreadSafetyMode.NONE) {
        UsersAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindRecycler()
        bindEvents()
        bindActions()
    }

    private fun bindRecycler() {
        adapter.clickListener = {

        }
        adapter.confirmListener = {
            usersViewModel.toggleConfirm(it)
        }
        adapter.generateQrListener = {
            usersViewModel.generateQr(it)
        }
        adapter.payedListener = {
            usersViewModel.togglePay(it)
        }
        adapter.viewQrListener = {
            usersViewModel.openQr(it)
        }
        with(usersRecycler) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = this@UsersFragment.adapter
            addItemDecoration(
                HorizontalDividerItemDecoration
                    .Builder(requireContext())
                    .colorResId(R.color.divider)
                    .build()
            )
        }
    }

    private fun bindEvents() {
        with(usersViewModel) {
            observe(users) {
                showUsers(it!!)
            }
            observe(showLoading) {
                showLoading(it!!)
            }
            observe(openQrAction) {
                it!!.getContentIfNotHandled()?.let {
                    openQr(it)
                }
            }
        }
    }

    private fun bindActions() {
        buttonAddUser.setOnClickListener {
            showAddUser()
        }
    }

    private fun showAddUser() {
        MaterialDialog(requireActivity()).show {
            input(hint = "Nombre") { _, text ->
                usersViewModel.addUser(text.toString())
            }
            positiveButton(text = "Guardar")
        }
    }

    private fun showUsers(users: List<User>) {
        adapter.submitList(users)
    }

    private fun showLoading(isLoading: Boolean) {
        usersLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun openQr(userId: String) {
        ViewQrFragment.show(childFragmentManager, userId)
    }



}