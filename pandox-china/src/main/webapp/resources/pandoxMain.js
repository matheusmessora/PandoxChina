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
                        $.each(response.data, function(key, val) {
                            var $userPageBoxFeed = $("#userPageBoxFeed");
                            console.log(val);
                            $userPageBoxFeed.append(val.message);
                            $userPageBoxFeed.append("<br />");
                        });
                    }else {
                        console.log("FALHA no FEED..." + id);
                    }
                })
                .fail(function() {
                    console.log("Fails...");
                });
        }
    },

    API: {

        getUser: function(id) {

        }
    }
}


$("#userPageBox").ready(function() {
    var id = $("#userPageBox").attr('title');
    PANDOX.FACEBOOK.getFeed(id);
});