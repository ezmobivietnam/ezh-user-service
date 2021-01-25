package vn.com.ezmobi.ezhealth.ezhuserservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Country;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.CountryRepository;
import vn.com.ezmobi.ezhealth.ezhuserservice.web.model.CountryDto;

import java.util.ArrayList;
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
@AutoConfigureMockMvc
class CountryServiceTest {

    @MockBean
    private CountryRepository countryRepository;

    @Autowired
    private CountryService countryService;

    private Country vietnam;
    private Country laos;


    @BeforeEach
    void setUp() {
        vietnam = Country.builder().id(1).name("Vietnam").build();
        laos = Country.builder().id(2).name("Laos").build();
    }

    /**
     * Find paginated with ALL three params not null. The research return only ONE item so "_links" element only
     * contain the link "self"
     * Ex.: http://localhost:8080/api/countries/?page=0&size=2&name=Viet
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
        given(countryRepository.findByNameContainingIgnoreCase(anyString(), any(PageRequest.class)))
                .willReturn(countryPage);
        //when
        CollectionModel<CountryDto> collectionModelList = countryService
                .findPaginated("Viet", PageRequest.of(0, 2));
        //then
        assertEquals(1, collectionModelList.getContent().size());
        assertEquals(1, collectionModelList.getLinks().stream().count());
    }

    /**
     * Find paginated with two parameters "page" and "size" are not null and the param "name" is null. The search
     * return many items so the "_links" element will have 04 links which are:
     * 1. "first": the link to the fist page
     * 2. "self": the link to self page
     * 3. "next": the link to the next page
     * 4. "last": the link to the last page
     * <p>
     * E.x: http://localhost:8080/api/countries/?page=0&size=2"
     */
    @Test
    void findPaginated_findAll() {
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
        Page<Country> stubData = getFindAllPaginatedStubData(); // stub data will presented into 5 page of 2 items
        given(countryRepository.findAll(any(PageRequest.class))).willReturn(stubData);
        //when
        CollectionModel<CountryDto> paginationModel = countryService
                .findPaginated(null, PageRequest.of(0, 2));
        //then
        assertEquals(stubData.getTotalElements(), paginationModel.getContent().size());
        assertEquals(4, paginationModel.getLinks().stream().count());
    }

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
        given(countryRepository.findAll()).willReturn(List.of(vietnam, laos));
        //when
        CollectionModel<CountryDto> countryDtoList = countryService.findAll();
        //then
        assertNotNull(countryDtoList);
        assertEquals(2, countryDtoList.getContent().size());
        assertEquals(1, countryDtoList.getLinks().stream().count()); //there is self link only
    }

    @Test
    void findById() {
        //given
        given(countryRepository.findById(vietnam.getId())).willReturn(Optional.of(vietnam));
        //when
        Optional<CountryDto> result = countryService.findById(vietnam.getId());
        //then
        assertTrue(result.isPresent());
    }

    @Test
    void findById_notFound() {
        //given
        given(countryRepository.findById(vietnam.getId())).willReturn(Optional.empty());
        //when
        Optional<CountryDto> result = countryService.findById(vietnam.getId());
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    void findByText() {
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
        given(countryRepository.findByNameContainingIgnoreCase(anyString())).willReturn(List.of(vietnam));
        //when
        CollectionModel<CountryDto> result = countryService.findByText(anyString());
        //then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getLinks().stream().count());
    }

    @Test
    void add() {
        //given
        given(countryRepository.save(any(Country.class))).willReturn(vietnam);
        //when
        CountryDto result = countryService.addNew(CountryDto.builder().id(vietnam.getId()).name(vietnam.getName()).build());
        assertEquals(vietnam.getId(), result.getId());
        assertEquals(1, result.getLinks().stream().count());
    }

    @Test
    void update() {
        //given
        given(countryRepository.findById(anyInt())).willReturn(Optional.of(vietnam));
        given(countryRepository.save(any(Country.class))).willReturn(vietnam);
        //when
        CountryDto result = countryService.update(CountryDto.builder()
                .id(vietnam.getId())
                .name(vietnam.getName()).build(), vietnam.getId());
        assertEquals(vietnam.getId(), result.getId());
        assertEquals(1, result.getLinks().stream().count());
    }

    @Test
    void delete() {
        //given
        willDoNothing().given(countryRepository).deleteById(anyInt());
        //when
        countryService.delete(1);
        //then
        verify(countryRepository, times(1)).deleteById(1);
    }

    private Page<Country> getFindAllPaginatedStubData() {
        int itemNo = 10;
        List<Country> countryList = new ArrayList<>(itemNo);
        for (int i = 1; i < itemNo + 1; i++) {
            Country c = new Country();
            c.setId(i);
            c.setName("Country_" + i);
            countryList.add(c);
        }
        //
        return new PageImpl<Country>(countryList, PageRequest.of(0, 2), countryList.size());
    }
}