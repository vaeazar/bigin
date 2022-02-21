package com.bigin.game;

import static org.assertj.core.api.Assertions.assertThat;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.domain.Human;
import com.bigin.game.domain.Monster;
import com.bigin.game.service.HumanService;
import com.bigin.game.service.MonsterService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HumanServiceTest {

  @InjectMocks
  private HumanService humanService;
  @InjectMocks
  private MonsterService monsterService;

  @DisplayName("Human 생성")
  @Test
  void getHuman() {
    Human human = new Human();

    humanService.makeHuman();
    Human resultHuman = humanService.getHuman();

    assertThat(resultHuman).isNotNull();
    assertThat(resultHuman).isEqualTo(human);
  }

  @DisplayName("Human 공격")
  @Test
  void attackMonster() {
    Human human = new Human();

    humanService.makeHuman();
    String resultString = humanService.attackMonster(new Monster());

    assertThat(resultString).isNotNull();
    assertThat(resultString).isEqualTo("success");
    assertThat(humanService.getHuman().getLastAttackTime()).isNotEqualTo(human.getLastAttackTime());
  }

  @DisplayName("Human 공격")
  @Test
  void monsterAttackUser() {
    Human afterAttack = new Human();
    afterAttack.getStatPoint().merge("healthPoint", -5.0, Double::sum);
    afterAttack.getStatPoint().put("avoid",0.0);
    Monster monster = new Monster();
//    monster.getStatPoint().put("damage", 2000.0);

    humanService.makeHuman();
    humanService.getHuman().getStatPoint().put("avoid",0.0);
    boolean resultString = humanService.monsterAttackUser(monster.getStatPoint().get("damage"));

    assertThat(resultString).isNotNull();
    assertThat(humanService.getHuman()).isEqualTo(afterAttack);
  }

  @DisplayName("Human 죽음")
  @Test
  void userDead() {
    Monster monster = new Monster();
    monster.getStatPoint().put("damage", 2000.0);

    humanService.makeHuman();
    humanService.getHuman().getStatPoint().put("avoid",0.0);
    boolean result = humanService.monsterAttackUser(monster.getStatPoint().get("damage"));

    assertThat(result).isNotNull();
    assertThat(result).isFalse();
  }

  @DisplayName("Human Iron Will 사용")
  @Test
  void useIronWill() {
    monsterService.makeMonster("hardMonster");
    Monster monster = monsterService.getMonster();
    monster.getStatPoint().put("damage", 2000.0);

    humanService.makeHuman();
    humanService.getHuman().getStatPoint().put("avoid",0.0);
    boolean result = monsterService.monsterAttack(humanService.getHuman());
    boolean ironWill = humanService.getHuman().isIronWill();

    assertThat(result).isNotNull();
    assertThat(result).isTrue();
    assertThat(ironWill).isFalse();
  }

  @DisplayName("Human 레벨업")
  @Test
  void humanLevelUp() {
    humanService.makeHuman();
    int resultLevel = humanService.levelUp();

    assertThat(resultLevel).isEqualTo(2);
    assertThat(humanService.getHuman().getSkill().get(Skills.INVINCIBLE.getSkillName())).isNull();
  }

  @DisplayName("Human 궁극기 배움")
  @Test
  void humanGetUltimateSkill() {
    humanService.makeHuman();
    humanService.getHuman().setLevel(98);
    int resultLevel = humanService.levelUp();

    assertThat(resultLevel).isEqualTo(99);
    assertThat(humanService.getHuman().getSkill().get(Skills.INVINCIBLE.getSkillName())).isNotNull();
    assertThat(humanService.getHuman().getSkill().get(Skills.INVINCIBLE.getSkillName())).isTrue();
  }

  @DisplayName("Human 스킬")
  @Test
  void useSkill() {
    Human afterSkill = new Human();
    afterSkill.getStatPoint().merge("damage", 4.0, Double::sum);
    afterSkill.getStatPoint().merge("magicPoint", -20.0, Double::sum);
    afterSkill.getInActionSkills().put("steam",60);

    humanService.makeHuman();
    boolean result = humanService.useSkill("steam");
    Human resultHuman = humanService.getHuman();

    assertThat(result).isTrue();
    assertThat(resultHuman).isEqualTo(afterSkill);
  }

  @DisplayName("Human 스킬 조회")
  @Test
  void getSkillList() {
    Human human = new Human();

    humanService.makeHuman();
    List<String> result = humanService.getSkillList();

    assertThat(result).isNotEmpty();
    assertThat(result.size()).isEqualTo(3);
    assertThat(result).isEqualTo(new ArrayList<>(human.getSkill().keySet()));
  }

  @DisplayName("Human 장비")
  @Test
  void useWeapon() {
    Human afterWeapon = new Human();
    afterWeapon.getStatPoint().merge("damage", 1.0, Double::sum);
    afterWeapon.setWeapon("shortSword");

    humanService.makeHuman();
    boolean result = humanService.useWeapon("shortSword");
    Human resultHuman = humanService.getHuman();

    assertThat(result).isTrue();
    assertThat(resultHuman).isEqualTo(afterWeapon);
  }

  @DisplayName("Human 스킬 실패")
  @Test
  void failSkill() {
    Human afterSkill = new Human();

    humanService.makeHuman();
    boolean result = humanService.useSkill("nothing");
    Human resultHuman = humanService.getHuman();

    assertThat(result).isFalse();
    assertThat(resultHuman).isEqualTo(afterSkill);
  }

  @DisplayName("Human 장비 실패")
  @Test
  void failWeapon() {
    Human afterWeapon = new Human();
    afterWeapon.setWeapon("hand");

    humanService.makeHuman();
    boolean result = humanService.useWeapon("longBow");
    Human resultHuman = humanService.getHuman();

    assertThat(result).isFalse();
    assertThat(resultHuman).isEqualTo(afterWeapon);
  }
}
