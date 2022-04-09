package io.flysium.sample.aspectj.cflow;

public aspect CflowAspect {

  pointcut callSound() : call(* io.flysium.sample.aspectj.cflow..*.sound(..));  
  pointcut callColor() : call(* io.flysium.sample.aspectj.cflow..*.color(..));  
  pointcut PointCut() : cflow(callSound()) && !within(CflowAspect);  

  before() : PointCut(){  
      System.out.println("----Entering : " + thisJoinPoint.getSourceLocation() +" --- "+ thisJoinPoint.toShortString());  
      //System.out.println("before cflow ");  
    }  
  
}
