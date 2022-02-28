package com.bigin.game.common.context;

import com.bigin.game.service.ElfService;
import com.bigin.game.service.HumanService;
import com.bigin.game.service.OrcService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceFactory {

  @Bean(name = "elfService")
  public ElfService elfService() {
    return new ElfService();
  }

  @Bean(name = "humanService")
  public HumanService humanService() {
    return new HumanService();
  }

  @Bean(name = "orcService")
  public OrcService orcService() {
    return new OrcService();
  }
}
