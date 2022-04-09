package io.flysium.sample.aspectj.targetAndThis;

public aspect AnimalAspect {

  // pointcut PointMove(): call (* io.flysium.sample.aspectj.targetAndThis..*.move(..));
  // pointcut PointMove(): call (* io.flysium.sample.aspectj.targetAndThis..*.move(..)) && target(Animal);
  // pointcut PointMove(): call (* io.flysium.sample.aspectj.targetAndThis..*.move(..)) && this(Animal); //nothing
  // pointcut PointMove(): call (* io.flysium.sample.aspectj.targetAndThis..*.move(..)) && this(TestsDemo);
  // pointcut PointMove(): execution(* io.flysium.sample.aspectj.targetAndThis..*.move(..)) && this(Animal);
  // pointcut PointMove(): execution(* io.flysium.sample.aspectj.targetAndThis..*.move(..)) && within(Animal);//nothing
  pointcut PointMove(): execution(* io.flysium.sample.aspectj.targetAndThis..*.move(..)) && within(Dragon);

  after(): PointMove(){
    System.out.println("---Entering " + thisJoinPoint.getSourceLocation() + " --- " + thisJoinPoint.toShortString());
  }

}
