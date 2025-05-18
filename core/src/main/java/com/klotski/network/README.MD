# CS通信规范
## 用户登录、注册类
### 登录相关
1. 客户端登录请求 code:0002
- 格式：0002|wanght2024@mail.sustech.edu.cn|123456
2. 服务端返回：code:0002
   - 格式:
   1. 登录失败，密码错误: 0002|403
   2. 登录失败，用户名不存在：0002|404
   3. 登录成功：0002|200
3. 服务器返回UserInfo和LevelArchive
- 格式：0004|email|{HashMap<Archive> 1}|{HashMap<Archive> 2}|{HashMap<Archive> 3}|1
4. 客户端提交存档（在每一步移动时执行）
- 格式：0005|email|{HashMap<Archive> 1}|{HashMap<Archive> 2}|{HashMap<Archive> 3}|1

### 注册相关
1. 请求验证码：
- 客户端发送：0030|email
- 如果邮箱未被注册 服务器返回：0030|email|code
- 如果已注册，服务器返回：0030|0
- 服务器向email发送code
2. 注册请求
- 客户端发送：0031|{UserInfo}|password|code
- 在客户端核对：如果userInfo.getEmail==email，则提示错误
- 如果验证码正确 回答：0032|1
- 如果错误 回答：0032|0

### 观战相关
#### 在线列表
1. 开始、结束观战：客户端发送0010|email|1/0
2. 开始游戏：当在线用户开始游戏时，客户端发送
- 0040|email
3. 结束游戏：当在线用户结束游戏时，客户端发送
- 0041|email
4. 在线列表
- 客户端每5s向服务器请求在线列表，格式：0020|
- 服务器收到后回复0020|{Arraylist\<String>}
#### 观战
5. 客户端选择观战：
- 点击“观战”时，观战客户端向服务器发送：0060|{email（被观战用户）}|1
- 服务端拿到UserInfo并通知被观战客户端：0060|{email（观战用户）}|1
- 所有客户端一直向服务器发送LevelArchive和最近一次移动：0015|email|{LevelArchive}|{MoveStep}
- 如果MoveStep不可移动（错误），则载入存档,重置棋盘
- 由服务器负责转发上条的包给所有在线客户端
- 第一条发送0061|{LevelArchive}
6. 客户端结束观战：点击退出时，客户端向服务器发送
- 0011|{email}，服务器不再转发
7. 聊天（再说吧）



