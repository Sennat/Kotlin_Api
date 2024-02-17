import java.util.*
import kotlin.collections.ArrayList

data class Bible(
    val reference: String,
    val verses: ArrayList<Verses>,
    val text: String,
    val translationId: String,
    val translationName: String,
    val translationNote: String
) {
    override fun toString(): String {
        return "Reference: $reference\n" +
                "Verses: $verses\n" +
                "Verse: $text\n" +
                "Bible Translation Id: ${translationId.uppercase(Locale.getDefault())}\n" +
                "Bible Translation: $translationName\n" +
                "Translation Note: $translationNote"
    }
}
