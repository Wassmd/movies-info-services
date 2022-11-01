package com.paxier.moviesinfoservice.integration

import com.paxier.moviesinfoservice.IntegrationSpec
import com.paxier.moviesinfoservice.domain.MovieInfo
import com.paxier.moviesinfoservice.repository.MovieInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import java.time.LocalDate

@DataMongoTest (excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@ActiveProfiles("test")
class MovieInfoRepositorySpec extends Specification {

    @Autowired
    MovieInfoRepository movieInfoRepository

    def setup() {
        def movieInfos = List.of(
                new MovieInfo("null", "Batman Begins",2005, List.of("Christine Bale","Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo("null", "The Dark Knight",2008, List.of("Christine Bale","Heath Ledger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight rises",2012, List.of("Christine Bale","Tom Hardy"), LocalDate.parse("2012-07-20")),
        )

        movieInfoRepository.saveAll(movieInfos).blockLast()
    }

    def cleanup() {
        movieInfoRepository.deleteAll().block( )
    }


    def "Find All from Repo"() {
        when: 'find all is called'
        def movieInfoFlux = movieInfoRepository.findAll()

        then: 'count should be 3'
        verifyAll {
            movieInfoFlux.hasElements()
        }
    }
}
