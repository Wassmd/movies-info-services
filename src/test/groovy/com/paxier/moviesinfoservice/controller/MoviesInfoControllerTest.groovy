package com.paxier.moviesinfoservice.controller

import com.paxier.moviesinfoservice.IntegrationSpec
import com.paxier.moviesinfoservice.domain.MovieInfo
import com.paxier.moviesinfoservice.repository.MovieInfoRepository
import com.paxier.moviesinfoservice.service.MovieInfoService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class MoviesInfoControllerTest extends Specification {
    @Autowired
    MovieInfoRepository movieInfoRepository

    @Autowired
    WebTestClient webTestClient

    def setup() {
        def movieInfos = List.of(
                new MovieInfo("1","Batman Begins",2005, List.of("Christine Bale","Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo("2", "The Dark Knight",2008, List.of("Christine Bale","Heath Ledger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight rises",2012, List.of("Christine Bale","Tom Hardy"), LocalDate.parse("2012-07-20")),
        )

        movieInfoRepository.saveAll(movieInfos).blockLast()
    }

    def cleanup() {
        movieInfoRepository.deleteAll().block( )
    }

    def 'Add movie Info'() {
        given: "A movie Info"
        def movieInfo = new MovieInfo("123", "Batman Begins Test",2005, List.of("Christine Bale","Michael Cane"), LocalDate.parse("2005-06-15"))

        when: "add movie info endpoint is called"
        def webClientResponse = webTestClient
                .post()
                .uri("/v1/movieinfos")
                .bodyValue(movieInfo)
                .exchange()

        then: "the status code of the response is created 201 "
        webClientResponse.expectStatus().isCreated()
        webClientResponse.expectBody(MovieInfo.class)
    }

    def 'Get all movie Infos'() {
        when: "get movie infos endpoint is called"
        def webClientResponse = webTestClient
                .get()
                .uri("/v1/movieinfos")
                .exchange()

        then: "the status code of the response is created 200 "
        webClientResponse.expectStatus().isOk()
        webClientResponse.expectBodyList(MovieInfo.class).hasSize(3 )
    }

    def 'Get  movie Infos by Id'() {
        when: "get movie infos endpoint is called"
        def webClientResponse = webTestClient
                .get()
                .uri("/v1/movieinfos/abc")
                .exchange()

        then: "the status code of the response is created 200 "
        webClientResponse.expectStatus().isOk()
        webClientResponse.expectBody().jsonPath('$.name').isEqualTo("Dark Knight rises1")
    }
}
