package com.bigin.game.controller;

import com.bigin.game.common.exception.MonsterDeadException;
import com.bigin.game.common.exception.UserDeadException;
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
@RequestMapping("/orc")
@RequiredArgsConstructor
public class OrcController {

  private final OrcService orcService;
  private final MonsterService monsterService;

  /**
   * 오크 유저 생성
   * @return
   */
  @GetMapping("/selectOrc")
  public ResponseEntity selectOrc() {
    try {
      orcService.makeUser();
      monsterService.setUserTribe(orcService.getUser());
      return new ResponseEntity<>(orcService.getUser(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * 오크 유저 정보
   * @return
   */
  @GetMapping("/userStat")
  public ResponseEntity userStat() {
    try {
      orcService.getUser().checkAlive();
      return new ResponseEntity<>(orcService.getUser(), HttpStatus.OK);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * 오크 유저 스킬 사용
   * @param param 스킬 정보
   * @return
   */
  @PostMapping(
      value = "/useSkill", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity useSkill(@RequestBody HashMap<String, Object> param) {
    try {
      orcService.getUser().checkAlive();
      String skillName = param.get("skillName") == null ? "" : param.get("skillName").toString();
      orcService.useSkill(skillName);
      return new ResponseEntity<>(orcService.getUser(), HttpStatus.OK);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * 오크 유저 무기 사용
   * @param param 무기 정보
   * @return
   */
  @PostMapping(
      value = "/useWeapon", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity useWeapon(@RequestBody HashMap<String, Object> param) {
    try {
      orcService.getUser().checkAlive();
      String weaponName = param.get("weaponName") == null ? "" : param.get("weaponName").toString();
      orcService.useWeapon(weaponName);
      return new ResponseEntity<>(orcService.getUser(), HttpStatus.OK);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * 오크 유저 공격
   * @return
   */
  @GetMapping("/userAttack")
  public ResponseEntity userAttack() {
    try {
      orcService.getUser().checkAlive();
      monsterService.getMonster().checkAlive();
      orcService.attackMonster(monsterService.getMonster());

      if (!monsterService.getMonster().isAlive()) {
        orcService.levelUp();
      }
      HashMap<String, Object> responseData = new HashMap<>();
      responseData.put("userInfo", orcService.getUser());
      responseData.put("monsterInfo", monsterService.getMonster());
      return new ResponseEntity<>(responseData, HttpStatus.OK);
    } catch (MonsterDeadException e) {
      return new ResponseEntity<>("monster is dead please encounter monster", HttpStatus.BAD_REQUEST);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * 오크 유저 특수 공격
   * @return
   */
  @GetMapping("/userAttack/{uniqueSkill}")
  public ResponseEntity userAttackWithSkill(@PathVariable String uniqueSkill) {
    try {
      orcService.getUser().checkAlive();
      monsterService.getMonster().checkAlive();
      orcService.attackMonsterWithSkill(monsterService.getMonster(), uniqueSkill);

      if (!monsterService.getMonster().isAlive()) {
        orcService.levelUp();
      }
      HashMap<String, Object> responseData = new HashMap<>();
      responseData.put("userInfo", orcService.getUser());
      responseData.put("monsterInfo", monsterService.getMonster());
      return new ResponseEntity<>(responseData, HttpStatus.OK);
    } catch (MonsterDeadException e) {
      return new ResponseEntity<>("monster is dead please encounter monster", HttpStatus.BAD_REQUEST);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }
}
