package com.project.doduchat.controller;

import com.project.doduchat.domain.dto.*;
import com.project.doduchat.service.ApplyService;
import com.project.doduchat.service.ChatService;
import com.project.doduchat.service.MatchingService;
import com.project.doduchat.service.MentorService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static com.project.doduchat.utils.StringToUuid.stringtoUUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ApiController {

  private final MentorService mentorService;
  private final ApplyService applyService;
  private final MatchingService matchingService;
  private final ChatService chatService;
  @Value("${chatgpt.api.key.script}")
  private String chatgptApiKeyScript;
  private final HttpSession httpSession;
  @Value("${server.url}")
  private String url;

  @GetMapping("/carousel")
  public ResponseEntity<List<MentorDTO>> getMentorsByCategoryId(@RequestParam(defaultValue = "0", required = false) String categoryId) {
    List<MentorDTO> mentors = mentorService.findAllByCategoryId(categoryId);
    return ResponseEntity.ok(mentors);
  }

//  @GetMapping("/mypage/{id}")
//  public ResponseEntity<MentorDTO> getMentorById(@PathVariable String id) {
//    UUID uuid = stringtoUUID(id);
//    return ResponseEntity.ok(mentorService.findById(id));
//  }

  @GetMapping("/send")
  public ResponseEntity<MailDTO> sendEmail(@RequestParam String address, @RequestParam String title, @RequestParam String message) {
    MailDTO dto = new MailDTO();
    dto.setAddress(address);
    dto.setTitle(title);
    dto.setMessage(message);

    mentorService.sendMail(dto);
    return ResponseEntity.ok(dto);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/offer/saveApply")
  public void saveApply(ApplyFormDTO applyFormDTO, HttpServletResponse res) throws IOException {
    try {
      ApplyFormDTO apply = applyService.saveApply(applyFormDTO);
      sendEmail(
              apply.getMentor().getEmail(),
              apply.getMentor().getNickname() + "님 새로운 dodu 신청서가 도착했어요!",
              url + apply.getId()
      );
      res.sendRedirect("/mentee/applyResult?menteeId=" + apply.getMentee().getId() + "&mentorId=" + apply.getMentor().getId());
    } catch (Exception e) {
      String message = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
      String redirectUrl = "/applyForm/" + applyFormDTO.getMentor().getId() + "?alert=true&message=" + message;
      res.sendRedirect(redirectUrl);
    }
  }

  public ResponseEntity<String> updateApplyStatus(Long applyId, int status) {
    try {
      if(status == 0) {
        applyService.updateApplyStatus(applyId, status);
        return ResponseEntity.ok("Apply status updated successfully");
      } else return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/saveMatching")
  public void saveMatching(MatchingDTO matchingDTO, HttpServletResponse response) throws IOException {
    MatchingDTO match = matchingService.saveMatching(matchingDTO);
    updateApplyStatus(match.getApply().getId(), match.getApply().getStatus());
    ChatDTO chat = new ChatDTO(match.getApply().getMentee(), match.getApply().getMentor(), match.getSelectedMatchTime());
    ChatDTO chatResult = chatService.createChat(chat);
    sendEmail(
            match.getApply().getMentor().getEmail(),
            match.getApply().getMentor().getNickname() + "님, " + match.getApply().getMentee().getNickname()  + " 멘티와의 매칭이 완료됐어요!",
            url + chatResult.getId()
    );
    sendEmail(
            match.getApply().getMentee().getEmail(),
            match.getApply().getMentee().getNickname() + "님, " + match.getApply().getMentor().getNickname() + " 멘토가 DoDu를 수락했어요!",
            url + chatResult.getId()
    );
    //response.sendRedirect("/");
    response.sendRedirect("https://www.google.com/"); // 멘토는 완료 후 전혀 다른 화면(구글/네이버)로 리다이렉트
  }

  @GetMapping("/mypage/{id}/applyList")
  public List<ApplyResultDTO> getApplyListById(@PathVariable String id) {
    UUID uuid = stringtoUUID(id);
    return mentorService.findAllDesc(id);
  }

  @GetMapping("/applyResult")
  public ApplyResultDTO getApplyResultRest(@RequestParam String menteeId, @RequestParam String mentorId) {
    return applyService.findByMenteeIdAndMentorId(Long.parseLong(menteeId), Long.parseLong(mentorId));
  }

  @GetMapping("/config")
  public String getConfig() {
    return chatgptApiKeyScript;
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleMyException(RuntimeException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

}
