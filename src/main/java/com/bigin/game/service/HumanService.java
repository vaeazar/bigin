package com.bigin.game.service;

import com.bigin.game.domain.Human;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HumanService {

  Human human;

  public HumanService() {
    this.human = new Human();
  }

  public List<String> getSkillList() {
    return new ArrayList<>(human.getSkill().keySet());
  }
}
