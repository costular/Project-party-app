package com.costular.projectpartyqr.presentation.users

import android.media.browse.MediaBrowser
import androidx.recyclerview.widget.DiffUtil
import com.costular.projectpartyqr.data.model.User

class UsersDiffUtil : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.id == newItem.id

}