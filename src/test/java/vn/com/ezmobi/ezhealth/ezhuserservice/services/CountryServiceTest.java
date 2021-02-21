package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.City;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CountryRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Ref:
 * https://www.baeldung.com/junit-assertions
 * https://github.com/spring-projects/spring-boot/issues/1454
 * <p>
 * Created by ezmobivietnam on 2021-01-12.
 */
@SpringBootTest
class CountryServiceTest {

    @MockBean
    private CountryRepository countryRepository;

    @Autowired
    private CountryService countryService;

    private Country vietnam;
    private Country laos;
    private Country cambodia;


    @BeforeEach
    void setUp() {
        vietnam = Country.builder().id(1).name("Vietnam").build();
        laos = Country.builder().id(2).name("Laos").build();
        cambodia = Country.builder().id(18).name("Cambodia").build();
    }

    /**
     * Given:
     * 1. Searching Ids is null
     * 2. Searching name is not null (find all countries by name)
     * 3. PageRequest is not null (page number =0 and page size = 2)
     * 4. The method countryRepository.findAllByNameContainingIgnoreCase(String, Pageable) return a collection of only
     * ONE item
     * <p>
     * Then expect:
     * 1. List of countries match the searching name is returned.
     * 2. The link "self" is added to the response
     * 3. The links (first, next, last) are not added to the response because the result is fixed into ONE page.
     * <p>
     * Target method: CountryService.findPaginated(String nameExp, PageRequest pageRequest)
     */
    @Test
    void findPaginated() {
        /**
         * Sample json format responded by findAndPaginated():
         * {
         *     "_embedded": {
         *         "countryDtoList": [
         *             {
         *                 "id": 105,
         *                 "name": "Vietnam",
         *                 "_links": {
         *                     "self": {
         *                         "href": "http://localhost:8080/api/countries/105"
         *                     }
         *                 }
         *             }
         *         ]
         *     },
         *     "_links": {
         *         "self": {
         *             "href": "http://localhost:8080/api/countries/?name=Viet&page=0&size=2"
         *         }
         *     },
         *     "page": {
         *         "size": 2,
         *         "totalElements": 1,
         *         "totalPages": 1,
         *         "number": 0
         *     }
         * }
         */
        //given
        PageImpl<Country> countryPage = new PageImpl(List.of(vietnam));
        given(countryRepository.findAllByNameContainingIgnoreCase(anyString(), any(PageRequest.class)))
                .willReturn(countryPage);
        //when
        CollectionModel<CountryDto> collectionModelList = countryService
                .findPaginated(null, "Viet", PageRequest.of(0, 2));
        //then
        assertEquals(1, collectionModelList.getContent().size());
        assertEquals(1, collectionModelList.getLinks().stream().count());
        assertNotNull(collectionModelList.getLink(IanaLinkRelations.SELF_VALUE));
    }

    /**
     * Given:
     * 1. Searching Ids is null
     * 2. Searching name is null
     * 2. PageRequest is not null (page number =0 and page size = 2)
     * 3. The method countryRepository.findAll(Pageable pageable) return a collection of 02 of 03 items (the first
     * one of two pages)
     * <p>
     * Then expect:
     * 1. The collection of countries corresponding to the PageRequest is returned
     * 2. Links (first, self, next, last) are added to the response
     * <p>
     * Target method: CountryService.findPaginated(List<Integer> withIds, String withName, PageRequest pageRequest)
     */
    @Test
    void findPaginated_givenSearchingParamsNullAndDataFixedIntoMultiPages_thenFirstPageReturnedWithPaginationLinks() {
        /**
         * Sample json format responded by findPaginated():
         * {
         *     "_embedded": {
         *         "countryDtoList": [
         *             {
         *                 "id": 1,
         *                 "name": "Afghanistan",
         *                 "_links": {
         *                     "self": {
         *                         "href": "http://localhost:8080/api/countries/1"
         *                     }
         *                 }
         *             },
         *             {
         *                 "id": 2,
         *                 "name": "Algeria",
         *                 "_links": {
         *                     "self": {
         *                         "href": "http://localhost:8080/api/countries/2"
         *                     }
         *                 }
         *             }
         *         ]
         *     },
         *     "_links": {
         *         "first": {
         *             "href": "http://localhost:8080/api/countries/?page=0&size=2"
         *         },
         *         "self": {
         *             "href": "http://localhost:8080/api/countries/?page=0&size=2"
         *         },
         *         "next": {
         *             "href": "http://localhost:8080/api/countries/?page=1&size=2"
         *         },
         *         "last": {
         *             "href": "http://localhost:8080/api/countries/?page=54&size=2"
         *         }
         *     },
         *     "page": {
         *         "size": 2,
         *         "totalElements": 109,
         *         "totalPages": 55,
         *         "number": 0
         *     }
         * }
         */
        //given
        PageRequest pageRequest = PageRequest.of(0, 2);
        List<Country> allEntities = List.of(cambodia, laos, vietnam);
        List<Country> firstPageEntities = List.of(cambodia, laos);
        Page<Country> stubData = new PageImpl<>(firstPageEntities, pageRequest, allEntities.size());
        given(countryRepository.findAll(any(PageRequest.class))).willReturn(stubData);
        //when
        CollectionModel<CountryDto> paginationModel = countryService.findPaginated(null, null, pageRequest);
        PagedModel<City> pagedModel = (PagedModel) paginationModel;
        //then expect
        assertNotNull(pagedModel.getMetadata());
        assertEquals(pagedModel.getMetadata().getTotalElements(), allEntities.size());
        assertEquals(pagedModel.getMetadata().getSize(), pageRequest.getPageSize());
        assertEquals(pagedModel.getContent().size(), firstPageEntities.size());
        assertNotNull(pagedModel.getLink(IanaLinkRelations.FIRST_VALUE).get());
        assertNotNull(pagedModel.getLink(IanaLinkRelations.SELF_VALUE).get());
        assertNotNull(pagedModel.getLink(IanaLinkRelations.NEXT_VALUE).get());
        assertNotNull(pagedModel.getLink(IanaLinkRelations.LAST_VALUE).get());
    }

    /**
     * Given:
     * 1. The searching ids is null
     * 2. The searching name is null
     * 3. The method CountryRepository.findAll() return a list of 03 countries.
     * <p>
     * Then expect:
     * 1. The collection of 03 CountryDto objects is returned.
     * 2. The "self" links is appended to the response
     * <p>
     * Target method: CountryService.findAll()
     */
    @Test
    void findAll() {
        /**
         * Sample JSon format responded by findAll:
         * {
         *     "_embedded": {
         *         "countryDtoList": [
         *             {
         *                 "id": 1,
         *                 "name": "Afghanistan",
         *                 "_links": {
         *                     "self": {
         *                         "href": "http://localhost:8080/api/countries/1"
         *                     }
         *                 }
         *             },
         *              {
         *                 "id": 105,
         *                 "name": "Vietnam",
         *                 "_links": {
         *                     "self": {
         *                         "href": "http://localhost:8080/api/countries/105"
         *                     }
         *                 }
         *             },
         * 			...
         *         ]
         *     },
         *     "_links": {
         *         "self": {
         *             "href": "http://localhost:8080/api/countries"
         *         }
         *     }
         * }
         *
         */
        //given
        List<Country> countryEntities = List.of(cambodia, vietnam, laos);
        given(countryRepository.findAll()).willReturn(countryEntities);
        //when
        CollectionModel<CountryDto> countryDtoList = countryService.findAll(null, null);
        //then
        assertNotNull(countryDtoList);
        assertEquals(countryEntities.size(), countryDtoList.getContent().size());
        assertNotNull(countryDtoList.getLink(IanaLinkRelations.SELF_VALUE).get());
    }


    /**
     * Given:
     * 1. Searching ids is null
     * 2. Searching name is not null and not empty
     * 3. The method CountryRepository.findByNameContainingIgnoreCase(nameExp) return ONE country matches the
     * searching condition
     * <p>
     * Then expect:
     * 1. The collection containing ONE CountryDto object is return.
     * 2. A "self" link is appended to the result.
     * <p>
     * Target method: CountryService.findAll(List<Integer> withIds, String withName)
     */
    @Test
    void findAll_givenIdsNullAndNameNotNullAndFoundOneCountryMatches_thenReturnListOfOneCountryWithSelfLink() {
        /**
         * Sample json format responded by findByName():
         * {
         *     "_embedded": {
         *         "countryDtoList": [
         *             {
         *                 "id": 105,
         *                 "name": "Vietnam",
         *                 "_links": {
         *                     "self": {
         *                         "href": "http://localhost:8080/api/countries/105"
         *                     }
         *                 }
         *             }
         *         ]
         *     },
         *     "_links": {
         *         "self": {
         *             "href": "http://localhost:8080/api/countries?name=Viet"
         *         }
         *     }
         * }
         */
        //given
        given(countryRepository.findAllByNameContainingIgnoreCase(anyString())).willReturn(List.of(vietnam));
        //when
        CollectionModel<CountryDto> result = countryService.findAll(null, "Viet");
        //then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertNotNull(result.getLink(IanaLinkRelations.SELF_VALUE));
    }

    /**
     * Given:
     * 1. Country id is not null.
     * 2. Country id is existed.
     * <p>
     * Then expect:
     * 1. The CountryDto object is returned
     * <p>
     * Target method: CountryService.findById(Integer id)
     */
    @Test
    void findById() {
        //given
        given(countryRepository.findById(vietnam.getId())).willReturn(Optional.of(vietnam));
        //when
        Optional<CountryDto> result = countryService.findById(vietnam.getId());
        //then
        assertTrue(result.isPresent());
    }

    /**
     * Given:
     * 1. Country id is not null.
     * 2. Country id is NOT existed.
     * <p>
     * Then expect:
     * 1. The empty Optional instance is returned
     * <p>
     * Target method: CountryService.findById(Integer id)
     */
    @Test
    void findById_givenCountryIdNotExisted_thenReturnEmptyOptionalInstance() {
        //given
        given(countryRepository.findById(vietnam.getId())).willReturn(Optional.empty());
        //when
        Optional<CountryDto> result = countryService.findById(vietnam.getId());
        //then
        assertTrue(result.isEmpty());
    }

    /**
     * Target method: CountryService.findById(Integer id)
     */
    @Test
    void findById_givenNullCountryId_thenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            countryService.findById(null);
        });
        String expectedMessage = "Country id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Given:
     * 1. A valid countryDto instance containing the country info
     * 2. The method CountryRepository.save() is executed successfully and return the saved instance Country entity
     * <p>
     * Then expect:
     * 1. The saved Country entity was converted into CountryDto including a "self" link then return to caller
     * <p>
     * Target method: addNew(CountryDto countryDto)
     */
    @Test
    void addNew() {
        //given
        given(countryRepository.save(any(Country.class))).willReturn(vietnam);
        //when
        CountryDto result = countryService.addNew(CountryDto.builder().name("Country name").build());
        assertEquals(vietnam.getId(), result.getId());
        assertNotNull(result.getLink(IanaLinkRelations.SELF_VALUE));
    }

    /**
     * Target method: addNew(CountryDto countryDto)
     */
    @Test
    void addNew_givenNullCountryDto_thenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            countryService.addNew(null);
        });
        String expectedMessage = "Country data must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Target method: addNew(CountryDto countryDto)
     */
    @Test
    void addNew_givenInvalidCountryDto_thenThrowsException() {
        assertThrows(ConstraintViolationException.class, () -> {
            countryService.addNew(CountryDto.builder().build());
        });
    }

    /**
     * Given:
     * 1. A valid countryDto instance containing the country info used to update
     * 2. A valid countryId refer to a country record in DB to be updated
     * <p>
     * Then expected:
     * 1. The updated Country entity was converted into CountryDto including a "self" link then return to caller
     * <p>
     * Target method: CountryService.update(CountryDto countryDto, Integer countryId)
     */
    @Test
    void update() {
        //given
        given(countryRepository.findById(anyInt())).willReturn(Optional.of(vietnam));
        given(countryRepository.save(any(Country.class))).willReturn(vietnam);
        //when
        CountryDto result = countryService.update(CountryDto.builder()
                .name("Updated country name").build(), vietnam.getId());
        assertEquals(vietnam.getId(), result.getId());
        assertEquals(1, result.getLinks().stream().count());
    }

    /**
     * Target method: CountryService.update(CountryDto countryDto, Integer countryId)
     */
    @Test
    void update_givenNullCountryDto_ThenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            countryService.update(null, 1);
        });
        String expectedMessage = "Country data must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Target method: CountryService.update(CountryDto countryDto, Integer countryId)
     */
    @Test
    void update_givenNullCountryId_ThenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            countryService.update(CountryDto.builder().name("Vietnam").build(), null);
        });
        String expectedMessage = "Country id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Target method: CountryService.update(CountryDto countryDto, Integer countryId)
     */
    @Test
    void update_givenInvalidCountryDto_thenThrowsException() {
        assertThrows(ConstraintViolationException.class, () -> {
            countryService.update(CountryDto.builder().build(), 1);
        });
    }

    /**
     * Given:
     * 1. A valid countryId
     * <p>
     * Then expect:
     * 1. Verify the countryRepository.deleteById(id) was called once
     * <p>
     * Target method: CountryService.delete(final Integer countryId)
     */
    @Test
    void delete() {
        //given
        willDoNothing().given(countryRepository).deleteById(anyInt());
        //when
        countryService.delete(1);
        //then
        verify(countryRepository, times(1)).deleteById(1);
    }

    /**
     * Target method: CountryService.delete(final Integer countryId)
     */
    @Test
    void delete_givenNullCountryId_thenThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            countryService.delete(null);
        });
        String expectedMessage = "Country id must not be null!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}