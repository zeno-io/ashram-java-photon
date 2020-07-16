arr = { "string", 100, "lua", function()
    print("Tutorial")
    return 1
end }
print(arr[1])
print(arr[2])
print(arr[3])
print(arr[4]())

print('---------')

for k, v in pairs(arr) do
    print(k, v)
end

print('---------')
-- 初始化表
mytable = {}
-- 指定值
mytable[1] = "Lua"
-- 移除引用
mytable = nil
-- lua 垃圾回收会释放内存


lucy = { name = 'lucy', age = 18, height = 105.5 }
lucy.age = 35
print(lucy.name, lucy.age, lucy.height)
print(lucy)

print('---------')
for k, v in pairs(lucy) do
    print(k, v)
end