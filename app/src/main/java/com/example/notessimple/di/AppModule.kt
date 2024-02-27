package com.example.notessimple.di

import android.content.Context
import com.example.notessimple.data.db.ITarefaDAO
import com.example.notessimple.data.db.TarefasDAOImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun proverTarefaDAO(
        @ApplicationContext context: Context
    ) : ITarefaDAO {
        return TarefasDAOImpl( context )
    }
}