package kr.co.hyunwook.gratitude_journal.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude.GratitudeRepository
import kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude.GratitudeRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    fun bindsGratitudeRepository(gratitudeRepositoryImpl: GratitudeRepositoryImpl): GratitudeRepository
}


