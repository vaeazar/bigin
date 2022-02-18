package com.bigin.game.controller;

import com.bigin.game.domain.Human;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/human")
@RequiredArgsConstructor
public class HumanController {

  private final HumanService humanService;
  private final MonsterService monsterService;

  @GetMapping("/selectHuman")
  public ResponseEntity selectOrc() {
    humanService.makeHuman();
    return new ResponseEntity<>(humanService.getHuman(), HttpStatus.OK);
  }

  @GetMapping("/userStat")
  public ResponseEntity userStat() {

    return new ResponseEntity<>(humanService.getHuman(), HttpStatus.OK);
  }

  @PostMapping(
      value = "/useSkill",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity useSkill(@RequestParam(name = "skillName", required = false, defaultValue = "fail") String skillName) {
    humanService.useSkillRenew(skillName);
    return new ResponseEntity<>(humanService.getHuman(), HttpStatus.OK);
  }

  @GetMapping("/monsterEncounter")
  public ResponseEntity monsterEncounter() {
    monsterService.makeMonster();
    return new ResponseEntity<>(monsterService.getMonster(), HttpStatus.OK);
  }

  @GetMapping("/userAttack")
  public ResponseEntity userAttack() {
    humanService.attackMonster(monsterService.getMonster());
    HashMap<String, Object> responseData = new HashMap<>();
    responseData.put("userInfo", humanService.getHuman());
    responseData.put("monsterInfo", monsterService.getMonster());
    return new ResponseEntity<>(responseData, HttpStatus.OK);
  }
}
