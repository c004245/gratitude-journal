package kr.co.hyunwook.gratitude_journal.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

fun getGratitudeEmojis(): String {
    val emojis = listOf(
        "🍫", "🍭", "❤️", "💐", "🍷", "🍪", "🌹", "🎁",
        "🍰", "🌸", "🌺", "💖", "🌈", "🎉", "💌", "🍀"
    )
    return emojis.random()
}

fun <A, B, C, R> zip(
    flow1: Flow<A>,
    flow2: Flow<B>,
    flow3: Flow<C>,
    transform: suspend (A, B, C) -> R
): Flow<R> {
    return flow1.zip(flow2) { a, b -> Pair(a, b) }
        .zip(flow3) { pair, c ->
            transform(pair.first, pair.second, c)
        }
}
