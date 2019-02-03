package com.codimiracle.service.loveletter.service;

import com.codimiracle.service.loveletter.entity.Letter;
import com.codimiracle.service.loveletter.model.Page;
import com.codimiracle.service.loveletter.repository.LetterRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Codimiracle
 */
@Service
public class LetterService {

    @Resource
    private LetterRepository letterRepository;

    public List<Letter> findAllByThemeId(Page page, String themeId, String runId) {
        return letterRepository.findAllByThemeId(page, themeId, runId);
    }

    public int countAll(String runId) {
        return letterRepository.countAll(runId);
    }

    public List<Letter> findAll(Page page, String runId) {
        return letterRepository.findAll(page, runId);
    }

    public int countAllByThemeId(String themeId, String runId) {
        return letterRepository.countAllByThemeId(themeId, runId);
    }

    public int countAllBySender(String sender, String runId) {
        return letterRepository.countAllBySender(sender, runId);
    }

    public List<Letter> findAllBySender(Page page, String sender, String runId) {
        return letterRepository.findAllBySender(page, sender, runId);
    }

    public List<Letter> findAllByReceiver(Page page, String receiver, String runId) {
        return letterRepository.findAllByReciever(page, receiver, runId);
    }


    public int countAllReceiver(String receiver, String runId) {
        return letterRepository.countAllByReceiver(receiver, runId);
    }

    public int countAllByKeyword(String keyword, String runId) {
        return letterRepository.countAllByKeyword(keyword, runId);
    }

    public List<Letter> findAllByKeyword(Page page, String keyword, String runId) {
        return letterRepository.findAllByKeyword(page, keyword, runId);
    }

    public Letter findById(String id, String runId) {
        return letterRepository.findById(id, runId);
    }
}
