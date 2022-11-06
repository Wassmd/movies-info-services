package com.paxier.moviesinfoservice.service

import com.paxier.moviesinfoservice.domain.MovieInfo
import com.paxier.moviesinfoservice.repository.MovieInfoRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class MovieInfoService(
    private val movieInfoRepository: MovieInfoRepository
    ) {

    fun addMovieInfo(movieInfo: MovieInfo): Mono<MovieInfo> {
        return movieInfoRepository.save(movieInfo)
    }

    fun getMovieInfoById(id:String): Mono<MovieInfo> {
        return movieInfoRepository.findById(id)
    }

    fun getAllMoviesInfos(): Flux<MovieInfo> {
        return movieInfoRepository.findAll()
    }
}
