var url0 = getApp().globalData.url;
Page({
  /**
   * 页面的初始数据
   */
  data: {
    suggestion: '',
    userId: ''
  },

  valueInput: function (e) {
    this.setData({
      suggestion: e.detail.value,
    })
  },
  btn_save: function (e) {
    var that = this;
    wx.request({
      url: url0 + '/wx/addGuggestion',
      data: {
        suggestion: that.data.suggestion,
        userid: that.data.userId
      },
      method: 'POST',
      header: {
        'content-type': 'application/json',
      },
      success: function (res) {
        if (res.data.result == "sucess") {
            wx.showModal({
              title: '提示：',
              showCancel: false,
              content: '反馈成功', 
            });
        }
        else {
          wx.showModal({
            title: '提示：',
            showCancel: false,
            content: '反馈失败了！',
          })
        }
      },
      fail: function () {
        wx.showModal({
          title: '提示',
          content: '跟新失败了！',
          showCancel: false
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var app = getApp();
    this.setData({
      introduce: app.globalData.user.introduce,
      userId: app.globalData.user.userId
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