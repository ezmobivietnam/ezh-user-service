package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.BaseControllerTest;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ezmobivietnam on 2021-02-20.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CityControllerITTest extends BaseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities} is used.
     * 2. The country with id 192 is existed and has cities data
     * 3. There is no parameters are used
     * <p>
     * When: The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 63 cities in json format
     * 2. The "self" link is added to the response
     */
    @Test
    void findList_givenNoParamsUsed_thenReturnAllCitiesWithSelfLink() throws Exception {
        String url = "/api/countries/192/cities";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.cityDtoList", hasSize(63)))
                .andExpect(jsonPath("$._links.self.href",
                        containsStringIgnoringCase("/api/countries/192/cities")));
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities} is used.
     * 2. The country with id 192 is existed and has cities data
     * 3. Parameters {page=1&size=20} is used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 20 cities of the page 01 in json format
     * 2. The pagination links ("first, "prev", "self", "next, "last") link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenPageAndSizeParamsUsed_thenReturnAllCitiesWithPaginationLinks() throws Exception {
        String url = "/api/countries/192/cities?page=1&size=20";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.cityDtoList", hasSize(20)))
                .andExpect(jsonPath("$._links.first.href",
                        containsStringIgnoringCase("/api/countries/192/cities?page=0&size=20")))
                .andExpect(jsonPath("$._links.prev.href",
                        containsStringIgnoringCase("/api/countries/192/cities?page=0&size=20")))
                .andExpect(jsonPath("$._links.self.href",
                        containsStringIgnoringCase("/api/countries/192/cities?page=1&size=20")))
                .andExpect(jsonPath("$._links.next.href",
                        containsStringIgnoringCase("/api/countries/192/cities?page=2&size=20")))
                .andExpect(jsonPath("$._links.last.href",
                        containsStringIgnoringCase("/api/countries/192/cities?page=3&size=20")))
                .andExpect(jsonPath("$.page.number", is(1)))
                .andExpect(jsonPath("$.page.size", is(20)))
                .andExpect(jsonPath("$.page.totalElements", is(63)))
                .andExpect(jsonPath("$.page.totalPages", is(4)))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities} is used.
     * 2. Parameter {ids=1,2,3,4,5} is used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 05 cities in json format
     * 2. The "self" link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenIdsParamsUsed_thenReturnIdentifiedCitiesWithSelfLink() throws Exception {
        String url = "/api/countries/192/cities?ids=1,2,3,4,5";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.cityDtoList", hasSize(5)))
                .andExpect(jsonPath("$._links.self.href",
                        containsStringIgnoringCase("/api/countries/192/cities?ids=1&ids=2&ids=3&ids=4&ids=5")));
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities} is used.
     * 2. Parameter {name=Hà} is used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 04 cities (Hà Giang, Hà Nam, Hà Nội and Hà Tĩnh) in json format
     * 2. The "self" link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenNameParamUsed_thenReturnMatchedCitiesWithSelfLink() throws Exception {
        String url = "/api/countries/192/cities?name=Hà";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.cityDtoList", hasSize(4)))
                .andExpect(jsonPath("$._embedded.cityDtoList[0].id", is(22)))
                .andExpect(jsonPath("$._embedded.cityDtoList[0].name", is("Hà Giang")))
                .andExpect(jsonPath("$._embedded.cityDtoList[1].id", is(23)))
                .andExpect(jsonPath("$._embedded.cityDtoList[1].name", is("Hà Nam")))
                .andExpect(jsonPath("$._embedded.cityDtoList[2].id", is(24)))
                .andExpect(jsonPath("$._embedded.cityDtoList[2].name", is("Hà Nội")))
                .andExpect(jsonPath("$._embedded.cityDtoList[3].id", is(25)))
                .andExpect(jsonPath("$._embedded.cityDtoList[3].name", is("Hà Tĩnh")))
                .andExpect(jsonPath("$._links.self.href",
                        containsStringIgnoringCase("/api/countries/192/cities?name=H%C3%A0")))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities} is used.
     * 2. Two parameters {ids=23,24} and {name=Hà} are used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 02 cities (Hà Nam and Hà Nội) in json format
     * 2. The "self" link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenIdsParamAndNameParamUsed_thenReturnMatchedCitiesWithSelfLink() throws Exception {
        String url = "/api/countries/192/cities?ids=23,24&name=Hà";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.cityDtoList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.cityDtoList[0].id", is(23)))
                .andExpect(jsonPath("$._embedded.cityDtoList[0].name", is("Hà Nam")))
                .andExpect(jsonPath("$._embedded.cityDtoList[1].id", is(24)))
                .andExpect(jsonPath("$._embedded.cityDtoList[1].name", is("Hà Nội")))
                .andExpect(jsonPath("$._links.self.href",
                        containsStringIgnoringCase("/api/countries/192/cities?ids=23&ids=24&name=H%C3%A0")));
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities} is used.
     * 2. ALL parameters {ids=22,23,24,25}, {name=Hà}, {page=0} and {size=2} are used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 02 cities (Hà Giang and 	Hà Nam) of the page 0 in json
     * format
     * 2. The pagination links ("first, "self", "next, "last") link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenAllParamsUsed_thenReturnMatchedCitiesWithPaginationLinks() throws Exception {
        String url = "/api/countries/192/cities?ids=22,23,24,25&name=Hà&page=0&size=2";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.cityDtoList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.cityDtoList[0].id", is(22)))
                .andExpect(jsonPath("$._embedded.cityDtoList[0].name", is("Hà Giang")))
                .andExpect(jsonPath("$._embedded.cityDtoList[1].id", is(23)))
                .andExpect(jsonPath("$._embedded.cityDtoList[1].name", is("Hà Nam")))
                .andExpect(jsonPath("$._links.first.href",
                        containsStringIgnoringCase("/api/countries/192/cities?ids=22,23,24,25&name=H%C3%A0&page=0&size=2")))
                .andExpect(jsonPath("$._links.self.href",
                        containsStringIgnoringCase("/api/countries/192/cities?ids=22,23,24,25&name=H%C3%A0&page=0&size=2")))
                .andExpect(jsonPath("$._links.next.href",
                        containsStringIgnoringCase("/api/countries/192/cities?ids=22,23,24,25&name=H%C3%A0&page=1&size=2")))
                .andExpect(jsonPath("$._links.last.href",
                        containsStringIgnoringCase("/api/countries/192/cities?ids=22,23,24,25&name=H%C3%A0&page=1&size=2")))
                .andExpect(jsonPath("$.page.number", is(0)))
                .andExpect(jsonPath("$.page.size", is(2)))
                .andExpect(jsonPath("$.page.totalElements", is(4)))
                .andExpect(jsonPath("$.page.totalPages", is(2)))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities} is used.
     * 2. Country with id 192 (Vietnam) existed in DB
     * 3. City with id 58 (TP Hồ Chí Minh) existed in DB
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the requested info in json format
     * 2. The "self" link is added to the response
     * 2. The "country" link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findById() throws Exception {
        String findByIdlUrl = "/api/countries/192/cities/58";
        mockMvc.perform(get(findByIdlUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(58)))
                .andExpect(jsonPath("$.name", is("TP Hồ Chí Minh")))
                .andExpect(jsonPath("$.capital", notNullValue()))
                .andExpect(jsonPath("$.lastUpdate", notNullValue()))
                .andExpect(jsonPath("$.creationDate", notNullValue()))
                .andExpect(jsonPath("$._links.self.href",
                        containsStringIgnoringCase("/api/countries/192/cities/58")))
                .andExpect(jsonPath("$._links.country.href",
                        containsStringIgnoringCase("/api/countries/192")))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities/1000} is used.
     * 2. Country with id 192 (Vietnam) existed in DB
     * 3. City with id 1000 does NOT exist in DB
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
    void findById_givenNotExistedCityId_thenReturnStatus204() throws Exception {
        // given
        String findByIdlUrl = "/api/countries/192/cities/1000";
        //when
        mockMvc.perform(get(findByIdlUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities/AAA} is used.
     * 2. Country with id 192 (Vietnam) existed in DB
     * 2. City with id AAA is INVALID
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
        String findByIdlUrl = "/api/countries/192/cities/AAA";

        //when
        mockMvc.perform(get(findByIdlUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities} is used.
     * 2. The Json string {"name": "NEW City", "capital": false} is set to the request body
     * <p>
     * When: The endpoint is called by using the POST method
     * <p>
     * Then expect:
     * 1. Client will receive the status code 201 (Created)
     * 2. The URL of the newly created city will be set to the Location header field
     *
     * @throws Exception
     */
    @Test
    @Rollback
    void addNew_givenValidCityDtoInJsonFormat_thenReturnStatus201WithLocationHeaderFieldURL() throws Exception {
        String postURL = "/api/countries/192/cities";
        CityDto newCity = CityDto.builder().name("NEW City").capital(false).build();
        mockMvc.perform(
                post(postURL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newCity)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string(HttpHeaders.LOCATION, containsString("/api/countries/192/cities/64")))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities} is used.
     * 2. The Json string {"id": 64, "name": "NEW City", "capital": false} is set to the request body
     * <p>
     * When: The endpoint is called by using the POST method
     * <p>
     * Then expect:
     * 1. Client will receive the status code 400 (Bad request)
     * 2. The error message {"id": "must be null"} is returned
     *
     * @throws Exception
     */
    @Test
    void addNew_givenCityDtoContainsId_thenReturnStatus400() throws Exception {
        String postURL = "/api/countries/192/cities";
        CityDto newCity = CityDto.builder().id(192).name("NEW City").capital(false).build();
        mockMvc.perform(
                post(postURL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newCity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id", is("must be null")))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities} is used.
     * 2. The Json string {"name": "", "capital": false} is set to the request body
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
    void addNew_givenCityDtoContainsEmptyName_thenReturnStatus400() throws Exception {
        String postURL = "/api/countries/192/cities";
        CityDto newCity = CityDto.builder().name("").capital(false).build();
        mockMvc.perform(
                post(postURL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newCity)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("size must be between 1 and 50")))
        ;
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities} is used.
     * 2. Country with id 192 (Vietnam) existed in DB
     * 3. City with id 58 (TP Hồ Chí Minh) existed in DB and the named has been modified
     * <p>
     * When:
     * 1. The endpoint is called by using the PUT method
     * <p>
     * Then expect:
     * 1. The new city name shall be updated.
     * 2. Client shall be receive the status code 200
     *
     * @throws Exception
     */
    @Rollback
    @Test
    void update_ValidCityDtoInJsonFormat_thenReturnStatus200() throws Exception {
        String url = "/api/countries/192/cities/58";
        String updatedName = "TP Hồ Chí Minh (Updated)";
        CityDto newCity = CityDto.builder().name(updatedName).capital(false).build();
        mockMvc.perform(
                put(url).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newCity)))
                .andExpect(status().isOk())
        ;
        //
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(58)))
                .andExpect(jsonPath("$.name", is(updatedName)))
                .andExpect(jsonPath("$.capital", notNullValue()))
                .andExpect(jsonPath("$.lastUpdate", notNullValue()))
                .andExpect(jsonPath("$.creationDate", notNullValue()))
                .andExpect(jsonPath("$._links.self.href",
                        containsStringIgnoringCase("/api/countries/192/cities/58")))
                .andExpect(jsonPath("$._links.country.href",
                        containsStringIgnoringCase("/api/countries/192")));
    }

    @Test
    @Rollback
    void delete_givenValidCityId_thenReturnStatus202() throws Exception {
        String url = "/api/countries/192/cities/58";
        mockMvc.perform(delete(url)).andExpect(status().isNoContent());
        //
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
