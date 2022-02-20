package com.bigin.game.controller;

import com.bigin.game.common.exception.UserDeadException;
import com.bigin.game.service.ElfService;
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
@RequestMapping("/elf")
@RequiredArgsConstructor
public class ElfController {

  private final ElfService elfService;
  private final MonsterService monsterService;

  @GetMapping("/selectElf")
  public ResponseEntity selectElf() {
    try {
      elfService.makeElf();
      monsterService.setUserTribe(elfService.getElf());
      return new ResponseEntity<>(elfService.getElf(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/userStat")
  public ResponseEntity userStat() {
    try {
      elfService.getElf().checkAlive();
      return new ResponseEntity<>(elfService.getElf(), HttpStatus.OK);
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
      elfService.getElf().checkAlive();
      String skillName = param.get("skillName") == null ? "" : param.get("skillName").toString();
      elfService.useSkill(skillName);
      return new ResponseEntity<>(elfService.getElf(), HttpStatus.OK);
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
      elfService.getElf().checkAlive();
      String weaponName = param.get("weaponName") == null ? "" : param.get("weaponName").toString();
      elfService.useWeapon(weaponName);
      return new ResponseEntity<>(elfService.getElf(), HttpStatus.OK);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/userAttack")
  public ResponseEntity userAttack() {
    try {
      elfService.getElf().checkAlive();
      elfService.attackMonster(monsterService.getMonster());

      if (!monsterService.getMonster().isAlive()) {
        elfService.levelUp();
      }
      HashMap<String, Object> responseData = new HashMap<>();
      responseData.put("userInfo", elfService.getElf());
      responseData.put("monsterInfo", monsterService.getMonster());
      return new ResponseEntity<>(responseData, HttpStatus.OK);
    } catch (UserDeadException e) {
      return new ResponseEntity<>("character is dead please select character", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
    }
  }
}
