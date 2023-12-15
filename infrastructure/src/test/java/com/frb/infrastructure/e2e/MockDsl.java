package com.frb.infrastructure.e2e;

import com.frb.domain.Identifier;
import com.frb.domain.purchase.PurchaseID;
import com.frb.infrastructure.configuration.json.Json;
import com.frb.infrastructure.purchase.models.PurchaseRequest;
import com.frb.infrastructure.purchase.models.PurchaseResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface MockDsl {

    MockMvc mvc();

    default PurchaseID givenAPurchase(final String aDescription, final LocalDate aPurchaseDate, final Double aAmount) throws Exception {
        final var aRequestBody = new PurchaseRequest(aDescription, aPurchaseDate, aAmount);
        final var actualId = this.given("/purchases", aRequestBody);
        return PurchaseID.from(actualId);
    }

    default PurchaseResponse retrieveAPurchase(final PurchaseID anId) throws Exception {
        return this.retrieve("/purchases/", anId, PurchaseResponse.class);
    }

    default <A, D> List<D> mapTo(final List<A> actual, final Function<A, D> mapper) {
        return actual.stream()
                .map(mapper)
                .toList();
    }

    private String given(final String url, final Object body) throws Exception {
        final var aRequest = post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(body));

        final var actualId = this.mvc().perform(aRequest)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse().getHeader("Location")
                .replace("%s/".formatted(url), "");

        return actualId;
    }

    private <T> T retrieve(final String url, final Identifier anId, final Class<T> clazz) throws Exception {
        final var aRequest = get(url + anId.getValue())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        final var json = this.mvc().perform(aRequest)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        return Json.readValue(json, clazz);
    }
}
