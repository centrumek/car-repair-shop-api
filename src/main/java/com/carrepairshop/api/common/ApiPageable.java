package com.carrepairshop.api.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameters({
    @Parameter(name = "page", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0"), description = "Results page you want to retrieve (0..N)"),
    @Parameter(name = "size", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "20"), description = "Number of records per page."),
    @Parameter(name = "sort", schema = @Schema(type = "string"), in = ParameterIn.QUERY, description = "Sorting criteria in the format: property(,asc|desc). "
                                                                                                           + "Default sort order is ascending. "
                                                                                                           + "Multiple sort criteria are supported.")
})
public @interface ApiPageable {
}
