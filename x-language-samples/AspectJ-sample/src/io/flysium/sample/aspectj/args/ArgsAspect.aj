package io.flysium.sample.aspectj.args;

public aspect ArgsAspect {

  //pointcut PointCut() : execution(* io.flysium.sample.aspectj.args.ArgsDemo.main(int));  
  pointcut PointCut(int i) : execution(* io.flysium.sample.aspectj.args.ArgsDemo.main(int)) && args(i);  

  before(int i) : PointCut(i){  
      //System.out.println("----Entering : " + thisJoinPoint.getSourceLocation());  
      System.out.println("before Hello world " + i);  
    }  
  
}
