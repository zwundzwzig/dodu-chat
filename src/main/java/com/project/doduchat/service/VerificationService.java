package com.project.doduchat.service;

import com.project.doduchat.domain.Verification;
import com.project.doduchat.domain.dto.VerificationDTO;
import com.project.doduchat.repository.VerificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VerificationService {

    private final VerificationRepository verificationRepository;

    //검증관리
    /*public List<Verification> findAll(){
        return verificationRepository.findAllByStatusNot(3);
    }*/

    public List<Verification> findAll(String sortBy) {
        Sort sort;
        switch (sortBy) {
            case "id": // 번호순
                sort = Sort.by(Sort.Direction.ASC, "mentor.id");
                System.out.println("sort테스트 1" + sort);
                break;
            case "name": // 이름순
                sort = Sort.by(Sort.Direction.ASC, "mentor.nickname");
                break;
            case "submitDate": // 제출일순
                sort = Sort.by(Sort.Direction.ASC, "indate");
                break;
            case "veriStatus": // 검증상태순
                sort = Sort.by(Sort.Direction.ASC, "status");
                System.out.println("sort테스트 2" + sort);
                break;
            default:
                sort = Sort.by(Sort.Direction.ASC, "mentor.id");
        }
        return verificationRepository.findAll(sort);
    }

    @Transactional
    public void updateStatus(Long id, VerificationDTO status){
        Verification verification = verificationRepository.findByMentorId(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 멘토는 존재하지 않습니다."));


        verification.update(status.getStatus());

    }

}

