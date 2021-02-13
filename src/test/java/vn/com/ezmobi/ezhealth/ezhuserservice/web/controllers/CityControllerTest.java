package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.CityService;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.assemblers.CityAssembler;
import vn.com.ezmobi.ezhealth.ezhuserservice.utils.mappers.CityMapper;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Ref:
 * 1. https://github.com/spring-projects/spring-data-commons/blob/master/src/test/java/org/springframework/data/web/PagedResourcesAssemblerUnitTests.java
 * 2. https://github.com/Damiox/ecommerce-rest-spring-jpa/blob/master/src/test/java/com/github/damiox/ecommerce/api/controller/CategoryProductsControllerTest.java
 * <p>
 * Created by ezmobivietnam on 2021-01-17.
 */
@WebMvcTest(value = {CityController.class, CityMapper.class, CityAssembler.class})
class CityControllerTest extends BaseControllerTest {

    @MockBean
    private CityService service;

    @Autowired
    private CityAssembler cityAssembler;

    @Autowired
    private MockMvc mockMvc;

    private City batnaCityEntity;
    private City bcharCityEntity;
    private City skikdaCityEntity;
    private Country algeriaCountryEntity;

    @BeforeEach
    void setUp() {
        //
        algeriaCountryEntity = Country.builder().id(2).name("Algeria").build();
        batnaCityEntity = City.builder().id(59).name("Batna").country(algeriaCountryEntity).build();
        bcharCityEntity = City.builder().id(63).name("Bchar").country(algeriaCountryEntity).build();
        skikdaCityEntity = City.builder().id(483).name("Skikda").country(algeriaCountryEntity).build();
    }

    /**
     * Test findList with all parameters are set.
     * <p>
     * Test URL: http://localhost:8080/api/countries/2/cities/?page=0&size=2&name=a
     * <p>
     * Response data:
     * {
     * "_embedded": {
     * "cityDtoList": [
     * {
     * "id": 59,
     * "name": "Batna",
     * "_links": {
     * "self": {
     * "href": "http://localhost:8080/api/countries/2/cities/59"
     * },
     * "country": {
     * "href": "http://localhost:8080/api/countries/2"
     * }
     * }
     * },
     * {
     * "id": 63,
     * "name": "Bchar",
     * "_links": {
     * "self": {
     * "href": "http://localhost:8080/api/countries/2/cities/63"
     * },
     * "country": {
     * "href": "http://localhost:8080/api/countries/2"
     * }
     * }
     * }
     * ]
     * },
     * "_links": {
     * "first": {
     * "href": "http://localhost:8080/api/countries/2/cities/?name=a&page=0&size=2"
     * },
     * "self": {
     * "href": "http://localhost:8080/api/countries/2/cities/?name=a&page=0&size=2"
     * },
     * "next": {
     * "href": "http://localhost:8080/api/countries/2/cities/?name=a&page=1&size=2"
     * },
     * "last": {
     * "href": "http://localhost:8080/api/countries/2/cities/?name=a&page=1&size=2"
     * }
     * },
     * "page": {
     * "size": 2,
     * "totalElements": 3,
     * "totalPages": 2,
     * "number": 0
     * }
     * }
     */
    @Test
    void findList() throws Exception {
        // given
        int countryId = 2;
        int page = 0;
        int size = 2;
        String searchingName = "a";
        PageRequest pageRequest = PageRequest.of(0, 2);
        List<City> cityEntityPage0 = List.of(batnaCityEntity, bcharCityEntity);
        Page<City> pageModel = new PageImpl(cityEntityPage0, pageRequest, 3);//page 0 contains 2 of total 3 items
        //
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        UriComponents baseUri =
                UriComponentsBuilder.fromUriString(
                        String.format("http://localhost:8080/api/countries/%d/cities?name=%s", countryId,
                                searchingName))
                        .build();
        PagedResourcesAssembler pagedResourcesAssembler = new PagedResourcesAssembler(resolver, baseUri);
        CollectionModel<CityDto> cityDtoCollectionModel = pagedResourcesAssembler.toModel(pageModel, cityAssembler);
        //
        given(service.findPaginated(anyInt(), isNull(), anyString(), any(PageRequest.class)))
                .willReturn(cityDtoCollectionModel);
        //when
        String requestURI = String.format("/api/countries/%d/cities?name=%s&page=%d&size=%d", countryId,
                searchingName, page, size);
        mockMvc.perform(MockMvcRequestBuilders.get(requestURI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.cityDtoList", hasSize(2)))
                .andExpect(jsonPath("$._links.first.href",
                        is("http://localhost:8080/api/countries/2/cities?name=a&page=0&size=2")))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost:8080/api/countries/2/cities?name=a&page=0&size=2")))
                .andExpect(jsonPath("$._links.next.href",
                        is("http://localhost:8080/api/countries/2/cities?name=a&page=1&size=2")))
                .andExpect(jsonPath("$._links.last.href",
                        is("http://localhost:8080/api/countries/2/cities?name=a&page=1&size=2")))
                .andExpect(jsonPath("$.page.size", is(pageRequest.getPageSize())))
                .andExpect(jsonPath("$.page.totalElements", is(3)))
                .andExpect(jsonPath("$.page.totalPages", is(2)))
                .andExpect(jsonPath("$.page.number", is(pageRequest.getPageNumber())));
    }

    @Test
    void findList_givenNoParametersSet_thenListAllCities() throws Exception {
        //given
        int countryId = 2;
        String url = String.format("http://localhost:8080/api/countries/%d/cities", countryId);
        CollectionModel<CityDto> collectionModel = cityAssembler.toCollectionModel(List.of(batnaCityEntity,
                bcharCityEntity, skikdaCityEntity));
        collectionModel.add(
                linkTo(methodOn(CityController.class)
                        .findList(countryId, null, null, null, null)).withSelfRel().expand());
        given(service.findAll(anyInt(), isNull(), isNull())).willReturn(collectionModel);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href",
                        is("/api/countries/2/cities")));
    }

    @Test
    void findById() throws Exception {
        //given
        int countryId = 2;
        int cityId = 59;
        String url = String.format("http://localhost:8080/api/countries/%d/cities/%d", countryId, cityId);
        given(service.findById(anyInt(), anyInt())).willReturn(Optional.of(cityAssembler.toModel(batnaCityEntity)));
        //when
        mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addNew() throws Exception {
        //given
        int countryId = 2;
        String url = String.format("http://localhost:8080/api/countries/%d/cities", countryId);
        City newCity = City.builder().id(1).name("New city").country(algeriaCountryEntity).build();
        given(service.addNew(anyInt(), any(CityDto.class))).willReturn(cityAssembler.toModel(newCity));
        //when
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(CityDto.builder().name("New city").build())))
                .andExpect(status().isCreated()).andExpect(header()
                .exists("Location"));
    }

    @Test
    void update() throws Exception {
        //given
        int countryId = 2;
        int cityId = 59;
        City updatedCityEnity = City.builder().id(59).name("Batna_UPDATED").country(algeriaCountryEntity).build();
        String url = String.format("http://localhost:8080/api/countries/%d/cities/%d", countryId, cityId);
        given(service.update(anyInt(), any(CityDto.class), anyInt()))
                .willReturn(cityAssembler.toModel(updatedCityEnity));
        //when
        mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(CityDto.builder().name("Batna_UPDATED").build())))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        //given
        int countryId = 2;
        int cityId = 59;
        String url = String.format("http://localhost:8080/api/countries/%d/cities/%d", countryId, cityId);
        willDoNothing().given(service).delete(anyInt(), anyInt());
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}