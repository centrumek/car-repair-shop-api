package com.carrepairshop.api.adapter.persistance;

import java.util.UUID;
import org.mapstruct.Named;

import static java.util.Objects.nonNull;

class TicketEntityMapperHelper {

    /**
     * This method is required due to hibernate and MapSturct limitation.
     * Hibernate automatically wants to create new entity when object is not null, but is empty (default implementation of MapStruct).
     * @param releasedByUuid
     * @return
     */
    @Named("mapReleasedBy")
    UserEntity mapReleasedBy(final UUID releasedByUuid) {
        if (nonNull(releasedByUuid)) {
            var userEntity = new UserEntity();
            userEntity.setUuid(releasedByUuid);
            return userEntity;
        } else {
            return null;
        }
    }
}
