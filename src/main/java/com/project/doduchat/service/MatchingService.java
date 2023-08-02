package com.project.doduchat.service;

import com.project.doduchat.domain.Matching;
import com.project.doduchat.domain.dto.MatchingDTO;
import com.project.doduchat.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MatchingService {

  private final MatchingRepository matchingRepository;

  @Transactional
  public MatchingDTO saveMatching(MatchingDTO matchingDTO) {
    Matching match = new Matching();
    match.setId(matchingDTO.getId());
    match.setSelectedMatchTime(matchingDTO.getSelectedMatchTime());
    match.setStatus(matchingDTO.getStatus());
    match.setApply(matchingDTO.getApply());

    matchingRepository.save(match);

    return new MatchingDTO(match);
  }

  //admin 매칭 관리
  public List<Matching> findAll(){
    return matchingRepository.findAll();
  }

  public List<Matching> findAll(String sortBy) {
    Sort sort;
    switch (sortBy) {
      case "id": // 번호순
        sort = Sort.by(Sort.Direction.ASC, "id");
        System.out.println("sort테스트 1" + sort);
        break;
      case "menteeName": // 멘티이름순
        sort = Sort.by(Sort.Direction.ASC, "apply.mentee.nickname");
        break;
      case "mentorName": // 멘토이름순
        sort = Sort.by(Sort.Direction.ASC, "apply.mentor.nickname");
        break;
      case "matchStatus": // 진행상태순
        sort = Sort.by(Sort.Direction.ASC, "status");
        break;
      case "applyDate": // 신청일자순
        sort = Sort.by(Sort.Direction.ASC, "indate");
        break;
      default:
        sort = Sort.by(Sort.Direction.ASC, "id");
    }
    System.out.println(sort);
    return matchingRepository.findAll(sort);
  }

  public List<MatchingDTO> findAllByApply_Mentee(Long id) {
    return matchingRepository.findAllByApply_Mentee_Id(id).stream()
            .map(MatchingDTO::new)
            .collect(Collectors.toList());
  }
}
