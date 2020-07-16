function myPower(x, y)
    return y + x
end
power2 = myPower(2, 3)
print(power2)

function newCounter()
    local i = 0
    return function()
        -- anonymous function
        i = i + 1
        return i
    end
end
c1 = newCounter()
print(c1())  --> 1
print(c1())  --> 2
print(c1())  --> 3


function add(...)
    local s = 0
    for i, v in ipairs { ... } do
        --> {...} 表示一个由所有变长参数构成的数组
        s = s + v
    end
    return s
end
print(add(3, 4, 5, 6, 7))  --->25

