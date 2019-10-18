package michal.task2.controller;

import michal.task2.model.User;
import michal.task2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class RemoveUser {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = {"/remove"}, params = "remove", method = RequestMethod.POST)
    public ModelAndView removeUser(ModelAndView modelAndView,
                                 @RequestParam(name = "id", required = true) long userId) {

        Optional<User> user = userRepository.findById(userId);
        userRepository.deleteById(userId);
        //if (user.isPresent()) userRepository.delete(userRepository.findById(userId));
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
