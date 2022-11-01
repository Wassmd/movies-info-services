package com.paxier.moviesinfoservice.unit

import com.paxier.moviesinfoservice.IntegrationSpec
import com.paxier.moviesinfoservice.controller.FluxAndMonoController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource


@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class FluxAndMonoControllerSpec extends IntegrationSpec {
    @Autowired (required = false)
    private FluxAndMonoController fluxAndMonoController

    def "when context is loaded then all expected beans are created"() {
        expect: "the fluxAndMonoController created"
        fluxAndMonoController
    }

    def 'mono endpoint return Hello World'() {
        when: 'the gateway is called'
        def responseSpec = webTestClient
                .get()
                .uri("/mono")
                .exchange()

        then: 'a bad request status code is returned in the response'
        responseSpec.expectStatus().isOk()
        responseSpec.expectBody(String.class).consumeWith(stringEntityExchangeResult -> {
            var responseBody = stringEntityExchangeResult.getResponseBody()
            assert responseBody == "Hello World"
        })
    }

    def 'flux endpoint return 3 elements'() {
        when: 'the gateway is called'
        def responseSpec = webTestClient
                .get()
                .uri("/flux")
                .exchange()

        then: 'a bad request status code is returned in the response'
        responseSpec.expectStatus().isOk()
        responseSpec.expectBodyList(Integer.class).hasSize(3)
    }
}
