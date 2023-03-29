package com.project.doduchat.service;

import com.project.doduchat.domain.Mentor;
import com.project.doduchat.dto.*;
import com.project.doduchat.repository.ApplyListRepository;
import com.project.doduchat.repository.MentorRepository;
import com.project.doduchat.repository.SaveApplyRepository;
import com.project.doduchat.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MentorService {

    private final JavaMailSender javaMailSender;
    private final ApplyListRepository applyListRepository;
    private final MentorRepository mentorRepository;
    private final SaveApplyRepository saveApplyRepository;
    private final ApplyService applyService;
    private final VerificationRepository verificationRepository;

    public void sendMail(MailDTO mailDTO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mailDTO.getAddress());
        simpleMailMessage.setSubject(mailDTO.getTitle());
        simpleMailMessage.setText(mailDTO.getMessage());

        javaMailSender.send(simpleMailMessage);
    }

    @Transactional(readOnly = true)
    public List<MentorDTO> findAllByCategoryId(String id) {
        return mentorRepository.findAllByCategoryId(Long.parseLong(id)).stream()
                .map(MentorDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MentorDTO findById(String id) {
        Mentor entity = mentorRepository.findById(Long.parseLong(id))
              .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new MentorDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ApplyResultDTO> findAllDesc(String id) {
        return applyListRepository.findAllByIdOrderByIndateDesc(Long.parseLong(id)).stream()
                .map(ApplyResultDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ApplyFormDTO.GetApplyForm getApplyForm(String id) {
        Mentor entity = mentorRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new ApplyFormDTO.GetApplyForm(entity);
    }

    // 맨토 관리 및 정렬
    public List<Mentor> findAllPass(String sortBy){
        List<Mentor> resultList = null;
        switch (sortBy) {
            case "id": // 번호순
                resultList = mentorRepository.findAllByStatusOrStatusOrderByIdAsc(1,9);
                break;
            case "name": // 이름순
                resultList = mentorRepository.findAllByStatusOrStatusOrderByNicknameAsc(1,9);
                break;
            case "university": // 대학순
                resultList = mentorRepository.findAllByStatusOrStatusOrderByUniversityAsc(1,9);
                break;
            case "major": // 전공순
                resultList = mentorRepository.findAllByStatusOrStatusOrderByMajorAsc(1,9);
                break;
            case "gender": // 성별순
                resultList = mentorRepository.findAllByStatusOrStatusOrderByGenderAsc(1,9);
                break;
            default:
                System.out.println();
                resultList = mentorRepository.findAllByStatusOrStatusOrderByIdAsc(1,9);
        }
        System.out.println(resultList);
        return resultList;

    }
    //멘토 블랙 관리
    @Transactional
    public void updateMentorStatus(Long id, MentorDTO status){
        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 멘토는 존재하지 않습니다."));

        mentor.update(Integer.parseInt(status.getStatus()));
    }
}

