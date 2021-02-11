package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ezmobivietnam on 2021-02-07.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CountryControllerITTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. There is no parameters are used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 109 countries in json format
     * 2. The "self" link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenNoParamsUsed_thenReturnAllCountriesWithSelfLink() throws Exception {
        mockMvc.perform(get(CountryController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.countryDtoList", hasSize(109)))
                .andExpect(jsonPath("$._links.self.href", containsStringIgnoringCase("/api/countries")));
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. Parameter {ids=1,2,3,4,5} is used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 05 countries in json format
     * 2. The "self" link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenIdsParamsUsed_thenReturnIdentifiedCountriesWithSelfLink() throws Exception {
        String url = CountryController.BASE_URL + "?ids=1,2,3,4,5";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.countryDtoList", hasSize(5)))
                .andExpect(jsonPath("$._links.self.href", containsStringIgnoringCase("/api/countries?ids=1&ids=2&ids=3&ids=4&ids=5")));
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. Parameter {name=united} is used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 03 countries (United Arab Emirates, United Kingdom and United States) in json format
     * 2. The "self" link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenNameParamUsed_thenReturnMatchedCountriesWithSelfLink() throws Exception {
        String url = CountryController.BASE_URL + "?name=united";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.countryDtoList", hasSize(3)))
                .andExpect(jsonPath("$._embedded.countryDtoList[0].name", is("United Arab Emirates")))
                .andExpect(jsonPath("$._embedded.countryDtoList[1].name", is("United Kingdom")))
                .andExpect(jsonPath("$._embedded.countryDtoList[2].name", is("United States")))
                .andExpect(jsonPath("$._links.self.href", containsStringIgnoringCase("/api/countries?name=united")))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. Parameters {page=1&size=5} is used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 05 countries of the page 01 in json format
     * 2. The pagination links ("first, "prev", "self", "next, "last") link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenPageAndSizeParamsUsed_thenReturnAllCountriesWithPaginationLinks() throws Exception {
        String url = CountryController.BASE_URL + "?page=1&size=5";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.countryDtoList", hasSize(5)))
                .andExpect(jsonPath("$._links.first.href", containsStringIgnoringCase("/api/countries?page=0&size=5")))
                .andExpect(jsonPath("$._links.prev.href", containsStringIgnoringCase("/api/countries?page=0&size=5")))
                .andExpect(jsonPath("$._links.self.href", containsStringIgnoringCase("/api/countries?page=1&size=5")))
                .andExpect(jsonPath("$._links.next.href", containsStringIgnoringCase("/api/countries?page=2&size=5")))
                .andExpect(jsonPath("$._links.last.href", containsStringIgnoringCase("/api/countries?page=21&size=5")))
                .andExpect(jsonPath("$.page.number", is(1)))
                .andExpect(jsonPath("$.page.size", is(5)))
                .andExpect(jsonPath("$.page.totalElements", is(109)))
                .andExpect(jsonPath("$.page.totalPages", is(22)))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. Parameters {page=1} is used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 20 (default) countries of the page 01 in json format
     * 2. The pagination links ("first, "prev", "self", "next, "last") link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenPageParamUsed_thenReturnAllCountriesWithDefaultSizeAndPaginationLinks() throws Exception {
        String url = CountryController.BASE_URL + "?page=1";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.countryDtoList", hasSize(20)))
                .andExpect(jsonPath("$._links.first.href", containsStringIgnoringCase("/api/countries?page=0&size=20")))
                .andExpect(jsonPath("$._links.prev.href", containsStringIgnoringCase("/api/countries?page=0&size=20")))
                .andExpect(jsonPath("$._links.self.href", containsStringIgnoringCase("/api/countries?page=1&size=20")))
                .andExpect(jsonPath("$._links.next.href", containsStringIgnoringCase("/api/countries?page=2&size=20")))
                .andExpect(jsonPath("$._links.last.href", containsStringIgnoringCase("/api/countries?page=5&size=20")))
                .andExpect(jsonPath("$.page.number", is(1)))
                .andExpect(jsonPath("$.page.size", is(20)))
                .andExpect(jsonPath("$.page.totalElements", is(109)))
                .andExpect(jsonPath("$.page.totalPages", is(6)))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. Parameters {page=1} is used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 20 (default) countries of the page 01 in json format
     * 2. The pagination links ("first, "self", "next, "last") link is added to the response
     * 3. Note: the link "prev" is not existed
     *
     * @throws Exception
     */
    @Test
    void findList_givenSizeParamUsed_thenReturnAllCountriesWithDefaultPageAndPaginationLinks() throws Exception {
        String url = CountryController.BASE_URL + "?size=5";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.countryDtoList", hasSize(5)))
                .andExpect(jsonPath("$._links.first.href", containsStringIgnoringCase("/api/countries?page=0&size=5")))
                .andExpect(jsonPath("$._links.self.href", containsStringIgnoringCase("/api/countries?page=0&size=5")))
                .andExpect(jsonPath("$._links.next.href", containsStringIgnoringCase("/api/countries?page=1&size=5")))
                .andExpect(jsonPath("$._links.last.href", containsStringIgnoringCase("/api/countries?page=21&size=5")))
                .andExpect(jsonPath("$.page.number", is(0)))
                .andExpect(jsonPath("$.page.size", is(5)))
                .andExpect(jsonPath("$.page.totalElements", is(109)))
                .andExpect(jsonPath("$.page.totalPages", is(22)))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. Two parameters {ids=101,102} and {name=united} are used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 02 countries (United Arab Emirates and United Kingdom) in json format
     * 2. The "self" link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenIdsParamAndNameParamUsed_thenReturnMatchedCountriesWithSelfLink() throws Exception {
        String url = CountryController.BASE_URL + "?ids=101,102&name=united";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.countryDtoList", hasSize(2)))
                .andExpect(jsonPath("$._links.self.href", containsStringIgnoringCase("/api/countries?ids=101&ids=102&name=united")));
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. ALL parameters {ids=101,102,103}, {name=united}, {page=0} and {size=2} are used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 02 countries (United Arab Emirates and United Kingdom) of the page 0 in json
     * format
     * 2. The pagination links ("first, "self", "next, "last") link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenAllParamsUsed_thenReturnMatchedCountriesWithPaginationLinks() throws Exception {
        String url = CountryController.BASE_URL + "?ids=101,102,103&name=united&page=0&size=2";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.countryDtoList", hasSize(2)))
                .andExpect(jsonPath("$._links.first.href", containsStringIgnoringCase("/api/countries?ids=101,102,103&name=united&page=0&size=2")))
                .andExpect(jsonPath("$._links.self.href", containsStringIgnoringCase("/api/countries?ids=101,102,103&name=united&page=0&size=2")))
                .andExpect(jsonPath("$._links.next.href", containsStringIgnoringCase("/api/countries?ids=101,102,103&name=united&page=1&size=2")))
                .andExpect(jsonPath("$._links.last.href", containsStringIgnoringCase("/api/countries?ids=101,102,103&name=united&page=1&size=2")))
                .andExpect(jsonPath("$.page.number", is(0)))
                .andExpect(jsonPath("$.page.size", is(2)))
                .andExpect(jsonPath("$.page.totalElements", is(3)))
                .andExpect(jsonPath("$.page.totalPages", is(2)))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/105} is used.
     * 2. Country with id 105 existed in DB
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the requested info in json format
     * 2. The "self" link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findById() throws Exception {
        String findByIdlUrl = CountryController.BASE_URL + "/105";
        mockMvc.perform(get(findByIdlUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(105)))
                .andExpect(jsonPath("$.name", is("Vietnam")))
                .andExpect(jsonPath("$.lastUpdate", notNullValue()))
                .andExpect(jsonPath("$._links.self.href", containsStringIgnoringCase("/api/countries/105")));
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/1000} is used.
     * 2. Country with id 1000 does NOT existed in DB
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive NO content with status code 204
     *
     * @throws Exception
     */
    @Test
    void findById_givenNotExistedCountryId_thenReturnNoContentWithStatus204() throws Exception {
        // given
        String findByIdlUrl = CountryController.BASE_URL + "/1000";
        //when
        mockMvc.perform(get(findByIdlUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/AAA} is used.
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the error code 400 (Bad request)
     *
     * @throws Exception
     */
    @Test
    void findById_givenInvalidCountryId_thenReturnErrorCode400() throws Exception {
        // given
        String findByIdlUrl = CountryController.BASE_URL + "/AAA";

        //when
        mockMvc.perform(get(findByIdlUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. The Json string {"name": "NEW Country"} is set to the request body
     * <p>
     * When:
     * 1. The endpoint is called by using the POST method
     * <p>
     * Then expect:
     * 1. Client will receive the status code 201 (Created)
     * 2. The URL of the newly created country will be added to the Location response header
     *
     * @throws Exception
     */
    @Test
//    @DirtiesContext
    @Rollback(true)
    void addNew_givenValidCountryDtoInJsonFormat_thenReturnStatus201WithLocationHeaderFieldURL() throws Exception {
        String postURL = CountryController.BASE_URL;
        CountryDto newCountry = CountryDto.builder().name("NEW country").build();
        mockMvc.perform(
                post(postURL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newCountry)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string(HttpHeaders.LOCATION, containsString(CountryController.BASE_URL + "/110")))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. The Json string {"id": 110, "name": "NEW Country"} is set to the request body
     * <p>
     * When:
     * 1. The endpoint is called by using the POST method
     * <p>
     * Then expect:
     * 1. Client will receive the status code 400 (Bad request)
     * 2. The error message {"id": "must be null"} is returned
     *
     * @throws Exception
     */
    @Test
    void addNew_givenCountryDtoContainsId_thenReturnStatus400() throws Exception {
        String postURL = CountryController.BASE_URL;
        CountryDto newCountry = CountryDto.builder().id(110).name("NEW country").build();
        mockMvc.perform(
                post(postURL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newCountry)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id", is("must be null")))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. The Json string {"name": ""} is set to the request body
     * <p>
     * When:
     * 1. The endpoint is called by using the POST method
     * <p>
     * Then expect:
     * 1. Client will receive the status code 400 (Bad request)
     * 2. The error message {"name": "size must be between 1 and 50"} is returned
     *
     * @throws Exception
     */
    @Test
    void addNew_givenCountryDtoContainsEmptyName_thenReturnStatus400() throws Exception {
        String postURL = CountryController.BASE_URL;
        CountryDto newCountry = CountryDto.builder().name("").build();
        mockMvc.perform(
                post(postURL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newCountry)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("size must be between 1 and 50")))
        ;
    }
}
