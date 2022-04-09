package io.flysium.sample.aspectj.annotaion;

public aspect AnnotationAspect {

  //pointcut action(): execution(* io.flysium.sample.aspectj.annotaion..*.action*(..)) ;
  //pointcut action(): execution(* @Bar io.flysium.sample.aspectj.annotaion..*.action*(..)) ;
  pointcut action(): execution(@Foo * @Bar io.flysium.sample.aspectj.annotaion..*.action*(..));

  after() : action(){
    System.out.println("---Entering : " + thisJoinPoint.getSourceLocation());
  }

}
