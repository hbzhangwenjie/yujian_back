// pages/member/member1.js
var url0 = getApp().globalData.url;
var timerDS //定义一个变量接收定时器的返回值
Page({
  data: {
    showModalStatus: false,
    num: 1,
    nickName: '',
    member: {},
    avatarUrl: '../../images/user.png',
  },
  bindTapsetUserInfo: function () {
    wx.navigateTo({
      url: '../personageMessage/personageMessage'
    })
  },
  goshowSeesion: function () {
    wx.navigateTo({
      url: '../chat/wechat'
    })
  },
  goshowUser:function(){
    wx.navigateTo({
      url: '../online/online'
    })
  }, goThumb: function () {
    wx.navigateTo({
      url: '../thumb/thumb'
    })
  },
  bindGuggestion:function() {
    wx.navigateTo({
      url: '../setGuggestion/setGuggestion'
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var app = getApp();
    this.setData({
      avatarUrl: app.globalData.user.avatarUrl,
      nickName: app.globalData.user.nickName,
      member: app.globalData.user
    })
  },
  onReady: function (options) {
  },
  onShow: function (options) {
  },
 
  buildding: function () {
    wx.showModal({
      showCancel: false,
      title: '',
      content: '该功能正在建设中，敬请期待！',
      success: function (res) {
        if (res.confirm) {
          console.log('用户点击确定');
        }
      }
    })
  }
})