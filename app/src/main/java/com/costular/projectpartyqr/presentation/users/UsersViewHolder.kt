package com.costular.projectpartyqr.presentation.users

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.costular.projectpartyqr.R
import com.costular.projectpartyqr.data.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UsersViewHolder(
    view: View,
    val clickListener: (user: User) -> Unit,
    val confirmListener: (user: User) -> Unit,
    val moneyListener: (user: User) -> Unit,
    val generateQrListener: (user: User) -> Unit,
    val viewQrListener: (user: User) -> Unit
) : RecyclerView.ViewHolder(view) {

    fun bind(user: User) {
        with(itemView) {
            textUserName.text = user.name

            if (user.confirmed) {
                buttonConfirmed.setIconResource(R.drawable.ic_confirmed)
                buttonConfirmed.setIconTintResource(R.color.colorPrimary)
            } else {
                buttonConfirmed.setIconResource(R.drawable.ic_not_confirmed)
                buttonConfirmed.setIconTintResource(R.color.white)
            }

            if (user.payed) {
                buttonPayed.setIconResource(R.drawable.ic_money_payed)
                buttonPayed.setIconTintResource(R.color.colorPrimary)
            } else {
                buttonPayed.setIconResource(R.drawable.ic_not_payed)
                buttonPayed.setIconTintResource(R.color.white)
            }

            if (user.qr.isNotEmpty()) {
                userGenerateQR.visibility = View.GONE
                buttonCheckQr.visibility = View.VISIBLE
            } else {
                userGenerateQR.visibility = View.VISIBLE
                buttonCheckQr.visibility = View.GONE
            }

            buttonConfirmed.setOnClickListener { confirmListener.invoke(user) }
            buttonPayed.setOnClickListener { moneyListener.invoke(user) }
            userGenerateQR.setOnClickListener { generateQrListener.invoke(user) }
            buttonCheckQr.setOnClickListener { viewQrListener.invoke(user) }
            setOnClickListener { clickListener(user) }
        }
    }

}