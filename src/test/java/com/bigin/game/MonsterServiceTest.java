package com.bigin.game;

import static org.assertj.core.api.Assertions.assertThat;

import com.bigin.game.domain.Human;
import com.bigin.game.domain.Monster;
import com.bigin.game.service.MonsterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MonsterServiceTest {

  @InjectMocks
  private MonsterService monsterService;

  @DisplayName("Monster 생성")
  @Test
  void makeMonster() {
    Monster monster = new Monster();

    monsterService.makeMonster();
    Monster resultMonster = monsterService.getMonster();

    assertThat(resultMonster).isNotNull();
    assertThat(resultMonster).isEqualTo(monster);
  }

  @DisplayName("특수 Monster 생성")
  @Test
  void makeSpecialMonster() {
    Monster monster = new Monster();
    Monster hardMonster = new Monster("hardMonster");

    monsterService.makeMonster("hardMonster");
    Monster resultMonster = monsterService.getMonster();

    assertThat(resultMonster).isNotNull();
    assertThat(resultMonster).isNotEqualTo(monster);
    assertThat(resultMonster).isEqualTo(hardMonster);
  }

  @DisplayName("Monster 공격")
  @Test
  void attackUser() {
    Monster monster = new Monster();
    Human human = new Human();

    monsterService.makeMonster();
    boolean result = monsterService.monsterAttack(human);

    assertThat(result).isNotNull();
    assertThat(result).isTrue();
    assertThat(monsterService.getMonster().getLastAttackTime()).isNotEqualTo(monster.getLastAttackTime());
  }
}
