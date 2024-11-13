package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.JwtDTO;
import dasturlash.uz.kun_uz.dto.ProfileDTO;
import dasturlash.uz.kun_uz.dto.UpdateProfileDetailDTO;
import dasturlash.uz.kun_uz.entity.Profile;
import dasturlash.uz.kun_uz.enums.ProfileRole;
import dasturlash.uz.kun_uz.enums.ProfileStatus;
import dasturlash.uz.kun_uz.exp.AppBadException;
import dasturlash.uz.kun_uz.repository.ProfileRepository;
import dasturlash.uz.kun_uz.util.MD5Util;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    public ProfileDTO add(ProfileDTO profileDTO, JwtDTO jwtDTO) {
        Optional<Profile> byEmail1 = profileRepository.findByEmail(jwtDTO.getUsername());

        if (!byEmail1.get().getRole().equals(ProfileRole.ROLE_ADMIN)){
            throw new AppBadException("Forbidden");
        }

        Optional<Profile> byEmail = profileRepository.findByEmail(profileDTO.getEmail());
        if (!byEmail.isEmpty()) {
            throw new AppBadException("Email already exists");
        }
        Profile byPhone = profileRepository.findByPhone(profileDTO.getPhone());
        if (byPhone != null) {
            throw new AppBadException("Phone already exists");
        }
        Profile profile = new Profile();
        profile.setName(profileDTO.getName());
        profile.setSurname(profileDTO.getSurname());
        profile.setPassword(MD5Util.md5(profileDTO.getPassword()));
        profile.setEmail(profileDTO.getEmail());
        profile.setPhone(profileDTO.getPhone());
        profile.setRole(profileDTO.getRole());
        profile.setStatus(ProfileStatus.IN_REGISTERED);
        profile.setVisible(true);
        profile.setCreateDate(LocalDateTime.now());
        profileRepository.save(profile);

        profileDTO.setId(profile.getId());
        return profileDTO;
    }

    public List<ProfileDTO> getAll() {
        List<ProfileDTO> profileDTOS = new ArrayList<>();

        for (Profile profile : profileRepository.findAll()) {
            ProfileDTO dto = toDTO(profile);
            profileDTOS.add(dto);
        }
        return profileDTOS;
    }

    public ProfileDTO toDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setName(profile.getName());
        profileDTO.setSurname(profile.getSurname());
        profileDTO.setPassword(profile.getPassword());
        profileDTO.setEmail(profile.getEmail());
        profileDTO.setPhone(profile.getPhone());
        return profileDTO;
    }

    public ProfileDTO getById(Integer id) {
        Optional<Profile> byId = profileRepository.findById(id);
        if (!byId.isPresent()) {
            throw new AppBadException("Profile not found");
        }
        return toDTO(byId.get());
    }

    public ProfileDTO update(Integer id, ProfileDTO profileDTO) {
        Optional<Profile> byId = profileRepository.findById(id);
        if (!byId.isPresent()) {
            throw new AppBadException("Profile not found");
        }
        Optional<Profile> byEmail = profileRepository.findByEmail(profileDTO.getEmail());
        if (byEmail != null) {
            throw new AppBadException("Email already exists");
        }
        Profile byPhone = profileRepository.findByPhone(profileDTO.getPhone());
        if (byPhone != null) {
            throw new AppBadException("Phone already exists");
        }
        Profile profile = new Profile();
        profile.setName(profileDTO.getName());
        profile.setSurname(profileDTO.getSurname());
        profile.setPassword(profileDTO.getPassword());
        profile.setEmail(profileDTO.getEmail());
        profile.setPhone(profileDTO.getPhone());
        profile.setRole(ProfileRole.ROLE_USER
        );
        profile.setStatus(ProfileStatus.IN_REGISTERED);
        profile.setVisible(true);
        profile.setCreateDate(LocalDateTime.now());
        profileRepository.save(profile);

        profileDTO.setId(profile.getId());
        return profileDTO;
    }

    public ProfileDTO delete(Integer id) {
        Optional<Profile> byId = profileRepository.findById(id);
        if (!byId.isPresent()) {
            throw new AppBadException("Profile not found");
        }
        profileRepository.deleteById(id);
        return toDTO(byId.get());
    }


    public Page<ProfileDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<ProfileDTO> profileDTOS = new LinkedList<>();

        Page<Profile> profilePage = profileRepository.findAll(pageable);
        long totalElements = profilePage.getTotalElements();

        for (Profile profile : profilePage.getContent()) {
            ProfileDTO dto = toDTO(profile);
            profileDTOS.add(dto);
        }
        PageImpl<ProfileDTO> profileDTOS1 = new PageImpl<>(profileDTOS, pageable, totalElements);
        return profileDTOS1;
    }
        public boolean updateDetail(@Valid UpdateProfileDetailDTO requestDTO, String username) {
            Profile profile = getByUsername(username);
            profile.setName(requestDTO.getName());
            profile.setSurname(requestDTO.getSurname());
            profileRepository.save(profile);

            return true;
        }


        public Profile getByUsername(String username) {
            return profileRepository.findByEmailAndVisibleTrue(username).orElseThrow(() -> new AppBadException("User not found"));
        }

    public Profile getByUsernameProfile(@NotBlank String email) {
        Optional<Profile> byEmail = profileRepository.findByEmail(email);
        if (byEmail.isPresent()) return byEmail.get();
        return null;
    }
}

