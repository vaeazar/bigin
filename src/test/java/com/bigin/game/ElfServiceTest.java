package com.bigin.game;

import static org.assertj.core.api.Assertions.assertThat;

import com.bigin.game.common.constant.Skills;
import com.bigin.game.domain.Elf;
import com.bigin.game.domain.Monster;
import com.bigin.game.service.ElfService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ElfServiceTest {

  @InjectMocks
  private ElfService elfService;

  @DisplayName("Elf 생성")
  @Test
  void getElf() {
    Elf elf = new Elf();

    elfService.makeUser();
    Elf resultElf = elfService.getUser();

    assertThat(resultElf).isNotNull();
    assertThat(resultElf).isEqualTo(elf);
  }

  @DisplayName("Elf 공격")
  @Test
  void attackMonster() {
    Elf elf = new Elf();

    elfService.makeUser();
    String resultString = elfService.attackMonster(new Monster());

    assertThat(resultString).isNotNull();
    assertThat(resultString).isEqualTo("success");
    assertThat(elfService.getUser().getLastAttackTime()).isNotEqualTo(elf.getLastAttackTime());
  }

  @DisplayName("Elf 공격")
  @Test
  void monsterAttackUser() {
    Elf afterAttack = new Elf();
    afterAttack.getStatPoint().merge("healthPoint", -5.0, Double::sum);
    afterAttack.getStatPoint().put("avoid",0.0);
    Monster monster = new Monster();
//    monster.getStatPoint().put("damage", 2000.0);

    elfService.makeUser();
    elfService.getUser().getStatPoint().put("avoid",0.0);
    boolean resultString = elfService.monsterAttackUser(monster.getStatPoint().get("damage"));

    assertThat(resultString).isNotNull();
    assertThat(elfService.getUser()).isEqualTo(afterAttack);
  }

  @DisplayName("Elf 죽음")
  @Test
  void userDead() {
    Monster monster = new Monster();
    monster.getStatPoint().put("damage", 2000.0);

    elfService.makeUser();
    elfService.getUser().getStatPoint().put("avoid",0.0);
    boolean result = elfService.monsterAttackUser(monster.getStatPoint().get("damage"));

    assertThat(result).isNotNull();
    assertThat(result).isFalse();
  }

  @DisplayName("Elf 레벨업")
  @Test
  void elfLevelUp() {
    elfService.makeUser();
    int resultLevel = elfService.levelUp();

    assertThat(resultLevel).isEqualTo(2);
    assertThat(elfService.getUser().getSkill().get(Skills.INVINCIBLE.getSkillName())).isNull();
  }

  @DisplayName("Elf 궁극기 배움")
  @Test
  void elfGetUltimateSkill() {
    elfService.makeUser();
    elfService.getUser().setLevel(98);
    int resultLevel = elfService.levelUp();

    assertThat(resultLevel).isEqualTo(99);
    assertThat(elfService.getUser().getSkill().get(Skills.RAPID.getSkillName())).isNotNull();
    assertThat(elfService.getUser().getSkill().get(Skills.RAPID.getSkillName())).isTrue();
  }

  @DisplayName("Elf 스킬")
  @Test
  void useSkill() {
    Elf afterSkill = new Elf();
    afterSkill.getStatPoint().merge("damage", 4.0, Double::sum);
    afterSkill.getStatPoint().merge("magicPoint", -20.0, Double::sum);
    afterSkill.getInActionSkills().put("steam",60);

    elfService.makeUser();
    boolean result = elfService.useSkill("steam");
    Elf resultElf = elfService.getUser();

    assertThat(result).isTrue();
    assertThat(resultElf).isEqualTo(afterSkill);
  }

  @DisplayName("Elf 스킬 조회")
  @Test
  void getSkillList() {
    Elf elf = new Elf();

    elfService.makeUser();
    List<String> result = elfService.getSkillList();

    assertThat(result).isNotEmpty();
    assertThat(result.size()).isEqualTo(3);
    assertThat(result).isEqualTo(new ArrayList<>(elf.getSkill().keySet()));
  }

  @DisplayName("Elf 장비")
  @Test
  void useWeapon() {
    Elf afterWeapon = new Elf();
    afterWeapon.getStatPoint().merge("attackSpeed", 25.0, Double::sum);
    afterWeapon.setWeapon("shortBow");

    elfService.makeUser();
    boolean result = elfService.useWeapon("shortBow");
    Elf resultElf = elfService.getUser();

    assertThat(result).isTrue();
    assertThat(resultElf).isEqualTo(afterWeapon);
  }

  @DisplayName("Elf 스킬 실패")
  @Test
  void failSkill() {
    Elf afterSkill = new Elf();

    elfService.makeUser();
    boolean result = elfService.useSkill("nothing");
    Elf resultElf = elfService.getUser();

    assertThat(result).isFalse();
    assertThat(resultElf).isEqualTo(afterSkill);
  }

  @DisplayName("Elf 장비 실패")
  @Test
  void failWeapon() {
    Elf afterWeapon = new Elf();
    afterWeapon.setWeapon("hand");

    elfService.makeUser();
    boolean result = elfService.useWeapon("longSword");
    Elf resultElf = elfService.getUser();

    assertThat(result).isFalse();
    assertThat(resultElf).isEqualTo(afterWeapon);
  }

  @DisplayName("Elf 포션 사용")
  @Test
  void usePotion() throws Exception {
    Elf afterPotion = new Elf();
    afterPotion.setPotion(4);

    elfService.makeUser();
    elfService.getUser().getStatPoint().put("healthPoint", 180.0);
    boolean result = elfService.usePotion();
    Elf resultElf = elfService.getUser();

    assertThat(result).isTrue();
    assertThat(resultElf).isEqualTo(afterPotion);
  }
}
