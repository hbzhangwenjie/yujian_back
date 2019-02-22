var app = getApp();
var myurl = app.globalData.url;
Page({
  data: {
    eMail: '',
    password: ''
  },

  // 获取输入账号 
  eMailInput: function (e) {
    this.setData({
      eMail: e.detail.value
    })
  },

  // 获取输入密码 
  passwordInput: function (e) {
    this.setData({
      password: e.detail.value
    })
  },

  // 登录 
  login: function () {
    if (this.data.eMail.length == 0 || this.data.password.length == 0) {
      wx.showToast({
        title: '账号密码不能空',
        icon: 'loading',
        duration: 2000
      })
    } else {
      wx.request({
        url: myurl + 'api/login',
method:'POST',
        data: {
          mail: this.data.eMail,
          passWord: this.data.password
        },
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          wx.hideToast();
          if (res.data.result == "fail") {
            wx.showToast({
              title: '账号或密码错误',
              icon: 'none',
              duration: 2000
            })
            console.log("登录失败， 返回：" + res.data);
          }else{
            var myuser = res.data.data;
            wx.setStorageSync('sessionId', myuser.userId);
            app.globalData.user = myuser;
            wx.redirectTo({
              url: '../../pages/user/user'
            })
            console.log("返回" + res.data);
          }
     
        }
      })
      // 这里修改成跳转的页面 
  
    }
  }
})