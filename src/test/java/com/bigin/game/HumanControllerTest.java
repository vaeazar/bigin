package com.bigin.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bigin.game.controller.HumanController;
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
public class HumanControllerTest {

  @InjectMocks
  private HumanController humanController;
  @Mock
  private HumanService humanService;
  @Mock
  private MonsterService monsterService;

  private MockMvc mockMvc;

  @BeforeEach
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(humanController).build();
  }

  @DisplayName("Human 생성")
  @Test
  void selectHuman() throws Exception {
    when(humanService.getHuman()).thenReturn(new Human());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/human/selectHuman")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
    assertThat(token).isEqualTo(new Gson().toJson(humanService.getHuman()));
  }

  @DisplayName("Human 정보")
  @Test
  void getHuman() throws Exception {
    when(humanService.getHuman()).thenReturn(new Human());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/human/userStat")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
    assertThat(token).isEqualTo(new Gson().toJson(humanService.getHuman()));
  }

  @DisplayName("Human 스킬")
  @Test
  void useSkill() throws Exception {
    Human tempHuman = new Human();
    when(humanService.getHuman()).thenReturn(tempHuman);

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/human/useSkill")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content("{\"skillName\" : \"steam\"}")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
    assertThat(token).isEqualTo(new Gson().toJson(tempHuman));
  }

  @DisplayName("Human 장비")
  @Test
  void useWeapon() throws Exception {
    Human tempHuman = new Human();
    when(humanService.getHuman()).thenReturn(tempHuman);

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/human/useWeapon")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content("{\"weaponName\" : \"shortSword\"}")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
    assertThat(token).isEqualTo(new Gson().toJson(tempHuman));
  }

  @DisplayName("Human 공격")
  @Test
  void userAttack() throws Exception {
    when(humanService.getHuman()).thenReturn(new Human());
    when(monsterService.getMonster()).thenReturn(new Monster());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/human/userAttack")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }
}
