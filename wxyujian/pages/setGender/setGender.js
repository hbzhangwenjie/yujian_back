// pages/user/setGender/setGender.js
var url0 = getApp().globalData.url;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    hidden: true,
    hide: true,
    sex: '',
    userId: ''
  },

  getGender: function(e) {
    var pages = getCurrentPages();
    var sex = this.data.sex;
    var memId = this.data.userId;
    var prevPage = pages[pages.length - 2]; //上一个页面 
    //直接调用上一个页面的setData()方法，把数据存到上一个页面中去
    prevPage.setData({
      sex: sex
    });

    wx.request({
      url: url0 + '/wx/updateUser',
      method: 'POST',
      data: {
        gender: sex,
        userId: memId
      },
      dataType: 'json',
      success: function(res) {
        if (res.data.result == "sucess") {
          getApp().globalData.user.gender = res.data.data.gender
          prevPage.setData({
              gender: res.data.data.gender
            }),
            wx.showModal({
              title: '提示：',
              showCancel: false,
              content: '跟新成功了！',
            success: function (res) {
              if (res.confirm) {
                wx.navigateBack();
              }
            }
            });
        } else {
          wx.showModal({
            title: '提示：',
            showCancel: false,
            content: '跟新失败了！',
          })
        }
      },
      fail: function() {
        wx.redirectTo({
          url: '../error/error'
        })
      }
    })
  },

  choose: function() {
    this.setData({
      hidden: false,
      hide: true,
      sex: 1
    });
  },
  chooseGender: function() {
    this.setData({
      hidden: true,
      hide: false,
      sex: 0
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    console.log(options.sex);
    this.setData({
      sex: options.sex,
      userId: options.userId,
    });
    if (this.data.sex == 1) {
      this.setData({
        hidden: false,
        hide: true,
      });
    } else if (this.data.sex == 0) {
      this.setData({
        hidden: true,
        hide: false,
      });
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})