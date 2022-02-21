package com.bigin.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bigin.game.controller.ElfController;
import com.bigin.game.domain.Elf;
import com.bigin.game.domain.Monster;
import com.bigin.game.service.ElfService;
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
public class ElfControllerTest {

  @InjectMocks
  private ElfController elfController;
  @Mock
  private ElfService elfService;
  @Mock
  private MonsterService monsterService;

  private MockMvc mockMvc;

  @BeforeEach
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(elfController).build();
  }

  @DisplayName("Elf 생성")
  @Test
  void selectElf() throws Exception {
    when(elfService.getElf()).thenReturn(new Elf());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/elf/selectElf")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }

  @DisplayName("Elf 정보")
  @Test
  void getElf() throws Exception {
    when(elfService.getElf()).thenReturn(new Elf());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/elf/userStat")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }

  @DisplayName("Elf 스킬")
  @Test
  void useSkill() throws Exception {
    when(elfService.getElf()).thenReturn(new Elf());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/elf/useSkill")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content("{\"skillName\" : \"steam\"}")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }

  @DisplayName("Elf 장비")
  @Test
  void useWeapon() throws Exception {
    when(elfService.getElf()).thenReturn(new Elf());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/elf/useWeapon")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content("{\"weaponName\" : \"shortSword\"}")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }

  @DisplayName("Elf 공격")
  @Test
  void userAttack() throws Exception {
    when(elfService.getElf()).thenReturn(new Elf());
    when(monsterService.getMonster()).thenReturn(new Monster());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/elf/userAttack")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }

  @DisplayName("Elf 포션 사용")
  @Test
  void usePotion() throws Exception {
    when(elfService.getElf()).thenReturn(new Elf());

    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/elf/usePotion")
    );

    MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    String token = mvcResult.getResponse().getContentAsString();

    assertThat(token).isNotNull();
  }
}
