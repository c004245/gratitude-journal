package kr.co.hyunwook.gratitude_journal.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.hyunwook.gratitude_journal.core.database.AppDatabase
import kr.co.hyunwook.gratitude_journal.core.database.GratitudeRecordDao
import android.app.Application
import android.content.Context
import javax.inject.Named
import javax.inject.Singleton
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room

@Module
@InstallIn(SingletonComponent::class)
internal object  DatabaseModule {
    private const val GRATITUDE_DATASTORE_NAME = "GRATITUDE_PREFERENCES"

    private val Context.gratitudeDataSource by preferencesDataStore(GRATITUDE_DATASTORE_NAME)

    @Provides
    @Singleton
    @Named("gratitude")
    fun provideGratitudeDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> =
        context.gratitudeDataSource


    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application
    ): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "Gratitude.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideGratitudeRecordDao(appDatabase: AppDatabase): GratitudeRecordDao {
        return appDatabase.gratitudeRecordDao()
    }
}
