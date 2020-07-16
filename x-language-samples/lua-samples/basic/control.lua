local i = 0
local max = 10

while i <= max do
    print(i)
    i = i + 1
end

sum = 0
for i = 100, 1, -2 do
    sum = sum + i
end
print(sum)

--[ 定义变量 --]
a = 100;
--[ 检查条件 --]
if (a < 20)
then
    --[ if 条件为 true 时执行该语句块 --]
    print("a 小于 20")
else
    --[ if 条件为 false 时执行该语句块 --]
    print("a 大于 20")
end
print("a 的值为 :", a)