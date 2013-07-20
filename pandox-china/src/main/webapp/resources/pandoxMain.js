/**
 * ===============================
 * |        FACEBOOK SDK         |
 * ===============================
 */
window.fbAsyncInit = function() {

    FB.init({
        appId      : '419303404849927', // App ID
        channelUrl : '//WWW.YOUR_DOMAIN.COM/channel.html', // Channel File
        status     : true, // check login status
        cookie     : true, // enable cookies to allow the server to access the session
        xfbml      : true  // parse XFBML
    });
    jQuery('#fb-root').trigger('facebook:init');

    // Here we subscribe to the auth.authResponseChange JavaScript event. This event is fired
    // for any authentication related change, such as login, logout or session refresh. This means that
    // whenever someone who was previously logged out tries to log in again, the correct case below
    // will be handled.
    FB.Event.subscribe('auth.authResponseChange', function(fbResponse) {
        // Here we specify what we do with the response anytime this event occurs.
        if (fbResponse.status === 'connected') {
            console.log(fbResponse);
            // The response object is returned with a status field that lets the app know the current
            // login status of the person. In this case, we're handling the situation where they
            // have logged in to the app.
            loginPandox(fbResponse);
        } else if (response.status === 'not_authorized') {
            // In this case, the person is logged into Facebook, but not into the app, so we call
            // FB.login() to prompt them to do so.
            // In real-life usage, you wouldn't want to immediately prompt someone to login
            // like this, for two reasons:
            // (1) JavaScript created popup windows are blocked by most browsers unless they
            // result from direct interaction from people using the app (such as a mouse click)
            // (2) it is a bad experience to be continually prompted to login upon page load.
            FB.login();
        } else {
            // In this case, the person is not logged into Facebook, so we call the login()
            // function to prompt them to do so. Note that at this stage there is no indication
            // of whether they are logged into the app. If they aren't then they'll see the Login
            // dialog right after they log in to Facebook.
            // The same caveats as above apply to the FB.login() call here.
            FB.login();
        }
    });
};

// Load the SDK asynchronously
(function(d){
    var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement('script'); js.id = id; js.async = true;
    js.src = "//connect.facebook.net/en_US/all.js";
    ref.parentNode.insertBefore(js, ref);
}(document));

// Here we run a very simple test of the Graph API after login is successful.
// This testAPI() function is only called in those cases.
function loginPandox(fbResponse) {
    FB.api('/me', function(response) {
        var user = new Object();
        user.uid = response.id;
        user.name = response.name;
        user.accessToken = fbResponse.authResponse.accessToken;

        loginUser(user);

    });
}
function loginUser(user){
    // Assign handlers immediately after making the request,
    // and remember the jqxhr object for this request
    console.log('Log in to Pandox. user=' + user.uid);
    var jqxhr = $.getJSON('/user?uid=' + user.uid, function() {})
        .done(function(data) {
            if(data == null){
                console.log("Creating user...");
                createUser(user);
            }else {
                console.log("Logado..." + data.id);
            }
        })
        .fail(function() {
            console.log("Fails...");
//            createUser(user);
        });
}
function createUser(user){
    console.log("Creating user.");
    console.log(user);
    var json = JSON.stringify(user);

    console.log(json);

    $.ajax({
        type : 'POST',
        url : 'user',
        dataType : 'json',
        contentType : "application/json",
        data: json,
        success: function(response){
            window.location = "usuario/" + response.id + "/admin";
            PANDOX.UI.showMessage(response.message, "alert-success");
        }
    });
}
/**
 * ===============================
 * |              END            |
 * ===============================
 */





var PANDOX = {

    /**
     * Manipulate Graphics Components
     */
    UI: {
        /**
         * Generate a message Box in the page.
         * @param text Text to be show
         * @param css CSS class to provide to the box. Valid class are: alert-success, alert-error, alert-warning
         */
        showMessage: function(text, css){
            var box = $("#msgBox");

            box.append(text);
            box.addClass(css);
            box.show();
        },

        /******************************************************************************************************
         * Parse dateStr as formatted date
         * @return: if dateStr can't be parsed as Date, return dateStr
         * @credits: neosmart.de/social-media/facebook-wall/
         ******************************************************************************************************/
        formatDate: function formatDate(dateStr){
            var year, month, day, hour, minute, dateUTC, date, ampm, d, time;
            var iso = (dateStr.indexOf(' ')==-1&&dateStr.substr(4,1)=='-'&&dateStr.substr(7,1)=='-'&&dateStr.substr(10,1)=='T') ? true : false;

            if(iso){
                year = dateStr.substr(0,4);
                month = parseInt((dateStr.substr(5,1)=='0') ? dateStr.substr(6,1) : dateStr.substr(5,2))-1;
                day = dateStr.substr(8,2);
                hour = dateStr.substr(11,2);
                minute = dateStr.substr(14,2);
                dateUTC = Date.UTC(year, month, day, hour, minute);
                date = new Date(dateUTC);
            }else{
                d = dateStr.split(' ');
                if(d.length!=6||d[4]!='at')
                    return dateStr;
                time = d[5].split(':');
                ampm = time[1].substr(2);
                minute = time[1].substr(0,2);
                hour = parseInt(time[0]);
                if(ampm=='pm')hour+=12;
                date = new Date(d[1]+' '+d[2]+' '+d[3] +' '+ hour+':'+minute);
                date.setTime(date.getTime()-(1000*60*60*7));
            }
            day = (date.getDate()<10)?'0'+date.getDate():date.getDate();
            month = date.getMonth()+1;
            month = (month<10)?'0'+month:month;
            hour = date.getHours();
            minute = (date.getMinutes()<10)?'0'+date.getMinutes():date.getMinutes();
//            if(o.timeConversion==12){
//                ampm = (hour<12) ? 'am' : 'pm';
//                if(hour==0)hour==12;
//                else if(hour>12)hour=hour-12;
//                if(hour<10)hour='0'+hour;
//                return day+'.'+month+'.'+date.getFullYear()+' at '+hour+':'+minute+' '+ampm;
//            }
//            return day+'.'+month+'.'+date.getFullYear()+' '+o.translateAt+' '+hour+':'+minute;
            return day+'/'+month+'/'+date.getFullYear()+' às '+hour+':'+minute;
        }


    },


    /**
     * Call FacebookAPI for resources
     */
    FACEBOOK: {

        getFeed: function(id){
            var jqxhr = $.getJSON('/user/' + id + "/feed", function() {})
                .done(function(response) {
                    if(response != null){
                        console.log("User Feed founded...");
                        PANDOX.FACEBOOK.renderWall(response.data);
                    }else {
                        console.log("FALHA no FEED..." + id);
                    }
                })
                .fail(function() {
                    console.log("Fails...");
                });
        },

        /**
         * Render Facebook WALL
         * @param data
         */
        renderWall: function(data){
            $.each(data, function(key, val) {
                var wall = $("#social");

                var output = '';

//                <!-- POST -->
//                <div class="span4">
//                    <div class="owner">
//                    Matheus Messora
//                    </div>
//                    <div class="postdate">
//                    2013-12-31
//                    </div>
//                    <div class="message">
//                    Hi i have used neosmart but i am getting this errror anyone can help me?<br />
//                    Error validating access token: Session has expired at unix time 1371988800. The current unix time is 1371995857
//
//                    Hi i have used neosmart but i am getting this errror anyone can help me?<br />
//                    Error validating access token: Session has expired at unix time 1371988800. The current unix time is 1371995857
//
//                    </div>
//                    <div class="commentBox">
//                        <div class="photo">
//                            <img src="https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/623646_100000194398266_210892354_q.jpg" width="32px" height="32px" />
//                        </div>
//                        <div class="comment">
//                            <span class="user">Icaro Paiva</span> Fiz muito isso no tcc Hahaha kakak Sorry Jamat, but we don't have a live chat option. Yes, there is a Facebook login feature, facebook profile sync and the posts from your site will auto post on your Facebook profile
//                            <span class="date">2013-12-31</span>
//                        </div>
//
//                        <div class="clearfix"> </div>
//                    </div>
//                    <div class="commentBox">
//                        <div class="photo">
//                            <img src="https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash3/623646_100000194398266_210892354_q.jpg" width="32px" height="32px" />
//                        </div>
//                        <div class="comment">
//                            <span class="user">Icaro Paiva</span> Fiz muito isso no tcc Hahaha kakak Sorry Jamat, but we don't have a live chat option. Yes, there is a Facebook login feature, facebook profile sync and the posts from your site will auto post on your Facebook profile
//                            <span class="date">2013-12-31</span>
//                        </div>
//
//                        <div class="clearfix"> </div>
//                    </div>
//                </div>
//                    <!-- POST -->

                output += '<div class="span4">';

                output += '<div class="owner">';
                output += val.from.name;
                output += '</div>';
                output += '<div class="postdate">';
                output += PANDOX.UI.formatDate(val.created_time);
                output += '</div>';

                output += '<div class="message">';
                output += val.message;
                output += '</div>';

                if(PANDOX.FACEBOOK.isMediaPost(val)){
                    output += PANDOX.FACEBOOK.generateMediaPost(val);
                }





                output += '</div>';

                wall.append(output);
            });
        },

        /**
         * Validade if post is MEDIA format
         * @param data
         */
        isMediaPost: function(data){
            return PANDOX.FACEBOOK.exists(data.description);
        },

        /**
         * Generate a Media Post for Wall
         * @param data
         */
        generateMediaPost: function(data){
//##                    <div class="mediaBox">
//            ##                        <span class="title">TheFWA - MICHAEL HEINSEN PHOTOGRAPHER</span><br />
//            ##                        <span class="subtitle">www.thefwa.com</span><br />
//            ##                        <span class="message">Favourite website awards. Web Awards recognising the very best in cutting edge website design.</span>
//            ##                    </div>
//##
//            ##                        <div class="clearfix"> </div>
            var output = '';

            output += '<div class="mediaPost">';
            output += '<img src=' + data.picture + ' />';
            output += '<div class="mediaBox">';
            output += '<span class="title">' + data.name + '</span><br />';
            output += '<span class="subtitle">' + data.caption + '</span><br />';
            output += '<span class="message">' + data.description + '</span>';
            output += '</div>';
            output += '</div>';

            return output;
        },

        /******************************************************************************************************
         * Helper Function
         ******************************************************************************************************/

        exists: function(data){
            if(!data || data==null || data=='undefined' || typeof(data)=='undefined') return false;
            else return true;
        }
    }
}


$("#userPageBox").ready(function() {
    var id = $("#userPageBox").attr('title');
    PANDOX.FACEBOOK.getFeed(id);
});