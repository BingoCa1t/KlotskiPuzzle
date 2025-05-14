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


