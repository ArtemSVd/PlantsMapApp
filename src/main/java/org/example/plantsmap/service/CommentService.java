package org.example.plantsmap.service;

import lombok.AllArgsConstructor;
import org.example.plantsmap.dto.Comment;
import org.example.plantsmap.exception.InvalidDataException;
import org.example.plantsmap.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getByPlantId(Integer plantId) {
        return commentRepository.getByPlantId(plantId);
    }

    public void save(Comment comment) throws InvalidDataException {
        Map<String, String> errorsMap = new HashMap<>();
        if (comment.getPlantId() == null) {
            errorsMap.put("emptyPlantId", "Не заполнен идентификатор сущности");
        }
        if (comment.getText() == null) {
            errorsMap.put("emptyText", "Не заполнен комментарий");
        }

        if (!errorsMap.isEmpty()) {
            throw new InvalidDataException("", errorsMap);
        }

        commentRepository.save(comment);
    }
}
