package com.bigin.game.controller;

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

  @GetMapping("/selectOrc")
  public ResponseEntity selectOrc() {
    try {
      orcService.makeOrc();
      monsterService.setUserTribe(orcService.getOrc());
      return new ResponseEntity<>(orcService.getOrc(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/userStat")
  public ResponseEntity userStat() {
    try {
      orcService.getOrc().checkAlive();
      return new ResponseEntity<>(orcService.getOrc(), HttpStatus.OK);
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
      orcService.getOrc().checkAlive();
      String skillName = param.get("skillName") == null ? "" : param.get("skillName").toString();
      orcService.useSkill(skillName);
      return new ResponseEntity<>(orcService.getOrc(), HttpStatus.OK);
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
      orcService.getOrc().checkAlive();
      String weaponName = param.get("weaponName") == null ? "" : param.get("weaponName").toString();
      orcService.useWeapon(weaponName);
      return new ResponseEntity<>(orcService.getOrc(), HttpStatus.OK);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/userAttack")
  public ResponseEntity userAttack() {
    try {
      if (!orcService.getOrc().isAlive()) {
        return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
      }
      orcService.attackMonster(monsterService.getMonster());

      if (!monsterService.getMonster().isAlive()) {
        orcService.levelUp();
      }
      HashMap<String, Object> responseData = new HashMap<>();
      responseData.put("userInfo", orcService.getOrc());
      responseData.put("monsterInfo", monsterService.getMonster());
      return new ResponseEntity<>(responseData, HttpStatus.OK);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }
}
