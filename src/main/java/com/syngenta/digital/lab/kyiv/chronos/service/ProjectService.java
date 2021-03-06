package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.mappers.ProjectMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.project.ProjectEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectTypeEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ApplicationBaseException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.ProjectRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.ProjectTypeRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private static final int ERR_CODE_NOT_EXISTING_PROJECT_TYPE = 9;

    private final ProjectRepository projectRepository;
    private final ProjectTypeRepository projectTypeRepository;
    private final ProjectMapper projectMapper;
    private final TaskRepository taskRepository;

    @Transactional
    public ProjectDto create(ProjectDto projectDto) {
        if (projectDto.isDeleted()) {
            throw new ApplicationBaseException(ERR_CODE_NOT_EXISTING_PROJECT_TYPE, "Cannot save new project with flag deleted set to true");
        }
        ProjectTypeEntity projectTypeEntity = projectTypeRepository.findById(projectDto.getProjectTypeId())
                .orElseThrow(() -> new ApplicationBaseException(ERR_CODE_NOT_EXISTING_PROJECT_TYPE, "Cannot find project type for id " + projectDto.getProjectTypeId()));
        ProjectEntity projectEntity = projectMapper.mapToEntity(projectDto, projectTypeEntity);
        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);
        return projectMapper.mapToDto(savedProjectEntity);
    }

    @Transactional
    public ProjectDto update(ProjectDto projectDto) {
        if (projectDto.getId() == null) {
            throw new ApplicationBaseException(ERR_CODE_NOT_EXISTING_PROJECT_TYPE, "Cannot update project for null id value");
        }

        ProjectTypeEntity projectTypeEntity = projectTypeRepository.findById(projectDto.getProjectTypeId())
                .orElseThrow(() -> new ApplicationBaseException(ERR_CODE_NOT_EXISTING_PROJECT_TYPE, "Cannot find project type for id " + projectDto.getProjectTypeId()));

        ProjectEntity existingProjectEntity = projectRepository.findById(projectDto.getId())
                .orElseThrow(() -> new ApplicationBaseException(ERR_CODE_NOT_EXISTING_PROJECT_TYPE, "Cannot find project for id " + projectDto.getId()));

        if (existingProjectEntity.isDeleted()) {
            throw new ApplicationBaseException(ERR_CODE_NOT_EXISTING_PROJECT_TYPE, "Cannot modify deleted project for id " + projectDto.getId());
        }

        ProjectEntity projectEntity = projectMapper.mapToEntity(projectDto, projectTypeEntity);
        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);
        return projectMapper.mapToDto(savedProjectEntity);
    }

    public ProjectDto find(long projectId) {
        return projectRepository.findById(projectId)
                .map(projectMapper::mapToDto)
                .orElseThrow(() -> new RuntimeException("Cannot find project for id " + projectId));
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> find() {
        return projectRepository.findAllWithJoinFetch().collect(Collectors.toList());
    }

    @Transactional
    public void delete(long id) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new ApplicationBaseException(ERR_CODE_NOT_EXISTING_PROJECT_TYPE, "Cannot find project for id " + id));
        projectEntity.setDeleted(true);
        projectRepository.save(projectEntity);
        taskRepository.freezeTasks(id);
    }
}
