package com.project.doduchat.controller;

import com.project.doduchat.domain.dto.ApplyResultDTO;
import com.project.doduchat.domain.dto.MenteeDTO;
import com.project.doduchat.service.ApplyService;
import com.project.doduchat.service.MenteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MenteeController {

    private final MenteeService menteeService;
    private final ApplyService applyService;

    // mentee 개인 페이지
    @GetMapping("/mypage/mentee/{id}")
    public MenteeDTO getMenteeById(@PathVariable String id) {
        Long menteeId = Long.parseLong(id);
        return menteeService.findById(menteeId);
    }

    @GetMapping("/api/v1/menteeApplyList/{id}")
    public List<ApplyResultDTO> getMenteeApplyList(@PathVariable String id) {
        return applyService.findAllByMenteeId(Long.parseLong(id));
    }

}