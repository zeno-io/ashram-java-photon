package io.flysium.sample.aspectj.withcode;

public aspect WithincodeAspect {

  //pointcut PointCut(int i) : call(* io.flysium.sample.aspectj.withcode..*.method(int)) && args(i) ; 
  pointcut PointCut(int i) : call(* io.flysium.sample.aspectj.withcode..*.method(int)) && withincode(* *..main(..)) && args(i)  ; 

  before(int i) : PointCut(i){  
      System.out.println("----Entering : " + thisJoinPoint.getSourceLocation());  
      System.out.println("before call withincode=" + i);  
    }  
  
}
