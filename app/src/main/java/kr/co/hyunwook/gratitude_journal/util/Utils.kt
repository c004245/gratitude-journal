package kr.co.hyunwook.gratitude_journal.util

fun getGratitudeEmojis(): String {
    val emojis = listOf(
        "🙏", "😊", "❤️", "💐", "🫶", "🤝", "👏", "🎁",
        "🤗", "🌸", "✨", "💖", "🌟", "🎉", "💌", "🍀"
    )
    return emojis.random()
}