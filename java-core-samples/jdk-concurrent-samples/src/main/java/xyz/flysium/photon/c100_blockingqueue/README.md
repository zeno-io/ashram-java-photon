### Queue
 <table BORDER CELLPADDING=3 CELLSPACING=1>
  <tr>
    <td></td>
    <td ALIGN=CENTER><em>如果操作失败，抛出异常</em></td>
    <td ALIGN=CENTER><em>一般不会抛出异常</em></td>
  </tr>
  <tr>
    <td><b>插入</b></td>
    <td>boolean add(e)}</td>
    <td>boolean offer(e)</td>
  </tr>
  <tr>
    <td><b>移除(返回且移除头元素)</b></td>
    <td>E remove()</td>
    <td>E poll()</td>
  </tr>
  <tr>
    <td><b>检查(返回头元素但不删除)</b></td>
    <td>E element()</td>
    <td>E peek()</td>
  </tr>
 </table>
 
 ### BlockingQueue
<table BORDER CELLPADDING=3 CELLSPACING=1>
 <tr>
   <td></td>
  <td ALIGN=CENTER><em>如果操作失败，抛出异常</em></td>
  <td ALIGN=CENTER><em>一般不会抛出异常</em></td>
   <td ALIGN=CENTER><em>阻塞操作</em></td>
   <td ALIGN=CENTER><em>超时操作</em></td>
 </tr>
 <tr>
   <td><b>插入</b></td>
  <td>boolean add(e)}</td>
   <td>boolean offer(e)</td>
   <td>void put(e)</td>
   <td>boolean offer(e, time, unit)</td>
 </tr>
 <tr>
   <td><b>移除(返回且移除头元素)</b></td>
  <td>E remove()</td>
  <td>E poll()</td>
   <td>E take()</td>
   <td>E poll(time, unit)</td>
 </tr>
 <tr>
   <td><b>检查(返回头元素但不删除)</b></td>
  <td>E element()</td>
   <td>E peek()</td>
   <td><em>没有此类方法</em></td>
   <td><em>没有此类方法</em></td>
 </tr>
</table>
