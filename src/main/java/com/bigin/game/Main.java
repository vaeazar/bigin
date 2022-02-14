package com.bigin.game;

import com.bigin.game.domain.Human;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

  public static void main(String[] args) {
//    System.out.println("schedulerCheck" + schedulerCheck());
//    System.out.println("endendend");
//    System.out.println("endendend");
//    System.out.println("endendend");
//    System.out.println("endendend");
//    System.out.println("endendend");
//    System.out.println("endendend");
//    System.out.println("endendend");

//    double increaseDamage = Math.round((111 / (double) Skills.RAPID.getSkillIncreaseValue()) * 10) / 10.0;
//    System.out.println("increaseDamage = " + increaseDamage);
//    long now1 = System.currentTimeMillis();
//    long now2 = Instant.now().toEpochMilli();
//    try {
//      Thread.sleep(1000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    long now3 = Instant.now().toEpochMilli();
//    System.out.println("now1 = " + now1);
//    System.out.println("now2 = " + now2);
//    System.out.println("now3 = " + now3);

    try {
      Human human = new Human();
      Method method = human.getClass().getMethod("testMethod");
      method.invoke(human);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static boolean schedulerCheck() {
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    Runnable task = () -> {
      System.out.println("(100 == 100.00) = " + (100 == 100.00));
    };
    executor.schedule(task, 5, TimeUnit.SECONDS);
    executor.shutdown();
    return true;
  }
}
