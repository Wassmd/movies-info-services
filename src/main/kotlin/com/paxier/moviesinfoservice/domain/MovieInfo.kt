package com.paxier.moviesinfoservice.domain

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
data class MovieInfo(
    @Id val id: String,
    @field:NotBlank(message = "Movie name should be valid ") var name: String,
    @field:Positive(message = "Movie year must be positive number ") var year: Int,
    var cast: List<String>,
    var releaseDate: LocalDate
)