package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.VetSpecialtyMapper;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetSpecialtyRepository vetSpecialtyRepository;
    private final VetSpecialtyMapper vetSpecialtyMapper;

    @Override
    public VetSpecialtyDTO assignSpecialtyToVet(VetSpecialtyDTO dto) {
        VetSpecialty entity = vetSpecialtyMapper.mapToEntity(dto);
        VetSpecialty saved = vetSpecialtyRepository.save(entity);
        return vetSpecialtyMapper.mapToDto(saved);
    }

    @Override
    public void removeSpecialtyFromVet(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);
        if (!vetSpecialtyRepository.existsById(id)) {
            throw new VetSpecialtyNotFoundException("VetSpecialty not found for vetId " + vetId + " and specialtyId " + specialtyId);
        }
        vetSpecialtyRepository.deleteById(id);
    }

    @Override
    public VetSpecialtyDTO findById(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);
        Optional<VetSpecialty> found = vetSpecialtyRepository.findById(id);
        if (found.isEmpty()) {
            throw new VetSpecialtyNotFoundException("VetSpecialty not found for vetId " + vetId + " and specialtyId " + specialtyId);
        }
        return vetSpecialtyMapper.mapToDto(found.get());
    }

    @Override
    public List<VetSpecialtyDTO> findSpecialtiesByVet(Integer vetId) {
        List<VetSpecialty> list = vetSpecialtyRepository.findByIdVetId(vetId);
        return list.stream()
                .map(vetSpecialtyMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetSpecialtyDTO> findVetsBySpecialty(Integer specialtyId) {
        List<VetSpecialty> list = vetSpecialtyRepository.findByIdSpecialtyId(specialtyId);
        return list.stream()
                .map(vetSpecialtyMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetSpecialtyDTO> findAll() {
        return vetSpecialtyRepository.findAll().stream()
                .map(vetSpecialtyMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
