package loans.validator;

import loans.domain.entity.User;
import loans.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.data.rest.core.ValidationErrors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class UserValidatorTest {

    @InjectMocks
    private UserValidator userValidator;
    @Mock
    private UserService userService;

    @Test
    void validateEmptyUsername() {

        User user = new User();
        Errors errors = new ValidationErrors(user, PersistentEntities.of(new JdbcMappingContext()));

        userValidator.validate(user, errors);

        FieldError error = errors.getFieldError("username");

        assertNotNull(error);

    }
}
