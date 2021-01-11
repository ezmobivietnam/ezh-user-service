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
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CountryService;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ezmobivietnam on 2021-01-06.
 */
@WebMvcTest(CountryController.class)
class CountryControllerTest extends BaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CountryService countryService;
    private CountryDto vietnam;
    private CountryDto laos;

    @BeforeEach
    void setUp() {
        vietnam = CountryDto.builder().id(1).name("Vietnam").build();
        laos = CountryDto.builder().id(2).name("Laos").build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() throws Exception {
        // given
        String findAllUrl = CountryController.BASE_URL;
        given(countryService.findAll()).willReturn(CollectionModel.of(List.of(vietnam, laos)));

        //when
        mockMvc.perform(get(findAllUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.countryDtoList", hasSize(2)))
                .andExpect(jsonPath("$['_embedded']['countryDtoList'][0]['name']", is(vietnam.getName())))
                .andExpect(jsonPath("$['_embedded']['countryDtoList'][1]['name']", is(laos.getName())));
    }

    @Test
    void findAll_givenEmptyResult_thenReceiveOKStatusWithNoData() throws Exception {
        // given
        String findAllUrl = CountryController.BASE_URL;
        given(countryService.findAll()).willReturn(CollectionModel.empty());

        //when
        mockMvc.perform(get(findAllUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['_embedded']").doesNotExist());
    }

    @Test
    void findAll_givenValidNameParam_thenFindByName() throws Exception {
        // given
        String findByNameUrl = CountryController.BASE_URL + "?name=viet";
        given(countryService.findByName(anyString())).willReturn(CollectionModel.of(List.of(vietnam)));

        //when
        mockMvc.perform(get(findByNameUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['_embedded']['countryDtoList'][0]['id']", is(vietnam.getId())))
                .andExpect(jsonPath("$['_embedded']['countryDtoList'][0]['name']", is(vietnam.getName())));
    }

    @Test
    void findAll_givenWrongParam_thenFindAll() throws Exception {
        // given
        String findByParamUrl = CountryController.BASE_URL + "?anotherparam=viet";
        given(countryService.findAll()).willReturn(CollectionModel.of(List.of(vietnam, laos)));

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
        given(countryService.findAll()).willReturn(CollectionModel.of(List.of(vietnam, laos)));

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
        given(countryService.findById(anyInt())).willReturn(Optional.of(vietnam));

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
    void add() throws Exception {
        //given
        String postURL = CountryController.BASE_URL;
        given(countryService.add(any())).willReturn(vietnam);
        //then
        mockMvc.perform(
                post(postURL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(vietnam)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
//                .andExpect(header().string(HttpHeaders.LOCATION, CountryController.BASE_URL + "/1"))
        ;
    }

    @Test
    void add_withEmptyName() throws Exception {
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
        CountryDto updatedCountry =
                vietnam.builder().id(vietnam.getId()).name(vietnam.getName() + "_Updated").build();
        given(countryService.update(vietnam, vietnam.getId()))
                .willReturn(updatedCountry);
        // then
        mockMvc.perform(
                put(putURL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(vietnam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['id']", is(updatedCountry.getId())))
                .andExpect(jsonPath("$['name']", is(updatedCountry.getName())))
        ;
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