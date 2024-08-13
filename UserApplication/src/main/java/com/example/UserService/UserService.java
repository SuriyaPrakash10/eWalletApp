package com.example.UserService;

import com.example.UserDto.CreateUserRequestDto;
import com.example.UserDto.UserDto;
import com.example.UserEntity.UserEntity;
import com.example.UserRepository.UserCacheRepository;
import com.example.UserRepository.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserService {
    private static final String USER_CREATE_TOPIC = "user_created";

    @Autowired private UserCacheRepository cacheRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired private ObjectMapper objectMapper;
    public void create(CreateUserRequestDto newUser) throws JsonProcessingException {
        UserEntity userEntity = new UserEntity();
        userEntity.setAge(newUser.getAge());
        userEntity.setName(newUser.getName());
        userEntity.setPhone(newUser.getPhone());
        userRepository.save(userEntity);
        JSONObject userObj = new JSONObject();
        userObj.put("phone",newUser.getPhone());
        userObj.put("name",newUser.getName());
        userObj.put("email",newUser.getEmail());
        kafkaTemplate.send(USER_CREATE_TOPIC,objectMapper.writeValueAsString(userObj));
    }

    public UserEntity get(Integer userId) throws Exception {
        UserEntity user = cacheRepository.get(userId);
        if (!ObjectUtils.isEmpty(user)) {
            return user;
        }
        user = userRepository.findById(userId).orElseThrow(Exception::new);
        cacheRepository.set(user);
        return user;
    }
}
