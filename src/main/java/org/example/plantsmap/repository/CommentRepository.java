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
import static org.example.plantsmap.generated.Tables.M_COMMENT;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final DSLContext jooq;
    private final MCommentDao dao;
    private final UserContext userContext;
    private final UserService userService;

    public Comment save(Comment comment) {
        MComment mComment = new MComment();
        mComment.setId(jooq.nextval(SEQ_COMMENT).intValue());
        mComment.setComment(comment.getText());
        mComment.setPlantId(comment.getPlantId());
        mComment.setCreatedDate(LocalDateTime.now());
        mComment.setUserId(userContext.getUser().getId());

        dao.insert(mComment);

        return mapMCommentToComment(dao.fetchOneById(mComment.getId()));
    }

    public List<Comment> getByPlantId(Integer plantId) {
        List<MComment> comments = jooq.selectFrom(M_COMMENT)
                .where(M_COMMENT.PLANT_ID.eq(plantId))
                .orderBy(M_COMMENT.CREATED_DATE.asc())
                .fetchInto(MComment.class);

        return comments.stream()
                .map(this::mapMCommentToComment)
                .collect(Collectors.toList());
    }

    private Comment mapMCommentToComment(MComment mComment) {
        return Comment
                .builder()
                .user(userService.getById(mComment.getUserId()))
                .createdDate(mComment.getCreatedDate())
                .plantId(mComment.getPlantId())
                .text(mComment.getComment())
                .build();
    }
}
