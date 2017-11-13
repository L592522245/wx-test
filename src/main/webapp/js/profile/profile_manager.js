//获取用户资料
function getProfilePortraitClick(chatAccount) {
	
    var tag_list = [
        "Tag_Profile_IM_Nick",//昵称
        "Tag_Profile_IM_Gender",//性别
        "Tag_Profile_IM_Image",//头像
        "Tag_Profile_IM_Location",
        "Tag_Profile_IM_AllowType",
    ];
    var options = {
        'To_Account': [chatAccount],
        'TagList': tag_list
    };
    var data = [];
    var userInfo = "";
    webim.getProfilePortrait(
        options,
        function (resp) {
        	console.log("获取用户资料");
        	console.log(resp);
            if (resp.UserProfileItem && resp.UserProfileItem.length > 0) {
                for (var i in resp.UserProfileItem) {
                    var nick, gender, allowType, imageUrl, location;
                    for (var j in resp.UserProfileItem[i].ProfileItem) {
                        switch (resp.UserProfileItem[i].ProfileItem[j].Tag) {
                            case 'Tag_Profile_IM_Nick':
                                nick = resp.UserProfileItem[i].ProfileItem[j].Value;
                                break;
                            case 'Tag_Profile_IM_Gender':
                            	switch (resp.UserProfileItem[i].ProfileItem[j].Value) {
	                                case 'Gender_Type_Male':
	                                    gender = '男';
	                                    break;
	                                case 'Gender_Type_Female':
	                                    gender = '女';
	                                    break;
	                                case 'Gender_Type_Unknown':
	                                    gender = '未知';
	                                    break;
	                            }
                                break;
                            case 'Tag_Profile_IM_AllowType':
                            	switch (resp.UserProfileItem[i].ProfileItem[j].Value) {
	                                case 'AllowType_Type_AllowAny':
	                                    allowType = '允许任何人';
	                                    break;
	                                case 'AllowType_Type_NeedConfirm':
	                                    allowType = '需要确认';
	                                    break;
	                                case 'AllowType_Type_DenyAny':
	                                    allowType = '拒绝任何人';
	                                    break;
	                                default:
	                                    allowType = '需要确认';
	                                    break;
	                            }
                                break;
                            case 'Tag_Profile_IM_Image':
                            	imageUrl = resp.UserProfileItem[i].ProfileItem[j].Value;
                                break;
                            case 'Tag_Profile_IM_Location':
                                location = resp.UserProfileItem[i].ProfileItem[j].Value;
                                break;
                        }
                    }
                    data.push({
                        'To_Account': chatAccount,
                        'Nick': webim.Tool.formatText2Html(nick),
                        'Gender': gender,
                        'AllowType': allowType,
                        'Image': imageUrl,
                        'Location': location
                    });
                }
            }
            
      		userInfo += '<div id="friendAccount" class="weui-cell weui-cell_access cell__c" data-account=' + data[0].To_Account + '>' +
    		                '<div class="weui-cell__hd" style="position: relative;margin-right: 10px;">' +
    		                    '<img src="' + data[0].Image + '" style="width: 50px;display: block;border-radius: 50%">' +
    		                '</div>' +
    		                '<div class="weui-cell__bd" style="color: black;text-align: left;">' +
    		                    '<p>' + data[0].Nick + '</p>' +
    		                    '<p style="font-size: 13px;color: #888888;">' + data[0].Gender + ' ' + data[0].Location + '</p>' +
    		                '</div>' +
    		                '<div class="icon-user" onclick="deleteF()">' +
    				        	'<img src="img/garbage.png" style="width: 20px;display: block;">' +
    				        '</div>' +
    		            '</div>';
    				   
      		dialog(userInfo);
        },
        function (err) {
            //alert(err.ErrorInfo);
        }
    );
}

// 弹出用户信息
function dialog(userInfo) {
	weui.dialog({
	    title: '好友资料',
	    content: userInfo,
	    buttons: [{
	        label: '确定',
	        type: 'primary',
	        onClick: function () { }
	    }]
	});
}

//搜索用户
var searchProfileByUserId = function () {

    if ($("#sp_to_account").val().length == 0) {
        alert('请输入用户ID');
        return;
    }

    if (webim.Tool.trimStr($("#sp_to_account").val()).length == 0) {
        alert('您输入的用户ID全是空格,请重新输入');
        return;
    }

    var tag_list = [
        "Tag_Profile_IM_Nick",//昵称
        "Tag_Profile_IM_Gender",//性别
        "Tag_Profile_IM_AllowType",//加好友方式
        "Tag_Profile_IM_Image"//头像
    ];
    var options = {
        'To_Account': [$("#sp_to_account").val()],
        'TagList': tag_list
    };

    webim.getProfilePortrait(
        options,
        function (resp) {
            var data = [];
            if (resp.UserProfileItem && resp.UserProfileItem.length > 0) {
                for (var i in resp.UserProfileItem) {
                    var to_account = resp.UserProfileItem[i].To_Account;
                    var nick = null, gender = null, allowType = null, imageUrl = null;
                    for (var j in resp.UserProfileItem[i].ProfileItem) {
                        switch (resp.UserProfileItem[i].ProfileItem[j].Tag) {
                            case 'Tag_Profile_IM_Nick':
                                nick = resp.UserProfileItem[i].ProfileItem[j].Value;
                                break;
                            case 'Tag_Profile_IM_Gender':
                                switch (resp.UserProfileItem[i].ProfileItem[j].Value) {
                                    case 'Gender_Type_Male':
                                        gender = '男';
                                        break;
                                    case 'Gender_Type_Female':
                                        gender = '女';
                                        break;
                                    case 'Gender_Type_Unknown':
                                        gender = '未知';
                                        break;
                                }
                                break;
                            case 'Tag_Profile_IM_AllowType':
                                switch (resp.UserProfileItem[i].ProfileItem[j].Value) {
                                    case 'AllowType_Type_AllowAny':
                                        allowType = '允许任何人';
                                        break;
                                    case 'AllowType_Type_NeedConfirm':
                                        allowType = '需要确认';
                                        break;
                                    case 'AllowType_Type_DenyAny':
                                        allowType = '拒绝任何人';
                                        break;
                                    default:
                                        allowType = '需要确认';
                                        break;
                                }
                                break;
                            case 'Tag_Profile_IM_Image':
                                imageUrl = resp.UserProfileItem[i].ProfileItem[j].Value;
                                break;
                        }
                    }
                    data.push({
                        'To_Account': webim.Tool.formatText2Html(to_account),
                        'Nick': webim.Tool.formatText2Html(nick),
                        'Gender': gender,
                        'AllowType': allowType,
                        'Image': imageUrl
                    });
                }
            }
            $('#search_profile_table').bootstrapTable('load', data);
        },
        function (err) {
            alert(err.ErrorInfo);
        }
    );
};

//搜索用户的加好友设置项
var searchProfileAllowTypeByUserId = function (to_account, cbok, cberr) {

    var allowType = '需要确认';
    if (to_account.length == 0) {
        if (cberr) {
            cberr('对方帐号为空');
            return;
        }
    }
    var tag_list = [
        "Tag_Profile_IM_AllowType"
    ];
    var options = {
        'To_Account': [to_account],
        'TagList': tag_list
    };

    webim.getProfilePortrait(
        options,
        function (resp) {
            if (resp.UserProfileItem && resp.UserProfileItem.length > 0) {
                for (var i in resp.UserProfileItem) {
                    for (var j in resp.UserProfileItem[i].ProfileItem) {
                        switch (resp.UserProfileItem[i].ProfileItem[j].Tag) {
                            case 'Tag_Profile_IM_AllowType':
                                switch (resp.UserProfileItem[i].ProfileItem[j].Value) {
                                    case 'AllowType_Type_AllowAny':
                                        allowType = '允许任何人';
                                        break;
                                    case 'AllowType_Type_NeedConfirm':
                                        allowType = '需要确认';
                                        break;
                                    case 'AllowType_Type_DenyAny':
                                        allowType = '拒绝任何人';
                                        break;
                                    default:
                                        allowType = '需要确认';
                                        break;
                                }
                                break;
                        }
                    }
                }
            }
            if (cbok) {
                cbok(allowType);
            }
        },
        function (errmsg) {
            if (cberr) {
                cberr(errmsg);
            }
        }
    );

};

//设置个人资料
var setProfilePortrait = function (data) {
    var profile_item = [
        {
            "Tag": "Tag_Profile_IM_Nick",
            "Value": data[0].nick
        },
        {
            "Tag": "Tag_Profile_IM_Image",
            "Value": data[0].image
        },
        {
            "Tag": "Tag_Profile_IM_AllowType",
            "Value": data[0].allowType
        },
        {
            "Tag": "Tag_Profile_IM_Gender",
            "Value": data[0].gender
        },
        {
            "Tag": "Tag_Profile_IM_Location",
            "Value": data[0].location
        }
    ];
    var options = {
        'ProfileItem': profile_item
    };

    webim.setProfilePortrait(
        options,
        function (resp) {
        	console.log('设置个人资料成功');
        },
        function (err) {
        	console.log(err.ErrorInfo);
        }
    );
};