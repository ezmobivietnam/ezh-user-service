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
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.DataNotFoundException;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.TaskExecutionException;
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
     * <p>
     * Target method: CityService.findPaginated(Integer rootId, String nameExp, PageRequest pageRequest)
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
     * <p>
     * Target method: CityService.findPaginated(Integer rootId, String nameExp, PageRequest pageRequest)
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

    /**
     * Target method: CityService.findPaginated(Integer rootId, String nameExp, PageRequest pageRequest)
     */
    @Test
    void findPaginated_givenNullCountryId_thenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.findPaginated(null, null, PageRequest.of(0, 2));
        });
        String expectedMessage = "Country id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Target method: CityService.findPaginated(Integer rootId, String nameExp, PageRequest pageRequest)
     */
    @Test
    void findPaginated_givenNullPageRequest_thenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.findPaginated(2, null, null);
        });
        String expectedMessage = "PageRequest must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Target method: CityService.findPaginated(String whatToFind, PageRequest pageRequest)
     */
    @Test
    void findPaginated_02_givenNameIsNullAndMultiPageData_thenAllDataReturnedWithPaginationLinks() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 2);
        List<City> allEntitiesList = List.of(city01Entity, city02Entity, city03Entity);
        given(cityRepository.findAll(any(PageRequest.class)))
                .willReturn(new PageImpl<City>(List.of(city01Entity, city02Entity), PageRequest.of(0, 2),
                        allEntitiesList.size()));
        //when
        CollectionModel<CityDto> cityDtoList = cityService
                .findPaginated(null, PageRequest.of(0, 2));
        PagedModel<City> pagedModel = (PagedModel) cityDtoList;
        //then expect
        assertNotNull(pagedModel.getMetadata());
        assertEquals(pagedModel.getMetadata().getTotalElements(), allEntitiesList.size()); //03 items
        assertEquals(pagedModel.getMetadata().getSize(), pageRequest.getPageSize());
        assertEquals(pagedModel.getContent().size(), pageRequest.getPageSize()); //02 items in first page
        assertNotNull(pagedModel.getLink(IanaLinkRelations.FIRST_VALUE).get());
        assertNotNull(pagedModel.getLink(IanaLinkRelations.SELF_VALUE).get());
        assertNotNull(pagedModel.getLink(IanaLinkRelations.NEXT_VALUE).get());
        assertNotNull(pagedModel.getLink(IanaLinkRelations.LAST_VALUE).get());
    }

    /**
     * Target method: CityService.findPaginated(String whatToFind, PageRequest pageRequest)
     */
    @Test
    void findPaginated_02_givenNameNotNullAndDataFixedInOnePage_thenAllDataReturnedWithSelfLink() {
        //given
        List<City> allEntitiesList = List.of(city01Entity, city02Entity, city03Entity);
        PageRequest pageRequest = PageRequest.of(0, allEntitiesList.size());
        given(cityRepository.findAllByNameContainingIgnoreCase(anyString(), any(PageRequest.class)))
                .willReturn(new PageImpl<City>(allEntitiesList, pageRequest, allEntitiesList.size()));
        //when
        CollectionModel<CityDto> cityDtoList = cityService
                .findPaginated("a", pageRequest);
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

    /**
     * Target method: CityService.findPaginated(String whatToFind, PageRequest pageRequest)
     */
    @Test
    void findPaginated_02_givenNullPageRequest_thenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.findPaginated(null, null);
        });
        String expectedMessage = "PageRequest must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Given a country id then return:
     * 1. all cities of that country.
     * 2. A self link
     * <p>
     * Target method: CityService.findAll(Integer rootId)
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

    /**
     * Target method: CityService.findAll(Integer rootId)
     */
    @Test
    void findAll_givenNullCountryId_thenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.findAll(null);
        });
        String expectedMessage = "Country id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Target method: CityService.findAll()
     */
    @Test
    void findAll_00_givenNoParameter_thenReturnAllDataWithSelfLink() {
        //given
        List<City> allEntitiesList = List.of(city01Entity, city02Entity, city03Entity);
        given(cityRepository.findAll()).willReturn(allEntitiesList);
        //when
        CollectionModel<CityDto> cityDtoList = cityService.findAll();
        assertEquals(cityDtoList.getContent().size(), allEntitiesList.size());
        assertNotNull(cityDtoList.getLink(IanaLinkRelations.SELF_VALUE).get());
    }

    /**
     * Given: the valid country id
     * Then return the CityDto object with two links "self" and "country"
     * <p>
     * Target method: CityService.findById(Integer rootId, Integer levelOneId)
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

    /**
     * Target method: CityService.findById(Integer rootId, Integer levelOneId)
     */
    @Test
    void findById_givenNullCountryId_thenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.findById(null, 59);
        });
        String expectedMessage = "Country id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Target method: CityService.findById(Integer rootId, Integer levelOneId)
     */
    @Test
    void findById_givenNullCityId_thenThrowsException() {
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
     * <p>
     * Target method: CityService.findByText(Integer rootId, String name)
     */
    @Test
    void findByText() {
        // given
        List<City> cityEntities = List.of(city01Entity, city02Entity, city03Entity);
        given(cityRepository.findAllByNameContainingIgnoreCaseAndCountry_Id(anyString(), anyInt()))
                .willReturn(cityEntities);
        //when
        CollectionModel<CityDto> cityDtoList = cityService.findByText(2, "a");
        assertEquals(cityDtoList.getContent().size(), cityEntities.size());
        assertNotNull(cityDtoList.getLink(IanaLinkRelations.SELF_VALUE));
    }

    /**
     * Target method: CityService.findByText(String whatToFind)
     */
    @Test
    void findByText_01_givenNonnullName_thenReturnMatchingAllCitiesWithSelfLink() {
        // given
        List<City> cityEntities = List.of(city01Entity, city02Entity, city03Entity);
        given(cityRepository.findAllByNameContainingIgnoreCase(anyString()))
                .willReturn(cityEntities);
        //when
        CollectionModel<CityDto> cityDtoList = cityService.findByText("a");
        assertEquals(cityDtoList.getContent().size(), cityEntities.size());
        assertNotNull(cityDtoList.getLink(IanaLinkRelations.SELF_VALUE));
    }

    /**
     * Given:
     * 1. countryId: the id of the country owning the new city
     * 2. cityDto: the information of the new city
     * Then expect:
     * 1. the saved CityDto object is returned with the two links "self" and "country"
     * <p>
     * Target method: CityService.addNew(Integer countryId, CityDto cityDto)
     */
    @Test
    void addNew() {
        //given
        given(countryRepository.findById(anyInt())).willReturn(Optional.of(algeriaCountryEntity));
        given(cityRepository.save(any()))
                .willReturn(City.builder().id(1).name("New city").country(algeriaCountryEntity).build());
        //when
        CityDto cityDto = cityService.addNew(2, CityDto.builder().name("New city").build());
        assertNotNull(cityDto);
        assertNotNull(cityDto.getId());
        assertNotNull(cityDto.getLink(IanaLinkRelations.SELF_VALUE));
    }

    /**
     * Target method: CityService.addNew(Integer countryId, CityDto cityDto)
     */
    @Test
    void addNew_givenNullCountryId_thenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.addNew(null, CityDto.builder().build());
        });
        String expectedMessage = "Country id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Target method: CityService.addNew(Integer countryId, CityDto cityDto)
     */
    @Test
    void addNew_givenNullCityDto_thenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.addNew(2, null);
        });
        String expectedMessage = "City data must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Given:
     * 1. countryId: the id of the country owning the new city
     * 2. cityDto: the information of the city being updated
     * 3. cityID: the id of the city being updated.
     * Then expect:
     * 1. the updated CityDto object is returned with the two links "self" and "country"
     * <p>
     * Target method: CityService.update(Integer countryId, CityDto cityDto, Integer cityId)
     */
    @Test
    void update() {
        //given
        given(cityRepository.findByIdAndCountry_Id(anyInt(), anyInt())).willReturn(Optional.of(city01Entity));
        given(cityRepository.save(any())).willReturn(city01Entity);
        //when
        CityDto updatedModel = cityService.update(2, CityDto.builder().name("New name").build(), 59);
        //then
        assertNotNull(updatedModel);
        assertNotNull(updatedModel.getLink(IanaLinkRelations.SELF_VALUE));
        assertNotNull(updatedModel.getLink("country"));
    }

    /**
     * Target method: CityService.update(Integer countryId, CityDto cityDto, Integer cityId)
     */
    @Test
    void update_givenFailedGettingCityInfoByCountryIdAndCityId_thenThrowsException() {
        //given
        given(cityRepository.findByIdAndCountry_Id(anyInt(), anyInt())).willReturn(Optional.empty());
        //when
        Exception exception = assertThrows(TaskExecutionException.class, () -> {
            cityService.update(2, CityDto.builder().name("New name").build(), 59);
        });
        //then
        String expectedMessage = String.format("Failed to search the city [%d] of the country [%d]", 59, 2);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Target method: CityService.update(Integer countryId, CityDto cityDto, Integer cityId)
     */
    @Test
    void update_givenNullCountryID_thenThrowsException() {
        //given
        Integer countryId = null;
        CityDto cityDto = CityDto.builder().build();
        Integer cityId = 59;
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.update(countryId, cityDto, cityId);
        });
        //then
        String expectedMessage = "Country id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Target method: CityService.update(Integer countryId, CityDto cityDto, Integer cityId)
     */
    @Test
    void update_givenNullCountryDto_thenThrowsException() {
        //given
        Integer countryId = 2;
        CityDto cityDto = null;
        Integer cityId = 59;
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.update(countryId, cityDto, cityId);
        });
        //then
        String expectedMessage = "City data must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Target method: CityService.update(Integer countryId, CityDto cityDto, Integer cityId)
     */
    @Test
    void update_givenNullCityId_thenThrowsException() {
        //given
        Integer countryId = 2;
        CityDto cityDto = CityDto.builder().build();
        Integer cityId = null;
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.update(countryId, cityDto, cityId);
        });
        //then
        String expectedMessage = "City id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Target method: CityService.delete(Integer countryId, Integer cityId)
     */
    @Test
    void delete() {
        //given
        given(cityRepository.deleteByIdAndCountry_Id(anyInt(), anyInt())).willReturn(1);
        //when
        int deletedRecords = cityService.delete(2, 29);
        //then
        assertEquals(deletedRecords, 1);
    }

    /**
     * Target method: CityService.delete(Integer countryId, Integer cityId)
     */
    @Test
    void delete_givenNoDBRecordDeleted_thenThrowsException() {
        //given
        Integer countryId = 2;
        Integer cityId = 59;
        given(cityRepository.deleteByIdAndCountry_Id(anyInt(), anyInt())).willReturn(0);
        //when
        Exception exception = assertThrows(TaskExecutionException.class, () -> {
            cityService.delete(countryId, cityId);
        });
        //then
        String expectedMessage = String.format("Failed to delete the city [%d] of the country [%d]", cityId, countryId);
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}