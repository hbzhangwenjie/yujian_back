var app = getApp();
Page({
    data: {
        url: "",
        hidden: false,
        toastHidden: true,
        modalHidden: true,
        toastText: "数据异常",
        loadingText: "加载中..."
    },

    onLoad: function (options) {
        if (options == null || options.url == null) {
            this.setData({ hidden: true, toastHidden: false });
            return;
        }

        this.setData({
            hidden: true,
            toastHidden: true,
            url: options.url
        })
    },
    //Toast信息改变
    onToastChanged: function (event) {
        this.setData({ toastHidden: true });
    },
    // 长按
    onlongclick: function () {
        this.setData({ modalHidden: false });
    },
    // 保存
    ondelClick: function (event) {
      wx.request({
        url: app.globalData.url +'api/deleteImage',
        method:'POST',
        data:{
          userId:app.globalData.user.userId,
          images: event.currentTarget.dataset.url
        },
        success:function(res){
          app.globalData.user.maxImage++;
          wx.showModal({
            title: '提示',
            content: '删除成功',
            showCancel: false,
            success: function(res) {
              if(res.confirm){
                wx.navigateBack({
                  delta: 2
                })
              }
            },
            fail: function(res) {},
            complete: function(res) {},
          })
       
        }

      })

    },
    // 取消
    onCancelClick: function (event) {
        this.setData({ modalHidden: true });
    },
});