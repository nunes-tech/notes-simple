package com.nunes.notas.di

import android.content.Context
import com.nunes.notas.data.db.TasksDAO
import com.nunes.notas.data.db.TasksDAOImpl
import com.nunes.notas.data.repository.TasksRepository
import com.nunes.notas.data.repository.TasksRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideTasksDAO(
        @ApplicationContext context: Context
    ) : TasksDAO {
        return TasksDAOImpl( context )
    }

    @Provides
    fun provideTasksRepository(
        tasksDAOImpl: TasksDAO
    ) : TasksRepository {
        return TasksRepositoryImpl( tasksDAOImpl )
    }

}