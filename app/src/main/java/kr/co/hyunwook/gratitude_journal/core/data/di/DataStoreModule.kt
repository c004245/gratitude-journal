package kr.co.hyunwook.gratitude_journal.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.hyunwook.gratitude_journal.core.datastore.datasource.DefaultGratitudePreferencesDataSource
import kr.co.hyunwook.gratitude_journal.core.datastore.datasource.GratitudePreferencesDataSource

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataStoreModule {
    @Binds
    abstract fun bindGratitudeLocalDataSource(
        dataSource: DefaultGratitudePreferencesDataSource
    ): GratitudePreferencesDataSource
}