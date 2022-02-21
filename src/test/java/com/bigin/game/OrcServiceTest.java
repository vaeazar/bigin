package com.bigin.game;

import static org.assertj.core.api.Assertions.assertThat;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.domain.Orc;
import com.bigin.game.domain.Monster;
import com.bigin.game.service.MonsterService;
import com.bigin.game.service.OrcService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrcServiceTest {

  @InjectMocks
  private OrcService orcService;
  @InjectMocks
  private MonsterService monsterService;

  @DisplayName("Orc 생성")
  @Test
  void getOrc() {
    Orc orc = new Orc();

    orcService.makeOrc();
    Orc resultOrc = orcService.getOrc();

    assertThat(resultOrc).isNotNull();
    assertThat(resultOrc).isEqualTo(orc);
  }

  @DisplayName("Orc 공격")
  @Test
  void attackMonster() {
    Orc orc = new Orc();

    orcService.makeOrc();
    String resultString = orcService.attackMonster(new Monster());

    assertThat(resultString).isNotNull();
    assertThat(resultString).isEqualTo("success");
    assertThat(orcService.getOrc().getLastAttackTime()).isNotEqualTo(orc.getLastAttackTime());
  }

  @DisplayName("Orc 공격")
  @Test
  void monsterAttackUser() {
    Orc afterAttack = new Orc();
    afterAttack.getStatPoint().merge("healthPoint", -5.0, Double::sum);
    afterAttack.getStatPoint().put("avoid",0.0);
    Monster monster = new Monster();
//    monster.getStatPoint().put("damage", 2000.0);

    orcService.makeOrc();
    orcService.getOrc().getStatPoint().put("avoid",0.0);
    boolean resultString = orcService.monsterAttackUser(monster.getStatPoint().get("damage"));

    assertThat(resultString).isNotNull();
    assertThat(orcService.getOrc()).isEqualTo(afterAttack);
  }

  @DisplayName("Orc 죽음")
  @Test
  void userDead() {
    Monster monster = new Monster();
    monster.getStatPoint().put("damage", 2000.0);

    orcService.makeOrc();
    orcService.getOrc().getStatPoint().put("avoid",0.0);
    boolean result = orcService.monsterAttackUser(monster.getStatPoint().get("damage"));

    assertThat(result).isNotNull();
    assertThat(result).isFalse();
  }

  @DisplayName("Orc 레벨업")
  @Test
  void orcLevelUp() {
    orcService.makeOrc();
    int resultLevel = orcService.levelUp();

    assertThat(resultLevel).isEqualTo(2);
    assertThat(orcService.getOrc().getSkill().get(Skills.INVINCIBLE.getSkillName())).isNull();
  }

  @DisplayName("Orc 궁극기 배움")
  @Test
  void orcGetUltimateSkill() {
    orcService.makeOrc();
    orcService.getOrc().setLevel(98);
    int resultLevel = orcService.levelUp();

    assertThat(resultLevel).isEqualTo(99);
    assertThat(orcService.getOrc().getSkill().get(Skills.FRENZY.getSkillName())).isNotNull();
    assertThat(orcService.getOrc().getSkill().get(Skills.FRENZY.getSkillName())).isTrue();
  }

  @DisplayName("Orc 스킬")
  @Test
  void useSkill() {
    Orc afterSkill = new Orc();
    afterSkill.getStatPoint().merge("damage", 4.0, Double::sum);
    afterSkill.getStatPoint().merge("magicPoint", -20.0, Double::sum);
    afterSkill.getInActionSkills().put("steam",60);

    orcService.makeOrc();
    boolean result = orcService.useSkill("steam");
    Orc resultOrc = orcService.getOrc();

    assertThat(result).isTrue();
    assertThat(resultOrc).isEqualTo(afterSkill);
  }

  @DisplayName("Orc 스킬 조회")
  @Test
  void getSkillList() {
    Orc orc = new Orc();

    orcService.makeOrc();
    List<String> result = orcService.getSkillList();

    assertThat(result).isNotEmpty();
    assertThat(result.size()).isEqualTo(3);
    assertThat(result).isEqualTo(new ArrayList<>(orc.getSkill().keySet()));
  }

  @DisplayName("Orc 장비")
  @Test
  void useWeapon() {
    Orc afterWeapon = new Orc();
    afterWeapon.getStatPoint().merge("damage", 2.0, Double::sum);
    afterWeapon.getStatPoint().merge("attackSpeed", -25.0, Double::sum);
    afterWeapon.setWeapon("shortAxe");

    orcService.makeOrc();
    boolean result = orcService.useWeapon("shortAxe");
    Orc resultOrc = orcService.getOrc();

    assertThat(result).isTrue();
    assertThat(resultOrc).isEqualTo(afterWeapon);
  }

  @DisplayName("Orc 스킬 실패")
  @Test
  void failSkill() {
    Orc afterSkill = new Orc();

    orcService.makeOrc();
    boolean result = orcService.useSkill("nothing");
    Orc resultOrc = orcService.getOrc();

    assertThat(result).isFalse();
    assertThat(resultOrc).isEqualTo(afterSkill);
  }

  @DisplayName("Orc 장비 실패")
  @Test
  void failWeapon() {
    Orc afterWeapon = new Orc();
    afterWeapon.setWeapon("hand");

    orcService.makeOrc();
    boolean result = orcService.useWeapon("longSword");
    Orc resultOrc = orcService.getOrc();

    assertThat(result).isFalse();
    assertThat(resultOrc).isEqualTo(afterWeapon);
  }

  @DisplayName("Orc berserk 스킬 사용")
  @Test
  void useBerserk() throws Exception {
    Monster afterMonster = new Monster();
    afterMonster.getStatPoint().put("healthPoint", 30.0);

    orcService.makeOrc();
    monsterService.makeMonster();
    String resultString = orcService.attackMonsterWithSkill(monsterService.getMonster(), "useBerserk");

    assertThat(resultString).isNotNull();
    assertThat(monsterService.getMonster()).isEqualTo(afterMonster);
  }
}
