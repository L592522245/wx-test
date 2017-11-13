//申请加好友
var applyAddFriend = function () {
	console.log($("#addWording").val());
    var add_friend_item = [
        {
            'To_Account': $("#friendAccount").data("account"),
            "AddSource": "AddSource_Type_Unknow",
            "AddWording": $("#addWording").val() //加好友附言，可为空
        }
    ];
    var options = {
        'From_Account': loginInfo.identifier,
        'AddFriendItem': add_friend_item
    };

    webim.applyAddFriend(
        options,
        function (resp) {
        	console.log("好友申请");
        	console.log(resp);
            if (resp.Fail_Account && resp.Fail_Account.length > 0) {
                for (var i in resp.ResultItem) {
                    //alert(resp.ResultItem[i].ResultInfo);
                    break;
                }
            } else {
            	weui.toast("已发送请求！", 1000);
            }
        },
        function (err) {
            //alert(err.ErrorInfo);
        }
    );
};

//删除好友
var deleteFriend = function () {
    var to_account = [];

    to_account = [
        $("#friendAccount").data("account")
    ];
    var options = {
        'From_Account': loginInfo.identifier,
        'To_Account': to_account,
        //Delete_Type_Both'//单向删除："Delete_Type_Single", 双向删除："Delete_Type_Both".
        'DeleteType': 'Delete_Type_Both'
    };

    webim.deleteFriend(
        options,
        function (resp) {
            //重新加载好友列表
        	getMyFriend();
            weui.toast('删除好友成功', 1000);
        },
        function (err) {
            //alert(err.ErrorInfo);
        }
    );

};
//获取我的好友
var getMyFriend = function () {
	var myFriend = $("#myFriend");
    var myFriendList = $("#myFriendList");
    var myFriendCount = $("#myFriendCount");
	//清空聊天对象列表
    myFriendList.html("");
    var totalCount = 100;

    var options = {
        'From_Account': loginInfo.identifier,
        'TimeStamp': 0,
        'StartIndex': 0,
        'GetCount': totalCount,
        'LastStandardSequence': 0,
        "TagList": [
			"Tag_Profile_IM_Nick",
			"Tag_Profile_IM_Image"
        ]
    };

    webim.getAllFriend(
        options,
        function (resp) {
        	console.log("获取好友列表");
        	console.log(resp);
            if (resp.ErrorCode == "0") {

            	var friends = resp.InfoItem;
		        if (!friends || friends.length == 0) {
		        	console.log("no friend");
		        	myFriendCount.html(0);
		            return;
		        }
		        
		        var count = friends.length;
		    	myFriendCount.html(count);
		        
		        var friendCard = "";
		        for (var i = 0; i < count; i++) {
		            var friend_name, friend_headUrl, friend_account;
		            friend_account = friends[i].Info_Account;
		            if (friends[i].SnsProfileItem && friends[i].SnsProfileItem[0] 
		                && friends[i].SnsProfileItem[0].Tag) {
		                friend_name = friends[i].SnsProfileItem[0].Value;
		                friend_headUrl = friends[i].SnsProfileItem[1].Value;
		            }
		            if (friend_name.length > 7) {//帐号或昵称过长，截取一部分
		                friend_name = friend_name.substr(0, 7) + "...";
		            }
		            //增加一个好友div
		            friendCard += '<div class="weui-cell weui-cell_access cell__c friend chat" onclick="chat(\'friend\', \'' + friend_name + '\', \'' + friend_account + '\')">' +
				                '<div class="weui-cell__hd" style="position: relative;margin-right: 10px;">' +
				                    '<img src="' + friend_headUrl + '" style="width: 50px;display: block;border-radius: 50%">' +
				                '</div>' +
				                '<div class="weui-cell__bd">' +
				                    '<p>' + friend_name + '</p>' +
				                '</div>' +
				            '</div>';
		            
		        }
		        myFriendList.html(friendCard);
            }
        },
        function (err) {
            //alert(err.ErrorInfo);
        	console.log(err.ErrorInfo);
        }
    );
};

//从我的好友列表中给好友发消息
function sendC2cMsg(){

    toAccount=$("#scm_to_account").val();
    msgtosend=$("#scm_content").val();

    var msgLen = webim.Tool.getStrBytes(msgtosend);

    var maxLen, errInfo;

    maxLen = webim.MSG_MAX_LENGTH.C2C;
    errInfo = "消息长度超出限制(最多" + Math.round(maxLen / 3) + "汉字)";

    if (msgtosend.length < 1) {
        alert("发送的消息不能为空!");
        $("#send_msg_text").val('');
        return;
    }

    if (msgLen > maxLen) {
        alert(errInfo);
        return;
    }

    var sess=webim.MsgStore.sessByTypeId(webim.SESSION_TYPE.C2C, toAccount);
    if (!sess) {
        sess = new webim.Session(webim.SESSION_TYPE.C2C, toAccount, toAccount, friendHeadUrl, Math.round(new Date().getTime() / 1000));
    }
    var isSend = true;//是否为自己发送
    var seq = -1;//消息序列，-1表示sdk自动生成，用于去重
    var random = Math.round(Math.random() * 4294967296);//消息随机数，用于去重
    var msgTime = Math.round(new Date().getTime() / 1000);//消息时间戳
    var subType;//消息子类型

    subType = webim.C2C_MSG_SUB_TYPE.COMMON;

    var msg = new webim.Msg(sess, isSend, seq, random, msgTime, loginInfo.identifier, subType, loginInfo.identifierNick);

    var text_obj;

    text_obj = new webim.Msg.Elem.Text(msgtosend);
    msg.addText(text_obj);

    webim.sendMsg(msg, function (resp) {


        if (!selToID) {//没有聊天会话
            selType=webim.SESSION_TYPE.C2C;
            selToID=toAccount;
            selSess=sess;
            addSess(selType,toAccount, toAccount, friendHeadUrl, 0, 'sesslist');
            setSelSessStyleOn(toAccount);
            //私聊时，在聊天窗口手动添加一条发的消息，群聊时，长轮询接口会返回自己发的消息
            addMsg(msg);
        }else{//有聊天会话
            if(selToID==toAccount){//聊天对象不变
                addMsg(msg);
            }else{//聊天对象发生改变
                var tempSessDiv = document.getElementById("sessDiv_" + toAccount);
                if(!tempSessDiv){//不存在这个会话
                    addSess(webim.SESSION_TYPE.C2C,toAccount, toAccount, friendHeadUrl, 0, 'sesslist');//增加一个会话
                }

                onSelSess(webim.SESSION_TYPE.C2C,toAccount);//再进行切换
            }
        }
        webim.Tool.setCookie("tmpmsg_" + toAccount, '', 0);
        $("#scm_content").val('');
        $('#send_c2c_msg_dialog').modal('hide');
        $('#get_my_friend_dialog').modal('hide');

    }, function (err) {
        alert(err.ErrorInfo);
        $("#scm_content").val('');
    });
}

//将我的好友资料（昵称和头像）保存在infoMap
var initInfoMapByMyFriends = function (cbOK) {
	var totalCount = 100;
    var options = {
        'From_Account': loginInfo.identifier,
        'TimeStamp': 0,
        'StartIndex': 0,
        'GetCount': totalCount,
        'LastStandardSequence': 0,
        "TagList": [
            "Tag_Profile_IM_Nick",
            "Tag_Profile_IM_Image"
        ]
    };

    webim.getAllFriend(
        options,
        function (resp) {
            if (resp.FriendNum > 0) {

                var friends = resp.InfoItem;
                if (!friends || friends.length == 0) {
                    if (cbOK)
                        cbOK();
                    return;
                }
                var count = friends.length;

                for (var i = 0; i < count; i++) {
                    var friend=friends[i];
                    var friend_account = friend.Info_Account;
                    var friend_name=friend_image='';
                    for (var j in friend.SnsProfileItem) {
                        switch (friend.SnsProfileItem[j].Tag) {
                            case 'Tag_Profile_IM_Nick':
                                friend_name = friend.SnsProfileItem[j].Value;
                                break;
                            case 'Tag_Profile_IM_Image':
                                friend_image = friend.SnsProfileItem[j].Value;
                                break;
                        }
                    }
                    var key=webim.SESSION_TYPE.C2C+"_"+friend_account;
                    infoMap[key]={
                        'name':friend_name,
                        'image':friend_image
                    };
                }
                if (cbOK)
                    cbOK();
            }
        },
        function (err) {
            console.log(err.ErrorInfo);
        }
    );
};


