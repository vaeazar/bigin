package com.bigin.game;

import com.bigin.game.common.constant.Weapon;
import com.bigin.game.domain.Human;
import com.bigin.game.domain.User;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
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

      // 필드
      Field damageField = human.getClass().getSuperclass().getDeclaredField("damage");
      damageField.setAccessible(true);
      // public 이외의 메소드 및 필드는 액세스 가능한 상태로 설정해야 함. 

      damageField.set(human, 100);              // 필드 값 설정
      Object damageFieldValue = damageField.get(human); // 필드 값 얻기
      System.out.println("damageFieldValue = " + damageFieldValue);

      System.out.println("Weapon.values() = " + Arrays.toString(Weapon.values()));
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
