package com.paxier.moviesinfoservice.integration

import com.paxier.moviesinfoservice.domain.MovieInfo
import com.paxier.moviesinfoservice.repository.MovieInfoRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

import org.springframework.test.context.ActiveProfiles

import spock.lang.Specification

import java.time.LocalDate

@DataMongoTest (excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@ActiveProfiles("test")
class MovieInfoRepositorySpec extends Specification {

    @Autowired
    MovieInfoRepository movieInfoRepository

    def setup() {
        def movieInfos = List.of(
                new MovieInfo(ObjectId.get(), "Batman Begins",2005, List.of("Christine Bale","Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(ObjectId.get(), "The Dark Knight",2008, List.of("Christine Bale","Heath Ledger"), LocalDate.parse("2008-07-18")),
                new MovieInfo(ObjectId.get(), "Dark Knight rises",2012, List.of("Christine Bale","Tom Hardy"), LocalDate.parse("2012-07-20")),
        )

        movieInfoRepository.saveAll(movieInfos).blockLast()
    }

    def cleanup() {
        movieInfoRepository.deleteAll().block( )
    }


    def "Find All from Repo"() {
        when: 'find all is called'
        def movieInfoFlux = movieInfoRepository.findAll().log()

        then: 'count should be 3'
        movieInfoFlux.subscribe(result -> println(result))
    }

    def "Find by id"() {
        when: 'find by id is called'
        def movieInfo = movieInfoRepository.findById("abc").log().block()

        then: 'a movie should be returned'
        movieInfo.name == "Dark Knight rises"

    }

    def 'save movie info'() {
        given: 'a new movie'
        def movieInfo = new MovieInfo(ObjectId.get(), "Batman Begins1",2005, List.of("Christine Bale","Michael Cane"), LocalDate.parse("2005-06-15"))

        when: 'save this movieinfo'
        def savedMovieInfo = movieInfoRepository.save(movieInfo).log().block()

        then: 'movieInfo saved successfully'
        savedMovieInfo.name == "Batman Begins1"
        savedMovieInfo.movieInfoId == "null"

    }
}
