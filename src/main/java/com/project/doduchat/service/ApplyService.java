package com.project.doduchat.service;

import com.project.doduchat.domain.Apply;
import com.project.doduchat.domain.Matching;
import com.project.doduchat.domain.dto.ApplyFormDTO;
import com.project.doduchat.domain.dto.ApplyResultDTO;
import com.project.doduchat.repository.ApplyListRepository;
import com.project.doduchat.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ApplyService {

  private final ApplyListRepository applyListRepository;
  private final MatchingRepository matchingRepository;

  public List<ApplyResultDTO> findAllByMenteeId(Long id) {
    return applyListRepository.findAllByMenteeId(id).stream()
            .map(ApplyResultDTO::new)
            .collect(Collectors.toList());
  }

  public ApplyResultDTO findByMenteeIdAndMentorId(Long menteeId, Long mentorId) {
    Apply entity = applyListRepository.findByMentee_IdAndMentor_Id(menteeId, mentorId)
            .orElseThrow(() -> new IllegalArgumentException("해당 멘토에게 지원하지 않았습니다. id = " + mentorId));

    return new ApplyResultDTO(entity);
  }

  public ApplyResultDTO findById(Long id) {
    Apply entity = applyListRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

    return new ApplyResultDTO(entity);
  }

  public int checkMatchTime(ApplyFormDTO applyFormDTO) {
    List<String> matchTimeList = Arrays.asList(
            applyFormDTO.getMatchTime1(),
            applyFormDTO.getMatchTime2(),
            applyFormDTO.getMatchTime3()
    );

    for (String match : matchTimeList) {
      if (LocalDateTime.now().isAfter(LocalDateTime.parse(match))) throw new RuntimeException("과거의 날짜는 신청할 수 없어요!");
    }

    List<Matching> matching = matchingRepository.findAllByApply_Mentor(applyFormDTO.getMentor());
    if (matching.size() == 0) return 0;

    for (int i = 0; i < matchTimeList.size(); i++) {
      for (Matching match : matching) {
        if (matchTimeList.get(i).substring(0, 13).equals(match.getSelectedMatchTime().substring(0, 13))) {
          return i + 1;
        }
      }
    }
    return 0;
  }

  @Transactional(rollbackFor = RuntimeException.class)
  public ApplyFormDTO saveApply(ApplyFormDTO applyFormDTO) {

    int status = checkMatchTime(applyFormDTO);

    if (status != 0) throw new RuntimeException(status + " 번째 제안 시간은 이미 멘토님의 dodu가 잡혀있는 시간이에요ㅠ");

    Apply apply = new Apply();
    apply.setId(applyFormDTO.getId());
    apply.setMatchTime1(applyFormDTO.getMatchTime1());
    apply.setMatchTime2(applyFormDTO.getMatchTime2());
    apply.setMatchTime3(applyFormDTO.getMatchTime3());
    apply.setStatus(applyFormDTO.getStatus());
    apply.setIndate(applyFormDTO.getIndate());
    apply.setMentee(applyFormDTO.getMentee());
    apply.setMentor(applyFormDTO.getMentor());
    apply.setQuestion(applyFormDTO.getQuestion());

    applyListRepository.save(apply);

    if (applyListRepository.findAllByMentee_IdAndMentor_Id(apply.getMentee().getId(), apply.getMentor().getId()).size() >= 2) {
      throw new RuntimeException("이미 신청한 멘토입니다.");
    }

    return ApplyFormDTO.applyDto(apply);
  }

  public void updateApplyStatus(Long id, int status) {
    Apply apply = applyListRepository.findById(id).orElse(new Apply());
    if (apply.equals(new Apply())) throw new RuntimeException("Apply not found with id : " + id);
    if (apply.getStatus() == 0) apply.setStatus(1);
    applyListRepository.save(apply);
  }
  public List<Apply> findAll(String sortBy) {
    Sort sort;
    switch (sortBy) {
      case "id": // 신청서 번호순
        sort = Sort.by(Sort.Direction.ASC, "id");
        break;
      case "menteeName": // 멘티이름순
        sort = Sort.by(Sort.Direction.ASC, "mentee.nickname");
        break;
      case "mentorName": // 멘토이름순
        sort = Sort.by(Sort.Direction.ASC, "mentor.nickname");
        break;
      case "applyStatus": // 수락 여부순
        sort = Sort.by(Sort.Direction.ASC, "status");
        break;
      default:
        sort = Sort.by(Sort.Direction.ASC, "id");
    }
    return applyListRepository.findAll(sort);
  }
}
