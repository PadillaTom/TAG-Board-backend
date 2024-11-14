package com.padillatom.TAG_Board.repository;

import com.padillatom.TAG_Board.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findById(Long id);

    Optional<Profile> findFirstByUserEntityUsername(String username);
}
