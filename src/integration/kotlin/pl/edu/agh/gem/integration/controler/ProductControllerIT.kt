package pl.edu.agh.gem.integration.controler

import pl.edu.agh.gem.integration.BaseIntegrationSpec
import pl.edu.agh.gem.integration.ability.ServiceTestClient
import pl.edu.agh.gem.integration.ability.stubExample

class ProductControllerIT(
    private val service: ServiceTestClient
) : BaseIntegrationSpec({
    should("return 200 OK for sample endpoint") {
        // given
        stubExample()

        // when
        val result = service.fetchFoo(FooExample(value = id))

        // then
        result shouldHaveHttpStatus HttpStatus.OK
        result shouldHaveBody FooDto("123")
    }

    should("process Hermes event") {
        // given
        val event = SampleAvroEvent.newBuilder()
            .setValue("xyz")
            .build()

        // when
        val response = service.notifySampleAvroEvent(event)

        // then
        response shouldHaveHttpStatus HttpStatus.OK
    }

    include(
        ResilienceTestFactory.retryTests(
            name = "/bar/{id}",
            maxFailures = 2,
            expectedSuccessResult = FooDto("123"),
            expectedFailResult = FooDto(""),
            failAttempt = { currentState, nextState ->
                ProjectConfig.wiremock.stubFor(
                    get("/bar/10")
                        .inScenario("Retry")
                        .whenScenarioStateIs(currentState)
                        .willReturn(serverError())
                        .willSetStateTo(nextState)
                )
            },
            successAttempt = { currentState ->
                ProjectConfig.wiremock.stubFor(
                    get("/bar/10")
                        .inScenario("Retry")
                        .whenScenarioStateIs(currentState)
                        .willReturn(
                            ok()
                                .withHeader(
                                    HttpHeaders.CONTENT_TYPE,
                                    InternalApiMediaType.APPLICATION_JSON_INTERNAL_VER_1
                                )
                                .withBody("{\"id\": \"10\", \"value\": \"123\"}")
                        )
                )
            },
            attemptVerification = { getRequestedFor(urlEqualTo("/bar/10")) },
            invocation = { service.fetchFoo(FooExample(value = "10")) }
        )
    )
})
