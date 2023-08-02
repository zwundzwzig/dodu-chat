package com.project.doduchat.service;

import com.project.doduchat.domain.Mentee;
import com.project.doduchat.domain.dto.MenteeDTO;
import com.project.doduchat.repository.MenteeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class MenteeService {

    private final MenteeRepository menteeRepository;

    public MenteeDTO findById(Long id) {
        Mentee entity = menteeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        return new MenteeDTO(entity);
    }
    // 멘티 조회
    public List<Mentee> findAll(){
        return menteeRepository.findAll(Sort.by(Sort.Direction.DESC,"email"));
    }
    // 멘티 정렬
    public List<Mentee> findAll(String sortBy) {
        Sort sort;
        switch (sortBy) {
            case "id": // 번호순
                sort = Sort.by(Sort.Direction.ASC, "id");
                System.out.println(sort);
                break;
            case "name": // 이름순
                sort = Sort.by(Sort.Direction.ASC, "nickname");
                break;
            case "gender": // 성별순
                sort = Sort.by(Sort.Direction.ASC, "gender");
                break;
            default:
                sort = Sort.by(Sort.Direction.ASC, "id");
        }
        return menteeRepository.findAll(sort);
    }
}
