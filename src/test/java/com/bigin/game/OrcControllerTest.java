package com.bigin.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bigin.game.controller.OrcController;
import com.bigin.game.domain.Elf;
import com.bigin.game.domain.Orc;
import com.bigin.game.domain.Monster;
import com.bigin.game.service.OrcService;
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
public class OrcControllerTest {

  @InjectMocks
  private OrcController orcController;
  @Mock
  private OrcService orcService;
  @Mock
  private MonsterService monsterService;

  private MockMvc mockMvc;

  @BeforeEach
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(orcController).build();
  }

  @DisplayName("Orc 생성")
  @Test
  void selectOrc() throws Exception {
    when(orcService.getOrc()).thenReturn(new Orc());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/orc/selectOrc")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }

  @DisplayName("Orc 정보")
  @Test
  void getOrc() throws Exception {
    when(orcService.getOrc()).thenReturn(new Orc());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/orc/userStat")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }

  @DisplayName("Orc 스킬")
  @Test
  void useSkill() throws Exception {
    when(orcService.getOrc()).thenReturn(new Orc());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/orc/useSkill")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content("{\"skillName\" : \"steam\"}")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }

  @DisplayName("Orc 장비")
  @Test
  void useWeapon() throws Exception {
    when(orcService.getOrc()).thenReturn(new Orc());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/orc/useWeapon")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content("{\"weaponName\" : \"shortSword\"}")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }

  @DisplayName("Orc 공격")
  @Test
  void userAttack() throws Exception {
    when(orcService.getOrc()).thenReturn(new Orc());
    when(monsterService.getMonster()).thenReturn(new Monster());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/orc/userAttack")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }

  @DisplayName("Orc Berserk 사용")
  @Test
  void useBerserk() throws Exception {
    when(orcService.getOrc()).thenReturn(new Orc());
    when(monsterService.getMonster()).thenReturn(new Monster());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/orc/userAttack/useBerserk")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }
}
