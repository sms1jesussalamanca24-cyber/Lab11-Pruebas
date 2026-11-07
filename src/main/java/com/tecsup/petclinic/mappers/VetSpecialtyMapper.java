package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VetSpecialtyMapper {

    default VetSpecialty mapToEntity(VetSpecialtyDTO dto) {
        if (dto == null) {
            return null;
        }
        VetSpecialty entity = new VetSpecialty();
        if (dto.getVetId() != null && dto.getSpecialtyId() != null) {
            entity.setId(new VetSpecialtyId(dto.getVetId(), dto.getSpecialtyId()));
        }
        entity.setCertificationDate(dto.getCertificationDate());
        entity.setYearsExperience(dto.getYearsExperience());
        entity.setIsPrimary(dto.getIsPrimary());
        entity.setNotes(dto.getNotes());
        return entity;
    }

    default VetSpecialtyDTO mapToDto(VetSpecialty entity) {
        if (entity == null) {
            return null;
        }
        return VetSpecialtyDTO.builder()
                .vetId(entity.getId() != null ? entity.getId().getVetId() : null)
                .specialtyId(entity.getId() != null ? entity.getId().getSpecialtyId() : null)
                .certificationDate(entity.getCertificationDate())
                .yearsExperience(entity.getYearsExperience())
                .isPrimary(entity.getIsPrimary())
                .notes(entity.getNotes())
                .build();
    }
}
