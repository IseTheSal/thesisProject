package com.example.demo.service;

import com.example.demo.controller.AdminController;
import com.example.demo.model.users.Admin;
import com.example.demo.model.users.Courier;
import com.example.demo.model.users.Moderator;
import com.example.demo.model.users.User;
import com.example.demo.repsitory.AdminRepository;
import com.example.demo.repsitory.CourierRepository;
import com.example.demo.repsitory.ModeratorRepository;
import com.example.demo.repsitory.UserRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;


@Data
@Service
public class AdminService {

    private final UserService userService;

    private final AdminRepository adminRepository;
    private final ModeratorRepository moderatorRepository;
    private final CourierRepository courierRepository;
    private final UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(AdminService.class);

    public HttpStatus createAdmin(Admin admin) {
        if (!adminRepository.existsByLogin(admin.getLogin())) {
            adminRepository.save(admin);
            logger.info("Admin created successfully");
            return HttpStatus.OK;
        }
        logger.error("Admin doesn`t created");
        return HttpStatus.BAD_REQUEST;
    }

    public HttpStatus authAdmin(String login, String password) {
        if (adminRepository.existsAdminByLoginAndPassword(login, password)) {
            logger.info("Admin logged in");
            return HttpStatus.ACCEPTED;
        }
        logger.error("Admin didn`t logged in");
        return HttpStatus.BAD_REQUEST;
    }

    public HttpStatus createModerator(Moderator moderator) {
        if (!moderatorRepository.existsByLoginAndPassword(moderator.getLogin(), moderator.getPassword())) {
            moderatorRepository.save(moderator);
            logger.info("Moderator was created");
            return HttpStatus.OK;
        }
        logger.error("Moderator wasn`t created");
        return HttpStatus.BAD_REQUEST;
    }

    public Moderator getModeratorByLogin(String login) {
        return moderatorRepository.getByLogin(login);
    }

    public List<Moderator> getAllModerators() {
        return moderatorRepository.findAll();
    }

    @Transactional
    public HttpStatus deleteModeratorByLogin(String login) {
        if (moderatorRepository.deleteByLogin(login) > 0) {
            logger.info("Moderator successfully deleted");
            return HttpStatus.OK;
        }
        logger.error("Moderator with this doesn`t exist");
        return HttpStatus.BAD_REQUEST;
    }


    public HttpStatus createCourier(Courier courier) {
        if (!courierRepository.existsByLoginAndPassword(courier.getLogin(), courier.getPassword())) {
            courierRepository.save(courier);
            logger.info("Courier successfully created");
            return HttpStatus.OK;
        }
        logger.info("Courier wasn`t created");
        return HttpStatus.BAD_REQUEST;
    }

    public Courier getCourierByLogin(String login) {
        return courierRepository.getByLogin(login);
    }

    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    @Transactional
    public HttpStatus deleteCourierByLogin(String login) {
        if (courierRepository.deleteByLogin(login) > 0) {
            logger.info("Courier deleted successfully");
            return HttpStatus.OK;
        }
        logger.error("Courier with login " + login + " was`nt delete");
        return HttpStatus.BAD_REQUEST;
    }

    public HttpStatus createUser(User user) {
        return userService.createUser(user);
    }

    public User getUser(String login) {
        return userRepository.getUserByLogin(login);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public HttpStatus deleteUserByLogin(String login) {
        return userService.deleteByLogin(login);
    }


}
