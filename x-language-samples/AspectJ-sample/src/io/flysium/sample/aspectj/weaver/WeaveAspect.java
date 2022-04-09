package io.flysium.sample.aspectj.weaver;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class WeaveAspect {

  @Pointcut("execution(* io.flysium.sample.aspectj.weaver..*.main(int)) && !within(WeaveAspect)")  
  public void doAspectj(){  
     }  
  
  @Before("doAspectj()")  
  public void testExecute(){  
      System.out.println("Weave using Java annotation in .java instead of .aj");  
  }  
 
}
