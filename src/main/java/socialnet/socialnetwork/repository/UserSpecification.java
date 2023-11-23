package socialnet.socialnetwork.repository;

import org.springframework.data.jpa.domain.Specification;
import socialnet.socialnetwork.model.User;
import socialnet.socialnetwork.web.model.UserFilter;

import java.time.LocalDate;

public interface UserSpecification {
    static Specification<User> withFilter(UserFilter userFilter, String login) {
        return Specification.where(
                byLogin(userFilter.getLogin()))
                .and(byFirstName(userFilter.getFirstName()))
                .and(byLastName(userFilter.getLastName()))
                .and(byCity(userFilter.getCity()))
                .and(byCountry(userFilter.getCountry()))
                .and(byBirthdayRange(userFilter.getBirthDateFrom(), userFilter.getBirthDateTo()))
                .and(byAgeRange(userFilter.getAgeFrom(), userFilter.getAgeTo()))
                .and(byOnline(userFilter.getIsOnline()))
                .and(excludeYourself(login));
    }

    static Specification<User> withExcludeYourselfFilter(String login) {
        return Specification.where(excludeYourself(login));
    }

    static Specification<User> excludeYourself(String login) {
        return (root, query, criteriaBuilder) -> {
            if (login == null) {
                return null;
            }
            return criteriaBuilder.notEqual(root.get("login"), login);
        };
    }

    static Specification<User> byLogin(String login) {
        return (root, query, criteriaBuilder) -> {
            if (login == null) {
                return null;
            }
            return criteriaBuilder.like(root.get("login"), login);
        };
    }

    static Specification<User> byFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> {
            if (firstName == null) {
                return null;
            }
            return criteriaBuilder.like(root.get("firstName"), firstName);
        };
    }
    static Specification<User> byLastName(String lastName) {
        return (root, query, criteriaBuilder) -> {
            if (lastName == null) {
                return null;
            }
            return criteriaBuilder.like(root.get("lastName"), lastName);
        };
    }

    static Specification<User> byBirthdayRange(LocalDate birthDateFrom, LocalDate birthDateTo) {
        return (root, query, criteriaBuilder) -> {
            if (birthDateFrom == null && birthDateTo == null) {
                return null;
            }
            if (birthDateFrom == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("birthDate"), birthDateTo);
            }
            if (birthDateTo == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("birthDate"), birthDateFrom);
            }
            if (birthDateFrom.isAfter(birthDateTo)) {
                return null;
            }
            return criteriaBuilder.between(root.get("birthDate"), birthDateFrom, birthDateTo);
        };
    }

    static Specification<User> byCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null) {
                return null;
            }
            return criteriaBuilder.like(root.get("city"), city + "%");
        };
    }

    static Specification<User> byCountry(String country) {
        return (root, query, criteriaBuilder) -> {
            if (country == null) {
                return null;
            }
            return criteriaBuilder.like(root.get("country"), country);
        };
    }

    static Specification<User> byAgeRange(Integer ageFrom, Integer ageTo) {
        return (root, query, criteriaBuilder) -> {
            if (ageFrom == null && ageTo == null) {
                return null;
            }
            if (ageFrom == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("birthDate"), ageTo);
            }
            if (ageTo == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("birthDate"), ageFrom);
            }
            if (ageFrom > ageTo) {
                return null;
            }
            return criteriaBuilder.between(root.get("birthDate"),
                    LocalDate.now().minusYears(ageTo + 1).plusDays(1),
                    LocalDate.now().minusYears(ageFrom));
        };
    }

    static Specification<User> byOnline(Boolean isOnline) {
        return (root, query, criteriaBuilder) -> {
            if (isOnline == null || !isOnline) {
                return null;
            }
            return criteriaBuilder.equal(root.get("isOnline"), true);
        };
    }

}
