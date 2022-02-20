package com.bigin.game.service;

import com.bigin.game.domain.Monster;
import com.bigin.game.domain.User;
import org.springframework.stereotype.Service;

@Service
public class MonsterService {

  Monster monster;
  User user;

  public void makeMonster() {
    this.monster = new Monster();
  }

  public void makeMonster(String monsterName) {
    this.monster = new Monster(monsterName);
  }

  public Monster getMonster() {
    return this.monster;
  }

  public void setUserTribe(User user) {
    this.user = user;
  }

  public User getUserInfo() {
    return this.user;
  }

  public boolean monsterAttack() {
    return monster.monsterAttack(user);
  }

  public boolean monsterAttack(User user) {
    return monster.monsterAttack(user);
  }
}
