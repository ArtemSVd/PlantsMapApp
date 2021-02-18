package org.example.plantsmap.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.plantsmap.dto.User;
import org.example.plantsmap.exception.InvalidDataException;
import org.example.plantsmap.repository.UserRepository;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) throws InvalidDataException {
        log.info("create user: " + user);
        if (user.getDeviceName() == null) {
            log.error("empty device number");
            throw new InvalidDataException("emptyDeviceName");
        }
        return userRepository.create(user);
    }

    public User getByDeviceName(String deviceName) throws InvalidDataException {
        log.info("get user by device number: " + deviceName);
        if (deviceName == null) {
            log.error("empty device number");
            throw new InvalidDataException("emptyDeviceName");
        }
        return userRepository.getByDeviceNumber(deviceName);
    }

    public User getById(Integer id) {
        log.info("get user by id: " + id);
        return userRepository.getByDeviceNumber(id);
    }

    public User getOrCreateUser(String deviceName, String name) throws InvalidDataException {
        User user = getByDeviceName(deviceName);

        if (user != null && user.getName() == null && name != null) {
            user.setName(name);
            log.info("update user : " + user);
            userRepository.update(user);
        }

        if (user == null) {
            User newUser = User
                    .builder()
                    .deviceName(deviceName)
                    .name(name)
                    .build();
            user = create(newUser);
        }

        return user;
    }
}
