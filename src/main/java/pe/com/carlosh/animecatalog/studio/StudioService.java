package pe.com.carlosh.animecatalog.studio;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.carlosh.animecatalog.exception.DuplicateResourceException;
import pe.com.carlosh.animecatalog.exception.ResourceNotFoundException;
import pe.com.carlosh.animecatalog.studio.dto.CreateStudioDTO;
import pe.com.carlosh.animecatalog.studio.dto.StudioResponseDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    @Transactional
    public StudioResponseDTO create(CreateStudioDTO req){
        if(studioRepository.existsByNameIgnoreCase(req.name())){
            throw new DuplicateResourceException("Studio with name: "+req.name()+" already exists.");
        }

        Studio studio = StudioMapper.toEntity(req);

        return StudioMapper.toResponse( studioRepository.save(studio));
    }

    @Transactional
    public StudioResponseDTO update(Long id, CreateStudioDTO req) {
        Studio studio = studioRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Studio not found with id: " + id));

        if (!studio.getName().equalsIgnoreCase(req.name())
                && studioRepository.existsByNameIgnoreCase(req.name())) {
            throw new DuplicateResourceException("Studio already exists with name: " + req.name());
        }
        StudioMapper.update(studio,req);

        return StudioMapper.toResponse(studio);
    }

    @Transactional
    public StudioResponseDTO delete(Long id) {
        Studio studio = studioRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Studio not found with id: " + id));
        studio.setActive(false);
        return StudioMapper.toResponse(studio);
    }

    @Transactional
    public StudioResponseDTO enable(Long id) {
        Studio studio = studioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Studio not found with id: " + id));
        studio.setActive(true);
        return StudioMapper.toResponse(studio);
    }
}
