
package com.sport.SportFacilities.utils;

import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@NoArgsConstructor
public class HateoasUtils {

        public URI getUriWithPathAndParams(String path, Object... params) {
            return ServletUriComponentsBuilder.fromCurrentRequest().path(path).buildAndExpand(params).toUri();
        }
}
