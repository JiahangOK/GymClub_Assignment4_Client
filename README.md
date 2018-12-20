# GymClub_Assignment4_Client
### Team Members:
16231114 喻琳珠
16301134 吴家行

### Running Result：
见录屏文件：  
手机端演示.mp4（演示断网缓存）  
服务端+安卓模拟器演示.mp4（演示断开服务端缓存）

### Technical Features Applied：
- 服务端  
负责传输sport 和 info 的信息

- 安卓端  
    - Room API  
    @Entity、@Dao、@Database 三层操作数据库  
    通过线程类DeleteThread,GetAllThread,InsertThread来实现对缓存数据库的删除、遍历、插入操作
    - ConnectivityManager  
    用于判断网络状态
    - HttpURLConnection  
    设定延时为1000ms，超时则认为无法连接服务端，访问缓存数据库，离线查看内容。
