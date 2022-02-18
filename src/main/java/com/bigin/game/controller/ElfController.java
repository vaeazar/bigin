package com.bigin.game.controller;

import com.bigin.game.service.ElfService;
import com.bigin.game.service.MonsterService;
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
@RequestMapping("/elf")
@RequiredArgsConstructor
public class ElfController {

  private final ElfService elfService;
  private final MonsterService monsterService;

  @GetMapping("/selectelf")
  public ResponseEntity selectOrc() {
    elfService.makeElf();
    return new ResponseEntity<>(elfService.getElf().getStatPoint(), HttpStatus.OK);
  }

  @GetMapping("/userStat")
  public ResponseEntity userStat() {

    return new ResponseEntity<>(elfService.getElf().getStatPoint(), HttpStatus.OK);
  }

  @PostMapping("/useSkill")
  public ResponseEntity useSkill(@RequestParam(required = false, defaultValue = "fail") String skillName) {
    elfService.useSkillRenew(skillName);
    return new ResponseEntity<>(elfService.getElf().getStatPoint(), HttpStatus.OK);
  }

  @GetMapping("/monsterEncounter")
  public ResponseEntity monsterEncounter() {
    monsterService.makeMonster();
    return new ResponseEntity<>(monsterService.getMonster().getStatPoint(), HttpStatus.OK);
  }

  @GetMapping("/userAttack")
  public ResponseEntity userAttack() {
    monsterService.makeMonster();
    return new ResponseEntity<>(elfService.attackMonster(monsterService.getMonster()), HttpStatus.OK);
  }
}
