package loans.service;

import loans.domain.entity.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
