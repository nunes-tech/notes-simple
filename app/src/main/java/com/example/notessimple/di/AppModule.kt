package com.example.notessimple.di

import android.content.Context
import com.example.notessimple.data.db.TasksDAO
import com.example.notessimple.data.db.TasksDAOImpl
import com.example.notessimple.data.repository.TasksRepository
import com.example.notessimple.data.repository.TasksRepositoryImpl
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