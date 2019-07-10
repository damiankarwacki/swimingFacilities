package com.sport.SportFacilities.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.web.util.UriComponentsBuilder.fromPath;

public class HateoasHelper {

    private HateoasHelper() {
    }

    public static URI getUriWithPathAndParams(String path, Object... params) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path(path).buildAndExpand(params).toUri();
    }
}