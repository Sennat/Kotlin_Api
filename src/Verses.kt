data class Verses(
    val bookId: String,
    val bookName: String,
    val chapter: Int,
    val verse: Int,
    val text: String
) {
    override fun toString(): String {
        return "Book Id: $bookId\n" +
                "Book: $bookName\n" +
                "Chapter: $chapter\n" +
                "Verse: $verse\n" +
                "Text: $text"
    }
}
