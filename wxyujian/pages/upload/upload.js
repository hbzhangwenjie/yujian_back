var app = getApp();
Page({
  name: 'upload-index',
  data: {
    toast: {
      show: false,
      isMask: false,
      content: ''
    },
    thumbImgs: [],//编辑时候才有的
    imgs: [],
    title: '',
    maxImg: '',//最大上传数量
  },
  save: function () {
    var _this = this,
      data = _this.data;
    if (!data.imgs.length) {
      wx.showModal({
        title: '提示：',
        showCancel: false,
        content: '请选择图片！',
      })
      return;
    }
    _this.setData({
      saveIng: true
    });

    wx.request({
      url: app.globalData.url + "/api/uploadImage",
      data: {
        userId: app.globalData.user.userId,
        images: data.imgs.join(',')
      },
      method: 'POST',
      complete: function () {
        _this.setData({
          saveIng: false
        });
      },
      success: function (res) {
        if (res.data.result == 'sucess') {
          app.globalData.user.maxImage = _this.data.maxImg - _this.data.imgs.length;
          _this.data.maxImg = app.globalData.user.maxImage;
          wx.redirectTo({
            url: '../user/user'
          })
        } else {
          wx.showModal({
            title: '提示：',
            showCancel: false,
            content: '图片最大不超过10MB',
          })
        }
      }
    });
  },

  delImg: function (e) {
    var index = e.currentTarget.dataset.index;
    console.log("当前位置第" + index + "个");
    //开始删除数组项
    var imgs = this.data.imgs;

    if (!imgs.length) {
      return;
    }
    this.data.thumbImgs.splice(index, 1);
    imgs.splice(index, 1);

    this.setData({
      "imgs": imgs,
      "thumbImgs": this.data.thumbImgs
    });
  },

  addImg: function () {
    
    var _this = this;
    if ((_this.data.maxImg - _this.data.imgs.length)<=0){
      wx.showModal({
        title: '提示：',
        showCancel: false,
        content:'相册最多5张图片！',
      })
      return;
    }
    var c = _this.data.maxImg - _this.data.imgs.length;
    wx.chooseImage({
      count: c ,//当前最多可以选择的图片数量
      success: function (data) {
        console.log(data);
        var upImgs = data.tempFilePaths,
          upImgsLength = upImgs.length;
        console.log(upImgs);
        if (!upImgsLength) {
          console.log("图片路径错误");
          return;
        }
        wx.showToast({
          title: '上传中',
          icon: 'loading',
          duration: 3000000
        });
        var errorNum = 0,
          successNum = 0,
          imgs = _this.data.imgs,
          thumbImgs = _this.data.thumbImgs;

        //开始循环上传
        (function multiImgUpload(i) {
          if (i === upImgsLength) {
            console.log("结束");
            if (errorNum) {
              wx.showModal({
                title: '提示：',
                showCancel: false,
                content: "上传结果：成功" + successNum + "个 失败" + errorNum + "个",
              })
            }
            _this.setData({
              "imgs": imgs,
              thumbImgs: thumbImgs
            });
            wx.hideToast();
            return;
          }
          wx.uploadFile({
            url: app.globalData.url + "/api/upload",
            filePath: upImgs[i],
            name: "file",
            complete: function () {
              //上传完毕 不论成与否 都继续下一个
              // _arguments.callee(++i);
              multiImgUpload(++i);
            },
            success: function (rt) {
              console.log("--success");
              console.log(rt);
              var mydata = JSON.parse(rt.data);
              if (mydata.result == 'sucess') {
                var imgUrl = mydata.data;
                imgs.push(imgUrl);
                thumbImgs.push(imgUrl);
                successNum++;
              } else {
                wx.showModal({
                  title: '提示：',
                  showCancel: false,
                  content: '单张图片最大不超过10MB',
                })
              }
            },
            fail: function (err) {
              errorNum++;
            }
          });
        }(0));
      
      },
      fail: function (err) {
        console.log("fail");
        console.log(err);
      }
    });
  },

  imageLoadErr: function (e) {
    var url = e.currentTarget.dataset.url,
      index = e.currentTarget.dataset.index;
    this.data.thumbImgs[index] = ~url.indexOf('?') ? '&' : '?' + 'e';
    this.setData({
      thumbImgs: this.data.thumbImgs
    });
  },

  previewImg: function (e) {
    var index = e.currentTarget.dataset.index,
      arr = this.data.imgs;

    wx.previewImage({
      current: arr[index],
      urls: arr
    });
  },

  bindInputChange: function (e) {
    var target = e.currentTarget.dataset.target,
      val = e.detail.value;

    if (target == "title") {
      this.setData({
        "title": val
      });
    }

  },

  onLoad: function (e) {
    this.setData({
      maxImg: app.globalData.user.maxImage
    })
  }
});
