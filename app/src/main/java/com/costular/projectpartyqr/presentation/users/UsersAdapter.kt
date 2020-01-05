package com.costular.projectpartyqr.presentation.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.costular.projectpartyqr.R
import com.costular.projectpartyqr.data.model.User

class UsersAdapter : ListAdapter<User, UsersViewHolder>(UsersDiffUtil()) {

    var clickListener: ((user: User) -> Unit) = {}
    var confirmListener: ((user: User) -> Unit) = {}
    var payedListener: ((user: User) -> Unit) = {}
    var generateQrListener: ((user: User) -> Unit) = {}
    var viewQrListener: ((user: User) -> Unit) = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(
            view,
            {
                clickListener.invoke(it)
            },
            {
                confirmListener.invoke(it)
            },
            {
                payedListener.invoke(it)
            },
            {
                generateQrListener.invoke(it)
            },
            {
                viewQrListener.invoke(it)
            }
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}