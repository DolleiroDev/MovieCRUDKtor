import com.example.models.Movie
import com.google.gson.Gson
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class MovieCreatedProducer {
    private val producer: KafkaProducer<String, String>

    init {
        val props = Properties()
        props["bootstrap.servers"] = "localhost:9092"
        props["key.serializer"] = StringSerializer::class.java
        props["value.serializer"] = StringSerializer::class.java
        producer = KafkaProducer<String, String>(props)
    }

    fun sendMovie(movie: Movie) {
        val topic = "movie-topic"
        val movieJson = Gson().toJson(movie)
        val record = ProducerRecord<String, String>(topic, null, movieJson)
        producer.send(record)
    }
}