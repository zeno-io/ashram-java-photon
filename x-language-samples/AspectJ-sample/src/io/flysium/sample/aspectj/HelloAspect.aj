package io.flysium.sample.aspectj;

public aspect HelloAspect {

  pointcut HelloWorldPointCut() : execution(* io.flysium.sample.aspectj.HelloWorld.main(..));  

  before() : HelloWorldPointCut(){  
      //System.out.println("----Entering : " + thisJoinPoint.getSourceLocation());  
      System.out.println("before Hello world ");  
    }  
  
}
