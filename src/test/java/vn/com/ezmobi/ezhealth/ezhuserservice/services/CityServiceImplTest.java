package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CityRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CountryRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CityDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

/**
 * Created by ezmobivietnam on 2021-01-24.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CityServiceImplTest {

    @Autowired
    CityService cityService;

    @MockBean
    CityRepository cityRepository;

    @MockBean
    CountryRepository countryRepository;

    private City city01Entity;
    private City city02Entity;
    private City city03Entity;
    private Country algeriaCountryEntity;

    @BeforeEach
    void setUp() {
        algeriaCountryEntity = Country.builder().id(2).name("Algeria").build();
        city01Entity = City.builder().id(59).name("Batna").country(algeriaCountryEntity).build();
        city02Entity = City.builder().id(63).name("Bchar").country(algeriaCountryEntity).build();
        city03Entity = City.builder().id(483).name("Skikda").country(algeriaCountryEntity).build();
    }

    /**
     * Test findPaginated with given conditions:
     * 1. Country id is not null
     * 2. Searching name is null (find all)
     * 3. PageRequest is not null and the data is more than one page
     * Then expect:
     * 1. List of cities of a country are returned.
     * 2. links (first, self, next, last) are added to the response
     */
    @Test
    void findPaginated() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 2);
        List<City> allEntitiesList = List.of(city01Entity, city02Entity, city03Entity);
        given(cityRepository.findAllByCountry_Id(anyInt(), any(PageRequest.class)))
                .willReturn(new PageImpl<City>(List.of(city01Entity, city02Entity), PageRequest.of(0, 2),
                        allEntitiesList.size()));
        //when
        CollectionModel<CityDto> cityDtoList = cityService
                .findPaginated(2, null, PageRequest.of(0, 2));
        PagedModel<City> pagedModel = (PagedModel) cityDtoList;
        //then expect
        assertNotNull(pagedModel.getMetadata());
        assertEquals(pagedModel.getMetadata().getTotalElements(), allEntitiesList.size());
        assertEquals(pagedModel.getMetadata().getSize(), pageRequest.getPageSize());
        assertEquals(pagedModel.getContent().size(), pageRequest.getPageSize());
        assertNotNull(pagedModel.getLink(IanaLinkRelations.FIRST_VALUE).get());
        assertNotNull(pagedModel.getLink(IanaLinkRelations.SELF_VALUE).get());
        assertNotNull(pagedModel.getLink(IanaLinkRelations.NEXT_VALUE).get());
        assertNotNull(pagedModel.getLink(IanaLinkRelations.LAST_VALUE).get());
    }

    /**
     * Test findPaginated with given conditions:
     * 1. Country id is not null
     * 2. Searching name is 'a'
     * 3. PageRequest is not null and the data fixed to one page
     * Then expect:
     * 1. List of cities of a country are returned.
     * 2. Only link 'self' is added to the response
     */
    @Test
    void findPaginated_givenNameNotNullAndDataFixedInOnePage_thenAllDataReturnedWithSelfLink() {
        //given
        List<City> allEntitiesList = List.of(city01Entity, city02Entity, city03Entity);
        PageRequest pageRequest = PageRequest.of(0, allEntitiesList.size());
        given(cityRepository.findAllByCountry_Id(anyInt(), any(PageRequest.class)))
                .willReturn(new PageImpl<City>(allEntitiesList, pageRequest, allEntitiesList.size()));
        //when
        CollectionModel<CityDto> cityDtoList = cityService
                .findPaginated(2, null, pageRequest);
        PagedModel<City> pagedModel = (PagedModel) cityDtoList;
        //then expect
        assertNotNull(pagedModel.getMetadata());
        assertEquals(pagedModel.getMetadata().getTotalElements(), allEntitiesList.size());
        assertEquals(pagedModel.getMetadata().getSize(), pageRequest.getPageSize());
        assertEquals(pagedModel.getContent().size(), allEntitiesList.size());
        assertNotNull(pagedModel.getLink(IanaLinkRelations.SELF_VALUE).get());
        assertTrue(pagedModel.getLink(IanaLinkRelations.FIRST_VALUE).isEmpty());
        assertTrue(pagedModel.getLink(IanaLinkRelations.NEXT_VALUE).isEmpty());
        assertTrue(pagedModel.getLink(IanaLinkRelations.LAST_VALUE).isEmpty());
    }

    @Test
    void findPaginated_givenNullCountryId_thenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.findPaginated(null, null, PageRequest.of(0, 2));
        });
        String expectedMessage = "Country id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void findPaginated_givenNullPageRequest_thenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.findPaginated(2, null, null);
        });
        String expectedMessage = "PageRequest must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Given a country id then return:
     * 1. all cities of that country.
     * 2. A self link
     */
    @Test
    void findAll() {
        //given
        List<City> allEntitiesList = List.of(city01Entity, city02Entity, city03Entity);
        given(cityRepository.findAllByCountry_Id(anyInt())).willReturn(allEntitiesList);
        //when
        CollectionModel<CityDto> cityDtoList = cityService.findAll(2);
        assertEquals(cityDtoList.getContent().size(), allEntitiesList.size());
        assertNotNull(cityDtoList.getLink(IanaLinkRelations.SELF_VALUE).get());
    }

    @Test
    void findAll_givenNullCountryId_thenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.findAll(null);
        });
        String expectedMessage = "Country id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Given: the valid country id then return the CityDto object with two links "self" and "country"
     */
    @Test
    void findById() {
        //given
        Integer countryId = 2;
        Integer cityId = 59;
        given(cityRepository.findByIdAndCountry_Id(anyInt(), anyInt())).willReturn(Optional.of(city01Entity));
        //when
        Optional<CityDto> cityDtoOptional = cityService.findById(countryId, cityId);
        //then
        assertFalse(cityDtoOptional.isEmpty());
        assertEquals(cityDtoOptional.get().getId(), cityId);
        assertNotNull(cityDtoOptional.get().getLink(IanaLinkRelations.SELF_VALUE).get());
        assertNotNull(cityDtoOptional.get().getLink("Country").get());
    }

    @Test
    void findById_givenNullCountryId_thenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.findById(null, 59);
        });
        String expectedMessage = "Country id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void findById_givenNullCityId_thenThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.findById(2, null);
        });
        String expectedMessage = "City id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Given valid country id and a name then return:
     * 1. A list of cities contain the searching name
     * 2. A self link
     */
    @Test
    void findByName() {
        // given
        List<City> cityEntities = List.of(city01Entity, city02Entity, city03Entity);
        given(cityRepository.findAllByNameContainingIgnoreCaseAndCountry_Id(anyString(), anyInt()))
                .willReturn(cityEntities);
        //when
        CollectionModel<CityDto> cityDtoList = cityService.findByName(2, "a");
        assertEquals(cityDtoList.getContent().size(), cityEntities.size());
        assertNotNull(cityDtoList.getLink(IanaLinkRelations.SELF_VALUE));
    }

    @Test
    void addNew() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void testFindPaginated() {
    }

    @Test
    void testFindAll() {
    }

    @Test
    void findByColumn() {
    }
}