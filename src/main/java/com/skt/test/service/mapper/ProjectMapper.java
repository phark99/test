package com.skt.test.service.mapper;

import com.skt.test.domain.*;
import com.skt.test.service.dto.ProjectDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring", uses = { ResourceMapper.class })
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "resources", source = "resources", qualifiedByName = "idSet")
    ProjectDTO toDto(Project s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoId(Project project);

    @Mapping(target = "removeResource", ignore = true)
    Project toEntity(ProjectDTO projectDTO);
}
