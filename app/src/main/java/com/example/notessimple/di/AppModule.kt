package com.example.notessimple.di

import android.content.Context
import com.example.notessimple.data.db.TarefasDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun proverTarefaDAO( @ActivityContext context: Context) : TarefasDAO {
        return TarefasDAO( context )
    }
}