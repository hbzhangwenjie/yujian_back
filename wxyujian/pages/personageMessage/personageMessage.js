// pages/component/personageMessage/personageMessage.js
var url0 = getApp().globalData.url;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    avatarUrl: '',
    nickName: '',
    gender: '',
    school: '',
    introduce: '',
    profession: '',
  }, 
  addImage: function () {
    var that = this;
    wx.navigateTo({
      url: '../../pages/upload/upload',
      complete: function (res) {
        console.log(res)
      }
    })
  },
  setMale: function () {
    var that = this ;
    wx.navigateTo({
      url: '../../pages/setGender/setGender?sex=' + that.data.gender + '&userId=' + that.data.userId,
      complete: function (res) {
        console.log(res)
      }
    })
  },
  setIintroduce: function () {
    wx.navigateTo({
      url: '../../pages/setIntroduce/setIntroduce',
      complete: function (res) {
        console.log(res)
      }
    })
  }, 
  setNick: function () {
    wx.navigateTo({
      url: '../../pages/setNick/setNick',
      complete: function (res) {
        console.log(res)
      }
    })
  },
  setProfession:function(){
    wx.navigateTo({
      url: '../../pages/setProfession/setProfession',
      complete: function (res) {
        console.log(res)
      }
    })
  }, 
  setavatarUrl:function(){
    wx.navigateTo({
      url: '../../pages/uploadava/uploadava',
      complete: function (res) {
        console.log(res)
      }
    })
  },
  setSchool: function () {
    wx.navigateTo({
      url: '../../pages/setSchool/setSchool',
      complete: function (res) {
        console.log(res)
      }
    })
  }, 
  onLoad: function (options) {
    var app = getApp();
    this.setData({
      avatarUrl: app.globalData.user.avatarUrl,
      nickName: app.globalData.user.nickName,
      gender: app.globalData.user.gender,
      school: app.globalData.user.school,
      introduce: app.globalData.user.introduce,
      profession: app.globalData.user.profession,
      openId: app.globalData.user.openId,
      userId: app.globalData.user.userId
    })
  }
})