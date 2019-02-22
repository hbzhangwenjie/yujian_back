var content = '';
var myUrl = getApp().globalData.url;
var mysessionId = '';
var mynickName = '';
var myuserId = '';
var myavatarUrl = '';
Page({
  data: {
    user: [],
    scrollTop: 0,
    items: [],
    sendmsg:''
  },
  bindChange: function (e) {
    content = e.detail.value
  }, //事件处理函数
  add: function (e) {
    var that = this;
    var userId = this.data.user.userId;
    var toUserId = myuserId;
    var sessionId = mysessionId;
    wx.request({
      url: myUrl + '/wx/addMessage',
      data: {
        sessionId: sessionId,
        userId: userId,
        toUserId: toUserId,
        message: content
      },
      method: 'POST',
      success: function (res) {
        if (res.data.result == "sucess") {
          content = '';
          getMessqge(that);
          // 使页面滚动到底部
          wx.pageScrollTo({
            scrollTop: 1000000
          })
          that.setData({
            sendmsg: '',
          })
        } else {
          wx.showToast({
            title: '发送失败',
          })
        }
      },
      fail: function (res) {
        wx.showToast({
          title: '网络故障',
        })
      }  
    });
  },
  onLoad: function (options) {
    var that = this;
    var app = getApp();
     mysessionId = options.sessionId;
     mynickName = options.nickName;
     myuserId = options.userId;
     myavatarUrl = options.avatarUrl;
    wx.setNavigationBarTitle({
      title: mynickName
    });
    console.log(mysessionId)
    //调用应用实例的方法获取全局数据 
    this.setData({
      user: app.globalData.user
    });
    getMessqge(that);
    wx.request({
      url: myUrl + '/wx/readMessage',
      data: {
        sessionId: mysessionId,
        userId: app.globalData.user.userId
      },
      method: 'POST',
      success: function (res) {

      }
    })
  }, // 页面加载
  onHide: function () {

  }
})

function getMessqge(that) {
  var items = [];
  wx.request({
    url: myUrl + '/wx/showMessage',
    data: {
      sessionId: mysessionId
    },
    method: 'POST',
    success: function (res) {
      var list = res.data.datas;
      var id = myuserId;
      for (var i = 0; i < list.length; i++) {
        var is_right = 1;
        if (list[i].userid == id) {
          is_right = 0;
        }
        items.push({
          nickName: mynickName,
          avatarUrl: myavatarUrl,
          message: list[i].message,
          is_show_right: is_right,
          time: list[i].createTime
        });
      };
    
    },
    fail: function (res) {
    },
    complete: function (res) {
      that.setData({
        items: items
      });
      console.log(that.data)
    }
  });
}