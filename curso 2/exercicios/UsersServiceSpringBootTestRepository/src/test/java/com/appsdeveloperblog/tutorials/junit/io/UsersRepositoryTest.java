package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UsersRepository usersRepository;

    private UserEntity userEntity1;

    private UserEntity userEntity2;

    private String email1 = "test1@email.com";
    private String email2 = "test2@email.com";
    private String userId1 = UUID.randomUUID().toString();
    private String userId2 = UUID.randomUUID().toString();

    @BeforeEach
    void setup(){
        userEntity1 = new UserEntity();
        userEntity1.setFirstName("Sergey");
        userEntity1.setLastName("Kargopolov");
        userEntity1.setEmail(email1);
        userEntity1.setUserId(userId1);
        userEntity1.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(userEntity1);

        userEntity2 = new UserEntity();
        userEntity2.setFirstName("Sergey");
        userEntity2.setLastName("Kargopolov");
        userEntity2.setEmail(email2);
        userEntity2.setUserId(userId2);
        userEntity2.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(userEntity2);


    }


    @Test
    void testFindByEmail_whenGivenCorrectEmail_returnsUserEntity(){
        //Arrange
        testEntityManager.persistAndFlush(userEntity1);

        //Act
        UserEntity storedUser = usersRepository.findByEmail(userEntity1.getEmail());

        //Assert
        assertEquals(userEntity1.getEmail(), storedUser.getEmail());
    }

    @Test
    void testFindByUserId_whenGivenCorrectId_returnsUserEntity(){
        //Arrange
        UserEntity storedUser = usersRepository.findByUserId(userId2);
        //Act  //Assert
        assertNotNull(storedUser);
        assertEquals(userId2, storedUser.getUserId());
    }

    @Test
    void testFindUsersWithEmailEndsWith_whenGiveEmailDomain_returnsUsersWithGivenDomain() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEmail("test@gmail.com");
        userEntity.setEncryptedPassword("123456789");
        userEntity.setFirstName("Sergey");
        userEntity.setLastName("Kargopolov");
        testEntityManager.persistAndFlush(userEntity);

        String emailDomainName = "@gmail.com";

        // Act
        List<UserEntity> users = usersRepository.findUsersWithEmailEndingWith(emailDomainName);

        // Assert
        assertEquals(1, users.size(),
                "There should be one user in the list");
        Assertions.assertTrue(users.get(0).getEmail().endsWith(emailDomainName),
                "User's email does not end with target domain name");
    }

}
