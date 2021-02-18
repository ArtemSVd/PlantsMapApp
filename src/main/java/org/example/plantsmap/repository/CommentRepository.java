package org.example.plantsmap.repository;

import lombok.RequiredArgsConstructor;
import org.example.plantsmap.dto.Comment;
import org.example.plantsmap.generated.tables.daos.MCommentDao;
import org.example.plantsmap.generated.tables.pojos.MComment;
import org.example.plantsmap.security.UserContext;
import org.example.plantsmap.service.UserService;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.plantsmap.generated.Sequences.SEQ_COMMENT;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final DSLContext jooq;
    private final MCommentDao dao;
    private final UserContext userContext;
    private final UserService userService;

    public void save(Comment comment) {
        MComment mComment = new MComment();
        mComment.setId(jooq.nextval(SEQ_COMMENT).intValue());
        mComment.setComment(comment.getText());
        mComment.setPlantId(comment.getPlantId());
        mComment.setCreatedDate(comment.getCreatedDate() != null ? comment.getCreatedDate() : LocalDateTime.now());
        mComment.setUserId(userContext.getUser().getId());

        dao.insert(mComment);
    }

    public List<Comment> getByPlantId(Integer plantId) {
        List<MComment> comments = dao.fetchByPlantId(plantId);

        return comments.stream()
                .map(this::mapMCommentToComment)
                .collect(Collectors.toList());
    }

    private Comment mapMCommentToComment(MComment mComment) {
        return Comment
                .builder()
                .user(userService.getById(mComment.getId()))
                .createdDate(mComment.getCreatedDate())
                .plantId(mComment.getPlantId())
                .text(mComment.getComment())
                .build();
    }
}
