package pe.com.carlosh.animecatalog.studio;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.carlosh.animecatalog.common.exception.DuplicateResourceException;
import pe.com.carlosh.animecatalog.common.exception.ResourceNotFoundException;
import pe.com.carlosh.animecatalog.studio.dto.CreateStudioDTO;
import pe.com.carlosh.animecatalog.studio.dto.StudioResponseDTO;

@Service
@RequiredArgsConstructor
public class StudioService {
    private final StudioRepository studioRepository;

    public Page<StudioResponseDTO> findAllActives(Pageable pageable){
        return studioRepository.findByActiveTrue(pageable).map(StudioMapper::toResponse);
    }

    public StudioResponseDTO findById(Long id){
        Studio existingStudio = studioRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()->new ResourceNotFoundException("Studio not found with id: "+id));

        return StudioMapper.toResponse(existingStudio);

    }

    public Page<StudioResponseDTO> findByNameContainingIgnoreCase(String name, Pageable pageable){
        Page<Studio> studios= studioRepository.findByNameContainingIgnoreCaseAndActiveTrue(name, pageable);
        return studios.map(StudioMapper::toResponse);
    }

    public StudioResponseDTO create(CreateStudioDTO req){
        if(studioRepository.existsByNameIgnoreCase(req.name())){
            throw new DuplicateResourceException("Studio with name: "+req.name()+" already exists.");
        }

        Studio studio = StudioMapper.toEntity(req);
        studioRepository.save(studio);

        return StudioMapper.toResponse(studio);
    }

    public StudioResponseDTO update(Long id, CreateStudioDTO req) {
        Studio studio = studioRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Studio not found with id: " + id));

        if (!studio.getName().equalsIgnoreCase(req.name())
                && studioRepository.existsByNameIgnoreCase(req.name())) {
            throw new DuplicateResourceException("Studio already exists with name: " + req.name());
        }

        studio.setName(req.name());
        studio.setCountry(req.country());
        studio.setYearCreation(req.yearCreation());
        studioRepository.save(studio);
        return StudioMapper.toResponse(studio);
    }

    public StudioResponseDTO delete(Long id) {
        Studio studio = studioRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Studio not found with id: " + id));
        studio.setActive(false);
        studioRepository.save(studio);
        return StudioMapper.toResponse(studio);
    }

    public StudioResponseDTO enable(Long id) {
        Studio studio = studioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Studio not found with id: " + id));
        studio.setActive(true);
        studioRepository.save(studio);
        return StudioMapper.toResponse(studio);
    }
}
