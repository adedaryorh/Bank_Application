package com.example.fintechapp.db

import com.example.fintechapp.ui.accounts.AccountsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

     viewModel { AccountsViewModel(get()) }
}

