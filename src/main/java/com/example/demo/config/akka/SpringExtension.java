package com.example.demo.config.akka;

import akka.actor.Extension;
import akka.actor.Props;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author qingyun
 * @date 2021/3/25 10:18 上午
 */
@Component("springExtension")
public class SpringExtension implements Extension {

  private ApplicationContext applicationContext;

  public void initialize(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  public Props props(String actorBeanName) {
    return Props.create(SpringActorProducer.class, applicationContext, actorBeanName);
  }
}
