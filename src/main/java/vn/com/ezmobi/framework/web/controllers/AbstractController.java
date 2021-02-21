package vn.com.ezmobi.framework.web.controllers;

import org.springframework.data.domain.PageRequest;

import java.util.Objects;

/**
 * Created by ezmobivietnam on 2021-02-20.
 */
public abstract class AbstractController {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;

    protected PageRequest getPageRequest(Integer page, Integer size) {
        int actualPageNumber = Objects.isNull(page) ? DEFAULT_PAGE_NUMBER : page;
        int actualPageSize = Objects.isNull(size) ? DEFAULT_PAGE_SIZE : size;
        return PageRequest.of(actualPageNumber, actualPageSize);
    }
}
