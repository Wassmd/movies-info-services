package com.paxier.moviesinfoservice.domain

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
data class MovieInfo(
    @Id val id: String,
    var name: String,
    var year: Int,
    var cast: List<String>,
    var releaseDate: LocalDate
)