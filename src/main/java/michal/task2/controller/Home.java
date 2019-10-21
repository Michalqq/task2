package michal.task2.controller;

import michal.task2.model.User;
import michal.task2.repository.UserRepository;
import michal.task2.service.FileEncoder;
import michal.task2.service.UserSaver;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.util.List;


@Controller
public class Home {

    private UserRepository userRepository;
    private UserSaver userSaver;
    private FileEncoder fileEncoder;

    @Autowired
    public Home(UserRepository userRepository,UserSaver userSaver, FileEncoder fileEncoder) {
        this.userRepository = userRepository;
        this.userSaver = userSaver;
        this.fileEncoder = fileEncoder;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getHome(ModelAndView modelAndView) {
        return this.homeGenerator(modelAndView, userRepository.findAll());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView submit(@RequestParam("file") MultipartFile file, ModelAndView modelAndView) throws IOException {
        modelAndView.addObject("file", file);
        return this.homeGenerator(modelAndView, fileEncoder.fileRead(file));
    }

    public ModelAndView homeGenerator(ModelAndView modelAndView, List<User> users){
        modelAndView.addObject("users", users);
        modelAndView.addObject("fileNameList", userRepository.fileNameList());
        modelAndView.setViewName("home");
        return modelAndView;
    }




}
