package com.skt.test.service.mapper;

import com.skt.test.domain.*;
import com.skt.test.service.dto.ProjectHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectHistory} and its DTO {@link ProjectHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProjectMapper.class })
public interface ProjectHistoryMapper extends EntityMapper<ProjectHistoryDTO, ProjectHistory> {
    @Mapping(target = "project", source = "project", qualifiedByName = "id")
    ProjectHistoryDTO toDto(ProjectHistory s);
}
