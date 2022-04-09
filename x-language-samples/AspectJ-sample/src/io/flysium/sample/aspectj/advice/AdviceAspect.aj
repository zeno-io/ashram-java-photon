package io.flysium.sample.aspectj.advice;

public aspect AdviceAspect {

  pointcut PointCut(int i) : execution(* io.flysium.sample.aspectj.advice..*.*(int)) && args(i);

  int around(int i): PointCut(i) {
    System.out.println("---Entering : " + thisJoinPoint.getSourceLocation());
    int newValue = proceed(i * 3);
    return newValue;
  }

}
