package com.bigin.game.domain;

import com.bigin.game.common.constant.MonsterStatPoint;
import java.util.HashMap;
import lombok.Data;

@Data
public class Monster {

  protected int healthPoint;
  protected int damage;
  protected int defend;
  protected double attackSpeed;
  protected double counter;
  protected boolean alive;
  protected HashMap<String, Double> statPoint;
  long lastAttackTime;

  public Monster() {
    MonsterStatPoint monsterStatPoint = MonsterStatPoint.selection("weekMonster");
    this.statPoint = new HashMap<>();
    for (int i = 0; i < monsterStatPoint.getStatValue().length; i++) {
      statPoint.put(monsterStatPoint.getStatName()[i], monsterStatPoint.getStatValue()[i]);
    }
    this.lastAttackTime = 0;
    this.counter = 70;
    this.alive = true;
  }

  public Monster(String monsterName) {
    MonsterStatPoint monsterStatPoint = MonsterStatPoint.selection(monsterName);
    this.statPoint = new HashMap<>();
    for (int i = 0; i < monsterStatPoint.getStatValue().length; i++) {
      statPoint.put(monsterStatPoint.getStatName()[i], monsterStatPoint.getStatValue()[i]);
    }
    this.lastAttackTime = 0;
    this.counter = 70;
    this.alive = true;
  }

  public boolean monsterAttack(User user) {
    long now = System.currentTimeMillis();
    Double damage = this.statPoint.get("damage");
    Double attackSpeed = this.statPoint.get("attackSpeed");

    if (now > this.lastAttackTime) {
      long attackDelay = (long) (Math.round((1000.0 / attackSpeed) * 10) / 10.0);
      this.lastAttackTime = now + attackDelay;
      user.monsterAttackUser(damage);
    }
    return user.alive;
  }

  public boolean isCounterAttack() {
    double randomValue = Math.random() * 100;
    return this.counter > randomValue;
  }

  public boolean userAttackMonster(User user) {
    double userDamage = user.statPoint.get("damage");
    double monsterDefend = this.statPoint.get("defend");
    double monsterHealthPoint = this.statPoint.get("healthPoint");
    double realDamage = Math.max(userDamage - monsterDefend, 0);
    double resultHealth = monsterHealthPoint - realDamage;

    if (resultHealth <= 0) {
      this.alive = false;
      this.statPoint.put("healthPoint", 0.0);
    } else if (isCounterAttack()) {
      int counter = (int) Math.round(this.damage * 0.7);
      user.monsterAttackUser(counter);
    }
    return this.alive;
  }
}
