package com.bigin.game.controller;

import com.bigin.game.common.exception.UserDeadException;
import com.bigin.game.service.HumanService;
import com.bigin.game.service.MonsterService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/human")
@RequiredArgsConstructor
public class HumanController {

  private final HumanService humanService;
  private final MonsterService monsterService;

  @GetMapping("/selectHuman")
  public ResponseEntity selectHuman() {
    humanService.makeHuman();
    monsterService.setUserTribe(humanService.getHuman());
    return new ResponseEntity<>(humanService.getHuman(), HttpStatus.OK);
  }

  @GetMapping("/userStat")
  public ResponseEntity userStat() {
    try {
      humanService.getHuman().checkAlive();
      return new ResponseEntity<>(humanService.getHuman(), HttpStatus.OK);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(
      value = "/useSkill", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity useSkill(@RequestBody HashMap<String, Object> param) {
    try {
      humanService.getHuman().checkAlive();
      String skillName = param.get("skillName") == null ? "" : param.get("skillName").toString();
      humanService.useSkill(skillName);
      return new ResponseEntity<>(humanService.getHuman(), HttpStatus.OK);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(
      value = "/useWeapon", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity useWeapon(@RequestBody HashMap<String, Object> param) {
    try {
      humanService.getHuman().checkAlive();
      String weaponName = param.get("weaponName") == null ? "" : param.get("weaponName").toString();
      humanService.useWeapon(weaponName);
      return new ResponseEntity<>(humanService.getHuman(), HttpStatus.OK);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/userAttack")
  public ResponseEntity userAttack() {
    try {
      humanService.getHuman().checkAlive();
      humanService.attackMonster(monsterService.getMonster());

      if (!monsterService.getMonster().isAlive()) {
        humanService.levelUp();
      }
      HashMap<String, Object> responseData = new HashMap<>();
      responseData.put("userInfo", humanService.getHuman());
      responseData.put("monsterInfo", monsterService.getMonster());
      return new ResponseEntity<>(responseData, HttpStatus.OK);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }
}
