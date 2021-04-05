package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ezmobivietnam on 2021-02-25.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AddressControllerITTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities/58/addresses} is used.
     * 2. The country with id 192 is existed
     * 3. The city with id 58 is existed
     * 4. There is no parameters are used
     * <p>
     * When: The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. All addresses belong to specified city will be returned
     * 2. "Self" link is added to the response data
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(authorities = "address:read")
    void findList_givenNoParamsSpecified_thenReturnAllDataWithSelfLink() throws Exception {
        String url = "/api/countries/192/cities/58/addresses";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.addressDtoList", hasSize(25)))
                .andExpect(jsonPath("$._links.self.href",
                        containsStringIgnoringCase("/api/countries/192/cities/58/addresses")));
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries/192/cities/58/addresses?page=0&size=20} is used.
     * 2. The country with id 192 is existed
     * 3. The city with id 58 is existed
     * 3. Parameters {page=0&size=20} is used
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of 20 cities of the page 01 in json format
     * 2. The pagination links ("first, "self", "next, "last") link is added to the response
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(authorities = "address:read")
    void findList_givenParamsPageAndSizeSpecified_thenReturnCorrespondingDataWithPagination() throws Exception {
        String url = "/api/countries/192/cities/58/addresses?page=0&size=20";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.addressDtoList", hasSize(20)))
                .andExpect(jsonPath("$._links.first.href",
                        containsStringIgnoringCase("/api/countries/192/cities/58/addresses?page=0&size=20")))
                .andExpect(jsonPath("$._links.self.href",
                        containsStringIgnoringCase("/api/countries/192/cities/58/addresses?page=0&size=20")))
                .andExpect(jsonPath("$._links.next.href",
                        containsStringIgnoringCase("/api/countries/192/cities/58/addresses?page=1&size=20")))
                .andExpect(jsonPath("$._links.last.href",
                        containsStringIgnoringCase("/api/countries/192/cities/58/addresses?page=1&size=20")))
                .andExpect(jsonPath("$.page.number", is(0)))
                .andExpect(jsonPath("$.page.size", is(20)))
                .andExpect(jsonPath("$.page.totalElements", is(25)))
                .andExpect(jsonPath("$.page.totalPages", is(2)))
        ;
    }

}
