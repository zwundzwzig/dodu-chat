package com.project.doduchat.controller;

import com.project.doduchat.config.auth.CustomOAuth2UserService;
import com.project.doduchat.config.auth.LoginUser;
import com.project.doduchat.config.auth.SessionUser;
import com.project.doduchat.dto.*;
import com.project.doduchat.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final CategoryService categoryService;
    private final MenteeService menteeService;
    private final MentorService mentorService;
    private final ApplyService applyService;
    private final MatchingService matchingService;
    // 추가
    private final CustomOAuth2UserService customOAuth2UserService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "0", required = false) Long categoryId, HttpSession session) {
        List<CategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/doduLogin")
    public String login(){
        return "login";
    }

    // 추가
    @GetMapping("/doduLogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/applyForm/{id}")
    public String getApplyForm(@PathVariable String id, Model model, @LoginUser SessionUser user, HttpSession session) {
        ApplyFormDTO.GetApplyForm applyFormDTO = mentorService.getApplyForm(id);
        model.addAttribute("mentorData", applyFormDTO);
        return "apply-form";
    }

    @GetMapping("/mentor/apply/confirm/{id}")
    public String getMentorApplyConfirm(@PathVariable String id, Model model) {
        ApplyResultDTO apply = applyService.findById(Long.parseLong(id));
        model.addAttribute("apply", apply);
        return "mentor-apply-confirm";
    }

    @GetMapping("/mentee/applyResult")
    public String getApplyResult(@RequestParam String menteeId, @RequestParam String mentorId, Model model) {
        ApplyResultDTO apply = applyService.findByMenteeIdAndMentorId(Long.parseLong(menteeId), Long.parseLong(mentorId));
        MenteeDTO mentee = menteeService.findById(Long.parseLong(menteeId));
        model.addAttribute("mentee", mentee);
        model.addAttribute("apply", apply);
        return "apply-result";
    }

    @GetMapping("/mentee/applyList/{id}")
    public String getMenteeApplyList(@PathVariable String id, Model model) {
        MenteeDTO mentee = menteeService.findById(Long.parseLong(id));
        List<ApplyResultDTO> applyList = applyService.findAllByMenteeId(Long.parseLong(id));
        List<MatchingDTO> match = matchingService.findAllByApply_Mentee(Long.parseLong(id));
        model.addAttribute("mentee", mentee);
        model.addAttribute("applyList", applyList);
        model.addAttribute("match", match);
        return "mentee-apply-list";
    }

    @GetMapping("/applyRefuse/{id}")
    public String refuseControl(@PathVariable String id, @RequestParam String message, Model model, @LoginUser SessionUser user, HttpSession session) {
        ApplyFormDTO.GetApplyForm applyFormDTO = mentorService.getApplyForm(id);
        model.addAttribute("mentorData", applyFormDTO);
        model.addAttribute("refuse", message);
        return "apply-form";
    }

}
