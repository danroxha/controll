package com.start.controll.controllers;

import com.start.controll.data.PasswordChange;
import com.start.controll.entities.User;
import com.start.controll.exceptions.UserNotFound;
import com.start.controll.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("dashboard/tools/usuarios")
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping
  public ModelAndView listUsers(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/tools/user/list.html") {{
      addObject("user", user);
      addObject("users", userService.findAllUsers());
    }};
  }

  @RequestMapping("adicionar")
  public ModelAndView registerUser(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/tools/user/form.html") {{
      addObject("user", user);
      addObject("newUser", new User());
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path = "adicionar")
  public ModelAndView registerUser(
      @AuthenticationPrincipal User user,
      @Valid User newUser,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {

    var userSaved = userService.findUserByEmail(newUser.getEmail());


    if(bindingResult.hasErrors() || userSaved.isPresent()) {
      return new ModelAndView("dashboard/tools/user/form.html") {{
        addObject("user", user);
        addObject("newUser", newUser);

        if(userSaved.isPresent())
          addObject("emailExistsError", "Email já registrado no sistema");
      }};
    }

    try {
      userService.createUser(newUser);
    }
    catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", String.format("Error ao tentar cadastrar usuário '%s' tente novamente", newUser.getName()));
      return new ModelAndView("redirect:/dashboard/tools/usuarios");
    }

    redirectAttributes.addFlashAttribute("message", String.format("Usuário '%s' foi cadastrado", newUser.getName()));

    return new ModelAndView("redirect:/dashboard/tools/usuarios");
  }

  @RequestMapping("perfil")
  public ModelAndView userProfile(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/tools/user/profile.html") {{
      addObject("user", user);
      addObject("userDetails", user);
    }};
  }

  @RequestMapping("perfil/senha")
  public ModelAndView userPasswordChange(@AuthenticationPrincipal User user) {
    return new ModelAndView("dashboard/tools/user/password.html") {{
      addObject("user", user);
      addObject("passwordChange", new PasswordChange());
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path = "perfil/senha")
  public ModelAndView userPasswordChange(
      @AuthenticationPrincipal User user,
      @Valid PasswordChange passwordChange,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) throws Exception {


    if( bindingResult.hasErrors() ||
        !passwordChange.isValidPassword() ||
        !passwordChange.isValidCurrentPassword(user.getPassword())) {

      return new ModelAndView("dashboard/tools/user/password.html") {{
        addObject("user", user);
        addObject("message", "Corrija os erros abaixo");
        addObject("passwordChange", passwordChange);

        if(!passwordChange.isValidPassword()) {
          addObject("errorPasswordMatch", "Senhas não são iguais");
        }

        if(!passwordChange.isValidCurrentPassword(user.getPassword())) {
          addObject("errorCurrentPasswordMatch", "Digite a senha atual válida");
        }

      }};
    }

    var userSaved = userService.updateUserPassword(user, passwordChange);

    if(userSaved.isEmpty())
      redirectAttributes.addFlashAttribute("message", "Senha não pode ser alterada");
    else
      redirectAttributes.addFlashAttribute("message", "Senha alterada com sucesso");


    return new ModelAndView("redirect:/dashboard/tools/usuarios/perfil/");
  }

  @RequestMapping("status")
  public ModelAndView enableOrDisableSCRUMMaster(@RequestParam Long id, RedirectAttributes redirectAttributes) {
    try {
      var user = userService.enableOrDisableUser(id);
      redirectAttributes.addFlashAttribute(
          "message",
          String.format("Usuário '%s' foi %s", user.getName(), user.isEnabled() ? "Ativado" : "Desativado")
      );
    }
    catch (UserNotFound u) {
      redirectAttributes.addFlashAttribute(
          "message",
          String.format("Usuário com ID '%s' não foi encontrado", id)
      );
    }

    return new ModelAndView("redirect:/dashboard/tools/usuarios");
  }
}
