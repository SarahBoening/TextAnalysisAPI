package de.rub.iaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.rub.iaw.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsernameIgnoreCase(String username);

}
