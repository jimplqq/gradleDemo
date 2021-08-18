package com.example.demo.config.akka;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

/**
 * @author qingyun
 * @date 2021/3/25 10:18 上午
 */
public class SpringActorProducer implements IndirectActorProducer {
  private final ApplicationContext applicationContext;
  private final String actorBeanName;

  public SpringActorProducer(ApplicationContext applicationContext, String actorBeanName) {
    this.applicationContext = applicationContext;
    this.actorBeanName = actorBeanName;
  }

  @Override
  public Actor produce() {
    return (Actor) applicationContext.getBean(actorBeanName);
  }

  @Override
  public Class<? extends Actor> actorClass() {
    return (Class<? extends Actor>) applicationContext.getType(actorBeanName);
  }
}
