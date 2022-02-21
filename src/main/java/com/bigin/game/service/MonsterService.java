package com.bigin.game.service;

import com.bigin.game.common.constant.UserEtcStatPoint;
import com.bigin.game.common.constant.UserStatPoint;
import com.bigin.game.domain.Human;
import com.bigin.game.domain.Monster;
import com.bigin.game.domain.User;
import org.springframework.stereotype.Service;

@Service
public class MonsterService {

  Monster monster;
  User user;      //우선 공격 대상

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

  /**
   * 몬스터의 공격 user 정보에 따라 공격 대상이 달라진다.
   * 인간만의 특징으로 레벨당 1회에 한하여 부활
   * @param user 유저 정보
   * @return
   */
  public boolean monsterAttack(User user) {
    boolean userAlive;

    if (user == null) {
      user = this.user;
    }
    userAlive = monster.monsterAttack(user);

    if (!userAlive && user.getClass().getName().contains(UserEtcStatPoint.HUMAN_UP)) {
      Human tempHuman = (Human) user;

      if (tempHuman.isIronWill()) {
        String healthPoint = UserStatPoint.HEALTH_POINT.getStatName();
        userAlive = true;
        tempHuman.setAlive(true);
        tempHuman.setIronWill(false);
        tempHuman.getStatPoint().put(healthPoint, tempHuman.getOriginalStatPoint().get(healthPoint));
      }
    }

    return userAlive;
  }
}
