package com.renaudfavier.learnbasque.core.domain.model

import com.renaudfavier.learnbasque.core.model.data.Knowledge

/**
 * degreeOfMastering: 0 is unknown, 1 is perfectly mastered, null is undetermined
 */
data class KnowledgeWithMastering(
    val knowledge: Knowledge,
    val mastering: Float?,
)
