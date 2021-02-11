package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CountryService;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers.CountryAssembler;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.CountryMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Ref: https://www.baeldung.com/hamcrest-text-matchers
 *
 * Created by ezmobivietnam on 2021-01-06.
 */
@WebMvcTest({CountryController.class, CountryAssembler.class, CountryMapper.class})
class CountryControllerTest extends BaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CountryService countryService;

    @Autowired
    CountryAssembler countryAssembler;

    private Country vietnam;
    private Country laos;

    @BeforeEach
    void setUp() {
        vietnam = Country.builder().id(1).name("Vietnam").build();
        laos = Country.builder().id(2).name("Laos").build();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. There is no parameters are used
     * 3. There are two countries returned from the method CountryService.findAll()
     * <p>
     * When:
     * 1. The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Client will receive the list of two countries in json format
     * 2. The "self" link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList() throws Exception {
        // given
        String findAllUrl = CountryController.BASE_URL;
        CollectionModel<CountryDto> collectionModel = countryAssembler.toCollectionModel(List.of(vietnam, laos));
        collectionModel.add(linkTo(methodOn(CountryController.class)
                .findList(null, null, null, null)).withSelfRel().expand());
        given(countryService.findAll(null, null)).willReturn(collectionModel);

        //when
        mockMvc.perform(get(findAllUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.countryDtoList", hasSize(2)))
//                .andExpect(jsonPath("$['_embedded']['countryDtoList'][0]['name']", is(vietnam.getName())))
//                .andExpect(jsonPath("$['_embedded']['countryDtoList'][1]['name']", is(laos.getName())))
                .andExpect(jsonPath("$._embedded.countryDtoList[0].name", is(vietnam.getName())))
                .andExpect(jsonPath("$._embedded.countryDtoList[1].name", is(laos.getName())))
                .andExpect(jsonPath("$._links.self.href", containsStringIgnoringCase("/api/countries")));
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. There is no parameters are used
     * 3. The method CountryService.findAll() return empty collection
     * <p>
     * When: The endpoint is called by using the GET method
     * <p>
     * Then expect:
     * 1. Only the "self" link is added to the response.
     *
     * @throws Exception
     */
    @Test
    void findList_givenEmptyResult_thenReceiveOKStatusWithNoData() throws Exception {
        // given
        String findAllUrl = CountryController.BASE_URL;
        CollectionModel<CountryDto> emptyCollectionModel = CollectionModel.empty();
        emptyCollectionModel.add(linkTo(methodOn(CountryController.class).findList(null,null, null, null))
                .withSelfRel().expand());
        given(countryService.findAll(isNull(), isNull())).willReturn(emptyCollectionModel);

        //when
        mockMvc.perform(get(findAllUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['_embedded']").doesNotExist())
                .andExpect(jsonPath("$._links.self.href", containsStringIgnoringCase("/api/countries")));
    }

    /**
     * Given:
     * 1. Target endpoint {http://localhost:8080/api/countries} is used.
     * 2. The parameter "ids" is NOT set
     * 3. The parameter "name" is set to the value "viet"
     * 4. The method findAll(List<Integer> withIds, String withName) return collection of ONE element
     *
     * When: The endpoint is called by using the GET method
     *
     * Then expect:
     * 1. Client will receive the list of ONE country in json format
     * 2. The "self" link is added to the response
     *
     * @throws Exception
     */
    @Test
    void findList_givenValidNameParam_thenFindByName() throws Exception {
        // given
        String findByNameUrl = CountryController.BASE_URL + "?name=viet";
        CollectionModel<CountryDto> collectionModel = countryAssembler.toCollectionModel(List.of(vietnam));
        collectionModel.add(linkTo(methodOn(CountryController.class)
                .findList(null,"viet", null, null)).withSelfRel().expand());
        given(countryService.findAll(isNull(), anyString())).willReturn(collectionModel);

        //when
        mockMvc.perform(get(findByNameUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['_embedded']['countryDtoList'][0]['id']", is(vietnam.getId())))
                .andExpect(jsonPath("$['_embedded']['countryDtoList'][0]['name']", is(vietnam.getName())))
                .andExpect(jsonPath("$._links.self.href", containsStringIgnoringCase("/api/countries?name=viet")));;
    }

    @Test
    void findList_givenWrongParam_thenFindAll() throws Exception {
        // given
        String findByParamUrl = CountryController.BASE_URL + "?anotherparam=viet";
        given(countryService.findAll(null, null))
                .willReturn(countryAssembler.toCollectionModel(List.of(vietnam, laos)));

        //when
        mockMvc.perform(get(findByParamUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['_embedded']['countryDtoList']", hasSize(2)))
                .andExpect(jsonPath("$['_embedded']['countryDtoList'][0]['name']", is(vietnam.getName())))
                .andExpect(jsonPath("$['_embedded']['countryDtoList'][1]['name']", is(laos.getName())));
    }

    @Test
    void findByName_givenNameParamEmpty_thenFindAll() throws Exception {
        // given
        String findByEmptyNameUrl = CountryController.BASE_URL + "?name=";
        given(countryService.findAll(null, ""))
                .willReturn(countryAssembler.toCollectionModel(List.of(vietnam, laos)));

        //when
        mockMvc.perform(get(findByEmptyNameUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['_embedded']['countryDtoList']", hasSize(2)))
                .andExpect(jsonPath("$['_embedded']['countryDtoList'][0]['name']", is(vietnam.getName())))
                .andExpect(jsonPath("$['_embedded']['countryDtoList'][1]['name']", is(laos.getName())));
    }

    @Test
    void findById() throws Exception {
        // given
        String findByIdlUrl = CountryController.BASE_URL + "/1";
        given(countryService.findById(anyInt())).willReturn(Optional.of(countryAssembler.toModel(vietnam)));

        //when
        mockMvc.perform(get(findByIdlUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(vietnam.getId())))
                .andExpect(jsonPath("$.name", is(vietnam.getName())));
    }

    @Test
    void findById_withNotExistedId() throws Exception {
        // given
        String findByIdlUrl = CountryController.BASE_URL + "/1000";
        given(countryService.findById(anyInt())).willReturn(Optional.empty());

        //when
        mockMvc.perform(get(findByIdlUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void findById_withInvalidId() throws Exception {
        // given
        String findByIdlUrl = CountryController.BASE_URL + "/AAA";

        //when
        mockMvc.perform(get(findByIdlUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addNew() throws Exception {
        //given
        String postURL = CountryController.BASE_URL;
        CountryDto newCountry = CountryDto.builder().name("New country").build();
        given(countryService.addNew(any())).willReturn(countryAssembler.toModel(vietnam));
        //then
        mockMvc.perform(
                post(postURL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(newCountry)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
//                .andExpect(header().string(HttpHeaders.LOCATION, CountryController.BASE_URL + "/1"))
        ;
    }

    @Test
    void addNew_withEmptyName() throws Exception {
        //given
        String postURL = CountryController.BASE_URL;
        //then
        mockMvc.perform(
                post(postURL).contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(CountryDto.builder().build())))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void update() throws Exception {
        /**
         * Sample format of actual response data
         * {
         *     "id": 1,
         *     "name": "Afghanistan_UPDATED",
         *     "_links": {
         *         "self": {
         *             "href": "http://localhost:8080/api/v1/countries/1"
         *         }
         *     }
         * }
         */
        //given
        String putURL = CountryController.BASE_URL + "/" + vietnam.getId();
        Country updatedCountry = Country.builder()
                .id(vietnam.getId()).name(vietnam.getName() + "_Updated").build();
        given(countryService.update(any(CountryDto.class), anyInt()))
                .willReturn(countryAssembler.toModel(updatedCountry));
        // then
        mockMvc.perform(
                put(putURL).contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(CountryDto.builder().name(updatedCountry.getName()).build())))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        //given
        String deleteURL = CountryController.BASE_URL + "/1";
        willDoNothing().given(countryService).delete(anyInt());
        //then
        mockMvc.perform(MockMvcRequestBuilders
                .delete(deleteURL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_withInvalidId() throws Exception {
        //given
        String deleteURL = CountryController.BASE_URL + "/AAA";
        //then
        mockMvc.perform(MockMvcRequestBuilders
                .delete(deleteURL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}