package com.paxier.moviesinfoservice.repository

import com.paxier.moviesinfoservice.domain.MovieInfo
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MovieInfoRepository: ReactiveMongoRepository<MovieInfo, String >  {
}