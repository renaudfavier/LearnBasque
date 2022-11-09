package com.renaudfavier.learnbasque

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.renaudfavier.learnbasque.core.designsystem.theme.LearnBasqueTheme
import com.renaudfavier.learnbasque.feature.vocabulary.VocabularyScreen
import com.renaudfavier.learnbasque.feature.vocabulary.VocabularyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var viewModel: VocabularyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            LearnBasqueTheme {
                VocabularyScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = viewModel
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }
}
