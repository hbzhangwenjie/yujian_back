//app.js
App({
  globalData: {
    url: 'http://139.199.90.33:8080/',
   //url:'https://www.yujianguanggu.xyz/',
  //url: 'http://127.0.0.1:8080/',
    user: {
      openId: '',
      userId:'',
      nickName: '',
      gender: 0,
      city: '',
      province: '',
      country: '',
      avatarUrl:'',
      profession:'',
      school:'',
      introduce:'',
      maxImage:''
    },
    loginData: {
      session_key: '',
      expires_in: '',
      openid: ''
    }
  },

  onLaunch: function () {
  
  },

})



