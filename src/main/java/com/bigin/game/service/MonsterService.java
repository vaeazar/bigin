package com.bigin.game.service;

import com.bigin.game.domain.Monster;
import com.bigin.game.domain.User;
import org.springframework.stereotype.Service;

@Service
public class MonsterService {

  Monster monster;

  public void makeMonster() {
    this.monster = new Monster();
  }

  public void makeMonster(String monsterName) {
    this.monster = new Monster(monsterName);
  }

  public boolean monsterAttack(User user) {
    return monster.monsterAttack(user);
  }
}
