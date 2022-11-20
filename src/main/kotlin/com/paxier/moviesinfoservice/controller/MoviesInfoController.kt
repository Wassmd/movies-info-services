package com.paxier.moviesinfoservice.controller

import com.paxier.moviesinfoservice.domain.MovieInfo
import com.paxier.moviesinfoservice.service.MovieInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid
import kotlin.math.log

@RestController
@RequestMapping("/v1")
class MoviesInfoController(
    private val movieInfoService: MovieInfoService
    ){

    @GetMapping("/movieinfos")
    fun getAllMoviesInfos(@RequestParam(value = "year", required = false) year: Int?): Flux<MovieInfo> {
        if (year != null) {
            return movieInfoService.getMovieInfosByYear(year)
        }
        return movieInfoService.getAllMoviesInfos()
    }

    @GetMapping("/movieinfos/{id}")
    fun getMoviesInfoById(@PathVariable id: String): Mono<ResponseEntity<MovieInfo>> {
        return movieInfoService.getMovieInfoById(id)
            .map(ResponseEntity.ok()::body)
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
            .log()
    }

    @PostMapping("/movieinfos")
    @ResponseStatus(HttpStatus.CREATED)
    fun addMovieInfo(@RequestBody @Valid movieInfo: MovieInfo): Mono<MovieInfo> {
        return movieInfoService.addMovieInfo(movieInfo).log()
    }

    @PutMapping("/movieinfos/{id}")
    fun addMovieInfo(@RequestBody movieInfo: MovieInfo, @PathVariable id: String): Mono<ResponseEntity<MovieInfo>> {
        return movieInfoService.updateMovieInfo(movieInfo, id)
            .map (ResponseEntity.ok()::body)
    }

    @DeleteMapping("/movieinfos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMovieInfo(@PathVariable id: String): Mono<Void> {
        return movieInfoService.deleteMovieInfo(id)
    }
}