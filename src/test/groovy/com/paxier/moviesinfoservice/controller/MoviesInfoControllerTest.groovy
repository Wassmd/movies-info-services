package com.paxier.moviesinfoservice.controller

import com.paxier.moviesinfoservice.domain.MovieInfo
import com.paxier.moviesinfoservice.repository.MovieInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "360000")
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
        def movieInfo = new MovieInfo("123", "" ,-2005, List.of("Christine Bale","Michael Cane"), LocalDate.parse("2005-06-15"))

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

    def 'Update movie Info'() {
        given: "A movie Info"
        def movieInfo = new MovieInfo("123", "Wasim the coder",1984, List.of("Christine Bale","Michael Cane"), LocalDate.parse("2005-06-15"))

        when: "add movie info endpoint is called"
        def webClientResponse = webTestClient
                .put()
                .uri("/v1/movieinfos/abc")
                .bodyValue(movieInfo)
                .exchange()

        then: "the status code of the response is 200 "
        webClientResponse.expectStatus().isOk()
        webClientResponse.expectBody().jsonPath('$.name').isEqualTo("Wasim the coder")
    }

    def 'Delete a movie Info'() {
        when: "movie info endpoint is called"
        def webClientResponse = webTestClient
                .delete()
                .uri("/v1/movieinfos/abc")
                .exchange()

        then: "the status code of the response is 204 NO_CONTENT"
        webClientResponse.expectStatus().isNoContent()

        then: "get movie infos endpoint is called"
        def webClientResponse1 = webTestClient
                .get()
                .uri("/v1/movieinfos")
                .exchange()

        then: "the status code of the response is 200"
        webClientResponse1.expectStatus().isOk()
        webClientResponse1.expectBodyList(MovieInfo.class).hasSize(2)
    }

    def 'Movie Info Id not found'() {
        when: "get movie infos endpoint is called"
        def webClientResponse = webTestClient
                .get()
                .uri("/v1/movieinfos/blahblah")
                .exchange()

        then: "the status code of the response is created 200 "
        webClientResponse.expectStatus().isNotFound()
    }

    def 'Find movie info by year'() {
        when: "Movie by year"
        def movieInfoFlux = movieInfoRepository.findByYear(2005)

        then: "a movie should be returned"
        movieInfoFlux.subscribe(result -> println(result))
    }

    def 'Get movies info by year'() {
        when: "get movie infos endpoint is called"
        def webClientResponse = webTestClient
                .get()
                .uri("/v1/movieinfos?year=2005")
                .exchange()

        then: "Movie info is found "
        webClientResponse.expectBodyList(MovieInfo.class).hasSize(1 )
    }
}
