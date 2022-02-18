package com.bigin.game.controller;

import com.bigin.game.service.MonsterService;
import com.bigin.game.service.OrcService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/orc")
@RequiredArgsConstructor
public class OrcController {

  private final OrcService orcService;
  private final MonsterService monsterService;

  @GetMapping("/selectOrc")
  public ResponseEntity selectOrc() {
    orcService.makeOrc();
    return new ResponseEntity<>(orcService.getOrc().getStatPoint(), HttpStatus.OK);
  }

  @GetMapping("/userStat")
  public ResponseEntity userStat() {

    return new ResponseEntity<>(orcService.getOrc().getStatPoint(), HttpStatus.OK);
  }

  @PostMapping("/useSkill")
  public ResponseEntity useSkill(@RequestParam(required = false, defaultValue = "fail") String skillName) {
    orcService.useSkillRenew(skillName);
    return new ResponseEntity<>(orcService.getOrc().getStatPoint(), HttpStatus.OK);
  }

  @GetMapping("/monsterEncounter")
  public ResponseEntity monsterEncounter() {
    monsterService.makeMonster();
    return new ResponseEntity<>(monsterService.getMonster().getStatPoint(), HttpStatus.OK);
  }

  @GetMapping("/userAttack")
  public ResponseEntity userAttack() {
    orcService.attackMonster(monsterService.getMonster());
    HashMap<String, Object> responseData = new HashMap<>();
    responseData.put("userInfo", orcService.getOrc());
    responseData.put("monsterInfo", monsterService.getMonster());
    return new ResponseEntity<>(responseData, HttpStatus.OK);
  }
}
