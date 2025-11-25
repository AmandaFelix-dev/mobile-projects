package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.todolistapp.ui.theme.TodoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Define o tema visual do app (Cores, Fontes padrão do Material 3)
            MaterialTheme {
                // Surface é o "fundo" da tela
                Surface {
                    // AQUI A MÁGICA ACONTECE: Chamamos a sua tela
                    TodoScreen()
                }
            }
        }
    }
}