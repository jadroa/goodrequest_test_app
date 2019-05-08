package click.jaromil.goodrequest.di_module

import android.content.Context
import click.jaromil.goodrequest.api.ApiClient
import click.jaromil.goodrequest.repository.Repository
import click.jaromil.goodrequest.repository.ApiRepository
import click.jaromil.goodrequest.viewmodel.UsersViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


object AppModule {
    val appModule = module {
        viewModel { UsersViewModel(get()) }
        single { ApiRepository(get()) as Repository }
        single { ApiClient(get()) }
    }
}