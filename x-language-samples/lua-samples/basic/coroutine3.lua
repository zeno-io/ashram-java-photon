local newProducer
local maximum = 10000

function producer()
    local i = 0
    while true do
        i = i + 1
        send(i)     -- 将生产的物品发送给消费者
        if i >= maximum then
            break
        end
    end
end

function consumer()
    while true do
        local i = receive()     -- 从生产者那里得到物品
        if i == nil then
            break
        end
        print(i)
    end
end

function receive()
    local status, value = coroutine.resume(newProducer)
    if status ~= true then
        return nil
    end
    return value
end

function send(x)
    coroutine.yield(x)     -- x表示需要发送的值，值返回以后，就挂起该协程
end

-- 启动程序
newProducer = coroutine.create(producer)
consumer()


