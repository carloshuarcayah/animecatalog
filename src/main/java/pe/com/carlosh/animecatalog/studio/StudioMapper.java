package pe.com.carlosh.animecatalog.studio;

import pe.com.carlosh.animecatalog.studio.dto.CreateStudioDTO;
import pe.com.carlosh.animecatalog.studio.dto.StudioResponseDTO;

public class StudioMapper {
    public static Studio toEntity(CreateStudioDTO request){
        return new Studio(request.name(),request.country(), request.yearCreation());
    }

    public static StudioResponseDTO toResponse(Studio studio){
        return new StudioResponseDTO(
                studio.getId(), studio.getName(), studio.getCountry(), studio.getYearCreation()
        );
    }

    public static void update(Studio studio,CreateStudioDTO req){
        studio.setName(req.name());
        studio.setCountry(req.country());
        studio.setYearCreation(req.yearCreation());
    }
}
