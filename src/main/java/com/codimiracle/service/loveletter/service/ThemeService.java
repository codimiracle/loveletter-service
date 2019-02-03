package com.codimiracle.service.loveletter.service;

import com.codimiracle.service.loveletter.entity.Theme;
import com.codimiracle.service.loveletter.model.Page;
import com.codimiracle.service.loveletter.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Codimiracle
 */
@Service
public class ThemeService {
    @Resource
    ThemeRepository themeRepository;

    public Theme findById(String id, String runId) {
        return themeRepository.findById(id, runId);
    }

    public List<Theme> findAll(Page page, String runId) {
        return themeRepository.findAll(page, runId);
    }

    public int countAll(String runId) {
        return themeRepository.countAll(runId);
    }
}
