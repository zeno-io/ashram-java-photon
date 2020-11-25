# -*- coding: UTF-8 -*-

# from random import Random
from java.util import Random

#################################################
# 通过java package导入java类
from com.github.flysium.io.sample.java2python.api import SayHello


# def random_str_python(randomlength=8,chars='AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789'):
#     str = ''
#     length = len(chars) - 1
#     random = Random()
#     for i in range(randomlength):
#         str+=chars[random.randint(0, length)]
#     return str

def random_str_java(randomlength=8,
  chars='AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789'):
  str = ''
  length = len(chars) - 1
  rng = Random()
  for i in range(randomlength):
    str += chars[rng.nextInt(length)]
  return str


#################################################
# 将python属性传入后续调用的java实例
execpy = SayHello()
# execpy.setUserName(random_str_python(randomlength))
execpy.setUserName(random_str_java(randomlength))


def say():
  execpy.say(randomlength)
  return


say()
