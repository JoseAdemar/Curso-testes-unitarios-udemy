package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserEntityIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private UserEntity userEntity;

    @BeforeEach
    void setup(){
        userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("Sergey");
        userEntity.setLastName("Kargopolov");
        userEntity.setEmail("teste@email.com");
        userEntity.setEncryptedPassword("12345678");

    }

    @Test
    void testUserEntity_whenValidUserDetailsProvided_shouldReturnStoredUserDetails(){
        //Arrange

        //Act
        UserEntity storedUserEntity = testEntityManager.persistAndFlush(userEntity);

        //Assert
        assertTrue(storedUserEntity.getId() > 0);
        assertEquals(userEntity.getUserId(), storedUserEntity.getUserId());
        assertEquals(userEntity.getFirstName(), storedUserEntity.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserEntity.getLastName());
        assertEquals(userEntity.getEmail(), storedUserEntity.getEmail());
        assertEquals(userEntity.getEncryptedPassword(), storedUserEntity.getEncryptedPassword());

    }

    @Test
    void testUserEntity_whenFirstNameIsTooLong_shouldThrowException(){
        //Arrange
        userEntity.setFirstName("Sergeyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");

        //Assert e Act
        assertThrows(PersistenceException.class, () -> {
            UserEntity storedUserEntity = testEntityManager.persistAndFlush(userEntity);
        });
    }

    @Test
    void testUserEntity_whenExistingUserProvided_shouldThrowException(){
        //Arrange
        UserEntity newEntity = new UserEntity();
        newEntity.setUserId("1");
        newEntity.setFirstName("Sergey");
        newEntity.setLastName("Kargopolov");
        newEntity.setEmail("teste@email.com");
        newEntity.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(newEntity);

        //Update existing user entity with the same user id
        userEntity.setUserId("1");

        //Assert e Act
        assertThrows(PersistenceException.class, () -> {
            testEntityManager.persistAndFlush(userEntity);
        });
    }
}
