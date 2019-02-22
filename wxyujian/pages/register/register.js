var app = getApp();
Page({
  scopeData:{},
  data: {
    img:'',
    toast:{
      show: false,
      isMask: false,
      content: ''
    }
  },

  onLoad:function () {
  },

  onPullDownRefresh:function () {
    wx.stopPullDownRefresh()
  },

  updateData: function (e) {
    var key = e.currentTarget.dataset.key;
    this.scopeData[key] = e.detail.value.replace(/^\s+|\s+$/,'') || '';
  },

  addImg : function() {

    var _this = this;

    wx.chooseImage({
      count : 1,//当前最多可以选择的图片数量
      success : function(data){
        var upImgs = data.tempFilePaths;
        wx.showToast({
          title: '上传中',
          icon: 'loading',
          duration: 3000000
        });
        wx.uploadFile({
          url: app.globalData.url+"/api/upload",
          filePath : upImgs[0],
          name : "file",
          complete : function(){
            wx.hideToast();
          },
          success : function(res){
            var mydata = JSON.parse(res.data);
            var result = mydata.result;
            if (result=="sucess"){
              _this.setData({
                img: mydata.data
              });          
            }else{ 
              wx.showModal({
                title: '提示：',
                showCancel: false,
                content: '图片最大不超过10MB',
              });
            }
          },
          fail : function(err){
            console.log(err);
            wx.showModal({
              title: '提示：',
              showCancel: false,
              content: '图片最大不超过10MB',
            })
          }
        });

      },
      fail : function(err){
        console.log("fail");
        console.log(err);
        wx.showModal({
          title: '提示：',
          showCancel: false,
          content: '图片最大不超过10MB',
        })
      }
    });
  },

  imageLoadErr: function(e){

    var url = e.currentTarget.dataset.url;
    url = ~url.indexOf('?')?'&':'?'+'e';
    this.setData({
      img:url
    });
  },

  previewImg : function(e){
    var img = this.data.img;
    wx.previewImage({
      current : img,
      urls : [img]
    });
  },
  doReg:function(e) {
    var self = this;

    if(!this.scopeData.username || !this.scopeData.password){
      wx.showToast({
        title: '邮箱和密码不能为空',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    if(this.scopeData.password!=this.scopeData.rpassword){
      wx.showToast({
        title: '两次密码不一致',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    if (!self.data.img || self.data.img==''){
      wx.showToast({
        title: '请上传一张头像！',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    wx.request({
      url: app.globalData.url +"/api/register",
      data : {
        img : self.data.img,
        mail : self.scopeData.username,
        passWord : self.scopeData.password
      },
      method:'POST',
      success : function(res){
        if(res.data.result=="sucess"){
          app.globalData.user = res.data.data;
          wx.showToast({
            title: '注册成功',
            icon: 'loading',
            duration: 1000,
            success: function (rt) {
              wx.redirectTo({
                url: '/pages/user/user'
              })
            }
          });
        }else{
          wx.showToast({
            title: '注册失败'+res.data.result,
            icon: 'none',
            duration: 3000
          });
        }
      },
      fail : function(){
        toast.show(self,'登录失败');
      }
    });
  }
})
