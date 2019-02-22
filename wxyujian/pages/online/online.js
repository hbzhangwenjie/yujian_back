//index.js
//获取应用实例
var app = getApp();
var myurl = app.globalData.url;
Page({
  data: {
    items: [],
    hidden: false,
    loading: false,
    // loadmorehidden:true,
    plain: false,
  },
  onItemClick: function (e) {
    var toUserid = e.currentTarget.dataset.id;
    var userId = app.globalData.user.userId;
    //获取sessionID
    wx.request({
      url: myurl + '/wx/findSession',
      data: {
        toUserId: toUserid,
        userId: userId
      },
      method: 'POST',
      success: function (res) {
        if (res.data.result == "sucess") {
          wx.redirectTo({
            url: '../message/message?sessionId=' + res.data.data + "&userId=" + e.currentTarget.dataset.id
              + "&nickName=" + e.currentTarget.dataset.name
              + "&avatarUrl=" + e.currentTarget.dataset.img
          })
        }
      }
    })
  },
  onImageClick: function (e) {
    var toUserid = e.currentTarget.dataset.id;
    //获取sessionID
    wx.navigateTo({
        url: '../tathumb/tathumb?userId=' + e.currentTarget.dataset.id    
      })
  },


  onReachBottom: function () {
    console.log('onLoad')
    var that = this
    that.setData({
      hidden: false,
    });
    requestData(that, mCurrentPage + 1);
  },

  onLoad: function () {
    var that = this;
    console.log('onLoad');
     names = [];
     avatarUrls = [];
     introduces = [];
     schools = [];
     professions = [];
     genders=[];

    var mCurrentPage = 0;
    this.setData({
      items: [],
    })
    requestData(that, 1);
  },

})

/**
 * 定义几个数组用来存取item中的数据
 */
var names = [];
var avatarUrls = [];
var introduces = [];
var schools = [];
var professions = [];
var userIds = [];
var genders=[];
var mCurrentPage = 0;

// 引入utils包下的js文件
//var Constant = require('../../utils/constant.js');

/**
 * 请求数据
 * @param that Page的对象，用来setData更新数据
 * @param targetPage 请求的目标页码
 */
function requestData(that, targetPage) {
  var userId = getApp().globalData.user.userId;
  wx.showToast({
    title: '加载中',
    icon: 'loading'
  });
  wx.request({
    url: myurl+'/wx/showUser',
    header: {
      "Content-Type": "application/json"
    },
    data: {
      pageNo: targetPage,
      gender: 1,
      userId: userId
    },
    method: 'POST',
    success: function (res) {
      if (res == null ||
        res.data == null ||
        res.data.datas == null ||
        res.data.datas.length <= 0) {
        wx.showToast({
          title: '到底了！',
          icon: 'none'
        });
      
        return;
      }

      for (var i = 0; i < res.data.datas.length; i++)
        bindData(res.data.datas[i]);

      //将获得的各种数据写入itemList，用于setData
      var itemList = [];
      for (var i = 0; i < names.length; i++)
        itemList.push({ nickName: names[i], avatarUrl: avatarUrls[i], introduces: introduces[i], school: schools[i], profession: professions[i], userId: userIds[i], gender: genders[i]});

      that.setData({
        items: itemList,
        hidden: true,
        // loadmorehidden:false,
      });

      mCurrentPage = targetPage;

      wx.hideToast();
    }
  });
}

/**
 * 绑定接口中返回的数据
 * @param itemData Gank.io返回的content;
 */
function bindData(itemData) {
  var userId = itemData.userId;
  var userName = itemData.nickName;
  var userAvatarUrl = itemData.avatarUrl;
  var userIntroduce = itemData.introduce;
  var userSchool = itemData.school;
  var userProfrssions = itemData.profession;
  var userGender = itemData.gender;
  names.push(userName);
  avatarUrls.push(userAvatarUrl);
  introduces.push(userIntroduce);
  schools.push(userSchool);
  professions.push(userProfrssions);
  userIds.push(userId);
  genders.push(userGender);

}