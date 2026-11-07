package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {

    default Specialty mapToEntity(SpecialtyDTO dto) {
        if (dto == null) return null;
        Specialty s = new Specialty();
        s.setId(dto.getId());
        s.setName(dto.getName());
        s.setOffice(dto.getOffice());
        s.setHOpen(dto.getHOpen());
        s.setHClose(dto.getHClose());
        return s;
    }

    default SpecialtyDTO mapToDto(Specialty entity) {
        if (entity == null) return null;
        return SpecialtyDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .office(entity.getOffice())
                .hOpen(entity.getHOpen())
                .hClose(entity.getHClose())
                .build();
    }
}
