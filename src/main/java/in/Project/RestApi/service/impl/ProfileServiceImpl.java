package in.Project.RestApi.service.impl;

import in.Project.RestApi.dto.ProfileDTO;
import in.Project.RestApi.entity.ProfileEntity;
import in.Project.RestApi.exceptions.ItemExistException;
import in.Project.RestApi.repository.ProfileRepository;
import in.Project.RestApi.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        if(profileRepository.existsByEmail(profileDTO.getEmail())){
            throw new ItemExistException("Profile already exists "+profileDTO.getEmail());
        }
        profileDTO.setPassword(encoder.encode(profileDTO.getPassword()));
        ProfileEntity profileEntity=mapToProfileEntity(profileDTO);
        profileEntity.setProfileId(UUID.randomUUID().toString());
        profileEntity = profileRepository.save(profileEntity);
        log.info("Printing the profile entity details {}",profileEntity);
        return mapToProfileDTO(profileEntity);

    }

    private ProfileDTO mapToProfileDTO(ProfileEntity profileEntity) {
        return modelMapper.map(profileEntity,ProfileDTO.class);
    }

    private ProfileEntity mapToProfileEntity(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO,ProfileEntity.class);
    }
}
