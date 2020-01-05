package com.costular.projectpartyqr.di

import com.costular.projectpartyqr.data.*
import com.costular.projectpartyqr.presentation.generate.ViewQrViewModel
import com.costular.projectpartyqr.presentation.stats.StatsViewModel
import com.costular.projectpartyqr.presentation.users.UsersViewModel
import com.costular.projectpartyqr.presentation.validate.ValidateViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val app = module {

    single {
        UserMapper()
    }

    single {
        FirebaseFirestore.getInstance()
    }

    single<UserRepository> {
        UserRepositoryDefault(get(), get())
    }

    single<StatsRepository> {
        StatsRepositoryDefault(get())
    }

    viewModel {
        UsersViewModel(get())
    }

    viewModel {
        ViewQrViewModel(get())
    }

    viewModel {
        StatsViewModel(get())
    }

    viewModel {
        ValidateViewModel(get())
    }

}