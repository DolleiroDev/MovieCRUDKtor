package com.example.consumers

import com.example.models.Movie
import com.example.services.StockService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

class StockConsumer(private val stockService: StockService,
                    private val ioDispatcher: CoroutineDispatcher
) {
    private val consumer: KafkaConsumer<String, String>

    init {
        val props = Properties()
        props["bootstrap.servers"] = "localhost:9092"
        props["group.id"] = "movie-consumer-group"
        props["key.deserializer"] = StringDeserializer::class.java
        props["value.deserializer"] = StringDeserializer::class.java
        consumer = KafkaConsumer(props)
    }

    fun start() {
        consumer.subscribe(listOf("movie-topic"))

        GlobalScope.launch(ioDispatcher) {
            while (true) {
                val records = consumer.poll(Duration.ofSeconds(10))

                records.iterator().forEach {
                    val movie = Gson().fromJson(it.value(), Movie::class.java)
                    stockService.create(movie)
                }
            }
        }
    }
}