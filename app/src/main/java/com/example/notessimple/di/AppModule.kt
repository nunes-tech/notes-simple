package com.example.notessimple.di

import android.content.Context
import com.example.notessimple.data.db.TarefasDAO
import com.example.notessimple.data.db.TarefasDAOImpl
import com.example.notessimple.data.repository.TarefasRepository
import com.example.notessimple.data.repository.TarefasRepositoryImpl
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
    ) : TarefasDAO {
        return TarefasDAOImpl( context )
    }

    @Provides
    fun proverTarefasRepository(
        tarefasDAOImpl: TarefasDAO
    ) : TarefasRepository {
        return TarefasRepositoryImpl( tarefasDAOImpl )
    }

}