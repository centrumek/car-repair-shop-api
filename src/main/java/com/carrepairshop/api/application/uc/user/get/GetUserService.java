package com.carrepairshop.api.application.uc.user.get;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import com.carrepairshop.api.application.domain.Ticket;
import com.carrepairshop.api.application.domain.User;
import com.carrepairshop.api.application.domain.UserPrincipal;
import com.carrepairshop.api.application.port.out.FindTicketByIdPort;
import com.carrepairshop.api.application.port.out.FindTicketsAllPort;
import com.carrepairshop.api.application.port.out.FindTicketsByCustomerIdPort;
import com.carrepairshop.api.application.port.out.FindUserByEmailPort;
import com.carrepairshop.api.application.port.out.FindUsersAllPort;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import static com.carrepairshop.api.application.domain.User.Role.CUSTOMER;

@RequiredArgsConstructor
class GetUserService implements GetUserUC {

    private final FindUsersAllPort findUsersAllPort;

    @Override
    public Page<User> getUsers(final Pageable pageable) {
        return findUsersAllPort.findUsersAll(pageable);
    }
}
