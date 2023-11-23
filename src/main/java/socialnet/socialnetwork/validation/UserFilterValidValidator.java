package socialnet.socialnetwork.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;
import socialnet.socialnetwork.web.model.UserFilter;

public class UserFilterValidValidator implements ConstraintValidator<UserFilterValid, UserFilter> {

    @Override
    public boolean isValid(UserFilter value, ConstraintValidatorContext context) {
        if(ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize())){
            return false;
        }
        return (value.getAgeFrom() != null || value.getAgeTo() == null)
                && (value.getAgeFrom() == null || value.getAgeTo() != null);
    }

}
