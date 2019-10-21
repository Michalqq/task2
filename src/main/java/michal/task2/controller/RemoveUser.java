package michal.task2.controller;

import michal.task2.model.LogsGenerator;
import michal.task2.model.User;
import michal.task2.repository.LogsRepository;
import michal.task2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class RemoveUser {

    private UserRepository userRepository;
    private LogsRepository logsRepository;
    @Autowired
    public RemoveUser(UserRepository userRepository, LogsRepository logsRepository){
        this.userRepository = userRepository;
        this.logsRepository = logsRepository;
    }

    @RequestMapping(value = {"/remove"}, params = "remove", method = RequestMethod.POST)
    public ModelAndView removeUser(ModelAndView modelAndView,
                                 @RequestParam(name = "id", required = true) long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            logsRepository.save(new LogsGenerator("Removed user : " + user.get().getName() + " " + user.get().getSurName()));
            userRepository.delete(user.get());
        }
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
    @RequestMapping(value = "/removeList", method = RequestMethod.POST)
    public String addNewItem(@RequestParam(value = "fileNameList", defaultValue = "") String fileName){
        List<User> users = userRepository.findUsersWhereFileNameIs(fileName);
        logsRepository.save(new LogsGenerator("Removed all users from file: " + fileName));
        for (User user:users){
            userRepository.delete(user);
        }
        return "redirect:/";
    }
}
