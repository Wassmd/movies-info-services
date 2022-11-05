package com.paxier.moviesinfoservice.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
class MovieInfo(
    @Id
    val id: ObjectId = ObjectId.get(),
    val name: String,
    val year: Int,
    val cast: List<String>,
    val releaseDate: LocalDate
)