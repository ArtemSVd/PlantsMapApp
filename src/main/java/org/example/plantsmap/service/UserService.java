package org.example.plantsmap.service;

import lombok.AllArgsConstructor;
import org.example.plantsmap.dto.User;
import org.example.plantsmap.exception.InvalidDataException;
import org.example.plantsmap.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
   private final UserRepository userRepository;

   public User create(User user) throws InvalidDataException {
       if (user.getDeviceName() == null) {
           throw new InvalidDataException("emptyDeviceName");
       }
       return userRepository.create(user);
   }

   public User getByDeviceName(String deviceName) throws InvalidDataException {
       if (deviceName == null) {
           throw new InvalidDataException("emptyDeviceName");
       }
       return userRepository.getByDeviceNumber(deviceName);
   }

    public User getById(Integer id){
        return userRepository.getByDeviceNumber(id);
    }

   public User getOrCreateUser(String deviceName, String name) throws InvalidDataException {
       User user = getByDeviceName(deviceName);

       if (user != null && user.getName() == null && name != null) {
           user.setName(name);
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
