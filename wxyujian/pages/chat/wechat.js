var app = getApp()
// console.log(JSON)
Page({
    data:{
      list:null,
      hidden:true,
      toast1Hidden:true
    },
    onReady:function(){
        getUser(this);
    },
    goPage:function(e){
        console.log(e)
        var _self = this;
        var newlist = _self.data.list
        var index = e.currentTarget.dataset.index
        newlist[index].count=0;
        _self.setData({
            list: newlist
        })

      wx.navigateTo({
              url: '../message/message?sessionId=' + e.currentTarget.dataset.session +"&userId="+e.currentTarget.dataset.id
            + "&nickName=" + e.currentTarget.dataset.name
            + "&avatarUrl=" + e.currentTarget.dataset.img
        })
        // console.log(test);
    },
    toast1Change:function(){
        this.setData({
            toast1Hidden: true
        })
    },
    onPullDownRefresh:function(){      
      getUser(this);     
    },
    
})
function getUser(obj) {
  obj.setData({
    hidden: false
  })
  wx.request({
    url: app.globalData.url + '/wx/showSession',
    data: {
      userId: app.globalData.user.userId
    },
    method:'POST',
    header: {
      'Content-Type': 'application/json'
    },
    success: function (res) {
        obj.setData({
          list: res.data.datas,
          hidden: true,
          toast1Hidden: false,
          toastText: "sucess"
        })
        wx.stopPullDownRefresh()    
    },
    fail: function (err) {
        obj.setData({
          hidden: true,
          toast1Hidden: false,
          toastText: "请检查server"
        })
        wx.stopPullDownRefresh()
      console.log(err);
    }
  })
}


