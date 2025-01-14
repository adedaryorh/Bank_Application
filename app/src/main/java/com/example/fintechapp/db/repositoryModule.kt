package com.example.fintechapp.db

import com.example.fintechapp.data.repository.AccountRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AccountRepository(get(), get()) }

}
