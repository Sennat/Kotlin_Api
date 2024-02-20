import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.util.*

class ApiCall {
    private var input: String
    private var randVerse = "?random=verse"

    init {
        println("\n==== BIBLE VERSE SEARCH ====")
        println("An Api call to demonstrate for searching a bible verse")
        println("--------------------------------------------------------")

        print("Enter a verse Ex. (John 3:16 OR John 3:1-16) OR enter for random verse:\t")
        input = readln()
        if (input.isNotBlank()) {
            input.removeWhitespaces().also { it.lowercase(Locale.getDefault()) }
            //println(input)
        } else {
            input = randVerse
        }

    }

    fun requestData() {
            val url: URL = URI.create(apiUrl.plus(input)).toURL()
            val connection = url.openConnection() as HttpURLConnection

            //get request method
            connection.requestMethod = RESTApiMethods.GET.toString()
            connection.setRequestProperty("Accept", "application/json")

            //Get response code
            val responseCode = connection.responseCode

            try {

                //Read and print response data
                handleResponse(responseCode, connection)

            } catch (e: Exception) {
                println("Error: ${e.message.toString()}")
            } finally {

            }

    }

    private fun handleResponse(responseCode: Int, connection: HttpURLConnection) {
        when (responseCode) {
            HttpURLConnection.HTTP_OK -> {
                //println(connection.contentType)

                //Read response data
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val res = StringBuilder()
                reader.useLines { lines ->
                    lines.forEach { res.append(it) }
                }

                reader.close()

                val data = ResultState.SUCCESS(res).response
                val jsonObject: JsonObject = JsonParser.parseString(data.toString()).asJsonObject
                val bible = Gson().fromJson(jsonObject, Bible::class.java)

                println("")
                println("   ~~ VERSE OF THE DAY ~~  ")
                println("*-----------------------------*")

                bible.verses.forEach {
                    println(
                        "${
                            it.text.replace(
                                "\n",
                                ""
                            )
                        } ~~${it.bookName} ${it.chapter}:${it.verse}"
                    )
                }
                println("Translation: ${bible.translationName}")
                //println("Response status code: ${HttpURLConnection.HTTP_OK}")

            }

            HttpURLConnection.HTTP_MULT_CHOICE -> println("The request has more than one possible response. Response code: ${HttpURLConnection.HTTP_MULT_CHOICE}")
            HttpURLConnection.HTTP_BAD_REQUEST -> println("Bad Request. The request could not be understood by the server due to incorrect syntax. Response code: ${HttpURLConnection.HTTP_BAD_REQUEST}")
            HttpURLConnection.HTTP_UNAUTHORIZED -> println("The request requires user authentication information. Response code: ${HttpURLConnection.HTTP_UNAUTHORIZED}")
            HttpURLConnection.HTTP_NOT_FOUND -> println("The server can not find the requested resource. Response code: ${HttpURLConnection.HTTP_NOT_FOUND}")

        }
    }

    private fun String.removeWhitespaces() = replace("\\s+".toRegex(), "+")
}