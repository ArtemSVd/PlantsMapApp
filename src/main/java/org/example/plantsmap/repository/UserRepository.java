package org.example.plantsmap.repository;

import lombok.RequiredArgsConstructor;
import org.example.plantsmap.dto.User;
import org.example.plantsmap.generated.Sequences;
import org.example.plantsmap.generated.tables.daos.MUserDao;
import org.example.plantsmap.generated.tables.pojos.MUser;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final DSLContext jooq;
    private final MUserDao dao;

    public User create(User user) {
        MUser mUser = userToMUser(user);
        dao.insert(mUser);
        return getById(mUser.getId());
    }

    public User getByDeviceNumber(String deviceNumber) {
        List<MUser> result = dao.fetchByDeviceName(deviceNumber);
        return result.isEmpty() ? null : mUserToUser(result.get(0));
    }

    public User getByDeviceNumber(Integer id) {
        return mUserToUser(dao.fetchOneById(id));
    }

    public void update(User user) {
        MUser mUser = userToMUser(user, false);
        dao.update(mUser);
    }

    private User getById(Integer id) {
        return mUserToUser(dao.fetchOneById(id));
    }

    private User mUserToUser(MUser mUser) {
        return User
                .builder()
                .id(mUser.getId())
                .deviceName(mUser.getDeviceName())
                .name(mUser.getName())
                .build();
    }

    private MUser userToMUser(User user) {
        return userToMUser(user, true);
    }

    private MUser userToMUser(User user, boolean newUser) {
        MUser mUser = new MUser();
        mUser.setId(newUser ? jooq.nextval(Sequences.SEQ_USER).intValue() : user.getId());
        mUser.setName(user.getName());
        mUser.setDeviceName(user.getDeviceName());
        return mUser;
    }
}
