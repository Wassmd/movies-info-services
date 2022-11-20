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

    fun updateMovieInfo(updateMovieInfo: MovieInfo, id: String): Mono<MovieInfo> {
        return movieInfoRepository.findById(id)
            .flatMap { movieInfo ->
                movieInfo.cast = updateMovieInfo.cast
                movieInfo.name = updateMovieInfo.name
                movieInfo.year = updateMovieInfo.year

                 movieInfoRepository.save(movieInfo)
            }
    }

    fun deleteMovieInfo(id: String): Mono<Void> {
        return movieInfoRepository.findById(id)
            .flatMap { movieInfo ->
                movieInfoRepository.delete(movieInfo)
            }
    }

    fun getMovieInfosByYear(year: Int): Flux<MovieInfo> {
        return movieInfoRepository.findByYear(year)
    }
}
