package com.paxier.moviesinfoservice.controller

import com.paxier.moviesinfoservice.domain.MovieInfo
import com.paxier.moviesinfoservice.service.MovieInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1")
class MoviesInfoController(
    private val movieInfoService: MovieInfoService
    ){

    @GetMapping("/movieinfos")
    fun getAllMoviesInfos(): Flux<MovieInfo> {
        return movieInfoService.getAllMoviesInfos()
    }

    @GetMapping("/movieinfos/{id}")
    fun getMoviesInfoById(@PathVariable id: String): Mono<MovieInfo> {
        return movieInfoService.getMovieInfoById(id)
    }

    @PostMapping("/movieinfos")
    @ResponseStatus(HttpStatus.CREATED)
    fun addMovieInfo(@RequestBody movieInfo: MovieInfo): Mono<MovieInfo> {
        return movieInfoService.addMovieInfo(movieInfo).log()
    }
}