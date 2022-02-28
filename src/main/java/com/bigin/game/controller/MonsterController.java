package com.bigin.game.controller;

import com.bigin.game.common.exception.UserDeadException;
import com.bigin.game.domain.User;
import com.bigin.game.service.ElfService;
import com.bigin.game.service.HumanService;
import com.bigin.game.service.MonsterService;
import com.bigin.game.service.OrcService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/monster")
@RequiredArgsConstructor
public class MonsterController {

  private final MonsterService monsterService;
  private final HumanService humanService;
  private final ElfService elfService;
  private final OrcService orcService;

  /**
   * 기본 몬스터 생성 (WEEK)
   * @return
   */
  @GetMapping("/monsterEncounter")
  public ResponseEntity monsterEncounter() {
    monsterService.makeMonster();
    return new ResponseEntity<>(monsterService.getMonster(), HttpStatus.OK);
  }

  /**
   * 틀수 몬스터 생성
   * @param monsterName 생성 할 몬스터
   * @return
   */
  @GetMapping("/monsterEncounter/{monsterName}")
  public ResponseEntity monsterEncounter(@PathVariable String monsterName) {
    monsterService.makeMonster(monsterName);
    return new ResponseEntity<>(monsterService.getMonster(), HttpStatus.OK);
  }

  /**
   * 생성 된 몬스터 정보
   * @return
   */
  @GetMapping("/monsterStat")
  public ResponseEntity userStat() {
    return new ResponseEntity<>(monsterService.getMonster(), HttpStatus.OK);
  }

  /**
   * 몬스터 공격
   * @param param 공격 타겟 유저
   * @return
   */
  @PostMapping(
      value = "/monsterAttack", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity monsterAttack(@RequestBody HashMap<String, Object> param) {
    HashMap<String, Object> responseData = new HashMap<>();

    try {
      String tribe = String.valueOf(param.get("tribe"));
      User attackedUser = null;

      switch (tribe) {
        case "human":
          attackedUser = humanService.getUser();
          break;
        case "elf":
          attackedUser = elfService.getUser();
          break;
        case "orc":
          attackedUser = orcService.getUser();
          break;
      }

      if (attackedUser == null && monsterService.getUserInfo() != null) {
        monsterService.getUserInfo().checkAlive();
        monsterService.monsterAttack(null);
        responseData.put("userInfo", monsterService.getUserInfo());
      } else if (attackedUser != null) {
        attackedUser.checkAlive();
        monsterService.monsterAttack(attackedUser);
        responseData.put("userInfo", attackedUser);
      } else {
        return new ResponseEntity<>("select user please", HttpStatus.BAD_REQUEST);
      }

      responseData.put("monsterInfo", monsterService.getMonster());
      return new ResponseEntity<>(responseData, HttpStatus.OK);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("monster encounter must first!", HttpStatus.BAD_REQUEST);
    }
  }
}
