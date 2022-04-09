package io.flysium.sample.aspectj.within;

public aspect WithinAspect {

  pointcut PointCut(int i) : execution(* io.flysium.sample.aspectj.within..*.main(int)) && !within(WithinDemo2) && args(i); 

  before(int i) : PointCut(i){  
      System.out.println("----Entering : " + thisJoinPoint.getSourceLocation());  
      System.out.println("before within=" + i);  
    }  
  
}
