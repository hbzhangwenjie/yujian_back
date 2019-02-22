// pages/index/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  
  bindGetUserInfo: function (e) {
    console.log(e.detail.userInfo);
    this.logins();
  },
  //get userinfo from encryptedData
  getUser(code) {
    var that = this;
    var app = getApp();
    var urlv = app.globalData.url;
  
    wx.getUserInfo({
      withCredentials: true,
      success: function (res2) {
        console.log(res2);
        var encryptedData = res2.encryptedData;
        var iv = res2.iv;
        wx.showToast({
          title: '即将进入',
          icon: 'loading....',
          duration: 10000
        });
        wx.request({
          url: urlv + 'wx/loginWx',
          data: {
            code: code,
            encryptedData: encryptedData,
            iv: iv
          },
          method: 'POST',
          header: {
            'content-type': 'application/json'
          },
          success: function (res) {
            wx.hideToast();
            if (res.data.result == "fail") {
              that.logins()
              console.log("登录失败， 返回：" + res.data);
            }
            var myuser = res.data.data;
            wx.setStorageSync('sessionId', myuser.userId);
            app.globalData.user = myuser;
            wx.redirectTo({
              url: '../../pages/user/user'
            })
            console.log("返回" + res.data);
          },
          fail: function () {
            wx.hideToast();
            wx.navigateTo({
              url: '../error/error'
            })
          }
        })
      },
      fail: function () {
        wx.showModal({
          title: '提示',
          content: '您好，必须授权才可访问',
          showCancel: false,
          success: function (resss) {
            if (resss.confirm) {
              wx.getSetting({
                success(ressss) {
                  if (!ressss.authSetting['scope.userInfo']) {
                    wx.openSetting({
                      success: (res) => {
                        that.logins()
                      }
                    });
                  }
                }
              })
            }
          }
        })
      }
    })
  },
  //session_key out time need get code retime
  logins(e) {
    var that = this;
    wx.login({
      success: function (res) {
        if (res.code) {
          var code = res.code;
          that.getUser(code);
        } else {
          that.logins()
        }
      },
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var app = getApp();
    var that = this;
    var urlv = app.globalData.url;
    console.info("cc:" + app.globalData.url);
    wx.checkSession({
      success: function () {
        //session_key in time so getuser without login to wx      
        try {
          var sessionId = wx.getStorageSync("sessionId");
          if (sessionId) {
            // Do something with return value
            wx.request({
              url: urlv + 'wx/getUser',
              data: {
                sessionId: sessionId
              },
              header: {
                'content-type': 'application/json'
              },
              success: function (res) {
                wx.hideToast();
                if (res.data.result == "fail") {
                  that.logins();
                  console.log("登录失败， 返回：" + res.data);
                }
                var myuser = res.data.data;
                wx.setStorageSync('sessionId', myuser.userId);
                app.globalData.user = myuser;
                wx.redirectTo({
                  url: '../../pages/user/user'
                })
                console.log("返回" + res.data);
              },
              fail: function () {
                wx.hideToast();
                wx.navigateTo({
                  url: '../error/error'
                })
              }
            })
          }else{
            that.logins();
          }
        } catch (e) {
          // Do something when catch error
          that.logins();
        }
      },
      fail: function () {
        //session_key out time so need login to wx
        that.logins();
              }
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})