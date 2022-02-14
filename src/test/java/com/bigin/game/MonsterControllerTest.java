package com.bigin.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bigin.game.controller.HumanController;
import com.bigin.game.controller.MonsterController;
import com.bigin.game.domain.Human;
import com.bigin.game.domain.Monster;
import com.bigin.game.service.HumanService;
import com.bigin.game.service.MonsterService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class MonsterControllerTest {

  @InjectMocks
  private MonsterController monsterController;
  @Mock
  private HumanService humanService;
  @Mock
  private MonsterService monsterService;

  private MockMvc mockMvc;

  @BeforeEach
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(monsterController).build();
  }

  @DisplayName("Monster 생성")
  @Test
  void makeMonster() throws Exception {
    when(monsterService.getMonster()).thenReturn(new Monster());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/monster/monsterEncounter")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
    assertThat(token).isEqualTo(new Gson().toJson(monsterService.getMonster()));
  }

  @DisplayName("Monster 정보")
  @Test
  void monsterStat() throws Exception {
    when(monsterService.getMonster()).thenReturn(new Monster());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/monster/monsterStat")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
    assertThat(token).isEqualTo(new Gson().toJson(monsterService.getMonster()));
  }

  @DisplayName("Monster 특정 몬스터 생성")
  @Test
  void makeSpecialMonster() throws Exception {
    when(monsterService.getMonster()).thenReturn(new Monster("hardMonster"));

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/monster/monsterEncounter/hardMonster")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
    assertThat(token).isEqualTo(new Gson().toJson(monsterService.getMonster()));
  }

  @DisplayName("Monster 공격")
  @Test
  void monsterAttack() throws Exception {
    when(monsterService.getUserInfo()).thenReturn(new Human());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/monster/monsterAttack")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content("{\"tribe\" : \"human\"}")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }
}
