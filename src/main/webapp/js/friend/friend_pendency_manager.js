//读取好友申请列表
//notifyFlag： true表示来自好友系统通知触发，false表示用户主动点击【获取好友申请】菜单
var getPendency = function (notifyFlag) {
    var options = {
        //请求发起方的帐号，一般情况下为用户本人帐号，在管理员代替用户发起请求的情况下，这里应该填写被代替的用户的帐号
        'From_Account': loginInfo.identifier,
        //Pendency_Type_ComeIn: 别人发给我的; Pendency_Type_SendOut: 我发给别人的
        'PendencyType': 'Pendency_Type_ComeIn',
        //好友申请的起始时间
        'StartTime': 0,
        //分页大小，如果取值为30 则表示客户端要求服务器端每页最多返回30个好友申请
        'MaxLimited': 30,
        //好友申请数据的版本号，用户每收到或删除一条好友申请，服务器端就自增一次好友申请数据版本号，一般传0，表示拉取最新的数据
        'LastSequence': 0
    };

    webim.getPendency(
        options,
        function (resp) {
        	console.log("获取好友申请列表");
        	console.log(resp);
            var data = [];
            if (resp.UnreadPendencyCount > 0) {//存在未读未决
                for (var i in resp.PendencyItem) {
                    var apply_time = webim.Tool.formatTimeStamp(resp.PendencyItem[i].AddTime);
                    var nick = webim.Tool.formatText2Html(resp.PendencyItem[i].Nick);
                    if (nick == '') {
                        nick = resp.PendencyItem[i].To_Account;
                    }
                    var addWording = webim.Tool.formatText2Html(resp.PendencyItem[i].AddWording);
                    data.push({
                        To_Account: webim.Tool.formatText2Html(resp.PendencyItem[i].To_Account),
                        Nick: nick,
                        AddWording: addWording,
                        AddSource: resp.PendencyItem[i].AddSource,
                        AddTime: apply_time
                    });
                }
                
                var date = new Date();
                var msg = "";
                msg = '<div class="weui-cell weui-cell_access chat" onclick="chat(\'system\', \'系统通知\')">' + 
            	    '<div class="weui-cell__hd" style="position: relative;margin-right: 10px;">' +
            	        '<img src="img/msg-system.png" style="width: 50px;display: block;border-radius: 50%">' +
            	        '<span class="weui-badge" style="position: absolute;top: -.4em;right: -.4em;">1</span>' +
            	    '</div>' +
            	    '<div class="weui-cell__bd">' +
            	        '<p>系统消息</p>' +
            	        '<p class="summaryInfo">' + data[0].Nick + '请求添加你为好友</p>' +
            	    '</div>' +
            	    '<div class="cell__rt">' + data[0].AddTime.substring(11, 16) + '</div>' +
            	'</div>';
                
                if (notifyFlag) {
                	$("#chatList").prepend(msg);
                	var span = '<span class="weui-badge weui-badge_dot" style="position: absolute;top: -.2em;right: -.4em;"></span>';
                	$("#tabbar-msg").append(span);
                } else {
                	var chatBody = $("#chatBody");
                	var msg = '<div class="msg-time">' + data[0].AddTime.substring(11, 16) + '</div>' +
                			  '<div id="applyAccount" class="weui-panel weui-panel_access" style="border-radius: 5px;border: 1px solid #eaeaea;" data-id="' + data[0].To_Account + '">' +
			                    '<div class="weui-panel__bd">' +
			                        '<div class="weui-media-box weui-media-box_text">' +
			                            '<h4 class="weui-media-box__title">申请添加好友</h4>' +
			                            '<p class="weui-media-box__desc">' + data[0].Nick + '请求添加你为好友</p>' +
			                            '<p class="weui-media-box__desc">备注：' + data[0].AddWording + '</p>' +
			                            '<ul class="weui-media-box__info">' +
				                            '<li class="weui-media-box__info__meta">' + data[0].AddTime + '</li>' +
				                        '</ul>' +
			                        '</div>' +
			                    '</div>' +
			                    '<div class="weui-panel__ft">' +
			                    	'<a href="javascript:void(0);" class="weui-cell weui-cell_access weui-cell_link" onclick="responseFriend()">' +
					                    '<div class="weui-cell__bd" style="color: #1aad19">同意</div>' +
					                    '<span class="icon-plus"></span>' +
					                '</a>' +
			                    '</div>' +
			                '</div>';
                	
                	chatBody.append(msg);
                }
            } else {
            	//alert('没有加好友申请');
            }

        },
        function (err) {
            alert(err.ErrorInfo);
        }
    );
};

//删除申请列表
var deletePendency = function (del_account) {

    var options = {
        'From_Account': loginInfo.identifier,
        'PendencyType': 'Pendency_Type_ComeIn',
        'To_Account': [del_account]

    };

    webim.deletePendency(
        options,
        function (resp) {

            //在表格中删除对应的行
            $('#get_pendency_table').bootstrapTable('remove', {
                field: 'To_Account',
                values: [del_account]
            });
            alert('删除好友申请成功');
        },
        function (err) {
            alert(err.ErrorInfo);
        }
    );
};
//处理好友申请
var responseFriend = function () {

    var response_friend_item = [
        {
            'To_Account': $("#applyAccount").data("id"),
            "ResponseAction": 'Response_Action_AgreeAndAdd'//类型：Response_Action_Agree 或者 Response_Action_AgreeAndAdd
        }
    ];
    var options = {
        'From_Account': loginInfo.identifier,
        'ResponseFriendItem': response_friend_item
    };

    webim.responseFriend(
        options,
        function (resp) {

            if (response_friend_item[0].ResponseAction == 'Response_Action_AgreeAndAdd') {
            	getMyFriend();
            }

            weui.toast('添加好友成功', 1000);
            onSendMsg("现在我是你的好友，开始聊天吧");
            location.hash = "";
        },
        function (err) {
            //alert(err.ErrorInfo);
        }
    );

};

