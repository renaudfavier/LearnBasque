package com.renaudfavier.learnbasque

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.renaudfavier.learnbasque.core.data.repository.BaseWordsRepository
import com.renaudfavier.learnbasque.core.data.repository.fake.FakeMemoryTestAnswerRepository
import com.renaudfavier.learnbasque.core.designsystem.theme.LearnBasqueTheme
import com.renaudfavier.learnbasque.core.domain.GetUserLevelUseCase
import com.renaudfavier.learnbasque.feature.vocabulary.domain.AddAnswerUseCase
import com.renaudfavier.learnbasque.feature.vocabulary.domain.GetNextWordToMemorizeUseCase
import com.renaudfavier.learnbasque.feature.vocabulary.VocabularyScreen
import com.renaudfavier.learnbasque.feature.vocabulary.VocabularyViewModel

class MainActivity : ComponentActivity() {

    private val wordRepository = BaseWordsRepository()
    private val answerRepository = FakeMemoryTestAnswerRepository()
    private val getUserLevelUseCase = GetUserLevelUseCase(answerRepository, wordRepository)

    private val getNextWordToMemorizeUseCase = GetNextWordToMemorizeUseCase(wordRepository, answerRepository, getUserLevelUseCase)
    private val addAnswerUseCase = AddAnswerUseCase(wordRepository, answerRepository)

    private val viewModel = VocabularyViewModel(getNextWordToMemorizeUseCase, addAnswerUseCase)

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
