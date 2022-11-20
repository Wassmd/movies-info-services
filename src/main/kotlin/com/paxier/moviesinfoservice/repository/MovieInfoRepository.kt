package com.paxier.moviesinfoservice.repository

import com.paxier.moviesinfoservice.domain.MovieInfo
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface  MovieInfoRepository: ReactiveMongoRepository<MovieInfo, String >  {
    fun findByYear(year: Int): Flux<MovieInfo>
}