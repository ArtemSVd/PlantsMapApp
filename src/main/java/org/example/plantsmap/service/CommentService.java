package org.example.plantsmap.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.plantsmap.dto.Comment;
import org.example.plantsmap.exception.InvalidDataException;
import org.example.plantsmap.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getByPlantId(Integer plantId) {
        log.info("getComments by plantId: " + plantId);
        return commentRepository.getByPlantId(plantId);
    }

    public Comment save(Comment comment) throws InvalidDataException {
        log.info("save comment:" + comment.toString());
        Map<String, String> errorsMap = new HashMap<>();
        if (comment.getPlantId() == null) {
            errorsMap.put("emptyPlantId", "Не заполнен идентификатор сущности");
        }
        if (comment.getText() == null) {
            errorsMap.put("emptyText", "Не заполнен комментарий");
        }

        if (!errorsMap.isEmpty()) {
            log.error("Валидационные ошибки: " + errorsMap);
            throw new InvalidDataException("", errorsMap);
        }

        log.info("comment saved successfully");
        Comment saved = commentRepository.save(comment);
        log.info("saved:" + saved.toString());

        return saved;
    }
}
