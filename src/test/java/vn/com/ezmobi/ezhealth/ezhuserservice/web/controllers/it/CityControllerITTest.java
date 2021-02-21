package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ezmobivietnam on 2021-02-20.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CityControllerITTest {
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
}
