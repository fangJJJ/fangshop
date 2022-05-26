# FangShop

<img width="1703" alt="frontpage" src="https://user-images.githubusercontent.com/55079090/170449457-3e208aa9-8c6c-421a-b7a9-ad85dced50fd.png">

fangshop是一個電子商務平台包括前台的購物商城和後台的管理專區，使用Vue.js+Spring MVC+Mybatis+MySQL，並部署於Heroku 
>前台：[Live Demo](https://fangshop.herokuapp.com/Index.do)  
>後台：[Live Demo](https://fangshop.herokuapp.com/admin/goProductList.do)  

可以使用以下測試帳號或自行註冊帳號來登入前台
```bash
email：test@gmail.com
password：test1
``` 
<br>

# 功能
### 前台
登入
* 一般註冊登入
* Facebook登入  

商品
* 依據商品類別分類(含分頁)
* 單一商品詳細說明   

購物車
* 增加或減少購物車商品數量
* 刪除商品
* 購物車總金額  

結帳(必須登入)
* 填寫收件資料  

訂單(必須登入)
* 訂單列表及明細  
### 後台
商品
* 商品列表(含分頁)
* 編輯、新增商品
* 下載Excel範本
* Excel匯入商品  
<br>

# 使用技術
### 前端
Vue2 + Axios + Bootstrap4 + jQuery + Font Awesome
###  後端
Spring + SpringMVC + MyBatis + Apache Tiles
###  測試
JUnit + Mockito
###  其他
Log4j + Apache fileupload + PageHelper分頁 + Hibernate-Validator + POI Excel讀取下載  
<br>

# 開發環境
JDK版本: JDK11  
IDE: Eclipse  
Web Server: Tomcat 9  
Build Tool: Maven  
Database: MySQL
