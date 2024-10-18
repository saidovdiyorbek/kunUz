package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.ProfileDTO;
import dasturlash.uz.kun_uz.entity.Profile;
import dasturlash.uz.kun_uz.enums.Role;
import dasturlash.uz.kun_uz.exp.AppBadException;
import dasturlash.uz.kun_uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    public ProfileDTO add(ProfileDTO profileDTO) {
        Profile byEmail = profileRepository.findByEmail(profileDTO.getEmail());
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
        profile.setRole(Role.MODERATOR);
        profile.setStatus(true);
        profile.setVisible(true);
        profile.setCreated_date(LocalDateTime.now());
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
        Profile byEmail = profileRepository.findByEmail(profileDTO.getEmail());
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
        profile.setRole(Role.MODERATOR);
        profile.setStatus(true);
        profile.setVisible(true);
        profile.setCreated_date(LocalDateTime.now());
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
}
