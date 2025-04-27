- 前端的`ChessBoard`类基本功能写完了，现在让我们来写后端的ChessBoardArray类吧
### 前情提要
0. 我们使用`Pos`类来表示坐标，有三个属性`x`、`y`、`z`（目前`z`还没有用，都是0）
    * `Pos`类内置了`add()`加法、`sub()`减法
    * 重写了`equals()`判断是否相等、重写了`toString()`方法转换成字符串`(x,y,z)`的形式
1. 每个`Chess`类有几个后端需要读取的属性
    * `getPosition()`: 获取棋子的坐标，返回一个`Pos`类的对象
    * `getChessWidth()`: 获取棋子的宽度
    * `getChessHeight()`: 获取棋子的高度
### `ChessBoardArray`类
0. 我们计划使用两种方式来存储当前棋盘，分别是

```java
//目前提供两种存储方式：int[]数组和HashMap
private int[][] chessBoard;
private ArrayList<Chess> chesses;
```
* 接下来的函数需要对这两种方式同时操作
* `int[][]`数组可以采用这种存储方式：0为Empty，1为曹操，2为关羽（只允许横着走）3是其他武将（只允许竖着走） 4是卒
#### 几个核心函数
0. 构造函数
```java
public ChessBoardArray(ArrayList<Chess> chesses)
{
    this.chesses=chesses;
    init(chesses);
}
```
1. 根据ArrayList<Chess>对象生成int[][]数组
```java
public void init()
{
    //根据ArrayList<Chess>对象生成int[][]数组
}
```
2. 判断这个棋子是否能被移动到指定位置
```java
public boolean isChessCanMove(Chess c,Pos p)
{
    //判断这个棋子是否能被移动到指定位置
}
```
3. 如果可以，移动这个棋子到指定位置
```java
public void move(Chess c,Pos p)
{
    //移动这个棋子到指定位置
}
```
暂时先写这么多就行
