package com.bigin.game.domain;

import com.bigin.game.common.constant.MonsterEtcStatPoint;
import com.bigin.game.common.constant.MonsterStatPoint;
import com.bigin.game.common.exception.MonsterDeadException;
import java.util.HashMap;
import lombok.Data;

@Data
public class Monster {

  protected double counterPercent;
  protected double counterDamage;
  protected boolean alive;
  protected HashMap<String, Double> statPoint;
  long lastAttackTime;

  /**
   * 몬스터가 죽었다면 예외를 발생시켜 다른 기능을 막는다.
   */
  public void checkAlive() {
    if (!this.alive) {
      throw new MonsterDeadException();
    }
  }

  public Monster() {
    MonsterStatPoint monsterStatPoint = MonsterStatPoint.selection("weekMonster");
    this.statPoint = new HashMap<>();
    for (int i = 0; i < monsterStatPoint.getStatValue().length; i++) {
      statPoint.put(monsterStatPoint.getStatName()[i], monsterStatPoint.getStatValue()[i]);
    }
    this.lastAttackTime = 0;
    this.counterPercent = MonsterEtcStatPoint.MONSTER_COUNTER_PERCENT;
    this.counterDamage = MonsterEtcStatPoint.MONSTER_COUNTER_DAMAGE;
    this.alive = true;
  }

  /**
   * 특수한 몬스터를 생성한다.
   * @param monsterName 몬스터 명
   */
  public Monster(String monsterName) {
    MonsterStatPoint monsterStatPoint = MonsterStatPoint.selection(monsterName);
    this.statPoint = new HashMap<>();
    for (int i = 0; i < monsterStatPoint.getStatValue().length; i++) {
      statPoint.put(monsterStatPoint.getStatName()[i], monsterStatPoint.getStatValue()[i]);
    }
    this.lastAttackTime = 0;
    this.counterPercent = MonsterEtcStatPoint.MONSTER_COUNTER_PERCENT;
    this.counterDamage = MonsterEtcStatPoint.MONSTER_COUNTER_DAMAGE;
    this.alive = true;
  }

  /**
   * 몬스터의 유저 공격
   * @param user 유저 정보
   * @return
   */
  public boolean monsterAttack(User user) {
    long now = System.currentTimeMillis();

    if (now > this.lastAttackTime) {
      Double damage = this.statPoint.get("damage");
      double attackSpeed = this.statPoint.get("attackSpeed") < 1 ? 1 : this.statPoint.get("attackSpeed");
      long attackDelay = (Math.round((1000.0 / attackSpeed) * 10)) * 100;
      this.lastAttackTime = now + attackDelay;
      user.monsterAttackUser(damage);
    }
    return user.alive;
  }

  /**
   * 일정 확률로 일정 데미지를 반격한다.
   * @return
   */
  public boolean checkCounterAttack() {
    double randomValue = Math.random() * 100;
    return this.counterPercent > randomValue;
  }

  /**
   * 유저가 몬스터를 공격 할 시 사용되는 메소드
   * @param user 유저 정보
   * @return
   */
  public boolean userAttackMonster(User user) {
    double userDamage = user.statPoint.get("damage");
    double monsterDefend = this.statPoint.get("defend");
    double monsterHealthPoint = this.statPoint.get("healthPoint");
    double monsterDamage = this.statPoint.get("damage");
    double realDamage = Math.max(userDamage - monsterDefend, 0);
    double resultHealth = monsterHealthPoint - realDamage;

    if (resultHealth <= 0) {
      this.alive = false;
      this.statPoint.put("healthPoint", 0.0);
    } else {
      this.statPoint.put("healthPoint", resultHealth);
    }

    if (this.alive && checkCounterAttack()) {
      double counter = Math.round(monsterDamage * this.counterDamage);
      user.monsterAttackUser(counter);
    }
    return this.alive;
  }
}
