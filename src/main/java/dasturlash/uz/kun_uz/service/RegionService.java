package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.RegionDTO;
import dasturlash.uz.kun_uz.entity.Region;
import dasturlash.uz.kun_uz.exp.AppBadException;
import dasturlash.uz.kun_uz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    RegionRepository regionRepository;

    public RegionDTO add(RegionDTO regionDTO) {
        Region region = new Region();

        region.setCreatedDate(LocalDateTime.now());

        region.setName_uz(regionDTO.getName_uz());
        region.setName_ru(regionDTO.getName_ru());
        region.setName_en(regionDTO.getName_en());
        region.setVisible(true);
        region.setOrderNumber(regionDTO.getOrderNumber());

        regionRepository.save(region);

        regionDTO.setId(region.getId());

        return regionDTO;
    }

    public List<RegionDTO> getAll() {
        Iterable<Region> all = regionRepository.findAll();
        List<RegionDTO> regionDTOList = new ArrayList<>();
        for (Region region : all) {
            RegionDTO dto = toDTO(region);
            regionDTOList.add(dto);
        }
        return regionDTOList;
    }



    public RegionDTO getById(Integer id) {
        Optional<Region> byId = regionRepository.findById(id);
        if (!byId.isPresent()) {
            throw new AppBadException("Region not found");
        }
        return toDTO(byId.get());
    }

    public RegionDTO update(Integer id, RegionDTO regionDTO) {
        Optional<Region> byId = regionRepository.findById(id);
        if (!byId.isPresent()) {
            throw new AppBadException("Region not found");
        }
        Region region = byId.get();
        region.setName_uz(regionDTO.getName_uz());
        region.setName_ru(regionDTO.getName_ru());
        region.setName_en(regionDTO.getName_en());
        region.setOrderNumber(regionDTO.getOrderNumber());

        regionDTO.setId(id);

        return regionDTO;
    }

    public Boolean delete(Integer id) {
        regionRepository.deleteById(id);
        return true;
    }
    public RegionDTO toDTO(Region region) {
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(region.getId());
        regionDTO.setName_uz(region.getName_uz());
        regionDTO.setName_ru(region.getName_ru());
        regionDTO.setName_en(region.getName_en());


        return regionDTO;
    }
}
