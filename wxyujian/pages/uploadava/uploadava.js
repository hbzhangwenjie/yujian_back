var app = getApp();
Page({
  scopeData: {},
  data: {
    img: ''
  },

  onLoad: function () {
  },

  onPullDownRefresh: function () {
    wx.stopPullDownRefresh()
  },

  addImg: function () {
    var _this = this;
    wx.chooseImage({
      count: 1,//当前最多可以选择的图片数量
      success: function (data) {
        var upImgs = data.tempFilePaths;
        wx.showToast({
          title: '上传中',
          icon: 'loading',
          duration: 3000000
        });
        wx.uploadFile({
          url: app.globalData.url + "/api/upload",
          filePath: upImgs[0],
          name: "file",
          complete: function () {
            wx.hideToast();
          },
          success: function (res) {
            var mydata = JSON.parse(res.data);
            var result = mydata.result;
            if (result == "sucess") {
              _this.setData({
                img: mydata.data
              });
             
            } else {
              wx.showModal({
                title: '提示：',
                showCancel: false,
                content: '图片最大不超过10MB',
              })
            }
          },
          fail: function (err) {
            console.log(err);
            wx.showModal({
              title: '提示：',
              showCancel: false,
              content: '图片最大不超过10MB',
            })
          }       
        });

      },
      fail: function (err) {
      }
    });
  },

  imageLoadErr: function (e) {
    var url = e.currentTarget.dataset.url;
    url = ~url.indexOf('?') ? '&' : '?' + 'e';
    this.setData({
      img: url
    });
  },
  previewImg: function (e) {
    var img = this.data.img;
    wx.previewImage({
      current: img,
      urls: [img]
    });
  },
  doReg: function (e) {
    var self = this;
    if (!self.data.img || self.data.img == '') {
      wx.showModal({
        title: '提示：',
        showCancel: false,
        content: '请上传一张头像！',
      })
      return;
    }
    var pages = getCurrentPages();
    var avatarUrl = self.data.img;
    var prevPage = pages[pages.length - 2]; //上一个页面 
    //直接调用上一个页面的setData()方法，把数据存到上一个页面中去
    prevPage.setData({
      avatarUrl: avatarUrl
    });
    wx.request({
      url: app.globalData.url + "/api/uploadava",
      data: {
        images: self.data.img,
        userId:app.globalData.user.userId
      },
      method: 'POST',
      success: function (res) {
        if (res.data.result == "sucess") {
          app.globalData.user.avatarUrl = self.data.img;
          wx.showToast({
            title: '跟新成功',
            icon: 'none',
            duration: 2000,
            success: function (rt) {
              wx.redirectTo({
                url: '/pages/user/user'
              })
            }
          });
        } else {
          wx.showModal({
            title: '提示：',
            showCancel: false,
            content: '最大不能超多10MB',
          })
        }
      },
      fail: function () {
        toast.show(self, '登录失败');
      }
    });
  }
})
