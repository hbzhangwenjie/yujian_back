//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    items: [],
    userId: '',
    hidden: false,
    loading: false,
    // loadmorehidden:true,
    plain: false
  },

  onItemClick: function(event) {
    var targetUrl = "../../pages/image/image";
    if (event.currentTarget.dataset.url != null)
      targetUrl = targetUrl + "?url=" + event.currentTarget.dataset.url;
    wx.navigateTo({
      url: targetUrl
    });
  },
  onLoad: function(options) {
    console.log('onLoad')
    var that = this
    this.setData({
      items: [],
      userId: options.userId
    });
    images = [];
    ids = [];
    userIds = [];
    requestData(that);
  }
})

/**
 * 定义几个数组用来存取item中的数据
 */
var images = [];
var ids = [];
var userIds = [];

/**
 * 请求数据
 * @param that Page的对象，用来setData更新数据
 * @param targetPage 请求的目标页码
 */
function requestData(that) {
  wx.request({
    url: app.globalData.url + '/api/showImage',
    header: {
      "Content-Type": "application/json"
    },
    data: {
      userId: that.data.userId,
    },
    method: 'POST',
    success: function(res) {
      if (res == null ||
        res.data == null ||
        res.data.datas == null ||
        res.data.datas.length <= 0) {
        return;
      }
      for (var i = 0; i < res.data.datas.length; i++)
        bindData(res.data.datas[i]);

      //将获得的各种数据写入itemList，用于setData
      var itemList = [];
      for (var i = 0; i < ids.length; i++)
        itemList.push({
          id: ids[i],
          userid: userIds[i],
          imageurl: images[i]
        });

      that.setData({
        items: itemList,
        hidden: true,
        // loadmorehidden:false,
      });
      wx.hideToast();
    }
  });
}

/**
 * 绑定接口中返回的数据
 * @param itemData Gank.io返回的content;
 */
function bindData(itemData) {
  var image = itemData.imageurl;
  var id = itemData.id;
  var userId = itemData.userId;
  images.push(image);
  ids.push(id);
  userIds.push(userId)
}