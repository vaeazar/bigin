package com.bigin.game.domain;

import lombok.Data;

@Data
public class Monster {

  protected int healthPoint;
  protected int damage;
  protected int defend;
  protected double counter;
  protected boolean alive;
  protected double attackSpeed;
  long lastAttackTime;

  public int monsterAttack(User user) {
    long now = System.currentTimeMillis();

    if (now > this.lastAttackTime) {
      long attackDelay = (long) (Math.round((1000.0 / attackSpeed) * 10) / 10.0);
      this.lastAttackTime = now + attackDelay;
      user.monsterAttackUser(this.damage);
      return 1;
    } else {
      return -1;
    }
  }

  public boolean isCounterAttack() {
    double randomValue = Math.random() * 100;
    return this.counter > randomValue;
  }

  public boolean userAttackMonster(User user) {
    int realDamage = Math.max(user.getDamage() - this.defend, 0);
    this.healthPoint -= realDamage;

    if (this.healthPoint <= 0) {
      this.alive = false;
    } else if (isCounterAttack()) {
      int counter = (int) Math.round(this.damage * 0.7);
      user.monsterAttackUser(counter);
    }
    return this.alive;
  }
}
