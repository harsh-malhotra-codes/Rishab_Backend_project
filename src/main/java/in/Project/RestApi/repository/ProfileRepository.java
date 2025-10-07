package in.Project.RestApi.repository;

import in.Project.RestApi.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Long> {


    Optional<ProfileEntity> findByEmail(String email);

    boolean existsByEmail(String email);

}
